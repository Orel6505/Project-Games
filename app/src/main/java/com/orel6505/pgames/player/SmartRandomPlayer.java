package com.orel6505.pgames.player;

import com.orel6505.pgames.action.Action;

public class SmartRandomPlayer extends RandomPlayer{

    public SmartRandomPlayer(String name) {
        super(name);
    }

    @Override
    public Action selectAction(Action[] actions) {
        Action bestMove = currentGame.getBestMove(this);
        if (bestMove != null) {
            return bestMove;
        }
        return super.selectAction(actions);
    }
    
}
