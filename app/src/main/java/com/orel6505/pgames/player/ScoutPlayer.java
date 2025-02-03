package com.orel6505.pgames.player;

import com.orel6505.pgames.action.Action;

public class ScoutPlayer extends Player {
    private Integer direction;
    private Action lastAction;

    public ScoutPlayer(String name) {
        super(name);
        direction = 0;
        lastAction = null;
    }

    @Override
    public Action selectAction(Action[] actions) {
        Action currentAction = actions[direction];
        
        // If same action was returned last time, it was likely invalid
        if (currentAction.equals(lastAction)) {
            direction = (direction + 1) % actions.length;
            currentAction = actions[direction];
        }
        
        lastAction = currentAction;
        return currentAction;
    }
}