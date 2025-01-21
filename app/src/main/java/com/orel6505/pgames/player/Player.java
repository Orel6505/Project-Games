package com.orel6505.pgames.player;

import com.orel6505.pgames.action.Action;
import com.orel6505.pgames.action.Actionable;

public abstract class Player implements Actionable, Scorable {
    private String name;
    private Integer score;

    protected Player(String name){
        this.name = name;
        this.score = 0;
    }

    public Boolean isWinner(Scorable p){
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
}