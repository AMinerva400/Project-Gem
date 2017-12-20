package com.omnipotence.game.Menu_Options;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
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
import com.badlogic.gdx.utils.Json;
import com.omnipotence.game.Main;
import com.omnipotence.game.MenuScreen;
import com.omnipotence.game.Stage.scoreData;
import com.omnipotence.game.Stage.userData;
import com.omnipotence.game.defaultScreen;
import com.omnipotence.game.util.AssetManager;
import com.omnipotence.game.util.Constants;

/**
 * Copyright 2015, Omnipotence, LLC, All rights reserved.
 * Created by Omnipotence, LLC.
 * This is the score Screen. All the previous results are shown in this Screen.
 */

public class ScoreScreen extends defaultScreen {

    private Stage stage;
    private Skin skin;
    private SpriteBatch batch;
    private Main gameInstance;
    private float time = 0;
    private userData player1, player2;
    private Animation[] playersAnim;
    private float[][] playersPositions;
    private Json json;
    private scoreData scores;
    private Table mainContainer;

    /**
     * This is the Constructor.
     * @param gameInstance: Main variable.
     */
    public ScoreScreen(Main gameInstance) {
        json = new Json();
        scores = new scoreData();
        extrasafe();
        loadData();
        this.stage = new Stage();
        initMainContainer();
        stage.addActor(mainContainer);
        this.skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        Gdx.input.setInputProcessor(stage);
        this.batch = new SpriteBatch();
        this.gameInstance = gameInstance;
        setupPlayers();
        this.playersAnim = new Animation[2];
        this.playersPositions = new float[][]{
                {.2f, .7f, .225f, .4f, .2f, .125f},
                {.825f, .7f, .85f, .4f, .875f, .125f}};
        setupButtons();
        showResults();
    }

    /**
     * This function avoids the scenario of no saved scores.
     */
    private void extrasafe(){
        scores.p1correct = 0;
        scores.p1missed = 0;
        scores.p1countered = 0;
        scores.p2correct = 0;
        scores.p2missed = 0;
        scores.p2countered = 0;
    }

    /**
     * This function sets up the players' data.
     */
    private void setupPlayers(){
        player1 = new userData(scores.p1correct, scores.p1missed, scores.p1countered);
        player2 = new userData(scores.p2correct, scores.p2missed, scores.p2countered);
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
     * This function sets up the exit button for the screen.
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
     * This function shows the results of the battle.
     */
    private void showResults() {
        createLabel("Score Board", 5f, .5f, .875f, Color.PURPLE);
        setPlayerResults(player1, "gemCollector", 0, Color.RED);
        setPlayerResults(player2, "mentor", 1, Color.BLUE);
        createLabel(" ", 5f, .5f, .6f, Color.BLACK);
    }

    /**
     * This functions sets up the results of the battle.
     */
    private void setPlayerResults(userData playerData, String playerName, int playerNum
            , Color playerColor) {
        this.playersAnim[playerNum] = new Animation(1/15f, (new TextureAtlas(
                Gdx.files.internal("Characters/"+playerName+"Idle.txt"))).getRegions());
        createLabel("Player "+(playerNum+1), 5f, playersPositions[playerNum][0],
                playersPositions[playerNum][1], playerColor);
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

    /**
     * This function loads saved score data.
     */
    private void loadData() {
        FileHandle scoreHandle = Gdx.files.local(Main.language + "/UserData/scores.json");
        if (!scoreHandle.exists()) save(); // Create the scoreData file.
        this.scores = json.fromJson(scoreData.class, scoreHandle);
    }

    /**
     * This function saves score data.
     */
    private void save() {
        FileHandle levelHandle = Gdx.files.local(Main.language + "/UserData/scores.json");
        levelHandle.writeString(json.toJson(new scoreData()), false);
    }

}
