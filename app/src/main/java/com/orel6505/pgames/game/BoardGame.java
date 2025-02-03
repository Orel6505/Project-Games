package com.orel6505.pgames.game;

import java.util.Random;

import com.orel6505.pgames.entity.Entity;
import com.orel6505.pgames.entity.Position;
import com.orel6505.pgames.entity.Positionable;

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

    protected void setEntityInPosition(Positionable p, Position pos){
        if (p.getPosition() != null) {
            this.board[p.getXPosition()][p.getYPosition()] = null;
        }
        p.setPosition(pos);
        this.board[pos.getX()][pos.getY()] = p;
    }
    
    protected void setEntityInRandomPosition(Positionable entity){
        Position position;
        do {
            position = getRandomPosition();
        } while (!isPositionEmpty(position.getX(), position.getY()));
        setEntityInPosition(entity, position);
    }

    protected void setEntitiesInRandomPositions(Positionable entity, int count){
        for(int i=0;i<count;i++){
            setEntityInRandomPosition(entity);
        }
    }

    protected boolean isInBounds(Position position) {
        return position.getX() >= 0 && position.getX() < boardSize 
            && position.getY() >= 0 && position.getY() < boardSize;
    }
}
