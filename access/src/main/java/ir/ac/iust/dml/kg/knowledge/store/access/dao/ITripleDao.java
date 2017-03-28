package ir.ac.iust.dml.kg.knowledge.store.access.dao;

import ir.ac.iust.dml.kg.knowledge.commons.PagingList;
import ir.ac.iust.dml.kg.knowledge.store.access.entities.Triple;
import org.bson.types.ObjectId;

/**
 * Interface for read and write triples
 */
public interface ITripleDao {
    void write(Triple... triples);

    void delete(Triple... triples);

    Triple read(ObjectId id);

    Triple read(String context, String subject, String predicate, String object);

    PagingList<Triple> search(String context, String subject, String predicate, String object, int page, int pageSize);
}
