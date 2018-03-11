import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.server.mvc.Viewable;

@Path("/")
public class HelloWorld {

    @GET
    @Produces(MediaType.TEXT_HTML)
    public Response getHelloWorld() {
        Viewable viewable = new Viewable("/test.jsp");
        return Response.ok(viewable).build();
    }
}
