package ir.ac.iust.dml.kg.knowledge.store.services.v1;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import ir.ac.iust.dml.kg.knowledge.commons.PagingList;
import ir.ac.iust.dml.kg.knowledge.store.access.entities.Triple;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.validation.Valid;
import javax.ws.rs.*;
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
    @WebMethod
    @ApiOperation(value = "Insert or update triple")
    Boolean insert(@Valid TripleData data);

    @GET
    @Path("/triple")
    @Produces(MediaType.APPLICATION_JSON)
    @WebMethod
    @ApiOperation(value = "Return triples by (context, subject, predicate, object)")
    Triple triple(@ApiParam(required = false, example = "http://kg.dml.iust.ac.ir")
                  @WebParam(name = "context") @QueryParam("context") String context,
                  @ApiParam(required = true, example = "http://url.com/subject")
                  @WebParam(name = "subject") @QueryParam("subject") String subject,
                  @ApiParam(required = true, example = "http://url.com/predicate")
                  @WebParam(name = "predicate") @QueryParam("predicate") String predicate,
                  @ApiParam(required = true, example = "http://url.com/object")
                  @WebParam(name = "object") @QueryParam("object") String object);

    @GET
    @Path("/search")
    @Produces(MediaType.APPLICATION_JSON)
    @WebMethod
    @ApiOperation(value = "Search triples by (context, subject, predicate, object)")
    PagingList<Triple> search(@WebParam(name = "context") @QueryParam("context") String context,
                              @WebParam(name = "subject") @QueryParam("subject") String subject,
                              @WebParam(name = "predicate") @QueryParam("predicate") String predicate,
                              @WebParam(name = "object") @QueryParam("object") String object,
                              @WebParam(name = "page") @QueryParam("page") int page,
                              @WebParam(name = "pageSize") @QueryParam("pageSize") int pageSize);
}
