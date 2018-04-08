package org.elsys.netprog.rest;

import org.elsys.netprog.game.Game;
import org.elsys.netprog.game.GameHub;
import org.elsys.netprog.model.Categories;
import org.elsys.netprog.model.Question;
import org.elsys.netprog.model.Stages;
import org.elsys.netprog.view.JsonWrapper;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
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
            System.err.println(e.getMessage());
            return Response.status(500).build();
        }

        return Response.status(200).entity(output).build();
    }

    @GET
    @Path("/category/{categoryId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCategory(@PathParam("categoryId") int categoryId,
                                @CookieParam("sessionId") String sessionId) {
        if (sessionId == null) { //or  is session has expired
            return Response.status(401).build();
        }

        String output = game.playCategory(categoryId, game.getUserId(Integer.valueOf(sessionId)));

        return Response.status(200).entity(output).build();
    }

    @GET
    @Path("/stage")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStageQuestions(String request,
                                      @DefaultValue("1") @QueryParam("stageId") int stageId,
                                      @CookieParam("sessionId") String sessionId) {
        JSONObject json = new JSONObject(request);
        int userId = game.getUserId(Integer.valueOf(sessionId));
        int categoryId = json.getInt("categoryId");

        String output;
        Stages stage = game.getCurrentStage() == null ||
                game.getCurrentStage().getId() != stageId ?
                game.playStage(stageId, userId, categoryId).getCurrentStage() :
                game.getCurrentStage();
        List<Question> questions = stage.getQuestions();

        try {
            output = JsonWrapper.getJsonFromObject(questions);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            return Response.status(500).build();
        } catch (IllegalStateException e) {
            return Response.status(403).entity("{\"msg\":\"" + e.getMessage() + "\"}").build();
        }

        return Response.status(200).entity(output).build();
    }

    @GET
    @Path("/buyStageAttempts")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response buyStageAttempts(String request, @CookieParam("sessionId") String sessionId) {
        if (sessionId == null) { //or is session has expired
            return Response.status(401).build();
        }

        JSONObject json = new JSONObject(request);
        int userId = game.getUserId(Integer.valueOf(sessionId));
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
    public Response submitAnswers(String request, @CookieParam("sessionId") String sessionId) {
        JSONObject json = new JSONObject(request);
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
            String[] answ = (String[]) answers.toArray();
            game.answerQuestion(question, answ);
        });

        int userId = game.getUserId(Integer.valueOf(sessionId));
        int categoryId = json.getInt("categoryId");
        int stageId = json.getInt("stageId");
        game.checkIfCurrentStageIsComplete(userId, categoryId, stageId);

        Stream<String> wrongQuestionTitles = game.getCurrentStage().getQuestions() //this should not be with current stage
                .stream()
                .filter(q -> !q.isSolved())
                .map(Question::getTitle);
        String output;

        if (wrongQuestionTitles.count() == 0) {
            output = "{\"wrongQuestions\":[]}";
        } else {
            try {
                output = "{\"wrongQuestions\":" +
                        JsonWrapper.getJsonFromObject(wrongQuestionTitles.toArray(String[]::new)) + "}";
            } catch (IOException e) {
                System.err.println(e.getMessage());
                return Response.status(500).build();
            }
        }

        return Response.status(200).entity(output).build();
    }
}
