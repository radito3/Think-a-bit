package org.elsys.netprog;

import org.elsys.netprog.model.User;

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
        String val1 = "default";

        User user = new User();

        try {
            val = conn.getInfo();
            user.setId(2);
            user.setUserName("oneUser");
            user.setPassword("onePass");
            conn.save(user);
            val1 = conn.getInfo();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Response.ok().entity("first check: " + val + "\nsecond check: " + val1).build();
    }
}
