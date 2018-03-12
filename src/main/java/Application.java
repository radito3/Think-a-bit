import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;

//@ApplicationPath("/Thinkabit")
//public class Application extends javax.ws.rs.core.Application {
//}

@ApplicationPath("/Thinkabit")
public class Application extends ResourceConfig {
    public Application() {
        this.register(org.glassfish.jersey.server.mvc.MvcFeature.class);
    }
}