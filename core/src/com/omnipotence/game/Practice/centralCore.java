package com.omnipotence.game.Practice;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Timer;
import com.omnipotence.game.Menu_Options.Journey.LevelScreen;
import com.omnipotence.game.Main;
import com.omnipotence.game.defaultScreen;
import com.omnipotence.game.util.AssetManager;
import com.omnipotence.game.util.Constants;
import java.util.ArrayList;

/**
 * Copyright 2015, Omnipotence, LLC, All rights reserved.
 * Created by Omnipotence, LLC.
 * This abstract class which is the foundation of all the practice modes.
 * */

public abstract class centralCore extends defaultScreen {

    protected SpriteBatch batch;
    protected Stage stage, stage2;
    protected Skin skin, skin2;
    public BitmapFont font;
    protected TextButton[] textButtons;
    protected ImageButton[] imageButtons;
    private Table mainContainer;
    private TextButton.TextButtonStyle buttonStyle;
    protected ArrayList<String> arr;
    protected String[] choices;
    protected int timesCorrect, timesIncorrect, numGems = 0;
    protected Main main;
    protected String character;
    private double numWrong = 0;
    protected float[][] positions;
    protected int limit = 0, start, end;
    protected float buttonWidth, buttonHeight, centralButtonWidth, centralButtonHeight;
    private Texture correctTexture, incorrectTexture, gemsTexture;

    /**
     * This is the Constructor.
     * @param gameInstance:required Main variable.
     * @param c: Level name which is also the right answer.
     * @param list: List of all levels in the stage.
     * @param size: Number of choices for the level.
     * @param lim: How times does the player needs to get the right answer.
     * @param start: The position of the starting stage.
     * @param end: The position of the ending stage.
     * */
    public centralCore(Main gameInstance, String c, ArrayList<String> list, int size, int lim,
                       int start, int end) {
        super();
        this.main = gameInstance;
        this.character = c;
        this.arr = list;
        this.limit =  lim;
        this.start = start;
        this.end = end;
        this.positions = new float[size][4];
        setPositions();
        this.arr.remove(character);
        this.choices = new String[size];
        this.buttonWidth = Constants.gameWidth(150f);
        this.buttonHeight = Constants.gameHeight(150f);
        this.centralButtonWidth = Constants.gameWidth(147f);
        this.centralButtonHeight = Constants.gameHeight(225f);
        initMainContainer();
        randomize();
        create();
    }

    /**
     * This function initializes the mainContainer and set the background of the whole level.
     * */
    private void initMainContainer() {
        mainContainer = new Table();
        mainContainer.setBackground(AssetManager.getInstance()
                .convertTextureToDrawable("practiceBackground"));
        mainContainer.setFillParent(true);
    }

    /**
     * This function randomizes the choices in choices[].
     * */
    protected void randomize() {
        int rightChoice = (int)(Math.random()*4);
        ArrayList<String> picked = new ArrayList<String>();
        choices[rightChoice] = character;
        for(int i = 0; i < choices.length; i++) {
            if(i != rightChoice) {
                int j = (int) (Math.random() * arr.size()) ;
                choices[i] = arr.get(j);
                picked.add(arr.get(j));
                arr.remove(j);
            }
        }
        arr.addAll(picked);
    }

    /**
     * This abstract function is where the positions variables are initialized.
     * */
    protected abstract void setPositions();

    /**
     * This function changes the image of the Imagebuttons.
     */
    protected ImageButton.ImageButtonStyle newStyle(String string) {
        ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
        style.up = AssetManager.getInstance().convertTextureToDrawable(string);
        return style;
    }

    /**
     * This function sets up the Imagebuttons for the choices.
     */
    private void setImageButton(final int i) {
        imageButtons[i] = new ImageButton(AssetManager.getInstance()
                .convertTextureToDrawable(choices[i]+".png"));
        imageButtons[i].setSize(buttonWidth, buttonHeight);
        imageButtons[i].setX(Constants.gameX(positions[i][2], buttonWidth));
        imageButtons[i].setY(Constants.gameY(positions[i][3], buttonHeight));
        stage.addActor(imageButtons[i]);
        setImageButtonListeners(i);
    }

