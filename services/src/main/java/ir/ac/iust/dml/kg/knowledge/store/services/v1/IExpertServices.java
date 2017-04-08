package ir.ac.iust.dml.kg.knowledge.store.services.v1;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import ir.ac.iust.dml.kg.knowledge.store.access.entities.Triple;
import ir.ac.iust.dml.kg.knowledge.store.access.entities.Vote;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Rest: /rs/v1/experts
 * SOA: /ws/v1/experts
 */
@WebService
@Path("/v1/experts")
@Api("/v1/experts")
public interface IExpertServices {
    @GET
    @Path("/triples")
    @Produces(MediaType.APPLICATION_JSON)
    @WebMethod
    @ApiOperation(value = "Get triples for expert ")
    List<Triple> triples(@WebParam(name = "module") @QueryParam("module") String module,
                         @WebParam(name = "expert") @QueryParam("expert") String expert,
                         @WebParam(name = "count") @QueryParam("count") int count);

    @POST
    @Path("/vote")
    @Produces(MediaType.APPLICATION_JSON)
    @WebMethod
    @ApiOperation(value = "Vote on triple")
    Boolean vote(@WebParam(name = "identifier") @QueryParam("identifier") String identifier,
                 @WebParam(name = "module") @QueryParam("module") String module,
                 @WebParam(name = "expert") @QueryParam("expert") String expert,
                 @WebParam(name = "vote") @QueryParam("vote") Vote vote);
}
