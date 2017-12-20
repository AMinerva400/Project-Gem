package com.omnipotence.game.Stage;

/**
 * Copyright 2015, Omnipotence, LLC, All rights reserved.
 * Created by Omnipotence, LLC.
 * Multiplayer data Object.
 */

public class userData {

    private int timesCorrect, timesMissed, timesCountered;

    /**
     * This is the Constructor.
     * @param correct: Number of times the player got the right answer.
     * @param missed: Number of times the player got the wrong answer.
     * @param countered: Number of times the player got the right answer after the other player
     *                 got it wrong.
     */
    public userData(int correct, int missed, int countered) {
        this.timesCorrect = correct;
        this.timesMissed = missed;
        this.timesCountered = countered;
    }

    /**
     * This function adds one to timesCorrect.
     */
    public void gotCorrect() {
        timesCorrect++;
    }

    /**
     * This function adds one to timesMissed.
     */
    public void hasMissed() {
        timesMissed++;
    }

    /**
     * This function adds one to timesCountered.
     */
    public void hasCountered() {
        timesCountered++;
    }

    /**
     * This function returns number of times the player got the answer correct.
     */
    public int getTimesCorrect() {
        return timesCorrect;
    }

    /**
     * This function returns number of times the player got the answer incorrect.
     */
    public int getTimesMissed() {
        return timesMissed;
    }

    /**
     * This function returns number of times the player countered.
     */
    public int getTimesCounter() {
        return timesCountered;
    }
}
