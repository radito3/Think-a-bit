package org.elsys.netprog;

import org.elsys.netprog.db.DatabaseUtil;
import org.elsys.netprog.model.Categories;
import org.elsys.netprog.model.UserProgress;
import org.elsys.netprog.view.JsonWrapper;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.function.Supplier;
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
        Supplier<Stream<String>> supplier = () -> Stream.of("test");
        Stream<String> stream = supplier.get();
        Stream<String> stream1 = supplier.get();
        long c = stream.count();
        return Response.ok().entity(stream1.count()).build();
    }
}
