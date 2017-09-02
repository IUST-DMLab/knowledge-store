package ir.ac.iust.dml.kg.knowledge.store.access2.dao;

import ir.ac.iust.dml.kg.knowledge.commons.PagingList;
import ir.ac.iust.dml.kg.knowledge.store.access2.entities.Ontology;
import org.bson.types.ObjectId;

/**
 * Interface for read and write triples
 */
public interface IOntologyDao {
    void write(Ontology... ontology);

    void delete(Ontology... ontology);

    Ontology read(ObjectId id);

    Ontology read(String context, String subject, String predicate, String object);

    PagingList<Ontology> search(String context, String subject, String predicate, String object, int page, int pageSize);
}
