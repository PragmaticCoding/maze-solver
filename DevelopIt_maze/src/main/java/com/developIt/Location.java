package com.developIt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

final class Location {
    private int x;

    public int X() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int Y() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Location(int x, int y) {
        this.x = x;
        this.y = y;
    }

    private int y;

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Location) {
            return x == ((Location) obj).X() && y == ((Location) obj).Y();
        }
        return false;
    }

    @Override
    public String toString() {
        return "X:" + x + " Y:" + y;
    }

    public Location rightNeighbour() {
        return new Location(x + 1, y);
    }

    public Location downNeighbour() {
        return new Location(x, y + 1);
    }

    public Location leftNeighbour() {
        return new Location(x - 1, y);
    }

    public Location upNeighbour() {
        return new Location(x, y - 1);
    }

    public List<MazeDirection> preferences(Location otherLocation) {
        ArrayList<MazeDirection> results = new ArrayList<>(Arrays.asList(MazeDirection.RIGHT, MazeDirection.DOWN, MazeDirection.LEFT, MazeDirection.UP));
        if (x == otherLocation.X()) {
            if (y < otherLocation.Y()) {
                results = new ArrayList<>(Arrays.asList(MazeDirection.DOWN, MazeDirection.RIGHT, MazeDirection.LEFT, MazeDirection.UP));
            } else {
                results = new ArrayList<>(Arrays.asList(MazeDirection.UP, MazeDirection.RIGHT, MazeDirection.LEFT, MazeDirection.DOWN));
            }
        }
        if (y == otherLocation.Y()) {
            if (x < otherLocation.X()) {
                results = new ArrayList<>(Arrays.asList(MazeDirection.RIGHT, MazeDirection.DOWN, MazeDirection.UP, MazeDirection.LEFT));
            } else {
                results = new ArrayList<>(Arrays.asList(MazeDirection.LEFT, MazeDirection.DOWN, MazeDirection.UP, MazeDirection.RIGHT));
            }
        }
        return results;
    }

}
