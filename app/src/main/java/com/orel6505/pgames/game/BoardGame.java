package com.orel6505.pgames.game;

import java.util.Random;

import com.orel6505.pgames.entity.Entity;
import com.orel6505.pgames.entity.Position;

public abstract class BoardGame extends Game {
    protected Entity[][] board;
    protected int boardSize;

    private Random rnd;
    
    protected BoardGame(String name, int maxPlayers, int boardSize) {
        super(name, maxPlayers);
        this.boardSize = boardSize;
        this.rnd = new Random();
        this.board = new Entity[boardSize][boardSize];
    }

    protected abstract void resetBoard();

    protected Position getRandomPosition(){
        return new Position(this.rnd.nextInt(0, this.boardSize), this.rnd.nextInt(0, this.boardSize));
    }

    protected boolean isPositionEmpty(int x, int y){
        return this.board[x][y] == null;
    }
    
    protected void setEntityInRandomPosition(Entity entity){
        Position position;
        do {
            position = getRandomPosition();
        } while (!isPositionEmpty(position.getX(), position.getY())); 
        this.board[position.getX()][position.getY()] = entity;
    }

    protected void setEntitiesInRandomPositions(Entity entity, int count){
        for(int i=0;i<count;i++){
            setEntityInRandomPosition(entity);
        }
    }

    protected boolean isInBounds(Position position) {
        return position.getX() >= 0 && position.getX() < boardSize 
            && position.getY() >= 0 && position.getY() < boardSize;
    }
}
