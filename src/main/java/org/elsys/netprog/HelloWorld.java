package org.elsys.netprog;

import org.elsys.netprog.db.DatabaseUtil;
import org.elsys.netprog.model.Categories;
import org.elsys.netprog.model.Stages;
import org.elsys.netprog.model.UserProgress;
import org.elsys.netprog.view.JsonWrapper;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Path("/test")
public class HelloWorld {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response getMessage() throws IOException {
        String str = JsonWrapper.getJsonFromObject(new Categories(1, "test"));
        String str1 = ",[{\"stageId\":1,\"isUnlocked\":true},{\"stageId\":1,\"isUnlocked\":true}," +
                "{\"stageId\":1,\"isUnlocked\":false}]}";
        String output = str.substring(0, str.length() - 1).concat(str1);

        return Response.ok().entity(output).build();
    }

    @GET
    @Path("/1")
    @Produces(MediaType.TEXT_PLAIN)
    public Response testDBObject() {
        DatabaseUtil db = DatabaseUtil.getInstance();
        db.processObject(s -> s.save(new UserProgress(1, 1)));
        UserProgress progress = db.getObject(s -> s.get(UserProgress.class, new UserProgress(1, 1)));

        return Response.ok().entity(progress.getReachedStage()).build();
    }

    @GET
    @Path("/2")
    @Produces(MediaType.TEXT_PLAIN)
    public Response test() {
        Supplier<Stream<String>> supplier = () -> Stream.of("test", "two").filter(s -> !s.equals("two"));
        Stream<String> stream = supplier.get();
        Stream<String> stream1 = supplier.get();
        long c = stream.count();
        long c1 = stream1.count();
        return Response.ok().entity("str1 length: " + c + ", str2 length: " + c1).build();
    }

    @GET
    @Path("/3")
    @Produces(MediaType.TEXT_PLAIN)
    public Response test21() {
        Stream<String> stream = Stream.of("one", "one", "two");
        Set<String> strngs = new HashSet<>();
        return Response.status(200).entity(stream.filter(s -> !strngs.add(s)).collect(Collectors.toSet()).size()).build();
    }
}
