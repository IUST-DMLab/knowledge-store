package ir.ac.iust.dml.kg.knowledge.store.services.v2.impl;

import ir.ac.iust.dml.kg.knowledge.commons.PagingList;
import ir.ac.iust.dml.kg.knowledge.store.access2.dao.ISubjectDao;
import ir.ac.iust.dml.kg.knowledge.store.access2.entities.Subject;
import ir.ac.iust.dml.kg.knowledge.store.services.v2.ISubjectService;
import ir.ac.iust.dml.kg.knowledge.store.services.v2.ITriplesServices;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jws.WebService;

/**
 * impl {@link ITriplesServices}
 */
@WebService(endpointInterface = "ir.ac.iust.dml.kg.knowledge.store.services.v2.ISubjectService")
public class SubjectServices implements ISubjectService {
    @Autowired
    private ISubjectDao dao;

    @Override
    public Subject get(String context, String subject) {
        if (context == null) context = "http://fkg.iust.ac.ir/";
        return dao.read(context, subject);
    }

    @Override
    public PagingList<Subject> hasPredicate(String predicate, int page, int pageSize) {
        return dao.searchHasPredicate(predicate, page, pageSize);
    }

    @Override
    public PagingList<Subject> hasValue(String predicate, String object, int page, int pageSize) {
        return dao.searchHasValue(predicate, object, page, pageSize);
    }
}
