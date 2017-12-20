package com.omnipotence.game.Practice;

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
 * This Screen is where the players practice the Graphemes.
 */

public class graphemesMode extends centralCore {

    /**
     * This is the Constructor.
     * @param main: Main variable.
     * @param name: Name of the level.
     * @param list: List of all the levels in the stages.
     * @param start: The position of the starting stage.
     * @param end: The position of the ending stage.
     */
    public graphemesMode(Main main, String name, ArrayList<String> list, int start, int end) {
        super(main, name, list, 4, 5, start, end);
    }

    /**
     * Sets up the positions[]. For each float[] in position[], the first two values are the
     * x & y positions for TextButton and the last two values are the x & y positions of the
     * ImageButtons. This function also adds the "choice" to the arr list and to the character var.
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
                Constants.gameY(.75f, centralButtonHeight));
        centralButton.setSize(centralButtonWidth, centralButtonHeight);
        stage.addActor(centralButton);
        centralButton.addListener(new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                if(Main.language.equals("English")) {
                    main.musicManager.playMusic(new String[]{"speech2.mp3",
                            "Grapheme" + character + ".mp3", "speech3.mp3"});
                } else {
                    main.musicManager.playMusic("Grapheme" + character + ".mp3");
                }
                return true;
            }
        });
        ImageButton bottomButton = new ImageButton(AssetManager.getInstance()
                .convertTextureToDrawable("Graphemes."+character+".png"));
        float bottomButtonWidth = Constants.gameHeight(225f);
        float bottomButtonHeight = Constants.gameWidth(147f);
        bottomButton.setPosition(Constants.gameX(.5f, bottomButtonWidth),
                Constants.gameY(.6f, bottomButtonHeight));
        bottomButton.setSize(bottomButtonWidth, bottomButtonHeight);
        stage.addActor(bottomButton);
        for (ImageButton imageButton : imageButtons) {
            imageButton.setStyle(newStyle("speaker.png"));
            imageButton.setSize(Constants.gameWidth(100f), Constants.gameHeight(150f));
        }
    }

    /**
     * This function sets up the Imagebuttons.
     * */
    @Override
    public void setImageButtonListeners(final int i) {
        imageButtons[i].addListener(new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                main.musicManager.playMusic("Grapheme"+choices[i]+".mp3");
                return true;
            }
        });
    }

    /**
     * This function renders the textures and this includes the circles textures for the numbers.
     */
    @Override
    protected void renderExtraStuff(float delta) {}

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
