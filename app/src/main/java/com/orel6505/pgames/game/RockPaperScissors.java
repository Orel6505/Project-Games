package com.orel6505.pgames.game;

import com.orel6505.pgames.action.Action;
import com.orel6505.pgames.player.Player;

public class RockPaperScissors extends Game {
    public static final String ROCK = "Rock";
    public static final String PAPER = "Paper";
    public static final String SCISSORS = "Scissors";

    public RockPaperScissors(Player p1, Player p2){
        super("Rock Paper Scissors", 2);
        this.players.add(p1);
        this.players.add(p2);
        this.actions = new Action[]{new Action(ROCK), 
                                    new Action(PAPER), 
                                    new Action(SCISSORS)};
    }

    @Override
    public Action getBestMove(Player player) {
        return null;
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
        
        boolean p1Wins = (a1.getName().equals(actions[0].getName()) && a2.getName().equals(actions[2].getName())) ||
                         (a1.getName().equals(actions[1].getName()) && a2.getName().equals(actions[0].getName())) ||
                         (a1.getName().equals(actions[2].getName()) && a2.getName().equals(actions[1].getName()));
                    
        if (p1Wins) {
            p1.increaseScore();
        } else {
            p2.increaseScore();
        }
    }
}
