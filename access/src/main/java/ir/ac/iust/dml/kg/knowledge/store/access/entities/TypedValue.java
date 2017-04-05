package ir.ac.iust.dml.kg.knowledge.store.access.entities;

/**
 * TypedValue of object
 */
public class TypedValue {
    private ValueType type;
    private String value;
    private String lang;

    public TypedValue() {
    }

    public TypedValue(ValueType type, String value) {
        this.type = type;
        this.value = value;
    }

    public TypedValue(ValueType type, String value, String lang) {
        this.type = type;
        this.value = value;
        this.lang = lang;
    }

    public ValueType getType() {
        return type;
    }

    public void setType(ValueType type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }
}
