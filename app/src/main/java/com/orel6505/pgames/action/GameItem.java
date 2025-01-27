package com.orel6505.pgames.action;

import com.orel6505.pgames.entity.Position;
import com.orel6505.pgames.entity.Positionable;

public class GameItem extends Action implements Positionable {
    private Position position;

    public GameItem(String name) {
        super(name);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof GameItem)) return false;
        
        GameItem other = (GameItem) obj;
        return super.equals(obj) && 
               (position == null ? other.position == null 
                               : position.equals(other.position));
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (position != null ? position.hashCode() : 0);
        return result;
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