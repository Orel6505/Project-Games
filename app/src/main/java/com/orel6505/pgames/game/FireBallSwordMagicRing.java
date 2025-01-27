package com.orel6505.pgames.game;

import com.orel6505.pgames.action.Action;
import com.orel6505.pgames.player.Player;

public class FireBallSwordMagicRing extends RockPaperScissors {
    public static final String FIREBALL = "Fireball";
    public static final String MAGIC_RING = "Magic ring";
    public static final String SWORD = "Sword";

    public FireBallSwordMagicRing(Player p1, Player p2) {
        super(p1, p2);
        this.actions = new Action[]{new Action(FIREBALL), 
                                    new Action(MAGIC_RING), 
                                    new Action(SWORD)};
    }

    @Override
    protected void judge(Player p1, Player p2) {
        Action a1 = p1.popAction();
        Action a2 = p2.popAction();
        
        System.out.println(p1.getName() + " selected " + a1.getName());
        System.out.println(p2.getName() + " selected " + a2.getName());
        System.out.println();
        
        if (a1.getName().equals(a2.getName())) {
            p1.setNotActive();
            p2.pushAction(a1);
            System.out.println("is a tie! and " + p1.getName() + " killed himself");
            return;
        }
        
        boolean p1Wins = (a1.getName().equals(SWORD) && a2.getName().equals(FIREBALL)) ||
                        (a1.getName().equals(FIREBALL) && a2.getName().equals(MAGIC_RING)) ||
                        (a1.getName().equals(MAGIC_RING) && a2.getName().equals(SWORD));
                        
        if (p1Wins) {
            p2.setNotActive();
            p1.pushAction(a2);
        } else {
            p1.setNotActive();
            p2.pushAction(a1);
        }
    }
}
