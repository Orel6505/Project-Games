package com.orel6505.pgames.player;

import java.util.Stack;

import com.orel6505.pgames.action.Action;
import com.orel6505.pgames.action.Actionable;
import com.orel6505.pgames.entity.Position;
import com.orel6505.pgames.entity.Positionable;

public abstract class Player implements Actionable, Scorable, Positionable {
    private String name;
    private Integer score;
    private Stack<Action> actions;
    private boolean isActive;
    private Position position;

    protected Player(String name){
        this.name = name;
        this.score = 0;
        this.actions = new Stack<>();
    }

    public boolean isWinner(Scorable p){
        return (this.score > p.getScore());
    }

    public void increaseScore(){
        updateScore(1);
    }

    public void updateScore(Integer score){
        this.score = this.score + score;
    }

    public Integer getScore(){
        return this.score;
    }

    public String getName(){
        return this.name;
    }

    public boolean isActionsEmpty() {
        return this.actions.isEmpty();
    }

    public Action popAction() {
        if (this.actions.isEmpty()) {
            return null;
        }
        return this.actions.pop();
    }

    public void pushAction(Action action) {
        this.actions.push(action);
    }

    public boolean isActive() {
        return this.isActive;
    }

    public void resetActive() {
        this.isActive = true;
    }

    public void setNotActive() {
        this.isActive = false;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
    
    public Position getPosition() {
        return this.position;
    }

    public int getXPosition() {
        return this.position.getX();
    }

    public int getYPosition() {
        return this.position.getY();
    }
}