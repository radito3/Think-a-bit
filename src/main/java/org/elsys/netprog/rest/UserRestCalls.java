package org.elsys.netprog.rest;

import org.elsys.netprog.game.UserManagement;
import org.elsys.netprog.model.User;
import org.elsys.netprog.view.JsonWrapper;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import java.io.IOException;

@Path("/users")
public class UserRestCalls {

    @GET
    @Path("/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(@PathParam("userId") int id) throws IOException {
        User user = new UserManagement().getUser(id);

        return Response.ok().entity(JsonWrapper.getJsonFromObject(user)).build();
    }

    @POST
    @Path("/create")
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.TEXT_HTML, MediaType.TEXT_PLAIN })
    public Response createUser(User user) {

        return Response.status(201)
                .entity("{\"msg\":\"A User has been created\"}")
                .header("newUserId", user.getId())
                .cookie(new NewCookie("newUserId", String.valueOf(user.getId())))
                .build();
    }

    @POST
    @Path("/update")
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN })
    public Response updateUser(User user) {

        return Response.status(202).build();
    }

    @DELETE
    @Path("/delete")
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN })
    public Response deleteUser(User user) {

        return Response.status(200).entity("{\"msg\":\"User deleted\"}").build();
    }
}
