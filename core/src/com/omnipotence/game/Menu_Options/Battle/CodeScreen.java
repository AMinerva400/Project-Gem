package com.omnipotence.game.Menu_Options.Battle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.omnipotence.game.Main;
import com.omnipotence.game.defaultScreen;
import com.omnipotence.game.util.AssetManager;
import com.omnipotence.game.util.Constants;
import com.omnipotence.game.util.PreferencesManager;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Copyright 2015, Omnipotence, LLC, All rights reserved.
 * Created by Omnipotence, LLC.
 * This Screen is where the player enters the code recieved from the multiplayer.
 */

public class CodeScreen extends defaultScreen {

    private Stage stage;
    private Skin skin;
    private Table mainContainer;
    private SpriteBatch spriteBatch;
    private Main gameMain;
    private TextField field;
    private Label submitLabel;
    private ArrayList<String> encryptedStrings;
    private boolean showVictory = false;
    private Animation victoryAnim;
    private float time = 0f;

    /**
     * This is the Constructor.
     * @param main: Main variable.
     * */
    public CodeScreen(Main main) {
        this.stage = new Stage();
        this.skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        this.spriteBatch = new SpriteBatch();
        this.gameMain = main;
        initMainContainer();
        stage.addActor(mainContainer);
        this.field = new TextField("", skin);
        encryptedStrings = new ArrayList<String>(
                Arrays.asList("A","C","E","P","O","L","K","M","Q","R"));
        victoryAnim =  new Animation(1/15f, (new TextureAtlas(
                Gdx.files.internal("Characters/gemCollectorVictory.txt"))).getRegions());
        setUpField();
        setUpButtons();
        submitLabel = new Label("", skin);
        submitLabel.setColor(Color.RED);
        submitLabel.setFontScale(Constants.gameWidth(5f), Constants.gameHeight(5f));
        submitLabel.setPosition(Constants.gameX(.4f,
                submitLabel.getWidth()*submitLabel.getFontScaleX()), Constants.gameY(.875f,
                submitLabel.getHeight() * submitLabel.getFontScaleY()));
        stage.addActor(submitLabel);
        Gdx.input.setOnscreenKeyboardVisible(true);
        Gdx.input.setInputProcessor(stage);

    }

    /**
     * This function sets up the mainContainer.
     */
    private void initMainContainer() {
        mainContainer = new Table();
        mainContainer.setBackground(AssetManager.getInstance()
                .convertTextureToDrawable("background6"));
        mainContainer.setFillParent(true);
    }

    /**
     * This function sets up the text field.
     * */
    private void setUpField() {
        field.setMessageText("test");
        float width = Constants.gameWidth(800f);
        float height = Constants.gameHeight(100f);
        field.setSize(width, height);
        BitmapFont font = new BitmapFont();
        font.getData().setScale(Constants.gameWidth(2.5f), Constants.gameHeight(2.5f));
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear,
                Texture.TextureFilter.Linear);
        TextField.TextFieldStyle textFieldStyle = skin.get(TextField.TextFieldStyle.class);
        textFieldStyle.font = font;
        field.setPosition(Constants.gameX(.5f, width), Constants.gameY(.75f, height));
        stage.addActor(field);
    }

    /**
     * This function sets up the exit and check buttons.
     * */
    private void setUpButtons() {
        float resizeWidth = Constants.gameWidth(150f);
        float resizeHeight = Constants.gameHeight(150f);

        final ImageButton exitbutton = new ImageButton(AssetManager.getInstance()
                .convertTextureToDrawable("Exit.png"));
        exitbutton.setSize(resizeWidth, resizeHeight);
        exitbutton.setPosition(Constants.gameX(.975f, resizeWidth),
                Constants.gameY(.975f, resizeHeight));
        stage.addActor(exitbutton);
        exitbutton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.input.setOnscreenKeyboardVisible(false);
                gameMain.setScreen(new MultiplayerSelectionScreen(gameMain));
            }
        });

        final ImageButton submitButton = new ImageButton(AssetManager.getInstance()
                .convertTextureToDrawable("check.png"));
        submitButton.setSize(resizeWidth, resizeHeight);
        submitButton.setPosition(Constants.gameX(.75f, resizeWidth),
                Constants.gameY(.75f, resizeHeight));
        stage.addActor(submitButton);
        submitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String text = field.getText();
                if(text.equals("")) {
                    submitLabel.setText("You haven't entered a code!");
                } else if(text.length() != 16) {
                    submitLabel.setText("Invalid code!");
                } else {
                    if(vaildScore(text.substring(0,2), text.substring(8,9)) &&
                            vaildScore(text.substring(6, 8), text.substring(2,3)) &&
                            vaildScore(text.substring(3, 5), text.substring(5,6)) &&
                            PreferencesManager.getInstance().getString(text) != null) {
                        submitLabel.setColor(Color.GREEN);
                        submitLabel.setText("Congratulations!");
                        createLabel("Correct: "+text.substring(0,2) +
                                "\nMissed: "+text.substring(3,5) +
                                "\nCounters: "+text.substring(6,8), 5f, .5f, .3f, Color.BLACK);
                        showVictory = true;
                        Gdx.input.setOnscreenKeyboardVisible(false);
                        stage.getActors().removeValue(field, true);
                        stage.getActors().removeValue(submitButton, true);
                    } else {
                        submitLabel.setText("Invalid code!");
                    }
                    //00A01C00A1034296
                }
                System.out.println(text);
            }
        });
    }

    /**
     * This function checks if two given Strings match based on the encryption.
     * */
    private boolean vaildScore(String s, String s2) {
        return (Character.isDigit(s.charAt(0)) && Character.isDigit(s.charAt(1)) &&
                encryptedStrings.contains(s2) &&
                s2.equals(encryptedStrings.get(Integer.parseInt(s))));
    }

    /**
     * This function create a label based on the given params.
     * */
    private void createLabel(String string, float scaleSize, float xPosition, float yPositon,
                             Color color) {
        Label label = new Label(string, skin);
        label.setColor(color);
        label.setFontScale(Constants.gameWidth(scaleSize), Constants.gameHeight(scaleSize));
        label.setPosition(Constants.gameX(xPosition, label.getWidth()*label.getFontScaleX()),
                Constants.gameY(yPositon, label.getHeight() * label.getFontScaleY()));
        stage.addActor(label);
    }

    /**
     * This function renders the textures and the animation for the Gem Collector cheering.
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(135 / 255f, 206 / 255f, 235 / 255f, 1);//135/255f, 206/255f, 235/255f, 1
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();//bottom//draws the texture
        stage.act();
        spriteBatch.begin();
        time += delta;
        if(showVictory) {
            TextureRegion textureRegion = victoryAnim.getKeyFrame(time, true);
            float width = Constants.gameWidth(textureRegion.getRegionWidth());
            float height = Constants.gameHeight(textureRegion.getRegionHeight());
            spriteBatch.draw(textureRegion, Constants.gameX(.5f, width),
                    Constants.gameY(.45f, height), width, height);
        }
        spriteBatch.end();
    }

    /**
     * This functions disposes the assets.
     */
    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
        spriteBatch.dispose();
    }

}
