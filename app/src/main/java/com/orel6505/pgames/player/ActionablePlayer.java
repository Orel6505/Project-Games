package com.orel6505.pgames.player;

import java.util.Stack;

import com.orel6505.pgames.action.Action;

public abstract class ActionablePlayer extends Player {
    private Stack<Action> actions;

    protected ActionablePlayer(String name) {
        super(name);
        this.actions = new Stack<>();
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
}