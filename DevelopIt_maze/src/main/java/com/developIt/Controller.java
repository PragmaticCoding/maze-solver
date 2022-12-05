package com.developIt;

import javafx.concurrent.Task;
import javafx.scene.layout.Region;

import java.util.ArrayList;
import java.util.List;

public class Controller {

    List<MazeSquare> squares = new ArrayList<>();
    ViewBuilder viewBuilder;
    Interactor interactor;

    Controller() {
        interactor = new Interactor(squares);
        viewBuilder = new ViewBuilder(squares, interactor::clickSquare, this::findPath);
    }

    private void findPath() {
        Task<List<MazeSquare>> pathTask = new Task<>() {
            @Override
            protected List<MazeSquare> call() {
                return interactor.findPath();
            }
        };
        pathTask.setOnSucceeded(evt -> interactor.animatePath(pathTask.getValue()));
        Thread pathThread = new Thread(pathTask);
        pathThread.start();
    }

    Region getView() {
        return viewBuilder.build();
    }
}
