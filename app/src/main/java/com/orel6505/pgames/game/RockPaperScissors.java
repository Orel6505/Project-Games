package com.orel6505.pgames.game;

import com.orel6505.pgames.action.Action;
import com.orel6505.pgames.player.Player;

public class RockPaperScissors extends Game {

    public RockPaperScissors(Player p1, Player p2){
        super("Rock Paper Scissors", 2);
        this.players.add(p1);
        this.players.add(p2);
        this.actions = new Action[]{new Action("Rock"), 
                                    new Action("Paper"), 
                                    new Action("Scissors")};
    }

    @Override
    public void play(Integer turnCount){
        Player p1 = this.players.get(0);
        Player p2 = this.players.get(1);
        for (int i = 0; i < turnCount; i++){
            p1.pushAction(p1.selectAction(actions));
            p2.pushAction(p2.selectAction(actions));
            judge(p1, p2);
        }
    }

    @Override
    protected void judge(Player p1, Player p2) {
        Action a1 = p1.popAction();
        Action a2 = p2.popAction();
        System.out.println(p1.getName() + " selected " + a1.getName());
        System.out.println(p2.getName() + " selected " + a2.getName());
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
