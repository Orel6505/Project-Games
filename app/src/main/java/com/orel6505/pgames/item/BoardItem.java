package com.orel6505.pgames.item;

import com.orel6505.pgames.entity.Position;
import com.orel6505.pgames.entity.Positionable;

public class BoardItem extends Item implements Positionable {
    private Position position;

    public BoardItem(String name) {
        super(name);
        this.position = null;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof BoardItem)) return false;
        
        BoardItem other = (BoardItem) obj;
        return super.equals(obj) && (position != null ? position.equals(other.position) : other.position == null);
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