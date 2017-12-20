package com.omnipotence.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.omnipotence.game.util.RendererManager;
import com.omnipotence.game.util.AssetManager;
import com.omnipotence.game.util.MusicManager;
import com.omnipotence.game.util.PreferencesManager;
import com.omnipotence.game.util.stageConstants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * The main game class. This will load all the assets needed by the game, as
 * well as initialize the background music and the LevelManager (which then
 * loads all the levels). It will then switch to the Main Menu screen.
 *
 * Copyright 2015, Omnipotence, LLC, All rights reserved.
 * @author Faizaan Datoo, Modified by Omnipotence, LLC
 *
 */
public class Main extends Game {

	/**
	 * 0 for English
	 * 1 for Swahili
	 * */
	private static final int langIndex = 0;
	public static final String[] characters = { "Mentor", "Chet", "Dan", "Dwight", "Heather", "Will"};
    public int[] trainingLevels;
    public int[][] characterLevelIntervals;
    public page[] storyPages;
	public static String language;
	//public LevelManager levelManager;
	public MusicManager musicManager;
    public ArrayList<com.omnipotence.game.Stage.gameStage> gameStages;
    public HashMap<String, String> answerKeyForTheQuestions;
	public SSIM androidSIMM;
    public int currentStage = 0;

	public Main(SSIM androidSIMM) {
		this.androidSIMM = androidSIMM;
	}

	@Override
	public void create() {
        setStages();
        loadAssets();

		RendererManager.getInstance();

		// Load the game preferences
		try {
			PreferencesManager.getInstance().loadPreferences();
			PreferencesManager.getInstance().checkDefaults();
		} catch (IOException e) { // Log to user device and exit the game.
			System.err.println("Error: Could not read the preferences file.");
			e.printStackTrace();
			Gdx.app.exit();
			return;
		}

        musicManager = new MusicManager();
        callNextScreen();
	}

	private void setStages() {
		switch (Main.langIndex) {
			case 1:
				gameStages = stageConstants.swahiliStages;
                trainingLevels = new int[]{0, 1, 2, 3};
                storyPages = new page[] {
                        new page("page1.png", "Mshauri anapenda kwenda nje na harufu maji ya miti"),
                        new page("page2.png", "Anapokwenda nje yeye anapenda kuvaa kofia yake"),
                        new page("page3.png", "Mshauri ni makini wakati yeye anatembea kwa " +
                                "sababu yeye hataki kuanguka katika pengo"),
                        new page("page4.png", "Wakati atakaporudi nyumbani Mshauri anapenda " +
                                "kusoma na kukaa pamoja na kitabu chake katika mikono yake"),
                        new page("page5.png", " Baada ya kusoma yeye ni uchovu hivyo yeye " +
                                "anachukua usingizi mdogo"),
                        new page("page6.png", "Yeye anaamka wakati anaposikia bomba bomba bomba" +
                                " juu ya mlango Ilikuwa ni mtoto mdogo ambaye aliwaambia Mshauri" +
                                " walitaka kuwa Gem Collector"),
                        new page("page7.png", "Mshauri alionyesha mtoto jinsi ya kutupwa Spell" +
                                " kwa mkono wake kwamba alikwenda Zap na alielezea jinsi kito" +
                                " johari naweza kukupa nguvu za kichawi"),
                        new page("page8.png", "Mshauri aliiambia mtoto kwamba kama walitaka " +
                                "kupata kito johari na kujifunza kutoka kwao wangeweza haja ya" +
                                " kutafuta ramani uchawi"),
                };
                characterLevelIntervals = new int[][] {new int[] {0, 3}, new int[] {3, 8},
                        new int[] {8, 11}, new int[] {11, 14}, new int[] {14, 19},
                        new int[] {19, 26}};
				language = "Swahili";
                answerKeyForTheQuestions = stageConstants.answerKeyForSwahili;
				break;

			default:
				gameStages = stageConstants.englishStages;
                trainingLevels = new int[]{0, 1, 2, 3};
                storyPages = new page[] {
                    new page("page1.png", "Mentor likes to go outside to smell the sap from the " +
                            "tree"),
                    new page("page2.png", "When he goes outside he likes to wear his cap"),
                    new page("page3.png", "Mentor is careful when he walks because he does " +
                            "not want to trip and fall into a gap"),
                    new page("page4.png", "When he goes back inside Mentor Ap likes to read and " +
                            "sit with his book in his lap"),
                    new page("page5.png", "After that he is tired so he takes a nap"),
                    new page("page6.png", "He wakes up when he hears a tap tap tap on the door" +
                            " It was a young kid who told Mentor they wanted to be a Gem Collector"),
                    new page("page7.png", "Mentor showed the kid how to cast a spell with his hand" +
                            " that went zap and explained how the Gems could give you magical powers"),
                    new page("page8.png", "Mentor told the kid that if they wanted to find the " +
                            "Gems and learn from them they would need to find the magic map"),
                };
                characterLevelIntervals = new int[][] {new int[] {0, 3}, new int[] {3, 9},
                        new int[] {9, 20}, new int[] {20, 25}, new int[] {25, 29},
                        new int[] {29, 30}};
                language = "English";
				answerKeyForTheQuestions = stageConstants.answerKeyForEnglish;
				break;
		}

        if(!gameStages.get(0).getLevel(0).levelData.isUnlocked()) {
            gameStages.get(0).getLevel(0).levelData.setUnlocked(true);
        }
	}

