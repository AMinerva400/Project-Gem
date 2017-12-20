package com.omnipotence.game.Menu_Options.Battle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.omnipotence.game.Main;
import com.omnipotence.game.MenuScreen;
import com.omnipotence.game.Stage.userData;
import com.omnipotence.game.defaultScreen;
import com.omnipotence.game.util.AssetManager;
import com.omnipotence.game.util.Constants;

/**
 * Copyright 2015, Omnipotence, LLC, All rights reserved.
 * Created by Omnipotence, LLC.
 * Displays the results of the Multiplayer to the user and also generates a code for the other
 * player to enter into the his/her device.
 */

public class MultiplayerResultScreen extends defaultScreen {

    private Stage stage;
    private Skin skin;
    private SpriteBatch batch;
    private Main gameInstance;
    private Table mainContainer;
    private float battleFinishedTime, time = 0;
    private userData player1, player2;
    private Animation[] playersAnim;
    private float[][] playersPositions;
    private String[] encrytedStrings;

    /**
     * This is the Constructor.
     * @param gameInstance: Main variable.
     * @param battleTime: The time it took to complete the battle.
     * @param player1: The user data of player 1.
     * @param player2: The user data of player 2.
     */
    public MultiplayerResultScreen(Main gameInstance, float battleTime,
                                   userData player1, userData player2) {
        this.stage = new Stage();
        initMainContainer();
        stage.addActor(mainContainer);
        Gdx.input.setInputProcessor(stage);
        this.skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        this.batch = new SpriteBatch();
        this.gameInstance = gameInstance;
        this.battleFinishedTime = battleTime;
        this.player1 = player1;
        this.player2 = player2;
        this.playersAnim = new Animation[2];
        this.playersPositions = new float[][]{
                {.2f, .7f, .225f, .4f, .2f, .125f},
                {.825f, .7f, .85f, .4f, .875f, .125f}};
        this.encrytedStrings = new String[]{"A","C","E","P","O","L","K","M","Q","R"};
        setupButtons();
        showResults();
    }

    /**
     * This function sets up the mainContainer, sets up the background.
     */
    private void initMainContainer() {
        mainContainer = new Table();
        mainContainer.setBackground(AssetManager.getInstance()
                .convertTextureToDrawable("background6"));
        mainContainer.setFillParent(true);
    }

    /**
     * Sets up the buttons for the screen.
     */
    private void setupButtons() {
        //Exit Button
        ImageButton exitbutton = new ImageButton(AssetManager.getInstance()
                .convertTextureToDrawable("Exit.png"));
        float resizeWidth = Constants.gameWidth(150f);
        float resizeHeight = Constants.gameHeight(150f);
        exitbutton.setSize(resizeWidth, resizeHeight);
        exitbutton.setPosition(Constants.gameX(.975f, resizeWidth),
                Constants.gameY(.975f, resizeHeight));
        stage.addActor(exitbutton);
        exitbutton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameInstance.setScreen(new MenuScreen(gameInstance));
            }
        });
    }

    /**
     * Shows the results of the battle.
     */
    private void showResults() {
        //System.out.println("Player 1:" +"\t Player 2:");
        //System.out.println("Correct: "+player1.getTimesCorrect() +"\t Correct: "+player2.getTimesCorrect());
        //System.out.println("Missed: "+player1.getTimesMissed() +"\t Missed: "+player2.getTimesMissed());
        //System.out.println("Countered: "+player1.getTimesCounter() +"\t Countered: "+player2.getTimesCounter());
        createLabel("Score Board", 5f, .5f, .875f, Color.PURPLE);
        setPlayerResults(player1, "gemCollector", 0, Color.RED);
        setPlayerResults(player2, "mentor", 1, Color.BLUE);
        createLabel("Code for player 2:", 5f, .5f, .6f, Color.BLACK);
        createLabel(newString(player2.getTimesCorrect()) +
                encrytedStrings[player2.getTimesCounter()] + newString(player2.getTimesMissed()) +
                encrytedStrings[player2.getTimesMissed()] + newString(player2.getTimesCounter())  +
                encrytedStrings[player2.getTimesCorrect()] +
                (""+battleFinishedTime).replace(".","").substring(0, 7), 5f, .5f, .55f, Color.BLACK);
    }

    /**
     * This functions returns a string where it takes a given number as 1 and return it as 01.
     */
    public String newString(int num) {
        return num/10 + "" + num%10;
    }

    /**
     * This functions sets up the results of the battle.
     */
    private void setPlayerResults(userData playerData, String playerName, int playerNum
            , Color playerColor) {
        this.playersAnim[playerNum] = new Animation(1/15f, (new TextureAtlas(
                Gdx.files.internal("Characters/"+playerName+"Idle.txt"))).getRegions());
        createLabel(playerName, 5f, playersPositions[playerNum][0], playersPositions[playerNum][1],
                playerColor);
        createLabel("Correct: "+playerData.getTimesCorrect() +
                "\nMissed: "+playerData.getTimesMissed()+"\nCountered: "+
                playerData.getTimesCounter(), 4f, playersPositions[playerNum][4],
                playersPositions[playerNum][5], playerColor);
    }

    /**
     * This functions creates a label.
     */
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
     * The functions renders the textures and animations.
     */
    @Override
    public void render(float delta) {
        stage.act();
        stage.draw();
        batch.begin();
        time += delta;
        for (int i = 0; i < playersAnim.length; i++) {
            TextureRegion textureRegion = playersAnim[i].getKeyFrame(time, true);
            float width = Constants.gameWidth(textureRegion.getRegionWidth());
            float height = Constants.gameHeight(textureRegion.getRegionHeight());
            batch.draw(textureRegion, Constants.gameX(playersPositions[i][2], width),
                    Constants.gameY(playersPositions[i][3], height), width, height);
        }
        batch.end();
    }

    /**
     * This functions disposes the assets.
     */
    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
        batch.dispose();
    }
}
