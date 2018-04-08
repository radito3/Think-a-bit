package org.elsys.netprog.rest;

import org.elsys.netprog.game.UserManagement;
import org.elsys.netprog.model.Sessions;
import org.elsys.netprog.model.User;
import org.json.JSONObject;

import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

@Path("/users")
public class UserRestCalls {

    private UserManagement users = new UserManagement();

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(String request) {
        JSONObject userData = new JSONObject(request);
        User user;
        try {
            user = users.login(userData.getString("username"),
                    userData.getString("password"));
        } catch (IllegalAccessException e) {
            System.err.println(e.getMessage());
            return Response.status(500).build();
        }
        Sessions session = new Sessions(user.getId(), Long.valueOf(UUID.randomUUID().toString()),
                Timestamp.from(Instant.now()), Timestamp.from(Instant.now().plusMillis(30 * 1000))); //30 minutes

        return Response.status(200)
                .cookie(new NewCookie("sessionId", String.valueOf(session.getSessionId())))
                .build();
    }

    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createUser(String request) {
        JSONObject userData = new JSONObject(request);
        User user = users.register(userData.getString("username"),
                userData.getString("password"));
        Sessions session = new Sessions(user.getId(), Long.valueOf(UUID.randomUUID().toString()),
                Timestamp.from(Instant.now()), Timestamp.from(Instant.now().plusMillis(30 * 1000)));

        return Response.status(201)
                .cookie(new NewCookie("sessionId", String.valueOf(session.getSessionId())))
                .build();
    }

    @GET
    @Path("/logout")
    public Response logout(@CookieParam("sessionId") String sessionId) {
        users.deleteSessionData(Integer.valueOf(sessionId));

        return Response.status(200).cookie().build();
    }

    @POST
    @Path("/update")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateUser(String request) {
        JSONObject json = new JSONObject(request);

        try {
            users.update(json.getInt("userId"),
                    json.getString("userName"),
                    json.getString("userPass"));
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
            return Response.status(500).build();
        }

        return Response.status(202).build();
    }

    @DELETE
    @Path("/delete")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteUser(String request) {
        JSONObject json = new JSONObject(request);
        users.delete(json.getInt("userId"),
                json.getString("userName"),
                json.getString("userPass"));

        return Response.status(200).build();
    }
}