    /**
     * This function sets up the TextButtons for the choices.
     */
    private void setTextButtons(final int i) {
        textButtons[i] = new TextButton("", buttonStyle);
        textButtons[i].setSize(buttonWidth, buttonHeight);
        textButtons[i].setX(Constants.gameX(positions[i][0], buttonWidth));
        textButtons[i].setY(Constants.gameY(positions[i][1], buttonHeight));
        textButtons[i].getLabel().setFontScale(3, 3);
        textButtons[i].getLabel().setAlignment(Align.center);
        stage.addActor(textButtons[i]);
        textButtons[i].addListener(new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                choiceMade(choices[i]);
                return true;
            }
        });
    }

    /**
     * This function initializes the values.
     */
    private void create() {
        batch = new SpriteBatch();

        skin2 = new Skin(Gdx.files.internal("skin/uiskin.json"));
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        stage2 = new Stage();
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        font = new BitmapFont();
        font.getData().setScale(Constants.gameWidth(6f), Constants.gameHeight(6f));
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear,
                Texture.TextureFilter.Linear);
        TextureAtlas buttonAtlas = new TextureAtlas("buttons/button.pack");
        skin.addRegions(buttonAtlas);
        skin.add("largeFont", new BitmapFont(Gdx.files
                .internal("skin/fonts/chalkboard-font-large.fnt")));
        Label.LabelStyle largeLabelStyle = new Label.LabelStyle();
        largeLabelStyle.font = skin.getFont("largeFont");
        largeLabelStyle.fontColor = Color.BLACK;
        skin.add("largeLabel", largeLabelStyle);
        buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.up = skin.getDrawable("ButtonUnpressed");
        buttonStyle.down = skin.getDrawable("ButtonsPressed");
        buttonStyle.font = font;
        buttonStyle.fontColor = Color.WHITE;
        stage.addActor(mainContainer);

        //Side UI Textures
        correctTexture = AssetManager.getInstance().getTexture("happyFace.png");
        incorrectTexture = AssetManager.getInstance().getTexture("sadFace.png");
        gemsTexture = new Texture(Gdx.files.internal("Chest_Gem.png"));

        textButtons = new TextButton[choices.length];
        imageButtons = new ImageButton[choices.length];
        for(int i = 0; i < textButtons.length; i++) {
            setTextButtons(i);
            setImageButton(i);
        }
        ImageButton exitbutton = new ImageButton(AssetManager.getInstance()
                .convertTextureToDrawable("Exit.png"));
        exitbutton.setSize(buttonWidth, buttonHeight);
        exitbutton.setX(Constants.gameX(.95f, buttonWidth));
        exitbutton.setY(Constants.gameY(.95f, buttonHeight));
        stage.addActor(exitbutton);
        exitbutton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                main.setScreen(new LevelScreen(main, start, end));
            }
        });

        createExtraAssets();
    }

    /**
     * This abstract function allows the practice modes to initializes extra variables after the
     * create().
     */
    protected abstract void createExtraAssets();

    /**
     * This abstract function is where the listeners for the Imagebuttons are set up.
     * */
    public abstract void setImageButtonListeners(final int i);

    /**
     * This function draws the score.
     */
    private void drawScore(SpriteBatch spriteBatch, Texture t, float x, float y, float w,
                           float h) {
        w = Constants.gameWidth(w);
        h = Constants.gameHeight(h);
        spriteBatch.draw(t, Constants.gameX(x, w), Constants.gameY(y, h), w, h);
    }

    /**
     * This function renders the textures and the animations.
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(135/255f, 206/255f, 235/255f, 1);//135/255f, 206/255f, 235/255f, 1
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();//bottom//draws the texture
        stage.act();
        batch.begin();
        drawScore(batch, correctTexture, .018f, .92f, 98f, 150f);
        drawScore(batch, incorrectTexture, .018f, .8f, 98f,150f);
        drawScore(batch, gemsTexture, .018f, .7f, 125f, 125f);
        String correctString = timesCorrect+"/"+(limit+1);
        font.draw(batch, correctString,
                Constants.gameX(.085f, font.getXHeight()*correctString.length()),
                Constants.gameY(.96f, font.getLineHeight()));
        String inCorrectString = timesIncorrect+"/"+(limit+1);
        if(timesIncorrect > limit) {
            font.setColor(Color.RED);
        }
        font.draw(batch, inCorrectString,
                Constants.gameX(.085f, font.getXHeight()*inCorrectString.length()),
                Constants.gameY(.84f, font.getLineHeight()));
        if(timesIncorrect > limit) {
            font.setColor(Color.WHITE);
        }
        String gemString = numGems+"/1";
        font.draw(batch, gemString,
                Constants.gameX(.085f, font.getXHeight()*gemString.length()),
                Constants.gameY(.74f, font.getLineHeight()));
        renderExtraStuff(delta);
        batch.end();
        stage2.draw();
    }

    /**
     * This function renders the textures and the animations from the practice modes.
     */
    protected abstract void renderExtraStuff(float delta);

    /**
     * This functions disposes the assets.
     */
    @Override
    public void dispose() {
        //Make sure to dispose of it
        batch.dispose();
        stage.dispose();
        stage2.dispose();
        skin.dispose();
        skin2.dispose();
        disposeExtraAssets();
    }

    /**
     * This abstract functions disposes the assets from the practice modes.
     */
    protected abstract void disposeExtraAssets();

    /**
     * This abstract function handles what to do when the player makes the right choice.
     */
    protected abstract void change();

    /**
     * This abstract function handles the choice made by the player.
     */
    private void choiceMade(String choice) {
        for (TextButton button: textButtons) {
            button.setDisabled(true);
        }
        if(character.equals(choice)) {
            timesCorrect++;
            if(timesCorrect < limit) {
                viewDialog(AssetManager.getInstance()
                        .convertTextureToDrawable("happyFace.png"));
            }
            if(timesCorrect > limit) {
                numGems++;
                float delay = .75f; // seconds

                Timer.schedule(new Timer.Task(){
                    @Override
                    public void run() {
                        // Do your work
                        main.setScreen(new writingScreen(main, 10 - (.1*numWrong), skin, skin2,
                                character, start, end));
                    }
                }, delay);
            } else {
                change();
            }
        } else {
            viewDialog(AssetManager.getInstance().convertTextureToDrawable("sadFace.png"));
            numWrong += 1.0;
            timesIncorrect++;
        }
    }

    /**
     * This function shows a dialog with the given drawable.
     */
    public void viewDialog(final Drawable drawable) {
        new Dialog("", skin2, "dialog") {
            {
                pad(50);
                setColor(Color.WHITE);
                setBackground(drawable);
            }
        }.show(stage2);
        float delay = .75f; // seconds

        Timer.schedule(new Timer.Task(){
            @Override
            public void run() {
                // Do your work
                for (TextButton button: textButtons) {
                    button.setDisabled(false);
                }
                stage2.dispose();
                stage2 = new Stage();
            }
        }, delay);

    }

}

