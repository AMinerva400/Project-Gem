package com.omnipotence.game.Practice;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
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
 * This Screen is where the player practice the individual Rhyme Family.
 */

public class rimeFamilyMode extends centralCore {

    private ImageButton centralButton;
    private String[] characters;
    private ArrayList<Sprite> rimeWordSprites;

    /**
     * This is the Constructor.
     * @param main: Main variable.
     * @param name: Name of the level.
     * @param list: List of all the levels in the stages.
     * @param start: The position of the starting stage.
     * @param end: The position of the ending stage.
     */
    public rimeFamilyMode(Main main, String name, ArrayList<String> list, int start, int end) {
        super(main, name, list, 4, 5, start, end);
    }


    /**
     * This returns a list of words under the Rime Family folder.
     * */
    private String[] getSub(String s) {
        String[] list = null;
        FileHandle dirHandle = Gdx.files.internal(((Gdx.app.getType() ==
                Application.ApplicationType.Desktop) ? "./bin/" : "") + Main.language +
                "/levelTextures/"+s);
        if(dirHandle != null) {
            FileHandle[] entries = dirHandle.list();
            list = new String[entries.length];
            for (int i = 0; i < entries.length; i++) {
                list[i] = entries[i].name();
            }
        }
        return (list == null) ? new String[]{"Wrong", "Path", "Please", "Fix"} : list;
    }

    /**
     * This function adds the listener to the center speaker imagebutton.
     * */
    private void speakerSpeech() {
        centralButton.addListener(new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                System.out.println(character+"."+
                        characters[timesCorrect/2].replace(".png", ".mp3"));
                main.musicManager.playMusic(new String[]{"speech4.mp3",character+"."+
                        characters[timesCorrect/2].replace(".png", ".mp3")});
                return true;
            }
        });
    }

    /**
     * Creates the sprites of the rime word on the screen.
     * */
    private void makeRimeWord() {
        float interval = .03f;
        String currentRimeWord = characters[timesCorrect/2].replace(".png", "");
        float startIndex = (currentRimeWord.length() < 24) ?
                .5f - (currentRimeWord.length()/2) * interval: .2f;
        rimeWordSprites = Constants.convertToSprites(currentRimeWord, startIndex, .6f, interval,
                60f, 60f, 24);
    }

    /**
     * Sets up the positions[]. For each float[] in position[], the first two values are the
     * x & y positions for TextButton and the last two values are the x & y positions of the
     * ImageButtons. Also sets up the characters[].
     */
    @Override
    protected void setPositions() {
        characters = getSub(character);
        limit = (characters.length*2)-1;
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
        speakerSpeech();
        makeRimeWord();
        for (ImageButton imageButton : imageButtons) {
            imageButton.setStyle(newStyle("speaker.png"));
            imageButton.setSize(centralButtonWidth, centralButtonHeight);
        }

    }

    /**
     * This function sets up the Imagebuttons.
     * */
    @Override
    public void setImageButtonListeners(final int i) {
        imageButtons[i].addListener(new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                if(choices[i].equals(character)) {
                    int soundIndex = (int) (Math.random()*characters.length);
                    main.musicManager.playMusic(choices[i]+"."+ characters[soundIndex]
                            .replace(".png", ".mp3"));
                } else {
                    String s = (getSub(choices[i])[0]+"").replace(".png", ".mp3");
                    if(getSub(choices[i])[0] != null) {
                        main.musicManager.playMusic(choices[i]+"."+s);

                    }
                }
                //}
                return true;
            }
        });
    }

    /**
     * This function renders the textures and this includes the circles textures for the numbers.
     */
    @Override
    protected void renderExtraStuff(float delta) {
        for (Sprite sprite: rimeWordSprites) {
            sprite.draw(batch);
        }
    }

    /**
     * This function disposes of the extra assets.
     */
    @Override
    protected void disposeExtraAssets() {
        for (Sprite sprite: rimeWordSprites) {
            sprite.getTexture().dispose();
        }
    }

    /**
     * This function handles what to do when the player makes the right choice.
     */
    @Override
    protected void change() {
        if(timesCorrect%2 == 0) {
            arr.add(characters[(timesCorrect/2)-1]);
            arr.remove(characters[timesCorrect/2]);
            randomize();
            makeRimeWord();
        } else {
            randomize();
        }
    }

}
