package com.orel6505.pgames.game;

import java.util.Random;

import com.orel6505.pgames.entity.Entity;

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

    protected int getRandomPosition(){
        return this.rnd.nextInt(0, this.boardSize);
    }

    protected boolean isPositionEmpty(int x, int y){
        return this.board[x][y] == null;
    }
    
    protected void setEntityInRandomPosition(Entity entity){
        int xPosition;
        int yPosition;
        do {
            xPosition = getRandomPosition();
            yPosition = getRandomPosition();
        } while (!isPositionEmpty(xPosition, yPosition)); 
        this.board[xPosition][yPosition] = entity;
    }

    protected void setEntitiesInRandomPositions(Entity entity, int count){
        for(int i=0;i<count;i++){
            setEntityInRandomPosition(entity);
        }
    }

    protected boolean isInBounds(int x, int y) {
        return x >= 0 && x < boardSize && y >= 0 && y < boardSize;
    }
}
