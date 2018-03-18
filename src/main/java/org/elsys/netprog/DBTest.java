package org.elsys.netprog;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

@Path("/dbtest")
public class DBTest {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response getDBInfo() {
        DatabaseConnector conn = DatabaseConnector.getInstance();

        String val = "default";

        try {
            val = conn.getInfo();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Response.ok().entity(val).build();
    }
}
