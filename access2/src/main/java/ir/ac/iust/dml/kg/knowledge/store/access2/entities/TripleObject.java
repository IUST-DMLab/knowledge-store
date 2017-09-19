package ir.ac.iust.dml.kg.knowledge.store.access2.entities;

import ir.ac.iust.dml.kg.knowledge.core.TypedValue;
import ir.ac.iust.dml.kg.knowledge.core.ValueType;

import java.util.HashMap;
import java.util.Map;

/**
 * data class for triples
 * http://194.225.227.161:8081/browse/KG-180
 */

public class TripleObject extends TypedValue {
    private Map<String, TypedValue> properties;
    private Source source;
    private TripleState state;
    private Map<String, ExpertVote> votes;
    private long creationEpoch;
    private long modificationEpoch;

    public TripleObject() {
    }

    public TripleObject(TypedValue value, Map<String, TypedValue> properties, String module) {
        super(value.getType(), value.getValue(), value.getLang());
        this.creationEpoch = System.currentTimeMillis();
        this.properties = properties;
        this.source = new Source(module);
    }

    public TripleObject(ValueType type, String value) {
        this(type, value, null);
    }

    public TripleObject(ValueType type, String value, String lang) {
        super(type, value, lang);
        this.creationEpoch = System.currentTimeMillis();
    }

    public Map<String, TypedValue> getProperties() {
        if (properties == null)
            properties = new HashMap<>();
        return properties;
    }

    public void setProperties(Map<String, TypedValue> properties) {
        this.properties = properties;
    }

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public TripleState getState() {
        return state;
    }

    public void setState(TripleState state) {
        this.state = state;
    }

    public Map<String, ExpertVote> getVotes() {
        if (votes == null) votes = new HashMap<>();
        return votes;
    }

    public void setVotes(Map<String, ExpertVote> votes) {
        this.votes = votes;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        TripleObject that = (TripleObject) o;

        if (properties != null ? !properties.equals(that.properties) : that.properties != null) return false;
        return source != null ? source.equals(that.source) : that.source == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (properties != null ? properties.hashCode() : 0);
        result = 31 * result + (source != null ? source.hashCode() : 0);
        return result;
    }
}

