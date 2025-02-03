package com.orel6505.pgames.game;

import com.orel6505.pgames.action.Action;
import com.orel6505.pgames.player.Player;

public interface GamePlayer {
    public boolean isValidMove(Player p, Action a);
    public Action getBestMove(Player p);
}
