package com.orel6505.pgames.game;

import java.util.ArrayList;
import java.util.List;

import com.orel6505.pgames.action.Action;
import com.orel6505.pgames.entity.Obstacle;
import com.orel6505.pgames.entity.Position;
import com.orel6505.pgames.entity.Positionable;
import com.orel6505.pgames.item.BoardItem;
import com.orel6505.pgames.item.Item;
import com.orel6505.pgames.player.Player;

public class TheWizardWar extends BoardGame{
    private static final int MAX_PLAYERS = 10;
    private static final int BOARD_SIZE = 10;

    public static final String TREE = "Tree";
    public static final String FIREBALL = "Fireball";
    public static final String MAGIC_RING = "Magic ring";
    public static final String SWORD = "Sword";

    List<Player> activePlayers;
    public TheWizardWar(){
        super("Wizard Wars", MAX_PLAYERS, BOARD_SIZE);

        this.actions = new Action[]{
            new Action("A"),
            new Action("S"),
            new Action("W"),
            new Action("D")
        };

        this.activePlayers = players;
    }

    public void printBoard(){
        for(int i=0;i<BOARD_SIZE;i++){
            for(int j=0;j<BOARD_SIZE;j++){
                if(this.board[i][j] instanceof Player p){
                    System.out.print(p.getName().charAt(0));
                } else if(this.board[i][j] instanceof Positionable a){
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

    @Override
    protected void resetBoard(){
        for(int i=0;i<BOARD_SIZE;i++){
            for(int j=0;j<BOARD_SIZE;j++){
                this.board[i][j] = null;
            }
        }
        // Need to fix this 
        for (int i = 0; i < 3; i++) {
            setEntityInRandomPosition(new Obstacle(TREE));
        }
        for (int i = 0; i < 2; i++) {
            setEntityInRandomPosition(new BoardItem(SWORD));
        }
        for (int i = 0; i < 2; i++) {
            setEntityInRandomPosition(new BoardItem(FIREBALL));
        }
        for (int i = 0; i < 2; i++) {
            setEntityInRandomPosition(new BoardItem(MAGIC_RING));
        }
        resetPlayersState();
        for(Player p : this.players){
            setEntityInPosition(p, getRandomPosition());
        }
    }

    private void resetPlayersState(){
        this.activePlayers = new ArrayList<>(this.players);
        for (Player p : this.players) {
            p.setPosition(null);
        }
    }

    private void addWeapon(Player p, BoardItem item){
        if(item != null && item.getPosition() != null){
            p.pushItem(item);
            this.board[item.getXPosition()][item.getYPosition()] = null;
            item.setPosition(null);
        }
    }

    private Action playerChooseAction(Player player) {
        Action direction;
        do {
            direction = player.selectAction(actions);
        } while (!isValidMove(player, direction));
        return direction;
    }

    @Override
    public boolean isValidMove(Player p, Action action) {
        Position pos = p.getPosition();
        Position tempPos = applyDirection(pos, action);
        
        return isInBounds(tempPos) && !(this.board[tempPos.getX()][tempPos.getY()] instanceof Obstacle);
    }

    public Position applyDirection(Position position, Action action) {
        switch (action.getName()) {
            case "W": return new Position(position.getX(), position.getY() - 1);
            case "S": return new Position(position.getX(), position.getY() + 1);
            case "D": return new Position(position.getX() + 1, position.getY());
            case "A": return new Position(position.getX() - 1, position.getY());
            default: return position;
        }
    }

    @Override
    public Action getBestMove(Player p) {
        BoardItem weaponInRange = getWeaponsAtRange(p, 1).get(0);
        Player playerInRange = getPlayersAtRange(p, 1).get(0);
        
        Position playerPos = p.getPosition();
        
        if (weaponInRange.getPosition() != null) {
            return subPositions(playerPos, weaponInRange.getPosition());
        } else if (playerInRange.getPosition() != null) {
            return subPositions(playerPos, playerInRange.getPosition());
        }
        return null;
    }

    public Action subPositions(Position start, Position end) {
        int dx = end.getX() - start.getX();
        int dy = end.getY() - start.getY();

        if (dx == 0 && dy == -1) {
            return new Action("W");
        } else if (dx == 0 && dy == 1) {
            return new Action("S");
        } else if (dx == 1 && dy == 0) {
            return new Action("D");
        } else if (dx == -1 && dy == 0) {
            return new Action("A");
        } else {
            return null;
        }
    }

    public List<Player> getPlayersAtRange(Player p, int range) {
        List<Player> playersInRange = new ArrayList<>();
        int x = p.getXPosition();
        int y = p.getYPosition();
        
        for (int i = Math.max(0, x - range); i <= Math.min(BOARD_SIZE - 1, x + range); i++) {
            for (int j = Math.max(0, y - range); j <= Math.min(BOARD_SIZE - 1, y + range); j++) {
                // Skip diagonal positions.
                if (i != x && j != y) {
                    continue;
                }
                if (board[i][j] instanceof Player player && !player.equals(p)) {
                    playersInRange.add(player);
                }
            }
        }
        return playersInRange;
    }
    
    public List<BoardItem> getWeaponsAtRange(Player p, int range) {
        List<BoardItem> itemsInRange = new ArrayList<>();
        int x = p.getXPosition();
        int y = p.getYPosition();
        
        for (int i = Math.max(0, x - range); i <= Math.min(BOARD_SIZE - 1, x + range); i++) {
            for (int j = Math.max(0, y - range); j <= Math.min(BOARD_SIZE - 1, y + range); j++) {
                // Skip diagonal positions.
                if (i != x && j != y) {
                    continue;
                }
                if (board[i][j] instanceof BoardItem item && item.getPosition() != null) {
                    itemsInRange.add(item);
                }
            }
        }
        return itemsInRange;
    }

    private void deactivatePlayer(Player p){
        removePlayerInRound(p);
        this.board[p.getXPosition()][p.getYPosition()] = null;
    }

    private Player encounter(Player p1, Player p2) {
        
        if (!isActiveInRound(p1)) {
            return p2;
        }
        if (!isActiveInRound(p2)) {
            return p1;
        }
        judge(p1, p2);
        if (!isActiveInRound(p1)) {
            deactivatePlayer(p1);
            return p2;
        }
        else {
            deactivatePlayer(p2);
            return p1;
        }
}

    private boolean isActiveInRound(Player p) {
        return this.activePlayers.contains(p);
    }

    private void removePlayerInRound(Player p) {
        this.activePlayers.remove(p);
    }

    @Override
    public void play(Integer turnCount) {
        for (int i = 0; i < turnCount; i++) {
            resetBoard();
            System.out.println("Turn " + (i+1));
            turn();
        }
        Player won = getWinner();
        int x = 0;
        for (Player p : this.players) {
            System.out.println("Player "+p.getName()+" got "+p.getScore());
            x += p.getScore();
        }
        System.out.println("Game winner: " + won.getName());
        System.out.println("Total points: "+x);
    }

    private void movePlayer(Player player, Action direction) {
        Position currentPos = player.getPosition();
        if (currentPos == null) {
            return;
        }
        
        // Clear the player's current position.
        int oldX = currentPos.getX();
        int oldY = currentPos.getY();
        this.board[oldX][oldY] = null;
        
        // Calculate the new position.
        Position newPos = applyDirection(currentPos, direction);
        int newX = newPos.getX();
        int newY = newPos.getY();
        
        // Check for an object at the destination.
        Object possibleInteraction = this.board[newX][newY];
        if (possibleInteraction instanceof BoardItem item) {
                    addWeapon(player, boardItem);
                    // Place the player after collecting the item.
                    this.board[newX][newY] = player;
        } else if (possibleInteraction instanceof Player otherPlayer) {
            if (newX == otherPlayer.getXPosition() && newY == otherPlayer.getYPosition()) {
                encounter(player, otherPlayer);
                if (isActiveInRound(player)) {
                    this.board[newX][newY] = player;
                } else if (isActiveInRound(otherPlayer)) {
                    this.board[newX][newY] = otherPlayer;
                } else {
                    this.board[newX][newY] = null;
                }
            }
        } else {
            this.board[newX][newY] = player;
        }
    }

    @Override
    protected void turn() {
        while (activePlayers.size() > 1) {
            printBoard();
            
            List<Player> currentTurnPlayers = new ArrayList<>(activePlayers);
            for (Player player : currentTurnPlayers) {
                if (!isActiveInRound(player)) {
                    continue;
                }
                
                // Let the player choose and execute a move.
                Action direction = playerChooseAction(player);
                movePlayer(player, direction);
            }
        }
        
        if (activePlayers.size() == 1) {
            declareRoundWinner(activePlayers.get(0));
            activePlayers.get(0).increaseScore();
        }
    }

    @Override
    protected void judge(Player p1, Player p2) {
        // Both players must have at least one item.
        Item a1 = p1.popItem();
        Item a2 = p2.popItem();
        
        if (a1 == null){
            this.activePlayers.remove(p1);
            System.out.println(p2.getName() + " wins the encounter!");
            return;
        } 
        if (a2 == null) {
            this.activePlayers.remove(p2);
            System.out.println(p1.getName() + " wins the encounter!");
            return;
        }

        // Log choices.
        System.out.println(p1.getName() + " selected " + a1.getName());
        System.out.println(p2.getName() + " selected " + a2.getName());
        System.out.println();
        
        // If the items are equal, treat it as a tie:
        if (a1.getName().equals(a2.getName())) {
            System.out.println("Tie! " + p1.getName() + " deactivates.");
            removePlayerInRound(p1);
            // Optionally, you might push one of the items back.
            p2.pushItem(a1);
            return;
        }
        
        // Determine win based on game rules.
        boolean p1Wins = (a1.getName().equals(SWORD) && a2.getName().equals(FIREBALL)) ||
                        (a1.getName().equals(FIREBALL) && a2.getName().equals(MAGIC_RING)) ||
                        (a1.getName().equals(MAGIC_RING) && a2.getName().equals(SWORD));
        
        if (p1Wins) {
            System.out.println(p1.getName() + " wins the encounter!");
            removePlayerInRound(p2);
            // Optionally, transfer p2's item to p1.
            p1.pushItem(a2);
        } else {
            System.out.println(p2.getName() + " wins the encounter!");
            removePlayerInRound(p1);
            p2.pushItem(a1);
        }
    }
}
