package com.omnipotence.game.Menu_Options.Training;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Timer;
import com.omnipotence.game.Main;
import com.omnipotence.game.MenuScreen;
import com.omnipotence.game.defaultScreen;
import com.omnipotence.game.util.AssetManager;
import com.omnipotence.game.util.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Copyright 2015, Omnipotence, LLC, All rights reserved.
 * Created by Omnipotence, LLC.
 * This Screen trains the player on all the Rhyme Family.
 */

public class DecodeScreen extends defaultScreen {

    private ImageButton centralButton;
    private ArrayList<Sprite> rimeWordSprites;
    protected SpriteBatch batch;
    private Stage stage, stage2;
    private Skin skin, skin2;
    public BitmapFont font;
    private TextButton[] textButtons;
    public ImageButton[] imageButtons;
    private Table mainContainer;
    private TextButton.TextButtonStyle buttonStyle;
    protected ArrayList<String> arr;
    public String[] choices;
    private int timesCorrect, timesIncorrect, numGems = 0;
    public Main main;
    public String character;
    public float[][] positions;
    private int limit;
    private float buttonWidth, buttonHeight, centralButtonWidth, centralButtonHeight;
    private Texture correctTexture, incorrectTexture, gemsTexture;
    private float time;
    private boolean runAnimation;
    private Animation animation;
    private int index;
    private HashMap<String, String[]> rimeMap;

    /**
     * This is the constructor, it does stuff.
     * @param main: required Main variable.
     * @param list: List of all levels in the stage.
     * */
    public DecodeScreen(Main main, ArrayList<String> list) {
        super();
        this.main = main;
        this.arr = list;
        this.index = 0;
        this.character = arr.get(index);
        this.positions = new float[][]{new float[]{0f, .5f, .1f, .5f},
                new float[]{1f, .5f, .9f, .5f}, new float[]{0f, .2f, .1f, .2f},
                new float[]{1f, .2f, .9f, .2f}};
        this.choices = new String[this.positions.length];
        this.buttonWidth = Constants.gameWidth(150f);
        this.buttonHeight = Constants.gameHeight(150f);
        this.centralButtonWidth = Constants.gameWidth(147f);
        this.centralButtonHeight = Constants.gameHeight(225f);
        time = 0;
        runAnimation = false;
        animation = new Animation(1/15f, (new TextureAtlas(Gdx.files.internal(
                "Characters/gemCollectorAttack.txt"))).getRegions());
        limit = this.arr.size();
        rimeMap = new HashMap<String, String[]>();
        for (String s: list) {
            getSub(s);
        }
        initMainContainer();
        randomize();
        create();}

    /**
     * This method initialize the mainContainer and set the background of the whole level.
     * */
    private void initMainContainer() {
        mainContainer = new Table();
        mainContainer.setBackground(AssetManager.getInstance()
                .convertTextureToDrawable("practiceBackground"));
        mainContainer.setFillParent(true);
    }

    /**
     * This method randomizes the choice var.
     * */
    protected void randomize() {
        ArrayList<Integer> picks = new ArrayList<Integer>();
        for(int i = 0; i < arr.size(); i++) {
            picks.add(i);
        }
        picks.remove(index);
        int rightChoice = (int)(Math.random()*choices.length);
        choices[rightChoice] = character;
        for(int i = 0; i < choices.length; i++) {
            if(i != rightChoice) {
                int j = (int) (Math.random() * picks.size()) ;
                choices[i] = arr.get(picks.get(j));
                picks.remove(i);
            }
        }
    }

