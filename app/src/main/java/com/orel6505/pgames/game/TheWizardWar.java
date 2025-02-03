package com.orel6505.pgames.game;

import java.util.ArrayList;
import java.util.List;

import com.orel6505.pgames.action.Action;
import com.orel6505.pgames.entity.Obstacle;
import com.orel6505.pgames.entity.Position;
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

    public TheWizardWar(){
        super("Wizard Wars", MAX_PLAYERS, BOARD_SIZE);

        this.actions = new Action[]{
            new Action("A"),
            new Action("S"),
            new Action("W"),
            new Action("D")
        };
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
        for (Player p : this.players) {
            p.resetActiveInRound();
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
        p.deactivateForRound();
        this.board[p.getXPosition()][p.getYPosition()] = null;
    }

    @Override
    public void play(Integer turnCount) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'play'");
    }

    @Override
    protected void turn() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'turn'");
    }

    @Override
    protected void judge(Player p1, Player p2) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'judge'");
    }
}
