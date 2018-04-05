package org.elsys.netprog.rest;

import org.elsys.netprog.game.Game;
import org.elsys.netprog.game.GameHub;
import org.elsys.netprog.model.Categories;
import org.elsys.netprog.model.Question;
import org.elsys.netprog.view.JsonWrapper;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
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
        String output;

        try {
            output = JsonWrapper.getJsonFromObject(category);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            return Response.status(500).build();
        }

        return Response.status(200).entity(output).build();
    }

    @GET
    @Path("/stageQuestions")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStageQuestions(@DefaultValue("1") @QueryParam("stageId") int stageId) {
        String output;
        List<Question> list = game.playStage(stageId);

        try {
            output = JsonWrapper.getJsonFromObject(list);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            return Response.status(500).build();
        } catch (IllegalStateException e) {
            return Response.status(400).entity("\"msg\":\"" + e.getMessage() + "\"").build();
        }

        return Response.status(200).entity(output).build();
    }
}
