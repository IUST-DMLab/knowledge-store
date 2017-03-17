package ir.ac.iust.dml.kg.knowledge.store.services;

import javax.jws.WebService;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@WebService
@Path("/sayHello")
public interface HelloWorld {
    @GET
    @Path("/{a}")
    @Produces(MediaType.TEXT_PLAIN)
    String sayHi(@PathParam("a") String text);
}