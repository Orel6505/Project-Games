package com.orel6505.pgames.entity;

public interface Positionable extends Entity{
    Position getPosition();
    void setPosition(Position position);
    public int getXPosition();
    public int getYPosition();
    public String getName();
}
