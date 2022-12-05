package com.developIt;

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
        findSquare(new Location(0, 0)).ifPresent(square -> square.setPingou(true));
        findSquare(new Location(10, 11)).ifPresent(square -> square.setFish(true));
    }

    void clickSquare(Location location) {
        squares.stream().filter(square -> square.getLocation().equals(location)).findFirst().ifPresent(square -> square.setWall(!square.isWall()));
    }

    void findPath() {
        squares.stream().filter(MazeSquare::isFish).findFirst().ifPresent(end -> {
            squares.stream().filter(MazeSquare::isPingou).findFirst().ifPresent(start -> {
                findFromHere(start, end.getLocation()).forEach(square -> square.setOnPath(true));
            });
        });
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
