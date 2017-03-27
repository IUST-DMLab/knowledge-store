package ir.ac.iust.dml.kg.knowledge.store.access.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

/**
 * data class for triples
 * http://194.225.227.161:8081/browse/KG-180
 */
@Document(collection = "triples")
@CompoundIndex(name = "triple_index", def = "{'context': 1, 'subject' : 2, 'predicate' : 3, 'object': 4}", unique = true)
public class Triple {
    @Id
    @JsonIgnore
    private ObjectId id;
    private String context;
    private String subject;
    private String object;
    private String predicate;
    private Set<Source> sources;
    private long creationEpoch;
    private long modificationEpoch;
    private String module;
    private ExpertState state;

    public Triple() {
    }

    public Triple(String context, String subject, String predicate, String object) {
        this.context = context;
        this.subject = subject;
        this.object = object;
        this.predicate = predicate;
        this.creationEpoch = this.modificationEpoch = System.currentTimeMillis();
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public String getPredicate() {
        return predicate;
    }

    public void setPredicate(String predicate) {
        this.predicate = predicate;
    }

    public Set<Source> getSources() {
        if (sources == null) sources = new HashSet<>();
        return sources;
    }

    public void setSources(Set<Source> sources) {
        this.sources = sources;
    }

    public long getCreationEpoch() {
        return creationEpoch;
    }

    public void setCreationEpoch(long creationEpoch) {
        this.creationEpoch = creationEpoch;
    }

    public long getModificationEpoch() {
        return modificationEpoch;
    }

    public void setModificationEpoch(long modificationEpoch) {
        this.modificationEpoch = modificationEpoch;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public ExpertState getState() {
        return state;
    }

    public void setState(ExpertState state) {
        this.state = state;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }
}

