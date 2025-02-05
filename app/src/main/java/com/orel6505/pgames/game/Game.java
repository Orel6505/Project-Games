package com.orel6505.pgames.game;

import java.util.List;
import java.util.ArrayList;

import com.orel6505.pgames.action.Action;
import com.orel6505.pgames.player.Player;

public abstract class Game implements GamePlayer {
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
    protected abstract void turn();
    protected abstract void judge(Player p1, Player p2);

    public void addPlayer(Player p){
        if(this.players.size() < maxPlayers){
            this.players.add(p);
        }
    }
 
    public String getName() {
        return name;
    }

    public Player getWinner(){
        if (this.players.isEmpty()) {
            return null;
        }
        
        return this.players.stream()
                .reduce((p1, p2) -> (p1.getScore() > p2.getScore()) ? p1 : p2)
                .orElse(null);
    }

    protected void declareRoundWinner(Player winner) {
        System.out.println("round winner: " + winner.getName());
        winner.increaseScore();
    }

    public List<Player> getPlayers() {
        return this.players;
    }

    public void removePlayer(Player p){
        this.players.remove(p);
    }
}
