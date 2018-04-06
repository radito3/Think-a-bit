package org.elsys.netprog.game;

import org.elsys.netprog.model.Categories;
import org.elsys.netprog.model.Question;

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
     * Get the current Category,
     *
     * @return The Category object
     */
    default Categories getCurrentCategory() {
        return null;
    }

    /**
     * Play a category with the given Id.
     *
     * @param categoryId The Id by which the Category is identified
     * @return The GameHub instance that is used for the current game session
     */
    default GameHub playCategory(int categoryId) {
        return null;
    }

    /**
     * Play a stage with the given Id.
     *
     * @param stageId The Id by which the Stage is identified
     * @return A {@link java.util.List} containing the Stage's Questions
     */
    default List<Question> playStage(int stageId) {
        return null;
    }

    /**
     * Check for current stage completion
     */
    default void checkIfCurrentStageIsComplete() {}

    /**
     * Answer a given question.
     *
     * @param question The Question needed to checked for answering
     * @param answers A variable amount of answers, depending on the Question Type
     * @return {@code true} if the answer(s) is correct, {@code false} otherwise
     */
    default boolean answerQuestion(Question question, String... answers) {
        return false;
    }

    /**
     * Add attempts to a Stage identified by given stage Id.
     *
     * @param stageId The Stage identifier
     * @throws IllegalAccessException If the Stage which is requested is locked
     */
    default void buyAttempts(int stageId) throws IllegalAccessException {}
}
