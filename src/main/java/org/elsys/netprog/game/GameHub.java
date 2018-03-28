package org.elsys.netprog.game;

import org.elsys.netprog.model.Categories;
import org.elsys.netprog.model.Stages;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GameHub extends AbstractGame implements GameOperations {

    public GameHub() {
        super();
    }

    @Override
    public void setupEnvironment() {
        categories = new LinkedList<>(
                Arrays.asList(new Categories(1, "test"),
                new Categories(2, "test")));

    }

    @Override
    public void playCategory(int categoryId) {
        currentCategory = categories.stream().filter(c -> c.getId() == categoryId).findFirst().get();

        currentCategory.setStages(IntStream.range(1, 10/*броя нива за тази категория*/).mapToObj(i ->
            db.getObject(s -> s.get(Stages.class, i))).collect(Collectors.toList()));
    }

    @Override
    public void playStage(int stageId) {
        currentStage = currentCategory.getStages().stream()
                .filter(s -> s.getId() == stageId).findFirst().get();
    }

}
