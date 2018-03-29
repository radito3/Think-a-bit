package org.elsys.netprog;

import org.elsys.netprog.db.DatabaseUtil;
import org.elsys.netprog.game.UserManagement;
import org.elsys.netprog.model.User;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.stream.Stream;

@Path("/test")
public class HelloWorld {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response getHelloWorld() {

//        DatabaseUtil db = DatabaseUtil.getInstance();

//        User u = UserManagement.login("test", "test");

//        UserManagement userManagement = new UserManagement();

//        User u = userManagement.login("' || '1'='1", "' || '1'='1");

        Stream<String> one = Stream.of("one", "two");
        Stream<String> two = Stream.of("one", "two");
        Stream<String> three = Stream.of("t", "w");

        boolean check = Stream.concat(one, two).reduce((a, b) -> a.equals(b) ? "" : a).get().length() == 0;
        boolean check1 = Stream.concat(Stream.builder().add("one").add("two").build(), three).
                reduce((a, b) -> a.equals(b) ? "" : a).toString().equals("Optional.empty");

        return Response.ok().entity("should be true: " + check + "; should be false: " + check1).build();
    }
}
