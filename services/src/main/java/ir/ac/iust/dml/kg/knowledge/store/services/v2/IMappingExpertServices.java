package ir.ac.iust.dml.kg.knowledge.store.services.v2;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import ir.ac.iust.dml.kg.knowledge.commons.PagingList;
import ir.ac.iust.dml.kg.knowledge.store.access2.entities.PropertyMapping;
import ir.ac.iust.dml.kg.knowledge.store.access2.entities.TemplateMapping;
import ir.ac.iust.dml.kg.knowledge.store.services.v2.data.MapRuleData;
import ir.ac.iust.dml.kg.knowledge.store.services.v2.data.TemplateData;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Farsi Knowledge Graph Project
 * Iran University of Science and Technology (Year 2017)
 * Developed by HosseiN Khademi khaledi
 * <p>
 * Service to define mappings
 */
@WebService
@Path("/v2/mappings/experts")
@Api("/v2/mappings/experts")
public interface IMappingExpertServices {
    @POST
    @Path("/properties")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @WebMethod
    @ApiOperation(value = "Insert or update template definition")
    PagingList<PropertyMapping> searchProperty(
            @WebParam(name = "template") @QueryParam("template") String template,
            @WebParam(name = "property") @QueryParam("property") String property,
            @WebParam(name = "page") @QueryParam("page") int page,
            @WebParam(name = "pageSize") @QueryParam("pageSize") int pageSize
    );

    @POST
    @Path("/templates")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @WebMethod
    @ApiOperation(value = "Insert or update template definition")
    PagingList<TemplateMapping> searchTemplate(
            @WebParam(name = "template") @QueryParam("template") String template,
            @WebParam(name = "page") @QueryParam("page") int page,
            @WebParam(name = "pageSize") @QueryParam("pageSize") int pageSize
    );

    @GET
    @Path("/predicates")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @WebMethod
    @ApiOperation(value = "Insert or update template definition")
    List<String> predicates(@WebParam(name = "keyword") @QueryParam("keyword") String keyword);


    @POST
    @Path("/insert")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @WebMethod
    @ApiOperation(value = "Insert or update template definition")
    TemplateData insert(
            @WebParam(name = "template") @QueryParam("template") String template,
            @WebParam(name = "property") @QueryParam("property") String property,
            @Valid MapRuleData data);

    @POST
    @Path("/delete")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @WebMethod
    @ApiOperation(value = "Insert or update template definition")
    TemplateData delete(
            @WebParam(name = "template") @QueryParam("template") String template,
            @WebParam(name = "property") @QueryParam("property") String property,
            @Valid MapRuleData data);


}
