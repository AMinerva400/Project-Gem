package com.omnipotence.game.Practice;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.omnipotence.game.Main;
import com.omnipotence.game.util.AssetManager;
import com.omnipotence.game.util.Constants;

import java.util.ArrayList;

/**
 * Copyright 2015, Omnipotence, LLC, All rights reserved.
 * Created by Omnipotence, LLC.
 * This Screen is where the player practices the math equations.
 */

public class mathPracticeMode extends centralCore {

    private Label equation;
    private int firstNum, secondNum, answer;
    private Texture[] answerTexture;
    private float groupHeight, time;
    private String equat;

    /**
     * This is the Constructor.
     * @param main: Main variable.
     * @param name: Name of the level and also the equation.
     * @param list: List of all the levels in the stages.
     * @param start: The position of the starting stage.
     * @param end: The position of the ending stage.
     */
    public mathPracticeMode(Main main, String name, ArrayList<String> list, int start, int end) {
        super(main, name, list, 4, 12, start, end);
        equation = new Label(equat, skin, "largeLabel");
        float eWidth = Constants.gameWidth(equation.getWidth());
        float eHeight = Constants.gameWidth(equation.getHeight());
        equation.setSize(eWidth, eHeight);
        equation.setPosition(Constants.gameX(.5f, eWidth), Constants.gameY(.75f, eHeight));
        stage.addActor(equation);
    }

    /**
     * Sets up the positions[]. For each float[] in position[], the first two values are the
     * x & y positions for TextButton and the last two values are the x & y positions of the
     * ImageButtons. This function also breaks down the equation and sets up the circle for
     * answerTexture.
     */
    @Override
    protected void setPositions() {
        time = 0;
        int signIndex = character.indexOf('+') + character.indexOf('-') + character.indexOf('*')+2;
        firstNum = Integer.parseInt(character.substring(0, signIndex).replace(" ", ""));
        secondNum = Integer.parseInt(character.substring(signIndex+1).replace(" ", ""));
        switch (character.charAt(signIndex)) {
            case '+' : answer = firstNum + secondNum; break;
            case '-' : answer = firstNum - secondNum; break;
            default: answer = firstNum * secondNum; break;
        }
        if (character.charAt(signIndex) == '*') {
            answerTexture = new Texture[secondNum];
            for(int i = 0; i < secondNum; i++){
                answerTexture[i] = AssetManager.getInstance().getTexture(firstNum+"CIRCLE.png");
            }
            groupHeight = 42f*firstNum;
        } else {
            int tens = answer/10;
            if(answer%10 != 0 || tens == 0) {
                answerTexture = new Texture[tens+1];
                answerTexture[answerTexture.length-1] = AssetManager.getInstance()
                        .getTexture((answer%10) + "CIRCLE.png");
            } else {
                answerTexture = new Texture[tens];
            }
            for(int i = 0; i < tens; i++){
                System.out.println(AssetManager.getInstance().getTexture("10CIRCLE.png").toString());
                answerTexture[i] = AssetManager.getInstance().getTexture("10CIRCLE.png");
            }
            groupHeight = 42f*12;
        }
        equat = character+" = ?";
        character = ""+answer;
        this.positions[0] = new float[]{0f, .5f, .1f, .5f};
        this.positions[1] = new float[]{1f, .5f, .9f, .5f};
        this.positions[2] = new float[]{0f, .2f, .1f, .2f};
        this.positions[3] = new float[]{1f, .2f, .9f, .2f};
    }

    /**
     * This function initializes extra variables.
     */
    @Override
    protected void createExtraAssets() {
        skin.add("largeFont", new BitmapFont(Gdx.files
                .internal("skin/fonts/chalkboard-font-large.fnt")));
        Label.LabelStyle largeLabelStyle = new Label.LabelStyle();
        largeLabelStyle.font = skin.getFont("largeFont");
        skin.add("largeLabel", largeLabelStyle);
        equation = new Label("", skin, "largeLabel");
        stage.addActor(equation);
    }

    /**
     * This function sets up the Imagebuttons.
     * */
    @Override
    public void setImageButtonListeners(final int i) {
        imageButtons[i].addListener(new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                viewDialog(AssetManager.getInstance().convertTextureToDrawable(choices[i]+".png"));
                return true;
            }
        });
    }

    /**
     * This function sets up the help Screen button.
     * */
    private void newButton() {
        ImageButton newScreen = new ImageButton(AssetManager.getInstance()
                .convertTextureToDrawable("speaker.png"));
        float imageButtonWidth = Constants.gameWidth(150f);
        float imageButtonHeight = Constants.gameHeight(150f);
        newScreen.setPosition(Constants.gameX(.5f, imageButtonWidth),
                Constants.gameY(.975f, imageButtonHeight));
        newScreen.setSize(imageButtonWidth, imageButtonHeight);
        newScreen.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

            }
        });
        stage.addActor(newScreen);
    }

    /**
     * This function renders the textures and this includes the circles textures for the numbers.
     */
    @Override
    protected void renderExtraStuff(float delta) {
        time += delta;
        Texture groupie = new Texture("group.png");
        float intervals = (answerTexture.length / 2);
        float startIndex = 4.75f - (intervals / 2f);
        intervals = .06f;
        startIndex *= .1f;
        for (int i = 0; i < answerTexture.length; i++) {
            batch.draw(groupie, Constants.gameX(startIndex + intervals * i,
                    Constants.gameWidth(90f)), Constants.gameY(.175f /*+ ((Math.round(time)%2 == 1) ? .025f : 0f)*/,
                    Constants.gameHeight(groupHeight + 75f)), Constants.gameWidth(90f),
                    Constants.gameHeight(groupHeight + 75f));
            batch.draw(answerTexture[i], Constants.gameX(startIndex + intervals * i,
                    Constants.gameWidth(130f)), Constants.gameY(.2f /*+ ((Math.round(time)%2 == 1) ? .025f : 0f)*/,
                    Constants.gameHeight(groupHeight)), Constants.gameWidth(130f),
                    Constants.gameHeight(groupHeight));
        }
    }

    /**
     * This function disposes of the extra assets.
     */
    @Override
    protected void disposeExtraAssets() {}

    /**
     * This function handles what to do when the player makes the right choice.
     */
    @Override
    protected void change() {
        if(timesCorrect == 4) {
            equat = equat.replace("?", "")+ answer;
            equat = "?" + equat.substring((""+firstNum).length());
            character = ""+firstNum;
            equation.setText(equat);
        } else if(timesCorrect == 8) {
            equat = equat.replace("?", ""+firstNum);
            equat = equat.substring(0, (""+firstNum).length()+3)+"?"
                    +equat.substring((""+firstNum).length()+3+(""+secondNum).length());
            character = ""+secondNum;
            equation.setText(equat);
        }
        randomize();
        for(int i = 0; i < textButtons.length; i++) {
            imageButtons[i].setStyle(newStyle(choices[i]+".png"));
        }
    }
}