    private void callNextScreen() {
        this.setScreen(new MenuScreen(this));
    }

	public void loadAssets() {
		// Load game sound effects
		AssetManager.getInstance().registerAudio("wrong-answer",
				"sounds/wrong-answer.wav");
		AssetManager.getInstance().registerAudio("correct-answer",
				"sounds/correct-answer.wav");
		AssetManager.getInstance().registerAudio("button-click",
				"sounds/button-click.wav");

		// Load game background music
		AssetManager.getInstance().registerAudio("music0", "music/music0.mp3");
		AssetManager.getInstance().registerAudio("music1", "music/music1.mp3");
		AssetManager.getInstance().registerAudio("music2", "music/music2.mp3");
        AssetManager.getInstance().registerAudio("random", "sounds/winner.wav");

        // Load certain assets
        loadCertainAssets();

		// Background for all menus
		AssetManager.getInstance().registerTexture("menuBackground",
				"Backgrounds/menuBackground.png");
        AssetManager.getInstance().registerTexture("levelsBackground",
                "textures/bg_castle.png");
        AssetManager.getInstance().registerTexture("practiceBackground",
                "Backgrounds/background-2.png");
        AssetManager.getInstance().registerTexture("battleBackground",
                "Backgrounds/industrial-background.jpg");
		AssetManager.getInstance().registerTexture("background1",
				"Backgrounds/background-1.png");
		AssetManager.getInstance().registerTexture("background2",
				"Backgrounds/background-2.png");
		AssetManager.getInstance().registerTexture("background3",
				"Backgrounds/background-3.png");
		AssetManager.getInstance().registerTexture("background4",
				"Backgrounds/background-4.png");
        AssetManager.getInstance().registerTexture("background5",
                "Backgrounds/abcBackground.png");
        AssetManager.getInstance().registerTexture("background6",
                "Backgrounds/4.png");
        AssetManager.getInstance().registerTexture("background7",
                "Backgrounds/rock.png");
        AssetManager.getInstance().registerTexture("snowBackground", "snow.png");
        AssetManager.getInstance().registerTexture("writingBackground", "Trees rescaled.png");
        AssetManager.getInstance().registerTexture("resultsBackground", "resutlsBackground.jpg");
        AssetManager.getInstance().registerTexture("board.png", "board.png");

        // Main menu stuff
		AssetManager.getInstance().registerTexture("gameLogo.png", "projectGemLogo.png");
	}

    public void setCurrentStage(int i) {
        this.currentStage = i;
    }

    private void loadCertainAssets() {
        AssetManager.getInstance().registerTexture("placeHolderTexture", "A.png");

        grabAssets("speeches");
        grabAssets( language+"/levelAudio");
        grabAssets( language+"/levelTextures");
        grabAssets( "Faces");
        grabAssets( "Circles");
        grabAssets( "levelButtons");
        grabAssets( "menuLogos");
        grabAssets( "menuButtons");
        grabAssets("storyImages");
        grabAssets("Symbols");

    }

    private void grabAssets(String location) {
        FileHandle dirHandle = Gdx.files.internal(((Gdx.app.getType() ==
                Application.ApplicationType.Desktop) ? "./bin/" : "") + location);
        //System.out.println(dirHandle.toString());
        if(dirHandle != null) {
            for (FileHandle entry : dirHandle.list()) {
                String key = entry.name();
                String value = location + "/" + entry.name();
                if (entry.isDirectory()) {
                    for (FileHandle innerEntry: entry.list()) {
                        String key2 = "." + innerEntry.name();
                        String value2 = "/" + innerEntry.name();
						register(key+key2, value+value2);
                    }
                } else {
                    register(key, value);
                }
            }
        }
    }

    private void register(String key, String value) {
		//System.out.println(key);
		//System.out.println(value);
		if(key.contains("mp3")) {
            AssetManager.getInstance().registerAudio(key, value);
		} else if(key.contains("png")) {
			AssetManager.getInstance().registerTexture(key, value);
		}
	}

	@Override
	public void render() {
        Gdx.gl.glClearColor(256f, 256f, 256f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		// The superclass' render method renders the current screen, call it
		super.render();
	}

	@Override
	public void dispose() {
		// Dispose of background music
		musicManager.dispose();
		// Dispose of all assets on exit
		AssetManager.getInstance().disposeAll();
	}

}
