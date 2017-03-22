package ir.ac.iust.dml.kg.knowledge.store.services.v1;

import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Rest: /rs/v1/triples
 * SOA: /ws/v1/triples
 */
@WebService
@Path("/v1/triples")
public interface ITriplesServices {
    @POST
    @Path("/insert")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Boolean insert(TripleData data);
}
