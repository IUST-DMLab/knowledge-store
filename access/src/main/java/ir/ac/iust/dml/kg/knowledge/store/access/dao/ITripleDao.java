package ir.ac.iust.dml.kg.knowledge.store.access.dao;

import ir.ac.iust.dml.kg.knowledge.commons.PagingList;
import ir.ac.iust.dml.kg.knowledge.store.access.entities.Triple;
import ir.ac.iust.dml.kg.knowledge.store.access.entities.TripleState;
import ir.ac.iust.dml.kg.knowledge.store.access.stats.KeyCount;
import org.bson.types.ObjectId;

import java.util.List;

/**
 * Interface for read and write triples
 */
public interface ITripleDao {
    void write(Triple... triples);

    void delete(Triple... triples);

    Triple read(ObjectId id);

    Triple read(String context, String subject, String predicate, String object);

    List<Triple> randomTripleForExpert(String notModule, String notExpert, int count);

    List<Triple> randomSubjectForExpert(String isSourceModule, String neModule, String neExpert, String subjectRegex,
                                        String subject, Integer size);

    PagingList<KeyCount> searchSubjectForExpert(String isSourceModule, String neModule, String neExpert, String subject, int page, int pageSize);

    PagingList<Triple> search(String context, String subject, String predicate, String object, int page, int pageSize);

    PagingList<Triple> read(TripleState state, Long after, int page, int pageSize);
}
