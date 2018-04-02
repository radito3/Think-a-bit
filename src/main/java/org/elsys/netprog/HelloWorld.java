package org.elsys.netprog;

import org.codehaus.jackson.map.ObjectMapper;
import org.elsys.netprog.db.DatabaseUtil;
import org.elsys.netprog.game.UserManagement;
import org.elsys.netprog.model.Question;
import org.elsys.netprog.model.Stages;
import org.elsys.netprog.model.User;
import org.elsys.netprog.view.JsonWrapper;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Stream;

@Path("/test")
public class HelloWorld {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response getHelloWorld() throws IOException {
//        DatabaseUtil db = DatabaseUtil.getInstance();

//        UserManagement userManagement = new UserManagement();
//
//        User u;
//        try {
//            userManagement.register("test1", "test1");
//            userManagement.register("test1", "test2");
//            userManagement.logout();
//
//            u = userManagement.login("test1", "test1");
//        } catch (IllegalAccessException | IllegalArgumentException e) {
//            return Response.status(400).entity(e.getMessage()).build();
//        }

//        Stream<String> one = Stream.of("one", "two");
//        Stream<String> two = Stream.of("one", "two");
//        Stream<String> three = Stream.of("t", "w");
//
//        boolean check = Stream.concat(one, two).reduce((a, b) -> a.equals(b) ? "" : a).get().length() == 0;
//        boolean check1 = Stream.concat(Stream.builder().add("one").add("two").build(), three).
//                reduce((a, b) -> a.equals(b) ? "" : a).toString().equals("Optional.empty");
        Stages s = new Stages(1, 1);
        s.setQuestions(Arrays.asList(new Question(1, Question.Type.CLOSED_ONE, "title1"),
                new Question(2, Question.Type.OPEN, "title2")));

        String json = JsonWrapper.getJsonFromObject(s);

        return Response.ok().entity(json).build();
    }
}
