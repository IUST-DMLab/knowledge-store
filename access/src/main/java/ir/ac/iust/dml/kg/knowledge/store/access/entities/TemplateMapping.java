package ir.ac.iust.dml.kg.knowledge.store.access.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.xml.bind.annotation.XmlType;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Class for mapping for map template
 * title: is template mapping class
 */
@XmlType(name = "TemplateMapping", namespace = "http://kg.dml.iust.ac.ir")
@Document(collection = "template-mapping")
public class TemplateMapping {
    @Id
    @JsonIgnore
    private ObjectId id;
    @Indexed(unique = true)
    private String template;
    private Map<String, PropertyMapping> properties;
    private Set<MapRule> rules;
    private long creationEpoch;
    private long modificationEpoch;

    public TemplateMapping() {
    }

    public TemplateMapping(String template) {
        this.creationEpoch = System.currentTimeMillis();
        this.template = template;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public Map<String, PropertyMapping> getProperties() {
        if (this.properties == null)
            this.properties = new HashMap<>();
        return properties;
    }

    public void setProperties(Map<String, PropertyMapping> properties) {
        this.properties = properties;
    }

    public Set<MapRule> getRules() {
        if (this.rules == null)
            this.rules = new HashSet<>();
        return rules;
    }

    public void setRules(Set<MapRule> rules) {
        this.rules = rules;
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
        TemplateMapping that = (TemplateMapping) o;
        return template != null ? template.equals(that.template) : that.template == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (template != null ? template.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return String.format("%s", template);
    }
}
