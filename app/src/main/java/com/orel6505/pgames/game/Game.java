package com.orel6505.pgames.game;

import java.util.List;
import java.util.ArrayList;

import com.orel6505.pgames.action.Action;
import com.orel6505.pgames.player.Player;

public abstract class Game {
    List<Player> players;
    String name;
    int maxPlayers;
    Action[] actions;

    protected Game(String name, int maxPlayers){
        this.players = new ArrayList<>();
        this.name = name;
        this.maxPlayers = maxPlayers;
    }

    public abstract void play(Integer turnCount);
    protected abstract void judge(Player p1, Player p2);

    protected void addPlayer(Player p){
        if(this.players.size() < maxPlayers){
            this.players.add(p);
        }
    }

    public String getName() {
        return name;
    }

    public Player getWinner(Player p1, Player p2){
        return p1.isWinner(p2) ? p1 : p2;
    }

    public List<Player> getPlayers() {
        return this.players;
    }
}
