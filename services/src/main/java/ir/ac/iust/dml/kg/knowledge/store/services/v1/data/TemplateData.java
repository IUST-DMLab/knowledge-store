package ir.ac.iust.dml.kg.knowledge.store.services.v1.data;

import io.swagger.annotations.ApiModelProperty;
import ir.ac.iust.dml.kg.knowledge.store.access.entities.PropertyMapping;
import ir.ac.iust.dml.kg.knowledge.store.access.entities.TemplateMapping;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

/**
 * Data for define template
 */
@XmlType(name = "TemplateData")
public class TemplateData {
    @NotNull
    @NotEmpty
    @ApiModelProperty(required = true, example = "شخص")
    private String template;
    @Valid
    private List<PropertyData> properties;
    private List<MapRuleData> rules;

    public TemplateMapping fill(TemplateMapping mapping) {
        if (mapping == null)
            mapping = new TemplateMapping(template);
        else
            assert template.equals(mapping.getTemplate());
        for (MapRuleData r : rules)
            mapping.getRules().add(r.fill(null));
        for (PropertyData property : properties) {
            PropertyMapping old = mapping.getProperties().get(property.getProperty());
            if (old == null) {
                old = new PropertyMapping(template, property.getProperty());
                mapping.getProperties().put(property.getProperty(), old);
            }
            property.fill(old);
        }
        return mapping;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public List<PropertyData> getProperties() {
        return properties;
    }

    public void setProperties(List<PropertyData> properties) {
        this.properties = properties;
    }

    public List<MapRuleData> getRules() {
        return rules;
    }

    public void setRules(List<MapRuleData> rules) {
        this.rules = rules;
    }
}
