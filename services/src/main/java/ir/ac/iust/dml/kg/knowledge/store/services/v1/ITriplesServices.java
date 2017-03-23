package ir.ac.iust.dml.kg.knowledge.store.services.v1;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.jws.WebService;
import javax.validation.Valid;
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
@Api("/v1/triples")
public interface ITriplesServices {
    @POST
    @Path("/insert")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Insert or update triple")
    Boolean insert(@Valid TripleData data);
}
