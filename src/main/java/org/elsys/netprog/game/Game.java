package org.elsys.netprog.game;

import org.elsys.netprog.db.DatabaseUtil;
import org.elsys.netprog.model.Categories;
import org.elsys.netprog.model.Question;
import org.elsys.netprog.model.Sessions;

import java.util.List;
import java.util.UUID;

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
     * Play a category with the given Id.
     *
     * @param categoryId The Id by which the Category is identified
     * @param userId The Id of the current user
     * @return The json needed from that category
     */
    default String playCategory(int categoryId, int userId) {
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
    default String playStage(int stageId, int userId, int categoryId) {
        return null;
    }

    /**
     * Check for current stage completion
     *
     * @param userId The Id of the current user
     * @param categoryId The Id of the current category
     * @param stageId The Id of the current stage
     */
    default void checkIfCurrentStageIsComplete(int userId, int categoryId, int stageId) {}

    /**
     * Answer a given question.
     *
     * @param question The Question needed to checked for answering
     * @param answers A variable amount of answers, depending on the Question Type
     * @param stageId The Id of the current stage
     */
    default void answerQuestion(Question question, int stageId, String... answers) {}

    /**
     * Get the questions for the current stage
     * @return A {@link java.util.List} with the questions
     */
    default List<Question> getCurrentStageQuestions() {
        return null;
    }

    /**
     * Check if stage is available
     *
     * @param stageId The Id of the current stage
     * @param userId The Id of the current user
     * @param categoryId he Id of the current category
     * @return {@code true} if the stage is available, {@code false} otherwise
     */
    default boolean checkIfStageIsAvailable(int stageId, int userId, int categoryId) {
        return false;
    }

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
     * @return The user id
     */
    default int getUserId(UUID sessionId) {
        return DatabaseUtil.getInstance().getObject(s -> s.get(Sessions.class, sessionId)).getUserId();
    }
}
