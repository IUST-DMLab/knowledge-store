package ir.ac.iust.dml.kg.knowledge.commons;

import java.util.List;

/**
 * Class for paging list
 */
public class PagingList<T> {
    private final List<T> data;
    private final int page;
    private final int pageSize;
    private final long pageCount;
    private final long totalSize;

    public PagingList(List<T> data) {
        this.data = data;
        this.page = this.pageSize = 0;
        this.totalSize = data.size();
        this.pageCount = 1;
    }

    public PagingList(List<T> data, int page, int pageSize, long totalSize) {
        this.data = data;
        this.page = page;
        this.pageSize = pageSize;
        this.totalSize = totalSize;
        this.pageCount = totalSize % pageSize != 0 ? totalSize / pageSize + 1 : totalSize / pageSize;
    }

    public List<T> getData() {
        return data;
    }

    public int getPage() {
        return page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public long getTotalSize() {
        return totalSize;
    }

    public long getPageCount() {
        return pageCount;
    }
}
