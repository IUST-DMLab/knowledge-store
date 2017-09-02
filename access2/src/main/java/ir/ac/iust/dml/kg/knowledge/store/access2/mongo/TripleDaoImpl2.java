package ir.ac.iust.dml.kg.knowledge.store.access2.mongo;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import ir.ac.iust.dml.kg.knowledge.commons.MongoDaoUtils;
import ir.ac.iust.dml.kg.knowledge.commons.PagingList;
import ir.ac.iust.dml.kg.knowledge.store.access2.dao.ITripleDao;
import ir.ac.iust.dml.kg.knowledge.store.access2.entities.Triple;
import ir.ac.iust.dml.kg.knowledge.store.access2.entities.TripleState;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * impl {@link ITripleDao}
 */
@SuppressWarnings("Duplicates")
@Repository
public class TripleDaoImpl2 implements ITripleDao {
    private final MongoOperations op;

    @Autowired
    public TripleDaoImpl2(@Qualifier("store2") MongoOperations op) {
        this.op = op;
    }

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
    public List<Triple> read(String context, String subject, String predicate) {
        return op.find(
                new Query().addCriteria(Criteria.where("context").is(context))
                        .addCriteria(Criteria.where("subject").is(subject))
                        .addCriteria(Criteria.where("predicate").is(predicate)),
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
            query.addCriteria(Criteria.where("object").is(object));
        return MongoDaoUtils.paging(op, Triple.class, query, page, pageSize);
    }

    @Override
    public PagingList<Triple> read(TripleState state, Long after, int page, int pageSize) {
        final Query query = new Query();
        if (state != null)
            query.addCriteria(Criteria.where("state").is(state));
        if (after != null)
            query.addCriteria(Criteria.where("modificationEpoch").gte(after));
        return MongoDaoUtils.paging(op, Triple.class, query, page, pageSize);
    }

    @Override
    public List<Triple> randomSubjectForExpert(String isSourceModule, String neModule, String neExpert, String subject,
                                               Integer size) {
        final List<BasicDBObject> sampleQuery = new ArrayList<>();
        if (subject != null)
            sampleQuery.add(new BasicDBObject("$match", new BasicDBObject("subject", subject)));
        if (isSourceModule != null)
            sampleQuery.add(new BasicDBObject("$match", new BasicDBObject("sources.module", isSourceModule)));
        if (size != null)
            sampleQuery.add(new BasicDBObject("$sample", new BasicDBObject("size", size)));
        if (neModule != null)
            sampleQuery.add(new BasicDBObject("$match", new BasicDBObject("votes.module", new BasicDBObject("$ne", neModule))));
        if (neExpert != null)
            sampleQuery.add(new BasicDBObject("$match", new BasicDBObject("votes.expert", new BasicDBObject("$ne", neExpert))));
        sampleQuery.add(new BasicDBObject("$match", new BasicDBObject("state", TripleState.None.toString())));
        sampleQuery.add(new BasicDBObject("$sample", new BasicDBObject("size", 1)));

        final Iterator<DBObject> subjectAggregate = op.getCollection("triples").aggregate(sampleQuery).results().iterator();
        if (!subjectAggregate.hasNext()) return new ArrayList<>();
        final Triple selectedSubject = op.getConverter().read(Triple.class, subjectAggregate.next());
        if (selectedSubject == null) return new ArrayList<>();
        final Query query = new Query().addCriteria(Criteria.where("subject").is(selectedSubject.getSubject()));
        if (isSourceModule != null)
            query.addCriteria(Criteria.where("sources.module").is(isSourceModule));
        if (neModule != null)
            query.addCriteria(Criteria.where("votes.module").ne(neModule));
        if (neExpert != null)
            query.addCriteria(Criteria.where("votes.expert").ne(neExpert));
        query.addCriteria(Criteria.where("state").is(TripleState.None));
        return op.find(query, Triple.class);
    }

}
