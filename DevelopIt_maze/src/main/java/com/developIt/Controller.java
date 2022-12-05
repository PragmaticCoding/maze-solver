package com.developIt;

import javafx.scene.layout.Region;

import java.util.ArrayList;
import java.util.List;

public class Controller {

    List<MazeSquare> squares = new ArrayList<>();
    ViewBuilder viewBuilder;
    Interactor interactor;

    Controller() {
        interactor = new Interactor(squares);
        viewBuilder = new ViewBuilder(squares, interactor::clickSquare, interactor::findPath);
    }

    Region getView() {
        return viewBuilder.build();
    }
}
