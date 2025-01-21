package com.orel6505.pgames.game;

import com.orel6505.pgames.action.Action;
import com.orel6505.pgames.player.Player;

public abstract class Game {
    Player p1;
    Player p2;
    String name;
    Action[] actions;

    protected Game(Player p1, Player p2, String name){
        this.p1 = p1;
        this.p2 = p2;
        this.name = name;
    }

    public abstract void play(Integer turnCount);
    public abstract void judge(Action a1, Action a2);

    public String getName() {
        return name;
    }

    public Player getWinner(){
        return p1.isWinner(p2) ? p1 : p2;
    }

    public Player getPlayerOne() {
        return p1;
    }

    public Player getPlayerTwo() {
        return p2;
    }
}
