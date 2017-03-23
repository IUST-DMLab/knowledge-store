package ir.ac.iust.dml.kg.knowledge.store.access.mongo;


import ir.ac.iust.dml.kg.knowledge.commons.PagingList;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

/**
 * Utils for mongo query dao
 */
class DaoUtils {
    @SuppressWarnings("unchecked")
    static <T> PagingList<T> paging(MongoOperations op, Class<T> clazz, Query query, int page, int pageSize) {
        if (pageSize > 0) {
            query.with(new PageRequest(page, pageSize));
            final List<T> list = op.find(query, clazz);
            final long total = op.count(query, clazz);
            return new PagingList(list, page, pageSize, total);
        } else {
            return new PagingList<T>(op.find(query, clazz));
        }
    }

}
