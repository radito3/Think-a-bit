package org.elsys.netprog.rest;

import org.elsys.netprog.game.UserManagement;
import org.elsys.netprog.model.Sessions;
import org.elsys.netprog.model.User;

import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Path("/users")
public class UserRestCalls {

    private UserManagement users = new UserManagement();

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response login(String request) {
        String req;
        User user;
        try {
            req = URLDecoder.decode(request, "UTF-8");
            String[] data = req.split("&");
            String username = data[0].substring(data[0].indexOf('=') + 1);
            String password = data[1].substring(data[1].indexOf('=') + 1);

            user = users.login(username, password);
        } catch (IllegalAccessException e) {
            return Response.status(404).build();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return Response.status(500).build();
        }

        Sessions session = new Sessions(user.getId(), UUID.randomUUID(),
                Timestamp.from(Instant.now()), Timestamp.from(Instant.now().plusMillis(1800 * 1000)));
        users.saveSessionData(session);

        NewCookie cookie = new NewCookie("sessionId", String.valueOf(session.getSessionId()),
                "/Think-a-bit","localhost",1,"",1800,
                new Date(System.currentTimeMillis() + (1800 * 1000)),false,true);
        Cookie cookie1 = new Cookie("sessionId", String.valueOf(session.getSessionId()),"/Think-a-bit",
                "localhost",1);
        return Response.status(200).cookie(new NewCookie(cookie1)).build();
    }

    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response register(String request) {
        String req;
        User user;
        try {
            req = URLDecoder.decode(request, "UTF-8");
            String[] data = req.split("&");
            String username = data[0].substring(data[0].indexOf('=') + 1);
            String password = data[1].substring(data[1].indexOf('=') + 1);

            user = users.register(username, password);
        } catch (IllegalAccessException e) {
            return Response.status(412).build();
        } catch (IllegalArgumentException e) {
            return Response.status(400).build();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return Response.status(500).build();
        }

        Sessions session = new Sessions(user.getId(), UUID.randomUUID(),
                Timestamp.from(Instant.now()), Timestamp.from(Instant.now().plusMillis(1800 * 1000)));
        users.saveSessionData(session);


        NewCookie cookie = new NewCookie("sessionId", String.valueOf(session.getSessionId()),
                "/Think-a-bit","localhost",1,"",1800,
                new Date(System.currentTimeMillis() + (1800 * 1000)),false,true);
        return Response.status(201).cookie(cookie).build();
    }

    @POST
    @Path("/logout")
    public Response logout(@CookieParam("sessionId") String sessionId) {
        if (sessionId == null) { //or  is session has expired
            return Response.status(401).build();
        }

        users.deleteSessionData(UUID.fromString(sessionId));

        NewCookie cookie = new NewCookie("sessionId","","/Think-a-bit/users","localhost",
                1,"",0, new Date(System.currentTimeMillis() - 10),false,true);
        return Response.status(204).cookie(cookie).build();
    }

//    @POST
//    @Path("/update")
//    @Consumes(MediaType.APPLICATION_JSON)
//    public Response updateUser(String request) {
//        JSONObject json = new JSONObject(request);
//
//        try {
//            users.update(json.getInt("userId"),
//                    json.getString("userName"),
//                    json.getString("userPass"));
//        } catch (IllegalArgumentException e) {
//            System.err.println(e.getMessage());
//            return Response.status(500).build();
//        }
//
//        return Response.status(202).build();
//    }
//
//    @DELETE
//    @Path("/delete")
//    @Consumes(MediaType.APPLICATION_JSON)
//    public Response deleteUser(String request) {
//        JSONObject json = new JSONObject(request);
//        users.delete(json.getInt("userId"),
//                json.getString("userName"),
//                json.getString("userPass"));
//
//        return Response.status(200).build();
//    }
}
