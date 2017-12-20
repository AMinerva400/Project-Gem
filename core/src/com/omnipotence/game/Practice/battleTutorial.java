package com.omnipotence.game.Practice;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.omnipotence.game.Battle.battleMode;
import com.omnipotence.game.Menu_Options.Journey.LevelScreen;
import com.omnipotence.game.Main;
import com.omnipotence.game.defaultScreen;
import com.omnipotence.game.util.AssetManager;
import com.omnipotence.game.util.Constants;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Copyright 2015, Omnipotence, LLC, All rights reserved.
 * Created by Omnipotence, LLC.
 * This is the tutorial for the battle mode.
 * Please reuse this screen to create more tutorials.
 */

public class battleTutorial extends defaultScreen {

    private Stage stage;
    private Skin skin;
    private SpriteBatch spriteBatch;
    private float elapsedTime, buttonWidth, buttonHeight;
    private int sceneIndex;
    private Table mainContainer;
    private Animation fingerAnim;
    private float[][] clickPositions, fingerPositions;
    private Texture nextButton;
    private Texture[] scenes;

    /**
     * This is the Constructor.
     * @param main: Main variable.
     * @param type: Character value telling what type of tutorial is it, 'b' is default.
     * @param start: The position of the starting stage.
     * @param end: The position of the ending stage.
     * */
    public battleTutorial(final Main main, final Character type, final int start, final int end) {

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        spriteBatch = new SpriteBatch();
        elapsedTime = 0f;
        sceneIndex = 0;
        mainContainer = new Table();
        mainContainer.setFillParent(true);
        stage.addActor(mainContainer);
        buttonWidth = Constants.gameWidth(150f);
        buttonHeight = Constants.gameHeight(150f);
        switch (type) {
            case 'b':
                clickPositions = new float[][]{
                        new float[]{.5f, .7f}, new float[]{.9f, .7f}, new float[]{.55f, .5f},
                        new float[]{.9f, .7f}, new float[]{.45f, .5f}, new float[]{.9f, .7f},
                        new float[]{ .1f, .0f}, new float[]{.9f, .7f}
                };
                scenes = new Texture[clickPositions.length/2];
                for(int i = 0; i < clickPositions.length/2; i++) {
                    scenes[i] = new Texture("tutorialScenes/tutorialBattle"+(i+2)+".png");
                }
                break;
            default:
                clickPositions = new float[][]{
                        new float[]{.45f, .8f}, new float[]{.9f, .7f}, new float[]{.1f, .5f},
                        new float[]{.9f, .7f}, new float[]{.0f, .5f}, new float[]{.9f, .7f},
                        new float[]{.1f, .2f}, new float[]{.9f, .7f}, new float[]{.0f, .2f},
                        new float[]{.9f, .7f}, new float[]{.9f, .7f}
                };
                scenes = new Texture[clickPositions.length/2];
                for(int i = 0; i < clickPositions.length/2; i++) {
                    scenes[i] = new Texture("tutorialScenes/practicemodeA.png");
                }
                break;
        }
        fingerPositions = new float[clickPositions.length][];
        for(int i = 0; i < fingerPositions.length; i++) {
            fingerPositions[i] = new float[]{
                    clickPositions[i][0]-(buttonWidth*1.125f/Constants.getScreenWidth()),
                    clickPositions[i][1]-((buttonHeight*3.25f)/Constants.getScreenHeight())};
        }
        nextButton = AssetManager.getInstance().getTexture("Button_up.png");
        fingerAnim = new Animation(1/15f, (new TextureAtlas(Gdx.files
                .internal("FingerPress.pack"))).getRegions());
        mainContainer.setBackground(new TextureRegionDrawable(
                new TextureRegion(scenes[sceneIndex])));
        final TextButton clickButton = new TextButton("", skin);
        clickButton.setColor(0f,0f,0f,0f);
        clickButton.setPosition(Constants.gameX(clickPositions[sceneIndex][0], buttonWidth*2f),
                Constants.gameY(clickPositions[sceneIndex][1], buttonHeight*2));
        clickButton.setSize(buttonWidth*2f, buttonHeight*2);
        stage.addActor(clickButton);
        clickButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(sceneIndex < clickPositions.length-1) {
                    if(sceneIndex % 2 == 0) {
                        mainContainer.setBackground(new TextureRegionDrawable(
                                new TextureRegion(scenes[sceneIndex/2])));
                    }
                    sceneIndex++;
                    clickButton.setPosition(Constants.gameX(clickPositions[sceneIndex][0],
                            buttonWidth*2f), Constants.gameY(clickPositions[sceneIndex][1],
                            buttonHeight*2));
                } else {
                    switch (type) {
                        case 'b': main.setScreen(new battleMode(main,
                                new ArrayList<String>(Arrays.asList("A", "B")), 10, 0, start, end));
                            break;
                        default: main.setScreen(new defaultPracticeMode(main, "A",
                                        new ArrayList<String>(Arrays.asList("A", "B", "C", "D",
                                                "E","F", "G", "H", "I", "J", "K", "L", "M", "N",
                                                "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X",
                                                "Y", "Z")), start, end));
                            break;
                    }
                }
            }
        });

        ImageButton exitbutton = new ImageButton(AssetManager.getInstance()
                .convertTextureToDrawable("Exit.png"));
        exitbutton.setPosition(Constants.gameX(.95f, buttonWidth),
                                            Constants.gameY(.95f, buttonHeight));
        exitbutton.setSize(buttonWidth, buttonHeight);
        stage.addActor(exitbutton);
        Gdx.input.setInputProcessor(stage);
        exitbutton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                main.setScreen(new LevelScreen(main, start, end));
            }
        });
    }

    /**
     * This function renders the textures and animation for the finger pointing.
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(135/255f, 206/255f, 235/255f, 1);//135/255f, 206/255f, 235/255f, 1
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        elapsedTime += Gdx.graphics.getDeltaTime();
        stage.draw();//bottom//draws the texture
        stage.act();
        spriteBatch.begin();
        spriteBatch.draw(nextButton, Constants.gameX(.9f, Constants.gameWidth(nextButton.getWidth())),
                Constants.gameY(.7f, Constants.gameHeight(nextButton.getHeight())),
                Constants.gameWidth(nextButton.getWidth()),
                Constants.gameHeight(nextButton.getHeight()));
        spriteBatch.draw(fingerAnim.getKeyFrame(elapsedTime, true),
                Constants.gameX(fingerPositions[sceneIndex][0],
                        Constants.gameWidth(fingerAnim.getKeyFrame(elapsedTime).getRegionWidth())),
                Constants.gameY(fingerPositions[sceneIndex][1],
                        Constants.gameHeight(fingerAnim.getKeyFrame(elapsedTime).getRegionHeight())));
        spriteBatch.end();
    }

    /**
     * This function disposes of the assets.
     */
    @Override
    public void dispose() {
        //Make sure to dispose of it
        spriteBatch.dispose();
        stage.dispose();
        skin.dispose();
    }

}
