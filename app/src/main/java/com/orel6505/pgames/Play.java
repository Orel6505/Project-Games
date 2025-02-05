package com.orel6505.pgames;

import java.util.ArrayList;
import java.util.List;

import com.orel6505.pgames.game.Game;
import com.orel6505.pgames.game.TheWizardWar;
import com.orel6505.pgames.player.Player;
import com.orel6505.pgames.player.RandomPlayer;

public class Play {
    public static void main(String[] args) {
        List<Player> players = new ArrayList<>();
        Player p1 = new RandomPlayer("Orel");
        Player p2 = new RandomPlayer("Goku");
        players.add(p1);
        players.add(p2);
        Game game = new TheWizardWar();
        game.addPlayer(p1);
        game.addPlayer(p2);
        game.play(3);

        System.out.println(p1.getName()+" got "+p1.getScore()+ " and "
            +p2.getName()+" got "+p2.getScore());
    }
}