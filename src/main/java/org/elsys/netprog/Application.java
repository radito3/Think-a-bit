package org.elsys.netprog;

import org.glassfish.jersey.logging.LoggingFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.server.TracingConfig;
import org.glassfish.jersey.server.mvc.MvcFeature;
import org.glassfish.jersey.server.mvc.jsp.JspMvcFeature;

import javax.ws.rs.ApplicationPath;

@ApplicationPath("/Think-a-bit")
public class Application extends ResourceConfig {

    public Application() {

        packages(HelloWorld.class.getPackage().getName());

        register(JspMvcFeature.class);

        register(LoggingFeature.class);

        property(ServerProperties.TRACING, TracingConfig.ON_DEMAND.name());

        property(JspMvcFeature.TEMPLATE_BASE_PATH, "WEB-INF/views");

//        this.register(MvcFeature.class);
//        this.property(MvcFeature.TEMPLATE_BASE_PATH, "WEB-INF/views");
//        this.register(HelloWorld.class);
    }
}
