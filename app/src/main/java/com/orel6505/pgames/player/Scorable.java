package com.orel6505.pgames.player;

public interface Scorable {
    //Get the score of the player
    Integer getScore();

    //Update the score of the player
    void increaseScore();
    void updateScore(Integer score);
}
