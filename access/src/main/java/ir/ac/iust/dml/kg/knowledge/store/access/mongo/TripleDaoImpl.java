package ir.ac.iust.dml.kg.knowledge.store.access.mongo;

import ir.ac.iust.dml.kg.knowledge.commons.PagingList;
import ir.ac.iust.dml.kg.knowledge.commons.Utils;
import ir.ac.iust.dml.kg.knowledge.store.access.dao.ITripleDao;
import ir.ac.iust.dml.kg.knowledge.store.access.entities.Triple;
import ir.ac.iust.dml.kg.knowledge.store.access.entities.TripleState;
import ir.ac.iust.dml.kg.knowledge.store.access.stats.KeyCount;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * impl {@link ITripleDao}
 */
@Repository
public class TripleDaoImpl implements ITripleDao {
    @Autowired
    private MongoOperations op;

    @Override
    public void write(Triple... triples) {
        for (Triple triple : triples)
            op.save(triple);
    }

    @Override
    public void delete(Triple... triples) {
        for (Triple triple : triples) {
            op.remove(triple);
        }
    }

    @Override
    public Triple read(ObjectId id) {
        return op.findOne(
                new Query().addCriteria(Criteria.where("id").is(id)),
                Triple.class
        );
    }

    @Override
    public Triple read(String context, String subject, String predicate, String object) {
        return op.findOne(
                new Query().addCriteria(Criteria.where("context").is(context))
                        .addCriteria(Criteria.where("subject").is(subject))
                        .addCriteria(Criteria.where("predicate").is(predicate))
                        .addCriteria(Criteria.where("object.value").is(object)),
                Triple.class
        );
    }

    @Override
    public PagingList<Triple> search(String context, String subject, String predicate, String object, int page, int pageSize) {
        final Query query = new Query();
        if (context != null)
            query.addCriteria(Criteria.where("context").is(context));
        if (subject != null)
            query.addCriteria(Criteria.where("subject").is(subject));
        if (predicate != null)
            query.addCriteria(Criteria.where("predicate").is(predicate));
        if (object != null)
            query.addCriteria(Criteria.where("object.value").regex(object));
        return DaoUtils.paging(op, Triple.class, query, page, pageSize);
    }

    @Override
    public PagingList<Triple> read(TripleState state, Long after, int page, int pageSize) {
        final Query query = new Query();
        if (state != null)
            query.addCriteria(Criteria.where("state").is(state));
        if (after != null)
            query.addCriteria(Criteria.where("modificationEpoch").gte(after));
        return DaoUtils.paging(op, Triple.class, query, page, pageSize);
    }

    @Override
    public List<Triple> randomTripleForExpert(String notModule, String notExpert, int count) {
        final Query query = new Query()
                .addCriteria(Criteria.where("votes.module").ne(notModule))
                .addCriteria(Criteria.where("votes.expert").ne(notExpert))
                .addCriteria(Criteria.where("state").is(TripleState.None))
                .with(new Sort(Sort.Direction.ASC, "subject"));
        final int total = (int) op.count(new Query(), Triple.class);
        final List<Triple> cs = new ArrayList<>();
        final Set<Integer> randomIndexes = Utils.randomIndex(count, total);
        for (int index : randomIndexes) {
            final PageRequest pageRequest = new PageRequest(index, 10);
            query.with(pageRequest);
            final List<Triple> list = op.find(query, Triple.class);
            cs.addAll(list);
            if (cs.size() >= count)
                break;
        }
        return cs;
    }

    @Override
    public List<Triple> randomSubjectForExpert(String isSourceModule, String neModule, String neExpert, String subject) {
        final PagingList<KeyCount> subjects = searchSubjectForExpert(isSourceModule, neModule, neExpert, subject, 0, 1);
        if (subjects.getTotalSize() == 0) return new ArrayList<>();
        final int index = Utils.randomGenerator.nextInt((int) subjects.getTotalSize());
        final PagingList<KeyCount> selectedSubject = searchSubjectForExpert(isSourceModule, neModule, neExpert, subject, index, 1);
        final Query query = new Query().addCriteria(Criteria.where("subject").is(selectedSubject.getData().get(0).getId()));
        if (neModule != null)
            query.addCriteria(Criteria.where("votes.module").ne(neModule));
        if (neExpert != null)
            query.addCriteria(Criteria.where("votes.expert").ne(neExpert));
        query.addCriteria(Criteria.where("state").is(TripleState.None));
        return op.find(query, Triple.class);
    }

    @Override
    public PagingList<KeyCount> searchSubjectForExpert(String isSourceModule, String neModule, String neExpert, String subject, int page, int pageSize) {
        final List<AggregationOperation> operations = new ArrayList<>();
        if (isSourceModule != null)
            operations.add(Aggregation.match(Criteria.where("sources.module").is(isSourceModule)));
        if (neModule != null)
            operations.add(Aggregation.match(Criteria.where("votes.module").ne(neModule)));
        if (neExpert != null)
            operations.add(Aggregation.match(Criteria.where("votes.expert").ne(neExpert)));
        if (subject != null)
            operations.add(Aggregation.match(Criteria.where("subject").regex(subject)));
        operations.add(Aggregation.group("subject").count().as("count"));
        return DaoUtils
                .aggregate(op, Triple.class, KeyCount.class, page, pageSize,
                        operations.toArray(new AggregationOperation[operations.size()]));
    }
}
