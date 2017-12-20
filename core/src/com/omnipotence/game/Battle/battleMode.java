package com.omnipotence.game.Battle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Timer;
import com.omnipotence.game.Menu_Options.Journey.LevelScreen;
import com.omnipotence.game.Main;
import com.omnipotence.game.Menu_Options.Battle.MultiplayerResultScreen;
import com.omnipotence.game.Menu_Options.Battle.MultiplayerSelectionScreen;
import com.omnipotence.game.Stage.scoreSaver;
import com.omnipotence.game.Stage.userData;
import com.omnipotence.game.defaultScreen;
import com.omnipotence.game.util.AssetManager;
import com.omnipotence.game.util.Constants;

import java.util.ArrayList;

/**
 * Copyright 2015, Omnipotence, LLC, All rights reserved.
 * Created by Omnipotence, LLC.
 * This class is the battle mode screen.
 */

public class battleMode extends defaultScreen {

	private Stage stage;
	private BitmapFont font;
	private Skin skin;
    private SpriteBatch batch;
    private Table mainContainer;
    private Main game;
    private ArrayList<String> arr;
    private String[] choices;
    private String choice;
    private ImageButton[] imageButtons;
    private boolean mentoratk, gemcolatk;
    private int battleType; // 0 is default, 1 is math equations, 2 is no Textures, 3 is questions
    private gameCharacter hero, villian;
    private float time, time2 = 0;
    //private boolean jumpDown, jumpUp = false;
    private int power = 0;
    private Texture clockTexture;
    private float battleTime = 0f;
    private userData player1, player2;
    private int whoseTurn = 0;
    private Integer start, end;
    private boolean falseFirstTime = false;
    private boolean villianJump = false;

    /**
     * This is the Constructor.
     * @param gameInstance: Main variable.
     * @param a: List of all the previous level names after the last battle mode.
     * @param gp: The average score that player received from the previous levels.
     * @param type: Integer value telling what type of battle is it, 0 is default.
     * @param start: The position of the starting stage.
     * @param end: The position of the ending stage.
     */
    public battleMode(Main gameInstance, ArrayList<String> a, int gp, int type,
                      int start, int end) {
        super();
        this.arr = a;
        game = gameInstance;
        this.battleType = type;
        this.start = start;
        this.end = end;

        choices = new String[(a.size() < 5) ? a.size() : 4];
        for (int i = 0; i < choices.length; i++) {
            choices[i] = a.get(i);
        }
        this.choice = arr.get(0);

        initMainContainer();
        //Setup Character
        hero = new gameCharacter("gemCollector", .1f, .2f, 2, 0, gp, false);
        if(battleType == 5) {
            villian = new gameCharacter("mentor", .9f, .2f, 2, 0, gp, true);
        } else {
            villian = new gameCharacter(Main.characters[start/(end-start)], .9f, .2f, 2, 0, 10,
                    true);
        }
        player1 = new userData(0,0,0);
        player2 = new userData(0,0,0);
        create();
    }

    /**
     * This function sets up the mainContainer.
     */
    private void initMainContainer() {
        mainContainer = new Table();
        mainContainer.setBackground(AssetManager.getInstance()
                .convertTextureToDrawable("battleBackground"));
        mainContainer.setFillParent(true);
    }

    /**
     * This function initializes the main components.
     */
    private void create () {
        //BASIC STUFF
		batch = new SpriteBatch();

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        stage.addActor(mainContainer);

        skin = new Skin();
        setupSkin();

        //Setup Hub
        hero.setupHUB(.08f, .9f);
        villian.setupHUB(.92f, .9f);

        //Attack
        gemcolatk = false;
        mentoratk = false;

        //Buttons
        setupButtons();
	}

    /**
     * This function sets up the skin.
     */
    private void setupSkin() {
        TextureAtlas buttonAtlas = new TextureAtlas("buttons/button.pack");
        skin.addRegions(buttonAtlas);
        font = new BitmapFont();
        font.getData().setScale(Constants.gameWidth(5), Constants.gameHeight(5));
        skin.add("largeFont", new BitmapFont(Gdx.files
                .internal("skin/fonts/chalkboard-font-large.fnt")));
        Label.LabelStyle largeLabelStyle = new Label.LabelStyle();
        largeLabelStyle.font = skin.getFont("largeFont");
        largeLabelStyle.fontColor = Color.WHITE;
        skin.add("largeLabel", largeLabelStyle);
    }

