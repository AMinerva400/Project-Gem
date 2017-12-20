package com.omnipotence.game.Practice;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
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
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Timer;
import com.omnipotence.game.Main;
import com.omnipotence.game.Menu_Options.Journey.LevelScreen;
import com.omnipotence.game.defaultScreen;
import com.omnipotence.game.util.AssetManager;
import com.omnipotence.game.util.Constants;

import java.util.ArrayList;

/**
 * Copyright 2015, Omnipotence, LLC, All rights reserved.
 * Created by Omnipotence, LLC.
 * This is the tutorial Screen for the default gameplay.
 * Do not modify and please create a new tutorial.
 */

public class abcTutorial extends defaultScreen {

    private ImageButton centralButton, nextButton;
    public SpriteBatch batch;
    private Stage stage, stage2;
    private Skin skin, skin2;
    public BitmapFont font;
    private TextButton[] textButtons;
    public ImageButton[] imageButtons;
    private Table mainContainer;
    private TextButton.TextButtonStyle buttonStyle;
    private ArrayList<String> arr;
    public String[] choices;
    private int timesCorrect, timesIncorrect, numGems = 0, scene = 0, limit = 0, start, end;
    public Main main;
    private String character;
    public double numWrong = 0;
    private float[][] positions, buttonPosition;
    private ImageButton exitbutton;
    private float buttonXSize = 150f, buttonYSize = 150f, elapsedTime = 0f;
    private Texture correctTexture, incorrectTexture, gemsTexture;
    private Label correctScore, incorrectScore, gemScore;
    private TextureAtlas textureAtlas;
    private Animation animationa;

    public abcTutorial(Main main, int start, int end) {
        this.main = main;
        this.start = start;
        this.end = end;
        this.character =  main.gameStages.get(0).getLevelName(0);
        this.arr = new ArrayList<String>();
        for (int i = 0; i < main.gameStages.get(0).getLevelSize(); i++) {
            String s = main.gameStages.get(0).getLevelName(i);
            if (!s.equals("BATTLE")) {
                arr.add(s);
            }
        }
        this.limit = 9;
        this.choices = new String[4];
        this.positions = new float[4][4];
        textureAtlas = new TextureAtlas(Gdx.files.internal("FingerPress.pack"));
        animationa = new Animation(1/15f, textureAtlas.getRegions());
        setPositions();
        this.arr.remove(character);
        randomize();
        initMainContainer();
        create();
        createExtraAssets();
    }

    private void setPositions() {
        this.positions[0] = new float[]{0f, .5f, .1f, .5f};
        this.positions[1] = new float[]{1f, .5f, .9f, .5f};
        this.positions[2] = new float[]{0f, .2f, .1f, .2f};
        this.positions[3] = new float[]{1f, .2f, .9f, .2f};
        this.buttonPosition = new float[][]{
                new float[]{Constants.gameX(.45f, textureAtlas.getRegions().get(0).getRegionWidth()),
                        Constants.gameY(.70f, textureAtlas.getRegions().get(0).getRegionHeight())},
          /*CLick*/      new float[]{Constants.gameX(.9f, textureAtlas.getRegions().get(0).getRegionWidth()),
                        Constants.gameY(.8f, textureAtlas.getRegions().get(0).getRegionHeight())},
                new float[]{Constants.gameX(-.11f, textureAtlas.getRegions().get(0).getRegionWidth()),
                        Constants.gameY(.38f, textureAtlas.getRegions().get(0).getRegionHeight())},
          /*CLick*/       new float[]{Constants.gameX(.9f, textureAtlas.getRegions().get(0).getRegionWidth()),
                        Constants.gameY(.8f, textureAtlas.getRegions().get(0).getRegionHeight())},
                new float[]{Constants.gameX(-.11f, textureAtlas.getRegions().get(0).getRegionWidth()),
                        Constants.gameY(-.08f, textureAtlas.getRegions().get(0).getRegionHeight())},
          /*CLick*/       new float[]{Constants.gameX(.9f, textureAtlas.getRegions().get(0).getRegionWidth()),
                        Constants.gameY(.8f, textureAtlas.getRegions().get(0).getRegionHeight())},
                new float[]{Constants.gameX(-.11f, textureAtlas.getRegions().get(0).getRegionWidth()),
                        Constants.gameY(.38f, textureAtlas.getRegions().get(0).getRegionHeight())},
          /*CLick*/       new float[]{Constants.gameX(.9f, textureAtlas.getRegions().get(0).getRegionWidth()),
                Constants.gameY(.8f, textureAtlas.getRegions().get(0).getRegionHeight())},
                new float[]{Constants.gameX(.01f, textureAtlas.getRegions().get(0).getRegionWidth()),
                        Constants.gameY(.38f, textureAtlas.getRegions().get(0).getRegionHeight())},
                new float[]{Constants.gameX(-.11f, textureAtlas.getRegions().get(0).getRegionWidth()),
                        Constants.gameY(.38f, textureAtlas.getRegions().get(0).getRegionHeight())},
          /*CLick*/       new float[]{Constants.gameX(.9f, textureAtlas.getRegions().get(0).getRegionWidth()),
                Constants.gameY(.8f, textureAtlas.getRegions().get(0).getRegionHeight())}
        };
    }

