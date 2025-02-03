package com.orel6505.pgames.entity;

public class Obstacle implements Positionable {
    private String name;
    private Position position;

    public Obstacle(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public Position getPosition() {
        return position;
    }

    @Override
    public void setPosition(Position position) {
        this.position = position;
    }

    @Override
    public int getXPosition() {
        return position != null ? position.getX() : -1;
    }

    @Override
    public int getYPosition() {
        return position != null ? position.getY() : -1;
    }
    
}
