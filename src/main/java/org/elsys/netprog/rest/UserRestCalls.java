package org.elsys.netprog.rest;

import org.elsys.netprog.game.UserManagement;
import org.elsys.netprog.model.User;
import org.elsys.netprog.view.JsonWrapper;
import org.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import java.io.IOException;

@Path("/users")
public class UserRestCalls {

    private UserManagement users = new UserManagement();

    @GET
    @Path("/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(@PathParam("userId") int id) { //not sure if needed
        User user = users.getUser(id);
        String output;

        try {
            output = JsonWrapper.getJsonFromObject(user);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            return Response.status(500).build();
        }

        return Response.ok().entity(output).build();
    }

    @GET
    @Path("/currentUser")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCurrentUser() {
        String output;

        try {
            output = JsonWrapper.getJsonFromObject(users.getCurrentUser());
        } catch (IOException e) {
            System.err.println(e.getMessage());
            return Response.status(500).build();
        }

        return Response.status(200).entity(output).build();
    }

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(String request) {
        JSONObject userData = new JSONObject(request);

        try {
            users.login(userData.getString("username"),
                    userData.getString("password"));
        } catch (IllegalAccessException e) {
            System.err.println(e.getMessage());
            return Response.status(500).build();
        }

        return Response.status(200).build();
    }

    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createUser(String request) {
        JSONObject userData = new JSONObject(request);
        users.register(userData.getString("username"),
                userData.getString("password"));
        User current = users.getCurrentUser();

        return Response.status(201)
                .cookie(new NewCookie("currentUserId", String.valueOf(current.getId())))
                .build();
    }

    @GET
    @Path("/logout")
    public Response logout() {
        users.logout();

        return Response.status(200).cookie(null).build();
    }

//    @POST
//    @Path("/update")
//    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response updateUser(User user) {
//
//        return Response.status(202).build();
//    }
//
//    @DELETE
//    @Path("/delete")
//    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response deleteUser(User user) {
//
//        return Response.status(200).entity("{\"msg\":\"User deleted\"}").build();
//    }
}
