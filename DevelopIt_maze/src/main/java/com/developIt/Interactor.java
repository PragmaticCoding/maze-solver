package com.developIt;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Interactor {

    private final List<MazeSquare> squares;

    Interactor(List<MazeSquare> squares) {
        this.squares = squares;
        for (int y = 0; y < 12; y++) {
            for (int x = 0; x < 15; x++) {
                squares.add(new MazeSquare(new Location(x, y), false));
            }
        }
        squares.forEach(square -> {
            findSquare(square.getLocation().rightNeighbour()).ifPresent(square::setRightNeighbour);
            findSquare(square.getLocation().downNeighbour()).ifPresent(square::setDownNeighbour);
            findSquare(square.getLocation().leftNeighbour()).ifPresent(square::setLeftNeighbour);
            findSquare(square.getLocation().upNeighbour()).ifPresent(square::setUpNeighbour);
        });
        findSquare(new Location(0, 0)).ifPresent(square -> square.setPingu(true));
        findSquare(new Location(10, 11)).ifPresent(square -> square.setFish(true));
    }

    void clickSquare(Location location) {
        squares.stream().filter(square -> square.getLocation().equals(location)).findFirst().ifPresent(square -> square.setWall(!square.isWall()));
    }

    List<MazeSquare> findPath() {
        List<MazeSquare> results = new ArrayList<>();
        squares.stream().filter(MazeSquare::isFish).findFirst().ifPresent(end ->
                squares.stream().filter(MazeSquare::isPingu).findFirst().ifPresent(start ->
                        results.addAll(findFromHere(start, end.getLocation()))
                )
        );
        return results;
    }


    void animatePath(List<MazeSquare> path) {
        final Animation animation = new Transition() {
            {
                setCycleDuration(Duration.millis(6000));
                setInterpolator(Interpolator.LINEAR);
            }

            protected void interpolate(double frac) {
                path.get(Math.round((path.size() - 1) * (float) frac)).setOnPath(true);
            }
        };
        animation.play();
    }

    private List<MazeSquare> findFromHere(MazeSquare square, Location fishLocation) {
        List<MazeSquare> results = new ArrayList<>();
        if (!square.isUsable() || square.isWall()) {
            return results;
        }
        square.setUsable(false);
        if (square.isFish()) {
            results.add(square);
        }
        square.getLocation().preferences(fishLocation).forEach(direction -> {
            if (results.isEmpty()) {
                square.getNeighbour(direction).ifPresent(neighbour -> integrate(results, square, findFromHere(neighbour, fishLocation)));
            }
        });
        return results;
    }

    private void integrate(List<MazeSquare> results, MazeSquare here, List<MazeSquare> foundSquares) {
        if (!foundSquares.isEmpty()) {
            results.add(here);
            results.addAll(foundSquares);
        }
    }

    private Optional<MazeSquare> findSquare(Location neighbourLocation) {
        return squares.stream().filter(square -> square.getLocation().equals(neighbourLocation)).findFirst();
    }
}
