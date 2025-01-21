package com.orel6505.pgames.game;

import com.orel6505.pgames.action.Action;
import com.orel6505.pgames.player.Player;

public class RockPaperScissors extends Game {

    public RockPaperScissors(Player p1, Player p2){
        super(p1, p2, "Rock Paper Scissors");
        this.actions = new Action[]{new Action("Rock"), 
                                    new Action("Paper"), 
                                    new Action("Scissors")};
    }

    @Override
    public void play(Integer turnCount){
        for (int i = 0; i < turnCount; i++){
            Action a1 = p1.selectAction(actions);
            Action a2 = p2.selectAction(actions);
            judge(a1, a2);
        }
    }

    @Override
    public void judge(Action a1, Action a2) {
        if (a1.equals(actions[1]) && a2.equals(actions[0])) {
            p1.increaseScore(); // p1 wins
        }
        if (a1.equals(actions[2]) && a2.equals(actions[1])) {
            p1.increaseScore(); // p1 wins
        }
        if (a1.equals(actions[0]) && a2.equals(actions[1])) {
            p2.increaseScore(); // p2 wins
        }
        if (a1.equals(actions[1]) && a2.equals(actions[2])) {
            p2.increaseScore(); // p2 wins
        }
        //Tie
    }
}
