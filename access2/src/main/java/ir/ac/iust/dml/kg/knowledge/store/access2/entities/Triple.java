package ir.ac.iust.dml.kg.knowledge.store.access2.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ir.ac.iust.dml.kg.knowledge.core.TypedValue;
import ir.ac.iust.dml.kg.knowledge.core.ValueType;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.xml.bind.annotation.XmlType;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * data class for triples
 * http://194.225.227.161:8081/browse/KG-180
 */
@XmlType(name = "Triple", namespace = "http://kg.dml.iust.ac.ir")
@Document(collection = "triples")
@CompoundIndexes({
        @CompoundIndex(name = "triple_index", def = "{'context': 1, 'subject' : 2, 'predicate' : 3, 'object': 4}")
})
public class Triple {
    @Id
    @JsonIgnore
    private ObjectId id;
    private String context;
    private String subject;
    private String predicate;
    private String object;
    private TypedValue value;
    private Map<String, TypedValue> properties;
    private Set<Source> sources;
    private long creationEpoch;
    private long modificationEpoch;
    private TripleState state;
    private Set<ExpertVote> votes;

    public Triple() {
    }

    public Triple(String context, String subject, String predicate, String object) {
        this.context = context;
        this.subject = subject;
        this.predicate = predicate;
        this.object = object;
    }

    public Triple(String context, String subject, String predicate, TypedValue value) {
        this.context = context;
        this.subject = subject;
        if (value.getType() != ValueType.Resource)
            this.value = value;
        else
            this.object = value.getValue();
        this.predicate = predicate;
        this.creationEpoch = this.modificationEpoch = System.currentTimeMillis();
        this.state = TripleState.None;
    }

    public String getIdentifier() {
        return id.toString();
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

    public TypedValue getValue() {
        return value;
    }

    public void setValue(TypedValue value) {
        this.value = value;
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

    public TripleState getState() {
        return state;
    }

    public void setState(TripleState state) {
        this.state = state;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public Set<ExpertVote> getVotes() {
        if (votes == null) votes = new HashSet<>();
        return votes;
    }

    public void setVotes(Set<ExpertVote> votes) {
        this.votes = votes;
    }

    public Map<String, TypedValue> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, TypedValue> properties) {
        this.properties = properties;
    }
}

