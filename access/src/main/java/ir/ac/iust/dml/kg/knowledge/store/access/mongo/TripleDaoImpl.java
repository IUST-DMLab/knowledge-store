package ir.ac.iust.dml.kg.knowledge.store.access.mongo;

import ir.ac.iust.dml.kg.knowledge.commons.PagingList;
import ir.ac.iust.dml.kg.knowledge.store.access.dao.ITripleDao;
import ir.ac.iust.dml.kg.knowledge.store.access.entities.Triple;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

/**
 * impl {@link ITripleDao}
 */
@Repository
public class TripleDaoImpl implements ITripleDao {
    @Autowired
    private MongoOperations op;

    @Override
    public void write(Triple... triples) {
        for (Triple triple : triples) {
            op.save(triple);
        }
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
    public PagingList<Triple> search(String subject, String predicate, String object, int page, int pageSize) {
        final Query query = new Query();
        if (subject != null)
            query.addCriteria(Criteria.where("subject").is(subject));
        if (predicate != null)
            query.addCriteria(Criteria.where("predicate").is(predicate));
        if (object != null)
            query.addCriteria(Criteria.where("object").is(object));
        return DaoUtils.paging(op, Triple.class, query, page, pageSize);
    }
}
