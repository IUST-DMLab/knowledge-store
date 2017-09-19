package ir.ac.iust.dml.kg.knowledge.store.access2.entities;

import java.util.*;

/**
 * data class for encapsulate
 */
public class Source {
    private String module;
    private Integer version;
    private Set<String> urls;
    private Map<String, String> parameters;
    private Double precession;

    public Source() {
    }

    public Source(String module) {
        this.module = module;
    }

    public Source(String module, Integer version, List<String> urls, Map<String, String> parameters, Double precession) {
        this.module = module;
        this.version = version;
        this.urls = new HashSet<>();
        if (urls != null)
            this.urls.addAll(urls);
        this.parameters = parameters;
        this.precession = precession;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Set<String> getUrls() {
        if (urls == null)
            urls = new HashSet<>();
        return urls;
    }

    public void setUrls(Set<String> urls) {
        this.urls = urls;
    }

    public Map<String, String> getParameters() {
        if (parameters == null)
            parameters = new HashMap<>();
        return parameters;
    }

    public void setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    public Double getPrecession() {
        return precession;
    }

    public void setPrecession(Double precession) {
        this.precession = precession;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Source source = (Source) o;

        return module != null ? module.equals(source.module) : source.module == null;
    }

    @Override
    public int hashCode() {
        return module != null ? module.hashCode() : 0;
    }

    @Override
    public String toString() {
        final StringBuilder urlBuilder = new StringBuilder("[");
        urls.forEach(u -> urlBuilder.append(u).append(" "));
        urlBuilder.append("]");
        return String.format("Source{ %s@%s}", urlBuilder, module);
    }
}
