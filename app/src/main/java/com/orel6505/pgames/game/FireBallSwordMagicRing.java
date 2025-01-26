package com.orel6505.pgames.game;

import com.orel6505.pgames.action.Action;
import com.orel6505.pgames.player.Player;

public class FireBallSwordMagicRing extends RockPaperScissors {
    public FireBallSwordMagicRing(Player p1, Player p2) {
        super(p1, p2);
        this.actions = new Action[]{new Action("Fireball"), 
                                    new Action("Magic ring"), 
                                    new Action("Sword")};
    }

    @Override
    public void judge(Player p1, Player p2){
        Action a1 = p1.popAction();
        Action a2 = p2.popAction();
        System.out.println(p1.getName() + " selected " + a1.getName());
        System.out.println(p2.getName() + " selected " + a2.getName());
        if (a1.equals(actions[1]) && a2.equals(actions[0])) {
            p1.pushAction(a2);
            p2.setNotActive(); // p1 wins
        }
        if (a1.equals(actions[2]) && a2.equals(actions[1])) {
            p1.pushAction(a2);
            p2.setNotActive(); // p1 wins
        }
        if (a1.equals(actions[0]) && a2.equals(actions[1])) {
            p2.pushAction(a1);
            p1.setNotActive(); // p2 wins
        }
        if (a1.equals(actions[1]) && a2.equals(actions[2])) {
            p2.pushAction(a1);
            p1.setNotActive(); // p2 wins
        }
        //Tie
    }
}
