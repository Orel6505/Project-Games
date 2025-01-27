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
    public static final String TREE = "Tree";
    protected static final Action[] weapons = new Action[]{
                new Action(FireBallSwordMagicRing.FIREBALL),   //Rock
                new Action(FireBallSwordMagicRing.MAGIC_RING), //Paper
                new Action(FireBallSwordMagicRing.SWORD)       //Scissors
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
                    if(a.getName().equals(TREE)){
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
        setEntitiesInRandomPositions(new Action(TREE), 3);
        for(Action a : weapons){
            setEntitiesInRandomPositions(a, 2);
        }
        resetPlayersState();
        for(Player p : this.players){
            setPlayerInPosition(p, getRandomPosition());
        }
    }

    // --------------------------- Players Functions ------------------------------------------//
    private void resetPlayersState(){
        for (Player p : this.players) {
            p.resetActive();
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

    private void setPlayerInPosition(Player p, Position pos){
        if (p.getPosition() != null) {
            this.board[p.getXPosition()][p.getYPosition()] = null;
        }
        p.setPosition(pos);
        this.board[pos.getX()][pos.getY()] = p;
    }

    private void deactivatePlayer(Player p){
        p.setNotActive();
        this.board[p.getXPosition()][p.getYPosition()] = null;
    }

    // --------------------------- Game Functions------------------------------------------//
    //definitely a pokemon reference
    private void encounter(Player p1, Player p2) {
        boolean p1Empty = p1.isActionsEmpty();
        boolean p2Empty = p2.isActionsEmpty();
        
        if (p1Empty && p2Empty) {
            deactivatePlayer(p1);
        } else if (p1Empty) {
            deactivatePlayer(p1);
        } else if (p2Empty) {
            deactivatePlayer(p2);
        } else {
            if (!p1.isActive()) {
                return;
            }
            judge(p1, p2);
        }
    }

    @Override
    protected void judge(Player p1, Player p2) {
        Game game = new FireBallSwordMagicRing(p1, p2);
        game.judge(p1, p2);
        
        if (p1.isActive() && p2.isActive()) {
            deactivatePlayer(p1);
            return;
        }
        if (!p1.isActive()) {
            deactivatePlayer(p1);
        }
        else { // (!p2.isActive())
            deactivatePlayer(p2);
        }
    }

    @Override
    public void play(Integer turnCount) {
        for (int i = 0; i < turnCount; i++) {
            resetBoard();
            System.out.println("Turn " + (i+1));
            playRound();
        }
        Player won = whoWon();
        int x = 0;
        for (Player p : this.players) {
            System.out.println("Player "+p.getName()+" got "+p.getScore());
            x += p.getScore();
        }
        System.out.println("Game winner: " + won.getName());
        System.out.println("Total points: "+x);
    }

    private void playRound() {
        while (getActivePlayers().size() > 1) {
            for (Player player : getActivePlayers()) {
                char direction = selectValidDirection(player);
                movePlayer(player, direction);

                List<Player> opponent = getPlayersAtRange(player, 1);
                for (Player opp : opponent) {
                    encounter(player, opp);
                }
            }
            if (getActivePlayers().size() == 1) {
                System.out.println("round winner: " + getActivePlayers().get(0).getName());
                getActivePlayers().get(0).increaseScore();
                break;
            }
        }
    }

    private void movePlayer(Player player, char direction) {
        Position pos = player.getPosition();
        if (pos == null) return;
        
        int x = pos.getX();
        int y = pos.getY();
        
        this.board[x][y] = null;
    
        applyDirToPosition(direction, pos);
        
        x = pos.getX();
        y = pos.getY();
    
        if(Arrays.asList(weapons).contains(this.board[x][y])){
            addWeapon(player, (Action)this.board[x][y]);
        }
    
        this.board[x][y] = player;
    }

    private char selectValidDirection(Player player) {
        char direction;
        do {
            direction = player.selectActions(actions).getName().charAt(0);
        } while (!isValidMove(player, direction));
        return direction;
    }

    private void applyDirToPosition(char direction, Position pos) {
        int x = pos.getX();
        int y = pos.getY();
        
        switch (direction) {
            case 'W': pos.setX(x - 1); break;  // Up
            case 'S': pos.setX(x + 1); break;  // Down
            case 'A': pos.setY(y - 1); break;  // Left
            case 'D': pos.setY(y + 1); break;  // Right
            default: break; // Do nothing
        }
    }

    // --------------------------- Validations ------------------------------------------//

    private boolean isValidMove(Player player, char direction) {
        Position pos = player.getPosition();
        Position tempPos = new Position(pos.getX(), pos.getY());
        
        applyDirToPosition(direction, tempPos);
        
        return isInBounds(tempPos) && !isBlocked(tempPos);
    }
    
    private boolean isBlocked(Position position) {
        if(this.board[position.getX()][position.getY()] instanceof Action a){
            return a.getName().equals(TREE);
        }
        return false;
    }
}