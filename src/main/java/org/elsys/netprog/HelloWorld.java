package org.elsys.netprog;

import org.elsys.netprog.db.DatabaseUtil;
import org.elsys.netprog.game.UserManagement;
import org.elsys.netprog.model.User;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/test")
public class HelloWorld {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response getHelloWorld() {

        DatabaseUtil db = DatabaseUtil.getInstance();

//        User u = UserManagement.login("test", "test");

        UserManagement userManagement = new UserManagement();

        userManagement.register(new User(2, "two", "two"));

        return Response.ok().entity(db.getObject(s -> s.get(User.class, 2)).toString()).build();
    }
}
