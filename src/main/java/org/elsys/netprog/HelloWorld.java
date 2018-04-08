package org.elsys.netprog;

import org.elsys.netprog.model.Categories;
import org.elsys.netprog.view.JsonWrapper;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

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
}
