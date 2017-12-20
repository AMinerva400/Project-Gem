package com.omnipotence.game.Practice;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.omnipotence.game.Main;
import com.omnipotence.game.util.AssetManager;
import com.omnipotence.game.util.Constants;

import java.util.ArrayList;

/**
 * Copyright 2015, Omnipotence, LLC, All rights reserved.
 * Created by Omnipotence, LLC.
 * This Screen is where the player test their understanding of tools.
 */

public class toolsMode extends centralCore {

    private Animation toolWord;
    private float time = 0;

    /**
     * This is the Constructor.
     * @param main: Main variable.
     * @param name: Name of the level.
     * @param list: List of all the levels in the stages.
     * @param start: The position of the starting stage.
     * @param end: The position of the ending stage.
     */
    public toolsMode(Main main, String name, ArrayList<String> list, int start, int end) {
        super(main, name, list, 4, 9, start, end);
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
        ImageButton centralButton = new ImageButton(AssetManager.getInstance()
                .convertTextureToDrawable("speaker.png"));
        centralButton.setPosition(Constants.gameX(.5f, centralButtonWidth),
                Constants.gameY(.76f, centralButtonHeight));
        centralButton.setSize(centralButtonWidth, centralButtonHeight);
        stage.addActor(centralButton);
        centralButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                main.musicManager.playMusic("speech.mp3");
            }
        });
        for (ImageButton imageButton : imageButtons) {
            imageButton.setStyle(newStyle("speaker.png"));
            imageButton.setSize(Constants.gameWidth(100f), Constants.gameHeight(150f));
        }
        toolWord = new Animation(1/15f, new TextureAtlas(
                Gdx.files.internal(Main.language+"/Tools/"+character+".txt")).getRegions());
    }

    /**
     * This function sets up the Imagebuttons.
     * */
    @Override
    public void setImageButtonListeners(final int i) {
        imageButtons[i].addListener(new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                main.musicManager.playMusic(choices[i]+".mp3");
                return true;
            }
        });
    }

    /**
     * This function renders the textures and this includes the circles textures for the numbers.
     */
    @Override
    protected void renderExtraStuff(float delta) {
        time += delta;
        batch.draw(toolWord.getKeyFrame(time/2, true), Constants.gameX(.5f, 180f),
                Constants.gameY(.625f, 90f), 180f, 90f);
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
    }

}
