package ir.ac.iust.dml.kg.knowledge.store.access.entities;

/**
 * Mapped result
 */
public class MapRule {
    private String predicate;
    private String constant;
    private ValueType type;
    private String unit;
    private String transform;

    public MapRule() {
    }

    public MapRule(String predicate, String constant, ValueType type) {
        this.predicate = predicate;
        this.constant = constant;
        this.type = type;
    }

    public MapRule(String predicate, ValueType type) {
        this.predicate = predicate;
        this.type = type;
    }

    public String getPredicate() {
        return predicate;
    }

    public void setPredicate(String predicate) {
        this.predicate = predicate;
    }

    public String getConstant() {
        return constant;
    }

    public void setConstant(String constant) {
        this.constant = constant;
    }

    public ValueType getType() {
        return type;
    }

    public void setType(ValueType type) {
        this.type = type;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getTransform() {
        return transform;
    }

    public void setTransform(String transform) {
        this.transform = transform;
    }
}
