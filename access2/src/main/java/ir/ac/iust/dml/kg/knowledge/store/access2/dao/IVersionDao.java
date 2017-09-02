package ir.ac.iust.dml.kg.knowledge.store.access2.dao;

import ir.ac.iust.dml.kg.knowledge.store.access2.entities.Version;
import org.bson.types.ObjectId;

import java.util.List;

/**
 * Interface for read and write versions
 */
public interface IVersionDao {
    void write(Version... versions);

    void delete(Version... versions);

    Version read(ObjectId id);

    Version readByModule(String module);

    List<Version> readAll();
}
