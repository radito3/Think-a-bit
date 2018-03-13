package org.elsys.netprog;

import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;

@ApplicationPath("/Thinkabit")
public class Application extends ResourceConfig {

    public Application() {
        this.register(org.glassfish.jersey.server.mvc.MvcFeature.class);
    }
}
