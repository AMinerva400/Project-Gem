package com.omnipotence.game.Stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.omnipotence.game.Main;

/**
 * Copyright 2015, Omnipotence, LLC, All rights reserved.
 * Created by Omnipotence, LLC.
 */

public class scoreSaver {

    private scoreData score = new scoreData();
    private Json json = new Json();

    public void saveScores(userData p1Score, userData p2Score){
        loadScores();
        updateScores(p1Score, p2Score);

        FileHandle scoreHandle = Gdx.files.local(Main.language + "/UserData/scores.json");
        scoreHandle.writeString(json.toJson(score), false);
    }

    private void loadScores(){
        FileHandle scoreHandle = Gdx.files.local(Main.language + "/UserData/scores.json");
        if (!scoreHandle.exists()) {
            FileHandle levelHandle = Gdx.files.local(Main.language + "/UserData/scores.json");
            levelHandle.writeString(json.toJson(score), false);
        }; // Create the score file.
        scoreData temp = json.fromJson(scoreData.class, scoreHandle);
        score.p1correct = temp.p1correct;
        score.p1missed = temp.p1missed;
        score.p1countered = temp.p1countered;
        score.p2correct = temp.p2correct;
        score.p2missed = temp.p2missed;
        score.p2countered = temp.p2countered;
    }

    private void updateScores(userData p1Score, userData p2Score){
        score.p1correct = score.p1correct + p1Score.getTimesCorrect();
        score.p1missed = score.p1missed + p1Score.getTimesMissed();
        score.p1countered = score.p1countered + p1Score.getTimesCounter();

        score.p2correct = score.p2correct + p2Score.getTimesCorrect();
        score.p2missed = score.p2missed + p2Score.getTimesMissed();
        score.p2countered = score.p2countered + p2Score.getTimesCounter();
    }
}
