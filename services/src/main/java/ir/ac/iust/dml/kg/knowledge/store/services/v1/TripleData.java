package ir.ac.iust.dml.kg.knowledge.store.services.v1;


import cz.jirutka.validator.collection.constraints.EachURL;
import io.swagger.annotations.ApiModelProperty;
import ir.ac.iust.dml.kg.knowledge.store.access.entities.Source;
import ir.ac.iust.dml.kg.knowledge.store.access.entities.Triple;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlType;
import java.util.HashMap;
import java.util.List;

@SuppressWarnings("WeakerAccess")
@XmlType(name = "TripleData")
public class TripleData {
    @URL
    @ApiModelProperty(required = false, example = "http://kg.dml.iust.ac.ir")
    private String context;
    @NotNull
    @NotEmpty
    @URL
    @ApiModelProperty(required = true, example = "http://knowledgegraph.ir/Esteghlal_F.C.")
    private String subject;
    @NotNull
    @NotEmpty
    @URL
    @ApiModelProperty(required = true, example = "http://knowledgegraph.ir/Alireza_Mansourian")
    private String predicate;
    @NotNull
    @NotEmpty
    @ApiModelProperty(required = true, example = "http://knowledgegraph.ir/mananger")
    private String object;
    @NotNull
    @NotEmpty
    @ApiModelProperty(value = "Module that triples was extracted from it", required = true, example = "wikipedia/infobox")
    private String module;
    @NotNull
    @NotEmpty
    @EachURL
    @ApiModelProperty(value = "Page url that triples was extracted from it", required = true, example = "https://en.wikipedia.org/wiki/Esteghlal_F.C.")
    private List<String> urls;
    @ApiModelProperty(value = "Additional parameter that module used to extract data", required = false, example = "{version: 2}")
    private HashMap<String, String> parameters;
    private Double precession;

    Triple fill(Triple triple) {
        if (triple != null)
            assert subject.equals(triple.getSubject()) && object.equals(triple.getObject())
                    && predicate.equals(triple.getPredicate());
        else
            triple = new Triple(context, subject, predicate, object);

        boolean found = false;
        for (Source s : triple.getSources())
            if (s.getModule().equals(module)) {
                s.getUrls().addAll(urls);
                s.setPrecession(precession);
                if (parameters != null)
                    s.getParameters().putAll(parameters);
                found = true;
            }

        if (!found) triple.getSources().add(new Source(module, urls, parameters, precession));
        triple.setModificationEpoch(System.currentTimeMillis());

        return triple;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getPredicate() {
        return predicate;
    }

    public void setPredicate(String predicate) {
        this.predicate = predicate;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public List<String> getUrls() {
        return urls;
    }

    public void setUrls(List<String> urls) {
        this.urls = urls;
    }

    public HashMap<String, String> getParameters() {
        return parameters;
    }

    public void setParameters(HashMap<String, String> parameters) {
        this.parameters = parameters;
    }

    public Double getPrecession() {
        return precession;
    }

    public void setPrecession(Double precession) {
        this.precession = precession;
    }
}
