package com.omnipotence.game.Stage;

/**
 * Copyright 2015, Omnipotence, LLC, All rights reserved.
 * Created by Omnipotence, LLC.
 * This Class is the level data Object.
 */

public class gameData {

    private boolean unlocked = true;
    private double score = 10;

    /**
     * This function returns the value of unlocked.
     */
    public boolean isUnlocked() {
        return unlocked;
    }

    /**
     * This function sets the value of unlocked.
     */
    public void setUnlocked(boolean unlocked) {
        this.unlocked = unlocked;
    }

    /**
     * This function sets the value of score.
     */
    public void setScore(double s) {
        this.score = s;
    }

    /**
     * This function returns the value of score.
     */
    public double getScore() {
        return this.score;
    }

}
