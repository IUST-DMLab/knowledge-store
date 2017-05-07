package ir.ac.iust.dml.kg.knowledge.store.access.dao;

import ir.ac.iust.dml.kg.knowledge.commons.PagingList;
import ir.ac.iust.dml.kg.knowledge.store.access.entities.TemplateMapping;
import org.bson.types.ObjectId;

/**
 * interface for read and write TemplateMapping
 */
public interface IMappingDao {
    void write(TemplateMapping... mappings);

    void delete(TemplateMapping... mappings);

    TemplateMapping read(ObjectId id);

    TemplateMapping read(String title);

    PagingList<TemplateMapping> readTemplate(Boolean hasTemplateMapping, Boolean hasPropertyMapping, int page, int pageSize);

    PagingList<TemplateMapping> searchTemplate(String template, int page, int pageSize);
}
