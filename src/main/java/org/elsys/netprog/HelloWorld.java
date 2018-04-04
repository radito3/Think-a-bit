package org.elsys.netprog;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Path("/test")
public class HelloWorld {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response getHelloWorld() {
//        String str;

        try {
//            str = "str";
            throw new IOException("test");
        } catch (IOException e) {
            Logger.getLogger(HelloWorld.class.getName()).log(Level.FINE, e.getMessage()); //doesn't work for some reason
            System.err.println(e.getMessage());
            return Response.serverError().entity(e.getMessage()).build();
        }

//        return Response.ok().entity(str).build();
    }
}
