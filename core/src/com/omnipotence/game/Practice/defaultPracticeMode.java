package com.omnipotence.game.Practice;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.omnipotence.game.Main;
import com.omnipotence.game.util.AssetManager;
import com.omnipotence.game.util.Constants;

import java.util.ArrayList;

/**
 * Copyright 2015, Omnipotence, LLC, All rights reserved.
 * Created by Omnipotence, LLC.
 * This Screen is where players practice shape, abc's, and numbers.
 */

public class defaultPracticeMode extends centralCore {

    private ImageButton centralButton;
    private Texture[] textures;

    /**
     * This is the Constructor.
     * @param main: Main variable.
     * @param name: Name of the level.
     * @param list: List of all the levels in the stages.
     * @param start: The position of the starting stage.
     * @param end: The position of the ending stage.
     */
    public defaultPracticeMode(Main main, String name, ArrayList<String> list, int start, int end) {
        super(main, name, list, 4, 9, start, end);
        if(name.matches(".*\\d+.*")) {
            int num = Integer.parseInt(name);
            int tens = num/10;
            if(num%10 == 0) {
                textures = new Texture[tens+1];
                textures[textures.length-1] = AssetManager.getInstance().getTexture((num%10) +
                        "CIRCLE.png");
            } else {
                textures = new Texture[tens];
            }
            for(int i = 0; i < tens; i++){
                textures[i] = AssetManager.getInstance().getTexture("10CIRCLE.png");
            }
        }
        viewDialog(AssetManager.getInstance().convertTextureToDrawable(character+".png"));
        main.musicManager.playMusic(character+".mp3");
    }

    /**
     * Sets up the positions[]. For each float[] in position[], the first two values are the
     * x & y positions for TextButton and the last two values are the x & y positions of the
     * ImageButtons.
     */
    @Override
    protected void setPositions() {
        this.positions[0] = new float[]{0f, .5f, .1f, .5f};
        this.positions[1] = new float[]{1f, .5f, .9f, .5f};
        this.positions[2] = new float[]{0f, .2f, .1f, .2f};
        this.positions[3] = new float[]{1f, .2f, .9f, .2f};
    }

    /**
     * This function initializes extra variables.
     */
    @Override
    protected void createExtraAssets() {
        centralButton = new ImageButton(AssetManager.getInstance()
                .convertTextureToDrawable("speaker.png"));
        centralButton.setPosition(Constants.gameX(.5f, centralButtonWidth),
                Constants.gameY(.75f, centralButtonHeight));
        centralButton.setSize(centralButtonWidth, centralButtonHeight);
        stage.addActor(centralButton);
        centralButton.addListener(new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                if(timesCorrect < 5) {
                    System.out.println(character);
                    main.musicManager.playMusic(character+".mp3");
                } else {
                    viewDialog(AssetManager.getInstance().convertTextureToDrawable(character+".png"));
                }
                return true;
            }
        });
    }

    /**
     * This function sets up the Imagebuttons.
     * */
    @Override
    public void setImageButtonListeners(final int i) {
        imageButtons[i].addListener(new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                if(timesCorrect < 5) {
                    viewDialog(AssetManager.getInstance().convertTextureToDrawable(choices[i]+".png"));
                } else {
                    main.musicManager.playMusic(choices[i]+".mp3");
                }
                return true;
            }
        });
    }

    /**
     * This function renders the textures and this includes the circles textures for the numbers.
     */
    @Override
    protected void renderExtraStuff(float delta) {
        if(character.matches(".*\\d+.*")) {
            float intervals = (textures.length/2);
            float startIndex = 5.5f - (intervals/1.6f);
            intervals = .0625f;
            startIndex *= .1f;
            int num = Integer.parseInt(character);
            int numTens = num/10;
            int numOnes = num - (numTens*10);
            float textureWidth = Constants.gameWidth(100f);
            float textureHeight = Constants.gameHeight(250f);
            for(int i = 0; i < textures.length; i++) {
                batch.draw(textures[i], Constants.gameX(startIndex + intervals*i, textureWidth),
                        Constants.gameY(.4f, textureHeight), textureWidth,
                        (i < textures.length-1) ? textureHeight :
                                Constants.gameHeight(25f*numOnes));
            }
        }
    }

    /**
     * This function disposes of the extra assets.
     */
    @Override
    protected void disposeExtraAssets() {}

    /**
     * This function handles what to do when the player makes the right choice.
     */
    @Override
    protected void change() {
        randomize();
        if(timesCorrect < 5) {
            for(int i = 0; i < imageButtons.length; i++) {
                imageButtons[i].setStyle(newStyle(choices[i]+".png"));
            }
        }
        if(timesCorrect == 5) {
            centralButton.setStyle(newStyle(character+".png"));
            centralButton.setSize(Constants.gameWidth(150f), Constants.gameHeight(150f));
            for (ImageButton imageButton : imageButtons) {
                imageButton.setStyle(newStyle("speaker.png"));
                imageButton.setSize(centralButtonWidth, centralButtonHeight);
            }
        }
    }
}
