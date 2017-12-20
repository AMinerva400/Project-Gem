package com.omnipotence.game.Menu_Options.Training;

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
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Timer;
import com.omnipotence.game.Main;
import com.omnipotence.game.Menu_Options.Training.TrainingSelectionScreen;
import com.omnipotence.game.defaultScreen;
import com.omnipotence.game.util.AssetManager;
import com.omnipotence.game.util.Constants;

/**
 * Copyright 2015, Omnipotence, LLC, All rights reserved.
 * Created by Omnipotence, LLC and modified by Anthony.
 * The function of this screen is to teach kids the abc's interactively.
 * This is restricted to only the Swahili version of the abc's.
 */

public class TrainingScreen extends defaultScreen {

    private Stage stage;
    private SpriteBatch spritebatch;
    private Main gameMain;
    private Table mainContainer;
    private String[] list;
    private Texture[] textures;
    private Animation[] alphabetAnimations;
    private ImageButton speakerButton;
    private float speakerButtonWidth, speakerButtonHeight, time;
    private int pointer = -1;
    private int mode;

    /**
     * This is the Constructor.
     * @param main: Main variable.
     * */
    public TrainingScreen(Main main, final int mode) {
        this.stage = new Stage();
        this.spritebatch = new SpriteBatch();
        this.gameMain = main;
        this.mode = mode;
        initMainContainer();
        stage.addActor(mainContainer);
        //This sets up the abc's assuming that the abc's are always the first stage.
        Object[] ob = main.gameStages.get(mode).getLevelNames().toArray();
        this.list = new String[ob.length];
        this.textures = new Texture[ob.length];
        for (int i = 0; i < ob.length; i++) {
            this.list[i] = ob[i].toString();
            System.out.println(this.list[i]);
            this.textures[i] = AssetManager.getInstance().getTexture(
                    ((mode == 1) ? "Graphemes." : "") + this.list[i]+".png");
        }
        //Sets up the exitbutton
        final ImageButton exitbutton = new ImageButton(AssetManager.getInstance()
                .convertTextureToDrawable("Exit.png"));
        float resizeWidth = Constants.gameWidth(150f);
        float resizeHeight = Constants.gameHeight(150f);
        exitbutton.setSize(resizeWidth, resizeHeight);
        exitbutton.setPosition(Constants.gameX(.975f, resizeWidth),
                Constants.gameY(.975f, resizeHeight));
        exitbutton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameMain.setScreen(new TrainingSelectionScreen(gameMain));
            }
        });
        //End setting up the exitbutton
        this.alphabetAnimations = new Animation[list.length];
        /*Sets up the speakerButton, this button, when pressed, plays the current Alphabet
            and increments the pointer*/
        this.speakerButton = new ImageButton(AssetManager.getInstance()
                .convertTextureToDrawable("speaker.png"));
        this.speakerButtonWidth = Constants.gameWidth(147f*.66f);
        this.speakerButtonHeight = Constants.gameHeight(225f*.66f);
        this.speakerButton.setSize(speakerButtonWidth, speakerButtonHeight);
        this.speakerButton.setPosition(Constants.gameX(.2f, speakerButtonWidth),
                Constants.gameY(.84f, speakerButtonHeight));
        stage.addActor(this.speakerButton);
        this.speakerButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //Plays the current alphabet's sound
                if(pointer+1 < list.length) {
                    if(mode == 1) {
                        gameMain.musicManager.playMusic("Grapheme"+list[pointer + 1] + ".mp3");
                    } else {
                        gameMain.musicManager.playMusic(list[pointer + 1] + ".mp3");
                    }
                }
                Timer.schedule(new Timer.Task(){
                    @Override
                    public void run() {
                        //Sets the speakerButton position under the next alphabet
                        speakerButton.setPosition(
                                Constants.gameX(.2f + (pointer+2)%7 * .1f, speakerButtonWidth),
                                Constants.gameY(.84f - (float) Math.ceil((pointer+2)/7) * .25f,
                                        speakerButtonHeight));
                        pointer++;
                    }
                }, .75f);

                /*Removes the speakerButton and shows the exitbutton once the kid clicks on the
                    last alphabet*/
                if(pointer > list.length-2) {
                    stage.getActors().removeValue(speakerButton, true);
                    stage.addActor(exitbutton);
                }
            }
        });
        //End setting up the speakerButton
        //Get the animations for the abc's
       /* if(mode == 0) {
            for (int i = 0; i < list.length; i++) {
                this.alphabetAnimations[i] = new Animation(1 / 15f, new TextureAtlas(
                        Gdx.files.internal("Alphabet/" + list[i] + ".txt")).getRegions());
            }
        }*/
        Gdx.input.setInputProcessor(stage);
    }

    /**
     * This function sets up the mainContainer.
     */
    private void initMainContainer() {
        mainContainer = new Table();
        mainContainer.setBackground(AssetManager.getInstance()
                .convertTextureToDrawable("background5"));
        mainContainer.setFillParent(true);
    }

    /**
     * This function renders the textures and the animations for the alphabets.
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(135/255f, 206/255f, 235/255f, 1);//135/255f, 206/255f, 235/255f, 1
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();//bottom//draws the texture
        stage.act();
        time += delta;
        spritebatch.begin();
        /*Plays the animation of the abc's and it only plays the animations of the alphabet if its
            index in the alphabetAnimation list is less than the pointer*/
        for (int i = 0; i < list.length; i++) {
            /*TextureRegion textureRegion = (i < pointer + 1) ?
                    alphabetAnimations[i].getKeyFrame(time, true) :
                    alphabetAnimations[i].getKeyFrame(0f, false);
            spritebatch.draw(textureRegion, Constants.gameX(.2f + i % 7 * .1f, speakerButtonWidth),
                    Constants.gameY(.95f - (float) Math.ceil(i / 7) * .25f, speakerButtonHeight),
                    speakerButtonWidth, speakerButtonHeight);*/
            spritebatch.draw(textures[i], Constants.gameX(.2f + i % 7 * .1f,
                    speakerButtonWidth), Constants.gameY(.95f - (float) Math.ceil(i / 7) * .25f,
                    speakerButtonHeight), speakerButtonWidth, speakerButtonHeight);
        }
        spritebatch.end();
    }

}
