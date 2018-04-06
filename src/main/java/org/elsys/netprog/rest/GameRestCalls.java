package org.elsys.netprog.rest;

import org.elsys.netprog.game.Game;
import org.elsys.netprog.game.GameHub;
import org.elsys.netprog.model.Categories;
import org.elsys.netprog.model.Question;
import org.elsys.netprog.model.Stages;
import org.elsys.netprog.view.JsonWrapper;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.List;

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
    public Response getCategory(@PathParam("categoryId") int categoryId) {
        Categories category = game.getCurrentCategory() == null ||
                game.getCurrentCategory().getId() != categoryId ?
                game.playCategory(categoryId).getCurrentCategory() :
                game.getCurrentCategory();
        String output; //includes which stage is unlocked

        try {
            output = JsonWrapper.getJsonFromObject(category);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            return Response.status(500).build();
        }

        return Response.status(200).entity(output).build();
    }

    @GET
    @Path("/stage")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStageQuestions(@DefaultValue("1") @QueryParam("stageId") int stageId) {
        String output;
        Stages stage = game.getCurrentStage() == null ||
                game.getCurrentStage().getId() != stageId ?
                game.playStage(stageId).getCurrentStage() :
                game.getCurrentStage();
        List<Question> questions = stage.getQuestions();

        try {
            output = JsonWrapper.getJsonFromObject(questions);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            return Response.status(500).build();
        } catch (IllegalStateException e) {
            return Response.status(403).entity("\"msg\":\"" + e.getMessage() + "\"").build();
        }

        return Response.status(200).entity(output).build();
    }

    @GET
    @Path("/buyStageAttempts")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buyStageAttempts(@DefaultValue("1") @QueryParam("stageId") int stageId) {

        try {
            game.buyAttempts(stageId);
        } catch (IllegalAccessException e) {
            return Response.status(403).entity("\"msg\":\"" + e.getMessage() + "\"").build();
        }

        return Response.status(200).build();
    }

    @POST
    @Path("/stage")
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_JSON)
    public Response submitAnswers() {

        return Response.status(200).build();
    }
}
