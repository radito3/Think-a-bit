package org.elsys.netprog.rest;

import org.elsys.netprog.game.Game;
import org.elsys.netprog.game.GameHub;
import org.elsys.netprog.model.Categories;
import org.elsys.netprog.model.Question;
import org.elsys.netprog.view.JsonWrapper;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Path("/game")
public class GameRestCalls {

    private Game game = GameHub.getInstance();

    @GET
    @Path("/{categoryId}")
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
            Logger.getLogger(GameHub.class.getName()).log(Level.FINE, e.getMessage(), e);
            System.err.println(e.getMessage());
            return Response.status(500).entity("{\"msg\":\"Internal Server Error\"}").build();
        }

        return Response.status(200).entity(output).build();
    }

    @GET
    @Path("/{questionId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getQuestion(@PathParam("questionId") int questionId) {
        Question question = game.getCurrentQuestion() == null ||
                game.getCurrentQuestion().getId() != questionId ?
                game.playQuesion(questionId).getCurrentQuestion() :
                game.getCurrentQuestion();
        String output;

        try {
            output = JsonWrapper.getJsonFromObject(question);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            return Response.status(500).entity("{\"msg\":\"Internal Server Error\"}").build();
        }

        return Response.status(200).entity(output).build();
    }
}
