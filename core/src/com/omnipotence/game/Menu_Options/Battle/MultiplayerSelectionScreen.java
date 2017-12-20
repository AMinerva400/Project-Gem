package com.omnipotence.game.Menu_Options.Battle;

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
import com.omnipotence.game.Battle.battleMode;
import com.omnipotence.game.Main;
import com.omnipotence.game.MenuScreen;
import com.omnipotence.game.Stage.gameStage;
import com.omnipotence.game.defaultScreen;
import com.omnipotence.game.util.AssetManager;
import com.omnipotence.game.util.Constants;

/**
 * Copyright 2015, Omnipotence, LLC, All rights reserved.
 * Created by Omnipotence, LLC.
 * This class is a Screen that show the mulitplayer levels.
 */

public class MultiplayerSelectionScreen extends defaultScreen {
    private Main gameInstance;
    private Table mainContainer;
    private Stage stage;
    private Skin skin;
    private ImageButton exitbutton;

    /**
     * This is the Constructor.
     * @param main: Main variable.
     */
    public MultiplayerSelectionScreen(Main main) {
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
     * This function sets up the list of multiplayer levels.
     */
    private void setupSelection() {
        for(int i = 0; i < 8; i++) {
            Button button = new Button(skin);
            final gameStage stage = gameInstance.gameStages.get(i);
            Label stageName = new Label(stage.getStageName(), skin, "largeLabel");
            stageName.setAlignment(Align.center);
            stageName.setFontScale(Constants.gameWidth(1f), Constants.gameHeight(1.25f));
            button.stack(new Image(AssetManager.getInstance()
                    .convertTextureToDrawable("board.png")), stageName)
                    .width(Constants.gameWidth(stageName.getWidth()*1.25f))
                    .height(Constants.gameHeight(stageName.getHeight()*1.75f));
            button.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    gameInstance.setScreen(new battleMode(gameInstance, stage.getLevelNames(), 10,
                            5, 0, 0));
                }
            });
            //Creates a new row after every 2 columns
            if((i+1) % 2 == 0) {
                mainContainer.add(button).pad(20f).row();
            } else {
                mainContainer.add(button).colspan(1);
            }
        }
        mainContainer.add(setCodeButton()).pad(20f).row();
    }

    /**
     * Sets up the code button.
     */
    private ImageButton setCodeButton() {
        TextureRegionDrawable buttonIcon = AssetManager.getInstance()
                .convertTextureToDrawable("shopButton.png");
        TextureRegionDrawable buttonLogo = AssetManager.getInstance()
                .convertTextureToDrawable("shopLogo.png");
        ImageButton imageButton = new ImageButton(buttonIcon);
        imageButton.add(new Image(buttonLogo));
        float width = Constants.gameWidth(256f + 60f * ("training").length());
        float height = Constants.gameHeight(192f);
        imageButton.setSize(width, height);
        imageButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gameInstance.setScreen(new CodeScreen(gameInstance));
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
     * This function disposes of the assets.
     */
    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}
