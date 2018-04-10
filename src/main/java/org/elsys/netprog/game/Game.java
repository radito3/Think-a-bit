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
    List<Categories> getCategories();

    /**
     * Play a category with the given Id.
     *
     * @param categoryId The Id by which the Category is identified
     * @param userId The Id of the current user
     * @return The json needed from the request
     */
    String playCategory(int categoryId, int userId);

    /**
     * Play a stage with the given Id.
     *
     * @param stageId The Id by which the Stage is identified
     * @param userId The Id of the current user
     * @param categoryId The Id of the current category
     * @return The Json string needed for the request
     */
    String playStage(int stageId, int userId, int categoryId);

    /**
     * Check for current stage completion
     *
     * @param userId The Id of the current user
     * @param categoryId The Id of the current category
     * @param stageId The Id of the current stage
     */
    void checkIfCurrentStageIsComplete(int userId, int categoryId, int stageId);

    /**
     * Answer a given question.
     *
     * @param question The Question needed to checked for answering
     * @param stageId The Id of the current stage
     * @param answers A variable amount of answers, depending on the Question Type
     */
    void answerQuestion(Question question, int stageId, String... answers);

    /**
     * Get the questions for the current stage
     * @return A {@link java.util.List} with the questions
     */
    List<Question> getCurrentStageQuestions();

    /**
     * Check if stage is available
     *
     * @param stageId The Id of the current stage
     * @param userId The Id of the current user
     * @param categoryId he Id of the current category
     * @return {@code true} if the stage is available, {@code false} otherwise
     */
    boolean checkIfStageIsAvailable(int stageId, int userId, int categoryId);

    /**
     * Add attempts to a Stage identified by given stage Id.
     *
     * @param stageId The Id of the current stage
     * @param userId The Id of the current user
     * @param categoryId The Id of the current category
     * @return The number of attempts received
     * @throws IllegalAccessException If the Stage which is requested is locked
     */
    int buyAttempts(int stageId, int userId, int categoryId) throws IllegalAccessException;

    /**
     * Get the current user id.
     *
     * @param sessionId The session id of the current session
     * @return The user id
     */
    default int getUserId(UUID sessionId) {
        return DatabaseUtil.getInstance().getObject(s -> s.get(Sessions.class, sessionId)).getUserId();
    }

    /**
     * Check whether the current session has expired or not
     *
     * @param value The {@link javax.ws.rs.core.Cookie} value
     * @return {@code true} If the session has expired, {@code false} otherwise
     */
    boolean hasSessionExpired(String value);
}
