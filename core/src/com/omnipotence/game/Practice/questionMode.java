package com.omnipotence.game.Practice;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.omnipotence.game.Main;
import com.omnipotence.game.util.Constants;

import java.util.ArrayList;

/**
 * Copyright 2015, Omnipotence, LLC, All rights reserved.
 * Created by Omnipotence, LLC.
 * This Screen for the player to practice reading and answering questions.
 */

public class questionMode extends centralCore {

    private ArrayList<Sprite> questionSprites;
    private ArrayList<ArrayList<Sprite>> choicesSprites;

    /**
     * This is the Constructor.
     * @param main: Main variable.
     * @param name: Name of the level.
     * @param answer: The right answer to the question(which is the name).
     * @param list: List of all the levels in the stages.
     * @param start: The position of the starting stage.
     * @param end: The position of the ending stage.
     */
    public questionMode(Main main, String name, String answer, ArrayList<String> list, int start,
                        int end) {
        super(main, answer, list, (list.size() < 4) ? list.size() : 4, 4, start, end);
        for(ImageButton imageButton: imageButtons) {
            stage.getActors().removeValue(imageButton, true);
        }
        this.questionSprites = Constants.convertToSprites(name, .15f, .9f, .025f, 32f, 32f, 32);
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
                    48f, 48f, 16));
        }
    }

    /**
     * Sets up the positions[]. For each float[] in position[], the first two values are the
     * x & y positions for TextButton and the last two values are the x & y positions of the
     * ImageButtons.
     */
    @Override
    protected void setPositions() {
        this.positions[0] = new float[]{.075f, .25f, .15f, .275f};
        this.positions[1] = new float[]{.925f, .25f, .85f, .275f};
        this.positions[2] = new float[]{.075f, .1f, .15f, .125f};
        this.positions[3] = new float[]{.925f, .1f, .85f, .125f};
    }

    /**
     * This function initializes extra variables.
     */
    @Override
    protected void createExtraAssets() {}

    /**
     * This function sets up the Imagebuttons.
     * */
    @Override
    public void setImageButtonListeners(int i) {}

    /**
     * This function renders the textures and this includes the circles textures for the numbers.
     */
    @Override
    protected void renderExtraStuff(float delta) {
        for (Sprite sprite : questionSprites) {
            sprite.draw(batch);
        }
        for (ArrayList<Sprite> sprites: choicesSprites) {
            for (Sprite sprite: sprites) {
                sprite.draw(batch);
            }
        }
    }

    /**
     * This function disposes of the extra assets.
     */
    @Override
    protected void disposeExtraAssets() {
        for (Sprite sprite: questionSprites) {
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
        makeChoicesSprites();
    }
}
