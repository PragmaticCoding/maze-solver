package com.developIt;

import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Builder;

import java.util.List;
import java.util.function.Consumer;

public class ViewBuilder implements Builder<Region> {

    private final List<MazeSquare> squares;
    private final Consumer<Location> clickHandler;
    private final Runnable pathFinder;

    ViewBuilder(List<MazeSquare> squares, Consumer<Location> clickHandler, Runnable pathFinder) {
        this.squares = squares;
        this.clickHandler = clickHandler;
        this.pathFinder = pathFinder;
    }

    @Override
    public Region build() {
        BorderPane results = new BorderPane();
        results.setCenter(createGrid());
        results.setBottom(createButton());
        return results;
    }

    private Region createGrid() {
        GridPane results = new GridPane();
        squares.forEach(square -> {
            StackPane stackPane = new StackPane();
            Rectangle rectangle = new Rectangle(40, 30, Color.WHITE);
            rectangle.setArcHeight(5);
            rectangle.setArcWidth(5);
            rectangle.setStroke(Color.BLACK);
            rectangle.fillProperty().bind(Bindings.createObjectBinding(() ->
                    determineColour(square.isWall(), square.isOnPath()), square.wallProperty(), square.onPathProperty()));
            ImageView pingu = getImageView(square.pinguProperty(), "pingu.png");
            ImageView fish = getImageView(square.fishProperty(), "fish.png");
            stackPane.getChildren().addAll(rectangle, pingu, fish);
            stackPane.setPickOnBounds(true);
            stackPane.setOnMouseClicked(evt -> clickHandler.accept(square.getLocation()));
            results.add(stackPane, square.getX(), square.getY());
        });
        return results;
    }

    private ImageView getImageView(BooleanProperty boundProperty, String fileName) {
        ImageView pingu = new ImageView(this.getClass().getResource(fileName).toExternalForm());
        pingu.setFitHeight(28);
        pingu.setPreserveRatio(true);
        pingu.visibleProperty().bind(boundProperty);
        return pingu;
    }

    private Color determineColour(boolean wall, boolean onPath) {
        if (wall)
            return Color.CRIMSON;
        if (onPath)
            return Color.GREEN;
        return Color.WHITE;
    }

    private Node createButton() {
        Button results = new Button("Find Path");
        results.setOnAction(evt -> pathFinder.run());
        return results;
    }
}
