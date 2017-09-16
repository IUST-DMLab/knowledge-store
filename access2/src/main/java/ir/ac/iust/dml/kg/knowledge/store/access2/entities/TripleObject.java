package ir.ac.iust.dml.kg.knowledge.store.access2.entities;

import ir.ac.iust.dml.kg.knowledge.core.TypedValue;

import java.util.HashMap;
import java.util.Map;

/**
 * data class for triples
 * http://194.225.227.161:8081/browse/KG-180
 */

public class TripleObject {
    private TypedValue value;
    private Map<String, TypedValue> properties;
    private Source source;
    private TripleState state;
    private Map<String, ExpertVote> votes;
    private long creationEpoch;
    private long modificationEpoch;

    public TripleObject() {
    }

    public TripleObject(TypedValue value) {
        this.value = value;
        this.creationEpoch = System.currentTimeMillis();
    }

    public TypedValue getValue() {
        return value;
    }

    public void setValue(TypedValue value) {
        this.value = value;
    }

    public Map<String, TypedValue> getProperties() {
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
}

