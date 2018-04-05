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
     * @param categoryId The Id by witch the Category should be identified
     * @return The GameHub instance that is used for the current game session
     */
    default GameHub playCategory(int categoryId) {
        return null;
    }

    default List<Question> playStage(int stageId) {
        return null;
    }

    default void checkIfCurrentStageIsComplete() {}

    default boolean answerQuestion(Question question, String... answers) {
        return false;
    }
}
