package ir.ac.iust.dml.kg.knowledge.store.services.v1;

import ir.ac.iust.dml.kg.knowledge.commons.PagingList;
import ir.ac.iust.dml.kg.knowledge.store.access.dao.ITripleDao;
import ir.ac.iust.dml.kg.knowledge.store.access.entities.Triple;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jws.WebService;
import javax.validation.Valid;

/**
 * impl {@link ITriplesServices}
 */
@WebService(endpointInterface = "ir.ac.iust.dml.kg.knowledge.store.services.v1.ITriplesServices")
public class TriplesServices implements ITriplesServices {
    @Autowired
    private ITripleDao dao;

    @Override
    public Boolean insert(@Valid TripleData data) {
        if (data.getContext() == null) data.setContext("http://kg.dml.iust.ac.ir");
        final Triple oldTriple = dao.read(data.getContext(), data.getSubject(), data.getPredicate(), data.getObject());
        final Triple newTriple = data.fill(oldTriple);
        dao.write(newTriple);
        return true;
    }

    @Override
    public Triple triple(String context, String subject, String predicate, String object) {
        if (context == null) context = "http://kg.dml.iust.ac.ir";
        return dao.read(context, subject, predicate, object);
    }

    @Override
    public PagingList<Triple> search(String context, String subject, String predicate, String object, int page, int pageSize) {
        return dao.search(context, subject, predicate, object, page, pageSize);
    }
}
