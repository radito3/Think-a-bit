package org.elsys.netprog;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.mvc.Template;
import org.glassfish.jersey.server.mvc.Viewable;

@Path("/w")
public class HelloWorld {

    @GET
    @Produces(MediaType.TEXT_HTML)
//    @Template(name="index.jsp")
    public Response getHelloWorld() {
        Viewable viewable = new Viewable("/index.jsp");
        return Response.ok(viewable).build();
    }
}
