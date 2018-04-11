package org.elsys.netprog.rest;

import org.elsys.netprog.game.Game;
import org.elsys.netprog.game.GameHub;
import org.elsys.netprog.model.Question;
import org.elsys.netprog.view.JsonWrapper;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;
import java.util.stream.Stream;

@Path("/game")
public class GameRestCalls {

    private Game game = GameHub.getInstance();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getIndexPage() {
        String output;

        try {
            output = JsonWrapper.getJsonFromObject(game.getCategories());
        } catch (IOException e) {
            e.printStackTrace();
            return Response.status(500).build();
        }

        return Response.status(200).entity(output).build();
    }

    @GET
    @Path("/category/{categoryId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCategory(@PathParam("categoryId") int categoryId,
                                @CookieParam("sessionId") Cookie cookie) {
        if (cookie == null) {
            return Response.status(401).build();
        }

        String output = game.playCategory(categoryId, game.getUserId(UUID.fromString(cookie.getValue())));

        return Response.status(200).entity(output).build();
    }

    @GET
    @Path("/stage")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStageQuestions(@DefaultValue("1") @QueryParam("stageId") int stageId,
                                      @DefaultValue("1") @QueryParam("categoryId") int categoryId,
                                      @CookieParam("sessionId") Cookie cookie) {
        if (cookie == null) {
            return Response.status(401).build();
        }
        int userId = game.getUserId(UUID.fromString(cookie.getValue()));
        String output;

        try {
            output = game.playStage(stageId, userId, categoryId);
        } catch (IllegalStateException e) {
            return Response.status(403).entity("{\"msg\":\"" + e.getMessage() + "\"}").build();
        } catch (IllegalArgumentException e) {
            return Response.status(400).entity("{\"msg\":\"" + e.getMessage() + "\"}").build();
        }

        return Response.status(200).entity(output).build();
    }

    @POST
    @Path("/buyStageAttempts")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response buyStageAttempts(String request, @CookieParam("sessionId") Cookie cookie) {
        if (cookie == null) {
            return Response.status(401).build();
        }

        JSONObject json = new JSONObject(request);
        int userId = game.getUserId(UUID.fromString(cookie.getValue()));
        int categoryId = json.getInt("categoryId");
        int stageId = json.getInt("stageId");
        int numAttempts;

        try {
            numAttempts = game.buyAttempts(stageId, userId, categoryId);
        } catch (IllegalAccessException e) {
            return Response.status(403).entity("{\"msg\":\"" + e.getMessage() + "\"}").build();
        }

        return Response.status(200).entity("{\"attempts\":\"" + numAttempts + "\"}").build();
    }

    @POST
    @Path("/submit")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response submitAnswers(String request, @CookieParam("sessionId") Cookie cookie) {
        if (cookie == null) {
            return Response.status(401).build();
        }

        JSONObject json = new JSONObject(request);
        int userId = game.getUserId(UUID.fromString(cookie.getValue()));
        int categoryId = json.getInt("categoryId");
        int stageId = json.getInt("stageId");

        if (!game.checkIfStageIsAvailable(stageId, userId, categoryId)) {
            return Response.status(403).build();
        }

        JSONArray qAndA = json.getJSONArray("results");
        List<JSONObject> entities = new LinkedList<>();
        for (Object entity : qAndA) {
            entities.add(new JSONObject(entity));
        }

        entities.forEach(ent -> {
            Question question = new Question(ent.getInt("questionId"),
                    Question.Type.valueOf(ent.getString("questionType")),
                    ent.getString("questionTitle"));
            List<Object> answers = ent.getJSONArray("answers").toList();
            String[] answ = answers.stream().toArray(String[]::new);
            game.answerQuestion(question, stageId, answ);
        });

        game.checkIfCurrentStageIsComplete(userId, categoryId, stageId);

        Supplier<Stream<Question>> supplier = () -> game.getCurrentStageQuestions().stream().filter(q -> !q.isSolved());
        String output;

        if (supplier.get().count() == 0) {
            output = "{\"wrongQuestions\":[]}";
        } else {
            try {
                String[] array = supplier.get().map(Question::getTitle).toArray(String[]::new);
                output = "{\"wrongQuestions\":" + JsonWrapper.getJsonFromObject(array) + "}";
            } catch (IOException e) {
                e.printStackTrace();
                return Response.status(500).build();
            }
        }

        return Response.status(200).entity(output).build();
    }
}
