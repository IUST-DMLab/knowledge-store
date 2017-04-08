package ir.ac.iust.dml.kg.knowledge.store.services.v1;

import ir.ac.iust.dml.kg.knowledge.store.access.dao.ITripleDao;
import ir.ac.iust.dml.kg.knowledge.store.access.entities.ExpertVote;
import ir.ac.iust.dml.kg.knowledge.store.access.entities.Triple;
import ir.ac.iust.dml.kg.knowledge.store.access.entities.Vote;
import ir.ac.iust.dml.kg.knowledge.store.services.ExpertLogic;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jws.WebService;
import java.util.List;

/**
 * impl {@link IExpertServices}
 */
@WebService(endpointInterface = "ir.ac.iust.dml.kg.knowledge.store.services.v1.IExpertServices")
public class ExpertServices implements IExpertServices {
    @Autowired
    private ITripleDao dao;

    @Override
    public List<Triple> triples(String module, String expert, int count) {
        return dao.randomTripleForExpert(module, expert, count);
    }

    @Override
    public Boolean vote(String identifier, String module, String expert, Vote vote) {
        final Triple triple = dao.read(new ObjectId(identifier));
        if (triple == null) return false;
        triple.getVotes().add(new ExpertVote(module, expert, vote));
        triple.setState(ExpertLogic.makeState(triple.getVotes()));
        return true;
    }
}
