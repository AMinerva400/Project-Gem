package com.omnipotence.game.Menu_Options;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.omnipotence.game.Main;
import com.omnipotence.game.MenuScreen;
import com.omnipotence.game.defaultScreen;
import com.omnipotence.game.page;
import com.omnipotence.game.util.AssetManager;
import com.omnipotence.game.util.Constants;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Copyright 2015, Omnipotence, LLC, All rights reserved.
 * Created by Omnipotence, LLC.
 * This is the story mode Screen.
 */

public class StoryScreen extends defaultScreen {

    private Stage stage;
    private Skin skin;
    private int pageNum;
    private Main main;
    private Image pageImage;
    private Table mainContainer;
    private ArrayList<ImageButton> pageDialog;

    /**
     * This is the Constructor.
     * @param main: Main variable.
     * */
    public StoryScreen(Main main) {
        this.stage = new Stage();
        initMainContainer();
        stage.addActor(mainContainer);
        this.skin = new Skin();
        this.pageNum = 0;
        this.main = main;
        pageImage = new Image(AssetManager.getInstance().getTexture(main.storyPages[0].getImage()));
        pageImage.setSize(Constants.getScreenWidth()*.5f, Constants.getScreenHeight()*.5f);
        pageImage.setPosition(Constants.gameX(.5f, Constants.getScreenWidth()*.5f),
                Constants.gameY(.85f, Constants.getScreenHeight()*.5f));
        stage.addActor(pageImage);
        setPageDialog();
        Gdx.input.setInputProcessor(stage);
        setupButtons();
    }

    /**
     * This function sets up the mainContainer.
     */
    private void initMainContainer() {
        mainContainer = new Table();
        mainContainer.setBackground(AssetManager.getInstance()
                .convertTextureToDrawable("snowBackground"));
        mainContainer.setFillParent(true);
    }


    /**
     * This function sets up the exit, next, and previous buttons.
     * */
    private void setupButtons() {
        //Exit Button
        createImageButton("Exit.png", .95f, .95f, 150f, 150f, new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                main.setScreen(new MenuScreen(main));
            }
        }, -1);
        //Next Button
        createImageButton("Button_up.png", .95f, .75f, 250f, 250f, new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(pageNum < main.storyPages.length-1) {
                    pageNum++;
                    pageImage.setDrawable(AssetManager.getInstance()
                            .convertTextureToDrawable(main.storyPages[pageNum].getImage()));
                    main.storyPages[pageNum].getImage();
                    setPageDialog();
                }
            }
        }, -1);
        //Back Button
        createImageButton("Button_up.png", .15f, .9f, 250f, 250f, new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(pageNum > 0) {
                    pageNum--;
                    pageImage.setDrawable(AssetManager.getInstance()
                            .convertTextureToDrawable(main.storyPages[pageNum].getImage()));
                    main.storyPages[pageNum].getImage();
                    setPageDialog();
                }
            }
        }, 180f);
    }

    /**
     * This function reates an Imagebutton. It sets the button up, and adds the button to the stage.
     * @param texturePath: path of the texture.
     * @param x: the x position of the button. (Values being from 0 to 1, going left to right)
     * @param y: the y position of the button. (Values being from 0 to 1, going bottom to top)
     * @param width: the width of the button.
     * @param height: the height of the button.
     * @param listener: the event of the button when clicked.
     * @param rotation: rotation of the image for the button. (Values: 0f to 360f)
     * */
    private void createImageButton(String texturePath, float x, float y, float width, float height,
                                  ClickListener listener, float rotation) {
        ImageButton imagebutton = new ImageButton(AssetManager.getInstance()
                .convertTextureToDrawable(texturePath));
        if(rotation != -1) {
            imagebutton.getImage().rotateBy(rotation);
        }
        width = Constants.gameWidth(width);
        height = Constants.gameHeight(height);
        imagebutton.setSize(width, height);
        imagebutton.setPosition(Constants.gameX(x, width), Constants.gameY(y, height));
        imagebutton.addListener(listener);
        stage.addActor(imagebutton);
    }

    /**
     * This function changes the page dialog based on the pageNum variable.
     * */
    private void setPageDialog() {
        if(pageNum != 0) {
            /*This clears the previous imageButtons and clears pageDialog list to prepare for a
               new dialog.*/
            for (ImageButton imagebutton : pageDialog) {
                stage.getActors().removeValue(imagebutton, true);
            }
            pageDialog.clear();
        }
        /*Gets new Imagebuttons using Constants.convertToButtons(...) and add the new button
            to the stage */
        pageDialog = Constants.convertToButtons(main, main.storyPages[pageNum]
                .getText(), .025f, .35f, 80f);
        for (ImageButton imagebutton : pageDialog) {
            stage.addActor(imagebutton);
        }
    }

    /**
     * This function renders the textures.
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(135/255f, 206/255f, 235/255f, 1);//135/255f, 206/255f, 235/255f, 1
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();//bottom//draws the texture
        stage.act();
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
