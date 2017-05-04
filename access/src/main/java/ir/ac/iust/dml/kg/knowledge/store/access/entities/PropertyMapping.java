package ir.ac.iust.dml.kg.knowledge.store.access.entities;

import java.util.ArrayList;
import java.util.List;

/**
 * Map template/property to triple
 */
public class PropertyMapping {
    private String template;
    private String property;
    private Double weight;
    private List<MapRule> rules;

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public List<MapRule> getRules() {
        if (rules == null)
            rules = new ArrayList<>();
        return rules;
    }

    public void setRules(List<MapRule> rules) {
        this.rules = rules;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PropertyMapping that = (PropertyMapping) o;

        if (template != null ? !template.equals(that.template) : that.template != null) return false;
        return property != null ? property.equals(that.property) : that.property == null;
    }

    @Override
    public int hashCode() {
        int result = template != null ? template.hashCode() : 0;
        result = 31 * result + (property != null ? property.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return String.format("%s - %s", template, property);
    }
}
