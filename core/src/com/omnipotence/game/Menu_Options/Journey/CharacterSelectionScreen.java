package com.omnipotence.game.Menu_Options.Journey;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.omnipotence.game.Main;
import com.omnipotence.game.MenuScreen;
import com.omnipotence.game.defaultScreen;
import com.omnipotence.game.util.AssetManager;
import com.omnipotence.game.util.Constants;

/**
 * Copyright 2015, Omnipotence, LLC, All rights reserved.
 * Created by Omnipotence, LLC.
 * This Screen is the Character selection where each Character has a specific stage.
 */

public class CharacterSelectionScreen extends defaultScreen {
    private Main gameInstance;
    private Table mainContainer;
    private Stage stage;
    private Skin skin;
    private ImageButton exitbutton;

    /**
     * This is the Constructor.
     * @param main: Main variable.
     */
    public CharacterSelectionScreen(Main main) {
        this.gameInstance = main;
        this.stage = new Stage();
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
     * This function sets up the list of Character levels.
     */
    private void setupSelection() {
        for(int i = 0; i < Main.characters.length; i++) {
            final int[] interval = gameInstance.characterLevelIntervals[i];
            Button button = new Button(skin);
            Label charName = new Label(Main.characters[i], skin, "largeLabel");
            charName.setAlignment(Align.center);
            charName.setFontScale(Constants.gameWidth(1.25f), Constants.gameHeight(1.25f));
            button.stack(new Image(AssetManager.getInstance()
                    .convertTextureToDrawable("board.png")), charName)
                    .width(Constants.gameWidth(charName.getWidth()*2f))
                    .height(Constants.gameHeight(charName.getHeight()*1.5f));
            button.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    gameInstance.setScreen(new LevelScreen(gameInstance, interval[0], interval[1]));
                }
            });
            //Creates a new row after every 2 columns
            if((i+1) % 2 == 0) {
                mainContainer.add(button).pad(50f).row();
            } else {
                mainContainer.add(button).colspan(1);
            }
        }
    }

    /**
     * Initialize the main table (container) with a background. This table holds
     * and positions all buttons on the menu.
     */
    private void initMainContainer() {
        mainContainer = new Table();
        mainContainer.setBackground(AssetManager.getInstance()
                .convertTextureToDrawable("snowBackground"));
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
