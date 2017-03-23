package ir.ac.iust.dml.kg.knowledge.store.services.v1;

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
        final Triple oldTriple = dao.read(data.getSubject(), data.getPredicate(), data.getObject());
        final Triple newTriple = data.fill(oldTriple);
        dao.write(newTriple);
        return true;
    }
}
