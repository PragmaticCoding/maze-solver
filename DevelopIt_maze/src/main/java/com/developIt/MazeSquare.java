package com.developIt;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import java.util.Optional;

public class MazeSquare {
    private final Location location;
    private MazeSquare rightNeighbour = null;
    private MazeSquare downNeighbour = null;
    private MazeSquare leftNeighbour = null;
    private MazeSquare upNeighbour = null;

    private final BooleanProperty wall = new SimpleBooleanProperty(false);
    private final BooleanProperty onPath = new SimpleBooleanProperty(false);
    private final BooleanProperty fish = new SimpleBooleanProperty(false);
    private final BooleanProperty pingu = new SimpleBooleanProperty(false);
    private Boolean usable = true;

    MazeSquare(Location location, Boolean isWall) {
        this.location = location;
        setWall(isWall);
    }

    public Optional<MazeSquare> getRightNeighbour() {
        return Optional.ofNullable(rightNeighbour);
    }

    public void setRightNeighbour(MazeSquare rightNeighbour) {
        this.rightNeighbour = rightNeighbour;
    }

    public Optional<MazeSquare> getDownNeighbour() {
        return Optional.ofNullable(downNeighbour);
    }

    public void setDownNeighbour(MazeSquare downNeighbour) {
        this.downNeighbour = downNeighbour;
    }

    public Optional<MazeSquare> getLeftNeighbour() {
        return Optional.ofNullable(leftNeighbour);
    }

    public void setLeftNeighbour(MazeSquare leftNeighbour) {
        this.leftNeighbour = leftNeighbour;
    }

    public Optional<MazeSquare> getUpNeighbour() {
        return Optional.ofNullable(upNeighbour);
    }

    public void setUpNeighbour(MazeSquare upNeighbour) {
        this.upNeighbour = upNeighbour;
    }

    public Boolean isUsable() {
        return usable;
    }

    public void setUsable(Boolean usable) {
        this.usable = usable;
    }

    Location getLocation() {
        return location;
    }

    int getX() {
        return location.X();
    }

    int getY() {
        return location.Y();
    }

    public Optional<MazeSquare> getNeighbour(MazeDirection direction) {
        return switch (direction) {
            case RIGHT -> getRightNeighbour();
            case DOWN -> getDownNeighbour();
            case LEFT -> getLeftNeighbour();
            case UP -> getUpNeighbour();
        };
    }

    public boolean isWall() {
        return wall.get();
    }

    public BooleanProperty wallProperty() {
        return wall;
    }

    public void setWall(boolean wall) {
        this.wall.set(wall);
    }

    public boolean isOnPath() {
        return onPath.get();
    }

    public BooleanProperty onPathProperty() {
        return onPath;
    }

    public void setOnPath(boolean onPath) {
        this.onPath.set(onPath);
    }

    public boolean isFish() {
        return fish.get();
    }

    public BooleanProperty fishProperty() {
        return fish;
    }

    public void setFish(boolean fish) {
        this.fish.set(fish);
    }

    public boolean isPingu() {
        return pingu.get();
    }

    public BooleanProperty pinguProperty() {
        return pingu;
    }

    public void setPingu(boolean pingu) {
        this.pingu.set(pingu);
    }
}
