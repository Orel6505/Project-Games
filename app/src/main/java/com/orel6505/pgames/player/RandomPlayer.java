package com.orel6505.pgames.player;

import com.orel6505.pgames.action.Action;

import java.util.Random;

public class RandomPlayer extends Player {
    private Random rnd;

    public RandomPlayer(String name){
        super(name);
        this.rnd = new Random();
    }

    @Override
    public Action selectAction(Action[] actions) {
        return actions[getRandomNumber(0, actions.length)];
    }
    
    @Override
    public Action selectActions(Action[] actions) {
        return selectAction(actions);
    }

    private int getRandomNumber(int min, int max) {
        return this.rnd.nextInt(min,max);
    }
}
