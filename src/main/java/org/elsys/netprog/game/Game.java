package org.elsys.netprog.game;

import org.elsys.netprog.db.DatabaseUtil;
import org.elsys.netprog.model.Categories;
import org.elsys.netprog.model.Question;
import org.elsys.netprog.model.Sessions;
import org.elsys.netprog.model.Stages;

import java.util.List;

public interface Game {

    /**
     * Get the Categories from the game.
     *
     * @return A {@link java.util.List} containing the Categories
     */
    default List<Categories> getCategories() {
        return null;
    }

    /**
     * Get the current Stage
     *
     * @return The Stage object
     */
    default Stages getCurrentStage() {
        return null;
    } //this will be removed

    /**
     * Play a category with the given Id.
     *
     * @param categoryId The Id by which the Category is identified
     * @param userId The Id of the current user
     * @return The category
     */
    default Categories playCategory(int categoryId, int userId) {
        return null;
    }

    /**
     * Play a stage with the given Id.
     *
     * @param stageId The Id by which the Stage is identified
     * @param userId The Id of the current user
     * @param categoryId The Id of the current category
     * @return The GameHub instance that is used for the current game session
     */
    default GameHub playStage(int stageId, int userId, int categoryId) {
        return null;
    }

    /**
     * Check for current stage completion
     *
     * @param userId The Id of the current user
     * @param categoryId The Id of the current category
     * @param stageId The Id of the current stage
     * @return {@code true} if the stage is complete, {@code false} otherwise
     */
    default boolean checkIfCurrentStageIsComplete(int userId, int categoryId, int stageId) {
        return false;
    }

    /**
     * Answer a given question.
     *
     * @param question The Question needed to checked for answering
     * @param answers A variable amount of answers, depending on the Question Type
     */
    default void answerQuestion(Question question, String... answers) {}

    /**
     * Add attempts to a Stage identified by given stage Id.
     *
     * @param stageId The Id of the current stage
     * @param userId The Id of the current user
     * @param categoryId The Id of the current category
     * @return The number of attempts received
     * @throws IllegalAccessException If the Stage which is requested is locked
     */
    default int buyAttempts(int stageId, int userId, int categoryId) throws IllegalAccessException {
        return 0;
    }

    /**
     * Get the current user id.
     *
     * @param sessionId The session id of the current session
     * @return The user is
     */
    default int getUserId(int sessionId) {
        return DatabaseUtil.getInstance().getObject(s -> s.get(Sessions.class, sessionId)).getUserId();
    }
}
