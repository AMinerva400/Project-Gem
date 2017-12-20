package com.omnipotence.game.Stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import com.omnipotence.game.Main;

/**
 * Copyright 2015, Omnipotence, LLC, All rights reserved.
 * Created by Omnipotence, LLC.
 * This Class is the level Object.
 */

public class gameLevel {

    private String stageName;
    private String levelName;
    private int levelID;
    public gameData levelData; // Data about the level
    private Json json; // JSON object for file writing.

    /**
     * This is the Constructor.
     * @param stageName: Name of the stage the level belongs to.
     * @param levelName: Name of the level.
     * @param id: Id of the level.
     */
    gameLevel(String stageName, String levelName, int id) {
        this.stageName = stageName;
        this.levelName = levelName;
        this.levelID = id;
        this.levelData = new gameData();
        this.json = new Json(JsonWriter.OutputType.minimal);
        loadData();
    }

    /**
     * This function returns the level name
     */
    public String getLevelName() {
        return levelName;
    }

    /**
     * This function loads level data.
     */
    private void loadData() {
        FileHandle levelHandle = Gdx.files.local(Main.language + "/Stages/" +
                stageName + "/level" + levelID + ".json");
        if (!levelHandle.exists()) save(); // Create the LevelData file.

        this.levelData = json.fromJson(gameData.class, levelHandle);
    }

    /**
     * This function saves level data.
     */
    void save() {
        FileHandle levelHandle = Gdx.files.local(Main.language + "/Stages/" +
                stageName + "/level" + levelID + ".json");
        levelHandle.writeString(json.toJson(levelData), false);
    }

}
