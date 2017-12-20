package com.omnipotence.game.Stage;

import com.badlogic.gdx.Screen;
import com.omnipotence.game.Battle.battleMode;
import com.omnipotence.game.Main;
import com.omnipotence.game.Practice.defaultPracticeMode;
import com.omnipotence.game.Practice.graphemesMode;
import com.omnipotence.game.Practice.mathPracticeMode;
import com.omnipotence.game.Practice.noTexturePracticeMode;
import com.omnipotence.game.Practice.questionMode;
import com.omnipotence.game.Practice.rimeFamilyMode;
import com.omnipotence.game.Practice.toolsMode;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Copyright 2015, Omnipotence, LLC, All rights reserved.
 * Created by Omnipotence, LLC.
 * Stage class, enough said.
 */

public class gameStage {

    private String stageName;
    private ArrayList<gameLevel> levels = new ArrayList<gameLevel>();
    private char type;
    private int currentLevelID = 0;

    /**
     * This is the Constructor.
     * @param stageName: Name of the stage.
     * @param type: Character value that specify the stage type.
     * @param levelNames: List of all the levels in the stages.
     * @param hasBattleModes: Boolean value that determines the if the stage has battle modes.
     */
    public gameStage(String stageName, char type, ArrayList<String> levelNames,
                     Boolean hasBattleModes) {
        this.stageName = stageName;
        this.type = type;
        loadLevels((hasBattleModes) ? addBattles(levelNames) : levelNames);
    }

    /**
     * This function returns a list of level name with "BATTLE" at every 4 or 3 levels.
     */
    private ArrayList<String> addBattles(ArrayList<String> levelNames) {
        int j = levelNames.size();
        while(j > 4) {
            levelNames.add(j, "BATTLE");
            j-=3;
        }
        levelNames.add(j, "BATTLE");
        return levelNames;
    }

    /**
     * This function returns a boolean if the levels are loaded.
     */
    private boolean loadLevels(ArrayList<String> levelNames) {
        for(int i = 0; i < levelNames.size(); i++) {
            levels.add(new gameLevel(stageName, levelNames.get(i), i));
        }
        return true;
    }

    /**
     * This function returns a level name based on the @param i.
     */
    public String getLevelName(int i) {
        return levels.get(i).getLevelName();
    }

    /**
     * This function returns a list of level name that don't have the name "BATTLE".
     */
    public ArrayList<String> getLevelNames() {
        ArrayList<String> names = new ArrayList<String>();
        for (int i = 0; i < levels.size(); i++) {
            String name = getLevelName(i);
            if(!name.equals("BATTLE")) {
                names.add(name);
            }
        }
        return names;
    }

    /**
     * This function sets the value of id.
     */
    public void setCurrentLevel(int id) {
        this.currentLevelID = id;
    }

    /**
     * This function saves the level progress and unlocks the next level.
     * Returns true if the next level is unlocked.
     * False, if there are no more levels to unlock.
     */
    public boolean unlockNextLevel(double score) {
        gameLevel level = levels.get(currentLevelID);
        level.levelData.setScore(score);
        level.save();
        if(currentLevelID+1 >= levels.size()) {
            return false;
        }
        setCurrentLevel(currentLevelID+1);
        gameLevel nextLevel = levels.get(currentLevelID);
        nextLevel.levelData.setUnlocked(true);
        nextLevel.save();
        return true;
    }

    /**
     * This function returns the name of the stage.
     */
    public String getStageName() {
        return stageName;
    }

    /**
     * This function returns the number of level for the stage.
     */
    public int getLevelSize() {
        return levels.size();
    }

    /**
     * This function returns a level based on the @param i.
     */
    public gameLevel getLevel(int i) {
        return levels.get(i);
    }

    /**
     * This function returns a practice mode Screen.
     */
    public Screen playLevel(Main main, int start, int end) {
        ArrayList<String> arr = new ArrayList<String>();
        String levelName = getLevelName(currentLevelID);
        //Return a Battle Mode Screen if the current level's name is "BATTLE"
        if(levelName.equals("BATTLE")) {
            String battleString = "mnq";
            int j = currentLevelID-1;
            double wisePoint = 0;
            while(j > -1 && !getLevelName(j).equals("BATTLE")) {
                arr.add(getLevelName(j));
                wisePoint += getLevel(j).levelData.getScore();
                j--;
            }
            return new battleMode(main, arr, (int) Math.ceil(wisePoint/arr.size()),
                    battleString.indexOf("m")+1, start, end);
        }
        //Fills up arr.
        switch (type) {
            case 'q':
                for (String o : main.answerKeyForTheQuestions.values()) {
                    arr.add(o);
                }
                break;
            default:
                for(gameLevel level : levels) {
                    String name = level.getLevelName();
                    if (!name.equals("BATTLE")) {
                        arr.add(name);
                    }
                }
                break;
        }
        //Returns the PracticeMode Screen based on the stage type.
        switch (type) {
            case 'm':
                ArrayList<String> nums = new ArrayList<String>();
                for(int i = 0; i < levels.size(); i++) {
                    int num = (int) (Math.random() * 99);
                    nums.add(""+num);
                }
                return new mathPracticeMode(main, getLevelName(currentLevelID), nums, start, end);
            case 'q':return new questionMode(main, getLevelName(currentLevelID),
                    main.answerKeyForTheQuestions.get(getLevelName(currentLevelID)),arr,start,end);
            case 't': return new toolsMode(main, getLevelName(currentLevelID), arr, start, end);
            case 'g': return new graphemesMode(main, getLevelName(currentLevelID), arr, start, end);
            case 'r': return new rimeFamilyMode(main, getLevelName(currentLevelID), arr, start, end);
            case 'n': return new noTexturePracticeMode(main, getLevelName(currentLevelID), arr,
                    start, end);
            default:
                return new defaultPracticeMode(main, getLevelName(currentLevelID), arr, start, end);
        }
    }

    /**
     * This function returns the stage scrore.
     */
    public int getStageScore(int max) {
        Double score = 0.0;
        for(gameLevel level : levels) {
            score += level.levelData.getScore();
        }
        //System.out.println("Total Score: "+score+" Level Size: "+levels.size());
        score /= levels.size();
        score *= .1;
        score *= max;
        //System.out.println("Stage score: "+score+"/"+max);
        return score.intValue();
    }

    /**
     * This function returns the value of type.
     */
    public char getType(){
        return type;
    }
}