    private void create () {
        batch = new SpriteBatch();

        //Stuff -_-
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
        correctTexture = new Texture(Gdx.files.internal("Faces/happyFace.png"));
        incorrectTexture = new Texture(Gdx.files.internal("Faces/sadFace.png"));
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
                main.setScreen(new MenuScreen(main));
            }
        });

        //The center speaker button.
        centralButton = new ImageButton(AssetManager.getInstance()
                .convertTextureToDrawable("speaker.png"));
        centralButton.setPosition(Constants.gameX(.5f, centralButtonWidth),
                Constants.gameY(.75f, centralButtonHeight));
        centralButton.setSize(centralButtonWidth, centralButtonHeight);
        stage.addActor(centralButton);
        speakerSpeech();
        makeRimeWord();
        //Sets all the imagebuttons for the choices to look like speaker.
        for (ImageButton imageButton : imageButtons) {
            imageButton.setStyle(newStyle("speaker.png"));
            imageButton.setSize(centralButtonWidth, centralButtonHeight);
        }
    }

    /**
     * This function changes the look of the Image button.
     * */
    protected ImageButton.ImageButtonStyle newStyle(String string) {
        ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
        style.up = AssetManager.getInstance().convertTextureToDrawable(string);
        return style;
    }

    /**
     * This function runs the attack animation.
     * */
    private void runAnimation() {
        TextureRegion textureRegion = animation.getKeyFrame(time, false);
        float width = Constants.gameWidth(textureRegion.getRegionWidth());
        float height = Constants.gameHeight(textureRegion.getRegionHeight());
        batch.draw(textureRegion, Constants.gameX(.5f, width), Constants.gameY(.5f, height), width, height);
        if(animation.isAnimationFinished(time)) {
            time = 0;
            runAnimation = false;
            System.out.println("Reset");
        }
    }

    /**
     * This returns a list of words under the Rime Family folder.
     * */
    private void getSub(String s) {
        //String[] list = null;
        FileHandle dirHandle = Gdx.files.internal(((Gdx.app.getType() ==
                Application.ApplicationType.Desktop) ? "./bin/" : "") + Main.language +
                "/levelTextures/"+s);
        if(dirHandle != null) {
            FileHandle[] entries = dirHandle.list();
            String[] stringEntry = new String[entries.length];
            //list = new String[entries.length];
            for (int i = 0; i < entries.length; i++) {
               // list[i] = entries[i].name();
                stringEntry[i] = entries[i].name();
            }
            System.out.println(s + " : "+stringEntry[0]);
            rimeMap.put(s, stringEntry);
        }
        //return (list == null) ? new String[]{"Wrong", "Path", "Please", "Fix"} : list;
    }

    /**
     * This function adds the listener to the center speaker imagebutton.
     * */
    private void speakerSpeech() {
        centralButton.addListener(new ClickListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                String string = character+"."+ rimeMap.get(character)[0].replace(".png", ".mp3");
                System.out.println(string);
                main.musicManager.playMusic(new String[]{"speech4.mp3", string});
                return true;
            }
        });
    }

    /**
     * Creates the sprites of the rime word on the screen.
     * */
    private void makeRimeWord() {
        float interval = .03f;
        String currentRimeWord = rimeMap.get(character)[0].replace(".png", "");
        float startIndex = (currentRimeWord.length() < 24) ?
                .5f - (currentRimeWord.length()/2) * interval: .2f;
        rimeWordSprites = Constants.convertToSprites(currentRimeWord, startIndex, .6f, interval,
                60f, 60f, 24);
    }

    /**
     * This function sets up Image buttons.
     * */
    private void setImageButton(final int i) {
        imageButtons[i] = new ImageButton(AssetManager.getInstance()
                .convertTextureToDrawable(choices[i] + ".png"));
        imageButtons[i].setSize(buttonWidth, buttonHeight);
        imageButtons[i].setX(Constants.gameX(positions[i][2], buttonWidth));
        imageButtons[i].setY(Constants.gameY(positions[i][3], buttonHeight));
        stage.addActor(imageButtons[i]);
        imageButtons[i].addListener(new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                if(choices[i].equals(character)) {
                    int soundIndex = (int) (Math.random()*rimeMap.get(character).length);
                    String choice = choices[i]+"."+ rimeMap.get(character)[soundIndex]
                            .replace(".png", ".mp3");
                    System.out.println("Answer : " + choice + " Index: "+soundIndex);
                    main.musicManager.playMusic(choice);
                } else {
                    String s = rimeMap.get((choices[i])+"")[0].replace(".png", ".mp3");
                    //(getSub(choices[i])[0]+"").replace(".png", ".mp3");
                    System.out.println("Choice: "+choices[i]+"/"+s);
                    if(rimeMap.get((choices[i])+"") != null) {
                        main.musicManager.playMusic(choices[i]+"."+s);
                    }
                }
                return true;
            }
        });
    }

    /**
     * This function sets up Text buttons.
     * */
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
     * This function handles the choice made by the player
     * */
    private void choiceMade(String choice) {
        for (TextButton button: textButtons) {
            button.setDisabled(true);
        }
        System.out.println(character + " : " + choice);
        if(character.equals(choice)) {
            timesCorrect++;
            if(timesCorrect <= limit) {
                //System.out.println("Set True");
                runAnimation = true;
                change();
            }
            if(timesCorrect > limit) {
                numGems++;
                float delay = .75f; // seconds

                Timer.schedule(new Timer.Task(){
                    @Override
                    public void run() {
                        // Do your work
                        main.setScreen(new MenuScreen(main));
                    }
                }, delay);
            }
        } else {
            timesIncorrect++;
        }
    }

    /**
     * Render assests.
     * */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(135/255f, 206/255f, 235/255f, 1);//135/255f, 206/255f, 235/255f, 1
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();//bottom//draws the texture
        stage.act();
        batch.begin();
        if(runAnimation) {
            //System.out.println("Running");
            time += delta;
            runAnimation();
        }
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
        for (Sprite sprite: rimeWordSprites) {
            sprite.draw(batch);
        }
        batch.end();
        stage2.draw();
    }

    /**
     * The functionality is in the name.
     * */
    private void drawScore(SpriteBatch spriteBatch, Texture t, float x, float y, float w,
                           float h) {
        w = Constants.gameWidth(w);
        h = Constants.gameHeight(h);
        spriteBatch.draw(t, Constants.gameX(x, w), Constants.gameY(y, h), w, h);
    }

    /**
     * Dispose assests.
     * */
    @Override
    public void dispose() {
        //Make sure to dispose of it
        batch.dispose();
        stage.dispose();
        stage2.dispose();
        skin.dispose();
        skin2.dispose();
        for (Sprite sprite: rimeWordSprites) {
            sprite.getTexture().dispose();
        }
    }

    /**
     * This function mixes up the choices after the player gets a right answer.
     * */
    protected void change() {
        if(index < limit-1) {
            index++;
            character = arr.get(index);
            System.out.println(character);
            randomize();
            makeRimeWord();
        }
    }

}
