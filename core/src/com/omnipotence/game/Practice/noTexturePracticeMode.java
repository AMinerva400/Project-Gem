package com.omnipotence.game.Practice;

import com.badlogic.gdx.graphics.g2d.Sprite;
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
 * This Screen is for the vocabulary and the sentences.
 */

public class noTexturePracticeMode extends centralCore {

    private ImageButton centralButton;
    private ArrayList<Sprite> centerSprites;
    private ArrayList<ArrayList<Sprite>> choicesSprites;

    /**
     * This is the Constructor.
     * @param main: Main variable.
     * @param name: Name of the level.
     * @param list: List of all the levels in the stages.
     * @param start: The position of the starting stage.
     * @param end: The position of the ending stage.
     */
    public noTexturePracticeMode(Main main, String name, ArrayList<String> list, int start,
                                 int end) {
        super(main, name, list, (list.size() < 4) ? list.size() : 4, 9, start, end);
        float interval = .03f;
        float startIndex = (character.length() < 24) ? .5f - (character.length()/2) * interval: .2f;
        centerSprites = Constants.convertToSprites(character, startIndex, .735f, interval,
                60f, 60f, 24);
        for(ImageButton imageButton: imageButtons) {
            stage.getActors().removeValue(imageButton, true);
        }
        makeChoicesSprites();
    }

    /**
     * This function sets up the sprites for the choices as they don't have their own textures.
     * */
    private void makeChoicesSprites() {
        choicesSprites = new ArrayList<ArrayList<Sprite>>();
        for (int i = 0; i < choices.length; i++) {
            float z = (positions[i][2] > .5f) ?
                    positions[i][2] - (choices[i].length() - 1) * .032f : positions[i][2];
            choicesSprites.add(Constants.convertToSprites(choices[i], z, positions[i][3], .032f,
                    32f, 32f, 24));
        }
    }

    /**
     * Sets up the positions[]. For each float[] in position[], the first two values are the
     * x & y positions for TextButton and the last two values are the x & y positions of the
     * ImageButtons.
     */
    @Override
    protected void setPositions() {
        this.positions[0] = new float[]{0f, .35f, .1f, .365f};
        this.positions[1] = new float[]{1f, .5f, .9f, .515f};
        this.positions[2] = new float[]{0f, .05f, .1f, .065f};
        this.positions[3] = new float[]{1f, .2f, .9f, .215f};
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
                    main.musicManager.playMusic(character+"mp3");
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
                if(timesCorrect > 4) {
                    main.musicManager.playMusic(choices[i] + "mp3");
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
        if(timesCorrect > 4) {
            for (Sprite sprite: centerSprites) {
                sprite.draw(batch);
            }
        } else if(timesCorrect < 5) {
            for (ArrayList<Sprite> sprites: choicesSprites) {
                for (Sprite sprite: sprites) {
                    sprite.draw(batch);
                }
            }
        }
    }

    /**
     * This function disposes of the extra assets.
     */
    @Override
    protected void disposeExtraAssets() {
        for (Sprite sprite: centerSprites) {
            sprite.getTexture().dispose();
        }
        for (ArrayList<Sprite> sprites: choicesSprites) {
            for (Sprite sprite: sprites) {
                sprite.getTexture().dispose();
            }
        }
    }

    /**
     * This function handles what to do when the player makes the right choice.
     */
    @Override
    protected void change() {
        randomize();
        if(timesCorrect < 5) {
            makeChoicesSprites();
        }
        if(timesCorrect == 5) {
            stage.getActors().removeValue(centralButton, true);
            for (ImageButton imageButton : imageButtons) {
                stage.addActor(imageButton);
                imageButton.setStyle(newStyle("speaker.png"));
                imageButton.setSize(centralButtonWidth, centralButtonHeight);
            }
        }
    }

}