    private void createExtraAssets() {
        centralButton = new ImageButton(AssetManager.getInstance()
                .convertTextureToDrawable("speaker.png"));
        centralButton.setPosition(Constants.gameX(.5f, 150f), Constants.gameY(.75f, 150f));
        centralButton.setSize(150f, 150f);
        stage.addActor(centralButton);
        centralButton.addListener(new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                if(scene == 0) {
                    main.musicManager.playMusic(character+".mp3");
                    scene += 1;
                }
                return true;
            }
        });
        nextButton = new ImageButton(AssetManager.getInstance()
                .convertTextureToDrawable("Button_up.png"));
        nextButton.setPosition(Constants.gameX(.9f, 250f), Constants.gameY(.8f, 250f));
        nextButton.setSize(250f, 250f);
        stage.addActor(nextButton);
        nextButton.addListener(new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                if(scene%2 == 1|| scene == 10) {
                    switch (scene) {
                        case 3: correctScore.setText("0/"+(limit+1)); break;
                        case 5: incorrectScore.setText("0/"+(limit+1));
                            correctScore.setText(((limit)+"/"+(limit+1))); break;
                        case 7:  correctScore.setText(("0/"+(limit+1)));
                            gemScore.setText(("0/1")); change(); break;
                        case 10: correctScore.setText("0/"+(limit+1)); startMainGame(); break;
                        default: break;
                    }
                    if(scene != 10) {
                        scene++;
                    }
                }

                return true;
            }
        });
    }

    private void setImageButtonListeners(final int i) {
        imageButtons[i].addListener(new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                if(i == 0 && scene == 8) {
                    main.musicManager.playMusic(character+".mp3");
                    scene++;
                }
                return true;
            }
        });
    }

    protected void change() {
        centralButton.setStyle(newStyle(character + ".png"));
        for (ImageButton imageButton : imageButtons) {
            imageButton.setStyle(newStyle("speaker.png"));
            imageButton.setSize(100f, 150f);

        }
    }

    private void initMainContainer() {
        mainContainer = new Table();
        mainContainer.setBackground(AssetManager.getInstance()
                .convertTextureToDrawable("practiceBackground"));
        mainContainer.setFillParent(true);
    }

    public void create () {
        batch = new SpriteBatch();

        //Stuff -_-
        skin2 = new Skin(Gdx.files.internal("skin/uiskin.json"));
        stage = new Stage();
        stage2 = new Stage();
        skin = new Skin();
        font = new BitmapFont();
        TextureAtlas buttonAtlas = new TextureAtlas("buttons/button.pack");
        skin.addRegions(buttonAtlas);
        buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.up = skin.getDrawable("ButtonUnpressed");
        buttonStyle.down = skin.getDrawable("ButtonsPressed");
        buttonStyle.font = font;
        buttonStyle.fontColor = Color.WHITE;
        skin.add("largeFont", new BitmapFont(Gdx.files
                .internal("skin/fonts/chalkboard-font-large.fnt")));
        Label.LabelStyle largeLabelStyle = new Label.LabelStyle();
        largeLabelStyle.font = skin.getFont("largeFont");
        largeLabelStyle.fontColor = Color.BLACK;
        skin.add("largeLabel", largeLabelStyle);
        stage.addActor(mainContainer);

        //Side UI Labels
        correctScore = new Label(timesCorrect+"/"+(limit+1), skin, "largeLabel");
        correctScore.setPosition(Constants.gameX(.06f, correctScore.getWidth()),
                Constants.gameY(.92f, correctScore.getHeight()));
        incorrectScore = new Label(timesIncorrect+"", skin, "largeLabel");
        incorrectScore.setPosition(Constants.gameX(.06f, incorrectScore.getWidth()),
                Constants.gameY(.82f, incorrectScore.getHeight()));
        gemScore = new Label(numGems+"/1", skin, "largeLabel");
        gemScore.setPosition(Constants.gameX(.06f, gemScore.getWidth()),
                Constants.gameY(.72f, gemScore.getHeight()));
        stage.addActor(correctScore);
        stage.addActor(incorrectScore);
        stage.addActor(gemScore);

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
        exitbutton = new ImageButton(AssetManager.getInstance()
                .convertTextureToDrawable("Exit.png"));
        exitbutton.setPosition(Constants.gameX(.95f, 150f), Constants.gameY(.95f, 150f));
        exitbutton.setSize(150f, 150f);
        stage.addActor(exitbutton);
        Gdx.input.setInputProcessor(stage);
        exitbutton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                main.setScreen(new LevelScreen(main, start, end));
            }
        });

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(135/255f, 206/255f, 235/255f, 1);//135/255f, 206/255f, 235/255f, 1
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        elapsedTime += Gdx.graphics.getDeltaTime();
        stage.draw();//bottom//draws the texture
        stage.act();
        batch.begin();
        batch.draw(animationa.getKeyFrame(elapsedTime,true),buttonPosition[scene][0],
                buttonPosition[scene][1]);
        batch.draw(correctTexture, Constants.gameX(.018f, 60f), Constants.gameY(.9f, 100f), 60f, 100f);
        batch.draw(incorrectTexture, Constants.gameX(.018f, 60f), Constants.gameY(.8f, 100f), 60f, 100f);
        batch.draw(gemsTexture, Constants.gameX(0f, 120f), Constants.gameY(.7f, 100f), 120f, 100f);
        batch.end();
        stage2.draw();
    }

    @Override
    public void dispose() {
        //Make sure to dispose of it
        batch.dispose();
        stage.dispose();
        stage2.dispose();
        skin.dispose();
        skin2.dispose();
    }

    private void setImageButton(final int i) {
        imageButtons[i] = new ImageButton(AssetManager.getInstance()
                .convertTextureToDrawable(choices[i] + ".png"));
        imageButtons[i].setPosition(Constants.gameX(positions[i][2], buttonXSize),
                Constants.gameY(positions[i][3], buttonYSize));
        imageButtons[i].setSize(buttonXSize, buttonYSize);
        stage.addActor(imageButtons[i]);
        Gdx.input.setInputProcessor(stage);
        setImageButtonListeners(i);
    }

    private void setTextButtons(final int i) {
        textButtons[i] = new TextButton("", buttonStyle);
        textButtons[i].setPosition(Constants.gameX(positions[i][0], buttonXSize),
                Constants.gameY(positions[i][1], buttonYSize));
        textButtons[i].setSize(buttonXSize, buttonYSize);
        textButtons[i].getLabel().setFontScale(3, 3);
        textButtons[i].getLabel().setAlignment(Align.center);
        stage.addActor(textButtons[i]);
        Gdx.input.setInputProcessor(stage);
        textButtons[i].addListener(new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                if(scene == 2) {
                    correctScore.setText("1/"+(limit+1));
                    scene++;
                } else if(scene == 4 && i == 2) {
                    incorrectScore.setText("1/"+(limit+1));
                    scene++;
                } else if(scene == 6) {
                    correctScore.setText((limit+1)+"/"+(limit+1));
                    gemScore.setText("1/1");
                    scene++;
                } else if(scene == 9) {
                    correctScore.setText("1/"+(limit+1));
                    scene++;
                }
                return true;
            }
        });
    }

    private ImageButton.ImageButtonStyle newStyle(String string) {
        ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
        style.up = AssetManager.getInstance().convertTextureToDrawable(string);
        return style;
    }

    private void startMainGame() {
        final Drawable drawable = AssetManager.getInstance()
                .convertTextureToDrawable("happyFace.png");
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
                main.setScreen(new defaultPracticeMode(main, character, arr, start, end));
            }
        }, delay);
    }

    private void randomize() {
        int rightChoice = 0;
        ArrayList<String> picked = new ArrayList<String>();
        choices[rightChoice] = character;
        for(int i = 1; i < choices.length; i++) {
            int j = (int) (Math.random() * arr.size()) ;
            choices[i] = arr.get(j);
            picked.add(arr.get(j));
            arr.remove(j);
        }
        arr.addAll(picked);
    }

}
