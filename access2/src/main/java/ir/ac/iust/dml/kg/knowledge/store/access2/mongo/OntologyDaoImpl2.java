package ir.ac.iust.dml.kg.knowledge.store.access2.mongo;

import ir.ac.iust.dml.kg.knowledge.commons.MongoDaoUtils;
import ir.ac.iust.dml.kg.knowledge.commons.PagingList;
import ir.ac.iust.dml.kg.knowledge.store.access2.dao.IOntologyDao;
import ir.ac.iust.dml.kg.knowledge.store.access2.entities.Ontology;
import ir.ac.iust.dml.kg.knowledge.store.access2.entities.TripleState;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class OntologyDaoImpl2 implements IOntologyDao {
    private final MongoOperations op;

    @Autowired
    public OntologyDaoImpl2(@Qualifier("store2") MongoOperations op) {
        this.op = op;
    }

    @Override
    public void write(Ontology... ontology) {
        for(Ontology o:ontology) {
            o.setModificationEpoch(System.currentTimeMillis());
            op.save(o);
        }
    }

    @Override
    public void delete(Ontology... ontology) {
        for(Ontology o:ontology)
            op.remove(o);
    }

    @Override
    public Ontology read(ObjectId id) {
        return op.findById(id, Ontology.class);
    }

    @Override
    public Ontology read(String context, String subject, String predicate, String object) {
        return op.findOne(
                new Query().addCriteria(Criteria.where("context").is(context))
                        .addCriteria(Criteria.where("subject").is(subject))
                        .addCriteria(Criteria.where("predicate").is(predicate))
                        .addCriteria(Criteria.where("object.value").is(object)),
                Ontology.class
        );
    }

    @Override
    public PagingList<Ontology> search(String context, String subject, String predicate, String object, TripleState state, int page, int pageSize) {
        final Query query = new Query();
        if (context != null)
            query.addCriteria(Criteria.where("context").is(context));
        if (subject != null)
            query.addCriteria(Criteria.where("subject").is(subject));
        if (predicate != null)
            query.addCriteria(Criteria.where("predicate").is(predicate));
        if (object != null)
            query.addCriteria(Criteria.where("object.value").is(object));
        if (state != null)
            query.addCriteria(Criteria.where("state").is(state));
        return MongoDaoUtils.paging(op, Ontology.class, query, page, pageSize);
    }
}
