package com.orel6505.pgames.player;

import java.util.Stack;

import com.orel6505.pgames.action.Actionable;
import com.orel6505.pgames.entity.Position;
import com.orel6505.pgames.entity.Positionable;
import com.orel6505.pgames.item.Item;

public abstract class Player implements Actionable, Scorable, Positionable {
    private String name;
    private Integer score;
    private Stack<Item> inventory;
    private Position position;

    protected Player(String name){
        this.name = name;
        this.score = 0;
        this.inventory = new Stack<>();
    }

    public String getName(){
        return this.name;
    }

    // Score related
    public void increaseScore(){
        updateScore(1);
    }

    public void updateScore(Integer score){
        this.score = score;
    }

    public Integer getScore(){
        return this.score;
    }

    // Inventory related
    public boolean isInventoryEmpty() {
        return this.inventory.isEmpty();
    }

    public Item popItem() {
        if (this.inventory.isEmpty()) {
            return null;
        }
        return this.inventory.pop();
    }

    public void pushItem(Item item) {
        this.inventory.push(item);
    }
    
    // positionable
    @Override
    public void setPosition(Position position) {
        this.position = position;
    }

    @Override
    public Position getPosition() {
        return this.position;
    }

    @Override
    public int getXPosition() {
        return this.position.getX();
    }

    @Override
    public int getYPosition() {
        return this.position.getY();
    }
}