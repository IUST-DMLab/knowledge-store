package ir.ac.iust.dml.kg.knowledge.store.access.entities;

import org.eclipse.rdf4j.model.ValueFactory;
import org.eclipse.rdf4j.model.impl.SimpleValueFactory;
import org.eclipse.rdf4j.model.vocabulary.XMLSchema;

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

    @Override
    public String toString() {
        if (lang != null)
            return String.format("\"%s\"^^%s@%s}", value, type, lang);
        else
            return String.format("\"%s\"^^%s@%s}", value, type, lang);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TypedValue that = (TypedValue) o;

        return value != null ? value.equals(that.value) : that.value == null;
    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }

    public Object createValue() {
        final ValueFactory vf = SimpleValueFactory.getInstance();
        if (type != null)
            switch (type) {
                case Resource:
                    return vf.createIRI(value);
                case String:
                    return vf.createLiteral(value, lang);
                case Boolean:
                    return vf.createLiteral(value, XMLSchema.BOOLEAN);
                case Byte:
                    return vf.createLiteral(value, XMLSchema.BYTE);
                case Short:
                    return vf.createLiteral(value, XMLSchema.SHORT);
                case Integer:
                    return vf.createLiteral(value, XMLSchema.INTEGER);
                case Long:
                    return vf.createLiteral(value, XMLSchema.LONG);
                case Double:
                    return vf.createLiteral(value, XMLSchema.DOUBLE);
                case Float:
                    return vf.createLiteral(value, XMLSchema.FLOAT);

            }
        return vf.createLiteral(value);
    }
}
