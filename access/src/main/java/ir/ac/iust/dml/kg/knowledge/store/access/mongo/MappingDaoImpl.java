package ir.ac.iust.dml.kg.knowledge.store.access.mongo;

import ir.ac.iust.dml.kg.knowledge.commons.PagingList;
import ir.ac.iust.dml.kg.knowledge.store.access.dao.IMappingDao;
import ir.ac.iust.dml.kg.knowledge.store.access.entities.TemplateMapping;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

/**
 * impl {@link IMappingDao}
 */
@Repository
public class MappingDaoImpl implements IMappingDao {
    @Autowired
    private MongoOperations op;


    @Override
    public void write(TemplateMapping... mappings) {
        for (TemplateMapping m : mappings) {
            m.setModificationEpoch(System.currentTimeMillis());
            op.save(m);
        }
    }

    @Override
    public void delete(TemplateMapping... mappings) {
        for (TemplateMapping m : mappings)
            op.remove(m);
    }

    @Override
    public TemplateMapping read(ObjectId id) {
        return op.findOne(
                new Query().addCriteria(Criteria.where("id").is(id)),
                TemplateMapping.class
        );
    }

    @Override
    public TemplateMapping read(String template) {
        return op.findOne(
                new Query().addCriteria(Criteria.where("template").is(template)),
                TemplateMapping.class
        );
    }

    @Override
    public PagingList<TemplateMapping> readTemplate(Boolean hasTemplateMapping, Boolean hasPropertyMapping, int page, int pageSize) {
        final Query query = new Query();
        if (hasTemplateMapping != null)
            query.addCriteria(Criteria.where("rules").exists(hasTemplateMapping));
        if (hasPropertyMapping != null)
            query.addCriteria(Criteria.where("properties.rules").exists(hasPropertyMapping));
        return DaoUtils.paging(op, TemplateMapping.class, query, page, pageSize);
    }
}
