package com.omnipotence.game.Menu_Options.Training;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.omnipotence.game.Main;
import com.omnipotence.game.MenuScreen;
import com.omnipotence.game.defaultScreen;
import com.omnipotence.game.util.AssetManager;
import com.omnipotence.game.util.Constants;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Copyright 2015, Omnipotence, LLC, All rights reserved.
 * Created by Omnipotence, LLC.
 * This Screen is where the player practice abc, Graphemes, Rhyme Family, and etc.
 */

public class TrainingSelectionScreen extends defaultScreen {
    private Main gameInstance;
    private Table mainContainer;
    private Stage stage;
    private Skin skin;
    private ImageButton exitbutton;
    private Main main;

    /**
     * This is the Constructor.
     * @param main: Main variable.
     */
    public TrainingSelectionScreen(Main main) {
        this.gameInstance = main;
        this.stage = new Stage();
        this.main = main;
        setSkin();
        initMainContainer();
        setupSelection();
        Gdx.input.setInputProcessor(stage);
    }

    /**
     * This function sets up the skin.
     */
    private void setSkin() {
        this.skin = new Skin(Gdx.files.internal("skin/uiskin.json"), new TextureAtlas(
                "skin/uiskin.atlas"));
        FileHandle fileHandle = Gdx.files.internal("skin/fonts/chalkboard-font-large.fnt");
        skin.add("largeFont", new BitmapFont(fileHandle));
        Label.LabelStyle largeLabelStyle = new Label.LabelStyle();
        largeLabelStyle.font = skin.getFont("largeFont");
        largeLabelStyle.fontColor = Color.valueOf("#ff7070");
        skin.add("largeLabel", largeLabelStyle);
    }

    /**
     * This function sets up the list of training levels.
     */
    private void setupSelection() {
        for(int i = 0; i < main.trainingLevels.length; i++) {
            Button button = new Button(skin);
            Label name = new Label(main.gameStages.get(main.trainingLevels[i]).getStageName(),
                    skin, "largeLabel");
            name.setAlignment(Align.center);
            name.setFontScale(Constants.gameWidth(1.25f), Constants.gameHeight(1.25f));
            button.stack(new Image(AssetManager.getInstance()
                    .convertTextureToDrawable("board.png")), name)
                    .width(Constants.gameWidth(name.getWidth()*2f))
                    .height(Constants.gameHeight(name.getHeight()*1.5f));
            final int finalI = i;
            button.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    gameInstance.setScreen(new TrainingScreen(main, finalI));
                }
            });
            mainContainer.add(button).pad(50f).row();
        }
        if(Main.language.equals("English")) {
            mainContainer.add(setDecodingButton()).pad(50f).row();
        }
    }

    /**
     * Sets up the decoding button.
     */
    private ImageButton setDecodingButton() {
        TextureRegionDrawable buttonIcon = AssetManager.getInstance()
                .convertTextureToDrawable("trainingButton.png");
        TextureRegionDrawable buttonLogo = AssetManager.getInstance()
                .convertTextureToDrawable("trainingLogo.png");
        ImageButton imageButton = new ImageButton(buttonIcon);
        imageButton.add(new Image(buttonLogo));
        float width = Constants.gameWidth(256f + 60f * ("training").length());
        float height = Constants.gameHeight(192f);
        imageButton.setSize(width, height);
        imageButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gameInstance.setScreen(new DecodeScreen(gameInstance,
                        new ArrayList<String>(Arrays.asList("Ig", "Ip", "Ish", "Ob", "Ock", "Op",
                                "Ub", "Uck", "Ug", "Un","Am", "Ap", "At", "Ad", "Ag", "An", "Ick",
                                "Id", "Ill", "In", "It"))));
                System.out.println("Playing Decode Screen");
                dispose();
            }
        });
        return imageButton;
    }

    /**
     * Initialize the main table (container) with a background. This table holds
     * and positions all buttons on the menu.
     */
    private void initMainContainer() {
        mainContainer = new Table();
        mainContainer.setBackground(AssetManager.getInstance()
                .convertTextureToDrawable("background7"));
        mainContainer.setFillParent(true);
        stage.addActor(mainContainer);
        //Exit Button
        exitbutton = new ImageButton(AssetManager.getInstance()
                .convertTextureToDrawable("Exit.png"));
        setPositionAndSize(exitbutton, .975f, .975f, 150f, 150f);
        exitbutton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameInstance.setScreen(new MenuScreen(gameInstance));
            }
        });
    }

    /**
     * This function sets up the exit button and adds it to the stage..
     */
    private void setPositionAndSize(Button button, float x, float y, float width, float height) {
        float resizeWidth = Constants.gameWidth(width);
        float resizeHeight = Constants.gameHeight(height);
        button.setSize(resizeWidth, resizeHeight);
        button.setPosition(Constants.gameX(x, resizeWidth), Constants.gameY(y, resizeHeight));
        stage.addActor(button);
    }

    /**
     * This function renders the textures.
     */
    @Override
    public void render(float delta) {
        stage.act();
        stage.draw();
    }

    /**
     * This functions disposes the assets.
     */
    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}
