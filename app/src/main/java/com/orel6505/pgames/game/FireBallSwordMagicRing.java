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
}