    /**
     * This function sets up the exit, robot, and the power buttons.
     */
    private void setupButtons() {
        //Exit Button
        ImageButton exitbutton = new ImageButton(AssetManager.getInstance()
                .convertTextureToDrawable("Exit.png"));
        setPositionAndSize(exitbutton, .975f, .975f, 150f, 150f);
        exitbutton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(battleType == 5) {
                    game.setScreen(new MultiplayerSelectionScreen(game));
                } else {
                    game.setScreen(new LevelScreen(game, start, end));
                }
            }
        });

        //Central Button
        ImageButton centralButton = new ImageButton(AssetManager.getInstance()
                .convertTextureToDrawable("speaker.png"));
        setPositionAndSize(centralButton, .5f, .7f, 147f, 225f);
        centralButton.addListener(new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                game.musicManager.playMusic(choice+".mp3");
                return true;
            }
        });

        //Choice Image Buttons
        imageButtons = new ImageButton[choices.length];
        float intervals = .1f;
        float startIndex = (choices.length == 2) ? .45f : (choices.length == 3) ? .4f : .35f;
        for (int i = 0; i < choices.length; i++) {
            imageButtons[i] = newImageButton(choices[i], startIndex + (intervals * i),
                    .5f, 150f, 225f, i);
            System.out.println(choices[i]);

        }

        //Power Button
        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.up = skin.getDrawable("ButtonUnpressed");
        buttonStyle.down = skin.getDrawable("ButtonsPressed");
        buttonStyle.font = font;
        buttonStyle.fontColor = Color.WHITE;
        TextButton powerButton = new TextButton("POWER!", buttonStyle);
        setPositionAndSize(powerButton, .1f, .05f, 180f, 120f);
        powerButton.getLabel().setFontScale(Constants.gameWidth(2f), Constants.gameHeight(2f));
        powerButton.addListener(new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                if(hero.getPointsLimits() > 0) {
                   // jumpUp = true;
                    time = 0;
                    power = 1;
                }
                return true;
            }
        });
        if(battleType == 5) {
            TextButton powerButton2 = new TextButton("POWER!", buttonStyle);
            setPositionAndSize(powerButton2, .9f, .05f, 180f, 120f);
            powerButton2.getLabel().setFontScale(Constants.gameWidth(2f), Constants.gameHeight(2f));
            powerButton2.addListener(new InputListener(){
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                    if(villian.getPointsLimits() > 0) {
                        // jumpUp = true;
                        villianJump = true;
                        time2 = 0;
                        power = 1;
                    }
                    return true;
                }
            });
        }
    }

    /**
     * This function sets the size and positions of the button based on the given x & y
     * and width & height. At the end, the button is added to the stage. Only for the exit, robot,
     * and the power buttons
     */
    private void setPositionAndSize(Button button, float x, float y, float width, float height) {
        float resizeWidth = Constants.gameWidth(width);
        float resizeHeight = Constants.gameHeight(height);
        button.setSize(resizeWidth, resizeHeight);
        button.setPosition(Constants.gameX(x, resizeWidth), Constants.gameY(y, resizeHeight));
        stage.addActor(button);
    }

    /**
     * This function sets up the Imagebuttons for the choices.
     */
    private ImageButton newImageButton(String style, float x, float y, float w, float h,
                                       final int i) {
        w = Constants.gameWidth(w);
        h = Constants.gameHeight(h);
        ImageButton imageButton = new ImageButton(newStyle(style, w, h));
        imageButton.setPosition(Constants.gameX(x, w), Constants.gameY(y, h));
        imageButton.setSize(w, h);
        stage.addActor(imageButton);
        imageButton.addListener(new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("Image Touch: " + i);
                if (!gemcolatk && !mentoratk) {
                    if(battleType == 5) {
                        if (choices[i].equals(choice)) {
                            if(whoseTurn == 0) {
                                if(falseFirstTime) {
                                    player1.hasCountered();
                                }
                                player1.gotCorrect();
                                hero.setupAttack();
                                gemcolatk = true;
                            } else {
                                if(falseFirstTime) {
                                    player2.hasCountered();
                                }
                                player2.gotCorrect();
                                villian.setupAttack();
                                mentoratk = true;
                            }
                            change();
                        } else {
                            if(!falseFirstTime) {
                                falseFirstTime = true;
                            }
                            if(whoseTurn == 0) {
                                player1.hasMissed();
                            } else {
                                player2.hasMissed();
                            }
                        }
                       whoseTurn = (whoseTurn+1) % 2;
                    } else {
                        if (choices[i].equals(choice)) {
                            change();
                            time = 0;
                            hero.setupAttack();
                            gemcolatk = true;
                        } else {
                            time2 = 0;
                            villian.setupAttack();
                            mentoratk = true;
                        }
                    }
                }
                return true;
            }
        });
        return imageButton;
    }

    /**
     * This function mixes up the choices after a right answer.
     */
	private void change() {
        int turn = (int) (Math.random() * choices.length);
        int indexPicked = (int) (Math.random() * arr.size());
        ArrayList<String> picked = new ArrayList<String>();
        choices[turn] = arr.get(indexPicked);
        picked.add(arr.get(indexPicked));
        arr.remove(arr.get(indexPicked));
        for (int i = 0; i < choices.length; i++) {
            if(i != turn) {
                int j = (int) (Math.random() * arr.size());
                choices[i] = arr.get(j);
                picked.add(arr.get(j));
                arr.remove(j);
            }
        }
        arr.addAll(picked);
        choice = choices[turn];

        for(int i = 0; i < choices.length; i++) {
            imageButtons[i].setStyle(newStyle(choices[i], imageButtons[i].getWidth(),
                    imageButtons[i].getHeight()));
        }

	}

    /**
     * This function changes the image of the Imagebuttons.
     */
    private ImageButton.ImageButtonStyle newStyle(String string, float width, float height) {
        ImageButton.ImageButtonStyle buttonStyle = new ImageButton.ImageButtonStyle();
        buttonStyle.down = skin.getDrawable("ButtonsPressed");
        buttonStyle.up = skin.getDrawable("ButtonUnpressed");
        Drawable image = AssetManager.getInstance().convertTextureToDrawable(string+".png");
        image.setMinWidth(width*.75f);
        image.setMinHeight(height*.75f);
        buttonStyle.imageUp = image;
        return buttonStyle;
    }

    /**
     * This functions handles how to end game based on whether the player successfully completed
     * the battle.
     */
    private void endGame(boolean failed) {
        float delay = 1f; // seconds
        Timer.schedule(new Timer.Task(){
           @Override
           public void run() {
           }
        }, delay);
        if(!failed) {
            if(!game.gameStages.get(game.currentStage).unlockNextLevel(10f)) {
                game.gameStages.get(game.currentStage+1).getLevel(0).levelData
                        .setUnlocked(true);
            }
        }
        game.setScreen(new LevelScreen(game, start, end));
    }

    /**
     * This function renders the textures and animations including the characters.
     */
    @Override
    public void render(float delta) {
        stage.draw();
        stage.act();
        batch.begin();

        //THE HUBS
        hero.drawHUB(batch);
        villian.drawHUB(batch);

        battleTime += delta;
        String gameTime = ((int) Math.floor(battleTime/60f))+":"+((int)(battleTime%60)/10)+""+
                ((int)(battleTime%60)%10);
        font.draw(batch, gameTime, Constants.gameX(.5f, font.getXHeight()*gameTime.length()),
                Constants.gameY(.85f, font.getLineHeight()));

        if(battleType == 5) {
            String playerTurn = "Player "+(whoseTurn+1)+"'s turn";
            font.draw(batch, playerTurn,
                    Constants.gameX(.5f, font.getXHeight()*playerTurn.length()),
                    Constants.gameY(.95f, font.getLineHeight()));
        }

        if (power == 1 && !villianJump) {
            power = hero.drawCharacterJump(batch, (int) Math.floor(time/.25f));
        } else if(!gemcolatk) {
            hero.drawCharacterIdle(batch, time);
        } else {
            hero.drawCharacterAttack(batch, time, 10f);

            if(hero.getAttackX() > Constants.gameX(1f, 0f)) {
                gemcolatk = false;
            } else if(hero.getAttackX() >= Constants.gameX(villian.getXPosition(),
                    villian.getCharacterWidth())) {
                gemcolatk = false;
                villian.changeHearts(-1);
                hero.changeGems(1);
            }

        }

        if (power == 1 && villianJump) {
            power = villian.drawCharacterJump(batch, (int) Math.floor(time2/.25f));
            villianJump = !(power == 0);
        } else if(!mentoratk) {
            villian.drawCharacterIdle(batch, time2);
        } else {
            villian.drawCharacterAttack(batch, time2, -10f);
            if(villian.getAttackX() < 0f) {
                mentoratk = false;
            } else if(hero.getCharacterY() <= villian.getAttackY()+(villian.getAttackHeight()) &&
                    Constants.gameX(hero.getXPosition(), 0f) >= villian.getAttackX()) {
                mentoratk = false;
                hero.changeHearts(-1);
                villian.changeGems(1);
            }
        }

        if (villian.getHeartsLimit() < 1) {
            if(battleType == 5) {
                scoreSaver scores = new scoreSaver();
                scores.saveScores(player1, player2);
                game.setScreen(new MultiplayerResultScreen(game, battleTime, player1, player2));
            } else {
                endGame(false);
            }
        }

        if (hero.getHeartsLimit() < 1) {
            if(battleType == 5) {
                scoreSaver scores = new scoreSaver();
                scores.saveScores(player1, player2);
                game.setScreen(new MultiplayerResultScreen(game, battleTime, player1, player2));
            } else {
                endGame(true);
            }
        }

        time += Gdx.graphics.getDeltaTime();
        time2 += delta;

        batch.end();

    }

    /*public void jump(gameCharacter character) {
        if(jumpUp && !jumpDown) {
            character.setY(15f);
            //System.out.println("Jumping Up");
            if(character.getCharacterY() >= Constants.gameX(.3f, character.getCharacterHeight())) {
                jumpDown = true;
                jumpUp = false;
          //      System.out.println("Jump change");
            }
        } else if(jumpDown) {
        //    System.out.println("Jumping Down");
            character.setY(-15f);
            jumpDown = character.getCharacterY() >
                    Constants.gameY(character.getYPosition(), character.getCharacterHeight());
            if(!jumpDown) {
         //       System.out.println("Jumped!");
                power = 0;
            }
        }
    }*/

    /**
     * This function disposes of the assets.
     */
    @Override
    public void dispose(){
        batch.dispose();
        hero.characterDispose();
        villian.characterDispose();
        stage.dispose();
        skin.dispose();
    }

}