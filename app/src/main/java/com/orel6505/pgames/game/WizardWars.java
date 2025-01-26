package com.orel6505.pgames.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.orel6505.pgames.action.Action;
import com.orel6505.pgames.entity.Position;
import com.orel6505.pgames.player.Player;

public class WizardWars extends BoardGame {
    private static final int MAX_PLAYERS = 10;
    private static final int BOARD_SIZE = 10;
    protected static final Action[] weapons = new Action[]{
                new Action("Fireball"),   //Rock
                new Action("Magic ring"), //Paper
                new Action("Sword")       //Scissors
    };

    public WizardWars(List<Player> players){
        super("Wizard Wars", MAX_PLAYERS, BOARD_SIZE);

        this.actions = new Action[]{new Action("A"),
                                    new Action("S"),
                                    new Action("W"),
                                    new Action("D")};
        
        this.players = players;
    }

    public void printBoard(){
        for(int i=0;i<BOARD_SIZE;i++){
            for(int j=0;j<BOARD_SIZE;j++){
                if(this.board[i][j] instanceof Player p){
                    System.out.print(p.getName().charAt(0));
                } else if(this.board[i][j] instanceof Action a){
                    if(a.getName().equals("Tree")){
                        System.out.print("T");
                    } else{
                        System.out.print(a.getName().charAt(0));
                    }
                } else {
                    System.out.print("*");
                }
                System.out.print(" ");
            }
            System.out.println();
        }
        System.out.println();
    }

    // --------------------------- Game Setup ------------------------------------------//
    @Override
    protected void resetBoard(){
        for(int i=0;i<BOARD_SIZE;i++){
            for(int j=0;j<BOARD_SIZE;j++){
                this.board[i][j] = null;
            }
        }
        setEntitiesInRandomPositions(new Action("Tree"), 3);
        for(Action a : weapons){
            setEntitiesInRandomPositions(a, 2);
        }
        resetPlayersState();
    }

    // --------------------------- Players Functions ------------------------------------------//
    private void resetPlayersState(){
        for (Player p : this.players) {
            p.resetActive();
            p.setPosition(new Position(getRandomPosition(), getRandomPosition()));
        }
    }
    
    private List<Player> getActivePlayers() {
        List<Player> activePlayers = new ArrayList<>();
        this.players.forEach(p -> {
            if (p.isActive()) {
                activePlayers.add(p);
            }
        });
        return activePlayers;
    }

    private List<Player> getPlayersAtRange(Player p, int range) {
        List<Player> playersInRange = new ArrayList<>();
        int x = p.getXPosition();
        int y = p.getYPosition();
        
        for (int i = Math.max(0, x - range); i <= Math.min(BOARD_SIZE - 1, x + range); i++) {
            for (int j = Math.max(0, y - range); j <= Math.min(BOARD_SIZE - 1, y + range); j++) {
                if (board[i][j] instanceof Player player && !player.equals(p)) {
                    playersInRange.add(player);
                }
            }
        }
        return playersInRange;
    }

    public Player whoWon() {
        if (this.players.isEmpty()) {
            return null;
        }
        
        return this.players.stream()
                .reduce((p1, p2) -> p1.isWinner(p2) ? p1 : p2)
                .orElse(null);
    }

    private void addWeapon(Player p, Action action){
        if(action != null){
            p.pushAction(action);
        }
    }

    // --------------------------- Game Functions------------------------------------------//
    //definitely a pokemon reference
    private void encounter(Player p1, Player p2) {
        boolean p1Empty = p1.isActionsEmpty();
        boolean p2Empty = p2.isActionsEmpty();
        
        if (p1Empty && p2Empty) {
            //Do nothing
        } else if (p1Empty) {
            p1.setNotActive();
        } else if (p2Empty) {
            p2.setNotActive();
        } else {
            judge(p1, p2);
        }
    }

    @Override
    protected void judge(Player p1, Player p2){
        Game game = new FireBallSwordMagicRing(p1, p2);
        game.judge(p1, p2);
        if(p1.isActive()){
            this.board[p2.getXPosition()][p2.getYPosition()] = null;
        } else {
            this.board[p1.getXPosition()][p1.getYPosition()] = null;
        }
    }

    @Override
    public void play(Integer turnCount) {
        for (int i = 0; i < turnCount; i++) {
            resetBoard();
            playRound();
        }
        Player won = whoWon();
        for (Player p : this.players) {
            System.out.println("Player "+p.getName()+" got "+p.getScore());
        }
        System.out.println("Game winner: " + won.getName());
    }

    private void playRound() {
        while (getActivePlayers().size() > 1) {
            for (Player player : getActivePlayers()) {
                char direction = selectValidDirection(player);
                movePlayer(player, direction);

                List<Player> opponent = getPlayersAtRange(player, 1);
                for (Player opp : opponent) {
                    encounter(player, opp);
                    if (!player.isActive() || !opp.isActive()) {
                        break;
                    }
                }
            }
            if (getActivePlayers().size() == 1) {
                System.out.println("round winner: " + getActivePlayers().get(0).getName());
                getActivePlayers().get(0).increaseScore();
            }
        }
    }

    private void movePlayer(Player player, char direction) {
        Position pos = player.getPosition();
        if (pos == null) return;
        
        int x = pos.getX();
        int y = pos.getY();
        this.board[x][y] = null;

        switch (direction) {
            case 'W' -> x--;
            case 'S' -> x++;
            case 'A' -> y--;
            case 'D' -> y++;
            default -> { /* do nothing */ }
        }
        
        if(Arrays.asList(weapons).contains(this.board[x][y])){
            addWeapon(player, (Action)this.board[x][y]);
        }

        if (isInBounds(x, y) && !isBlocked(x, y)) {
            player.setPosition(new Position(x, y));
            this.board[x][y] = player;
        } else {
            player.setPosition(pos);
            this.board[pos.getX()][pos.getY()] = player;
        }
    }

    private char selectValidDirection(Player player) {
        char direction;
        do {
            direction = player.selectActions(actions).getName().charAt(0);
        } while (!isValidMove(player, direction));
        return direction;
    }

    // --------------------------- Validations ------------------------------------------//

    private boolean isValidMove(Player player, char direction) {
        int x = player.getXPosition();
        int y = player.getYPosition();
        switch (direction) {
            case 'A': return y > 0;  // Left
            case 'D': return y < BOARD_SIZE - 1;  // Right
            case 'W': return x > 0;  // Up
            case 'S': return x < BOARD_SIZE - 1;  // Down
            default: return false;
        }
    }
    
    private boolean isBlocked(int x, int y) {
       if(this.board[x][y] instanceof Action a){
            return a.getName().equals("Tree");
       }
       return false;
    }
}