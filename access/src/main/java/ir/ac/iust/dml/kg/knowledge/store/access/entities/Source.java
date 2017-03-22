package ir.ac.iust.dml.kg.knowledge.store.access.entities;

/**
 * data class for encapsulate
 */
public class Source {
    private String module;
    private String url;

    public Source() {
    }

    public Source(String module, String url) {
        this.module = module;
        this.url = url;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Source source = (Source) o;

        if (module != null ? !module.equals(source.module) : source.module != null) return false;
        return url != null ? url.equals(source.url) : source.url == null;
    }

    @Override
    public int hashCode() {
        int result = module != null ? module.hashCode() : 0;
        result = 31 * result + (url != null ? url.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return String.format("from %s by %s", url, module);
    }
}
