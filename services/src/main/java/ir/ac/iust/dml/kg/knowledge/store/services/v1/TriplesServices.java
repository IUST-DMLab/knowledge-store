package ir.ac.iust.dml.kg.knowledge.store.services.v1;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import ir.ac.iust.dml.kg.knowledge.store.access.dao.ITripleDao;
import ir.ac.iust.dml.kg.knowledge.store.access.entities.Triple;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jws.WebService;

/**
 * impl {@link ITriplesServices}
 */
@Api("/v1/triples")
@WebService(endpointInterface = "ir.ac.iust.dml.kg.knowledge.store.services.v1.ITriplesServices")
public class TriplesServices implements ITriplesServices {
    @Autowired
    private ITripleDao dao;

    @Override
    @ApiOperation(value = "Insert or update triple")
    public Boolean insert(TripleData data) {
        final Triple oldTriple = dao.read(data.getSubject(), data.getPredicate(), data.getObject());
        final Triple newTriple = data.fill(oldTriple);
        dao.write(newTriple);
        return true;
    }
}
