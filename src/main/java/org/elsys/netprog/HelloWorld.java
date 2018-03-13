package org.elsys.netprog;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.mvc.Template;
import org.glassfish.jersey.server.mvc.Viewable;

import java.util.HashMap;
import java.util.Map;

@Path("/test")
@Template
@Produces(MediaType.TEXT_HTML)
public class HelloWorld {

    @GET
    public Viewable getHelloWorld() {

        Map<String, String> test = new HashMap<>();
        test.put("key", "val");
        test.put("one", "two");

        return new Viewable("index.jsp", test);
    }
}
