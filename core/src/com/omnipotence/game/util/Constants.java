package com.omnipotence.game.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.omnipotence.game.Main;

import java.util.ArrayList;

/**
 * Copyright 2015, Omnipotence, LLC, All rights reserved.
 * Created by Omnipotence, LLC.
 * This class contains the basic stuff need for almost every class.
 */

public class Constants {

    private static float screenHeight = Gdx.graphics.getHeight();
    private static float screenWidth = Gdx.graphics.getWidth();
    private static float deltaY = screenHeight / 1800f;
    private static float deltaX = screenWidth / 2560f;

    /**
     * This function returns a re-sized the value if the device's screen width is not same as a
     * Pixel C. Must use this when developing in or for other devices that are not Pixel C.
     */
    public static float gameWidth(float width) {
        return width * deltaX;
    }

    /**
     * This function returns a re-sized the value if the device's screen height is not same as a
     * Pixel C. Must use this when developing in or for other devices that are not Pixel C.
     */
    public static float gameHeight(float height) {
        return height * deltaY;
    }

    public static float gameX(float percent, float width) {
        return (screenWidth - width) * percent;
    }

    public static float gameY(float percent, float height) {
        return (screenHeight - height) * percent;
    }

    public static float getScreenWidth() {
        return screenWidth;
    }

    public static float getScreenHeight() {
        return screenHeight;
    }


    public static Texture grabATexture(char c) {
        if (!Character.isDigit(c) && !Character.isLetter(c)) {
            return  AssetManager.getInstance().getTexture(((c == ',') ? "comma" : (c == '.') ?
                    "period" : "question") +".png");
        }
        return AssetManager.getInstance().getTexture(((Character.isLetter(c)) ?
                ((Character.isUpperCase(c)) ? "Uppercase.": "Lowercase.") : "") +c+".png");
    }

    public static ArrayList<Sprite> convertToSprites(String string, float x, float y,
                                            float interval, float w, float h, int maxChar) {
        ArrayList<Sprite> sprites = new ArrayList<Sprite>();
        ArrayList<String> dividedString = new ArrayList<String>();
        w = Constants.gameWidth(w);
        h = Constants.gameHeight(h);
        while(string.length() > maxChar-1) {
            String tempString = string.substring(0, maxChar);
            int j = (tempString.lastIndexOf(" ") > maxChar/2) ?
                    tempString.lastIndexOf(" ") : maxChar;
            dividedString.add(string.substring(0, j));
            string = string.substring(j);
        }
        dividedString.add(string);
        for (int i = 0; i < dividedString.size(); i++) {
            String section = dividedString.get(i);
            for (int j = 0; j < section.length(); j++) {
                if(section.charAt(j) != ' ') {
                    Sprite sprite = new Sprite(grabATexture(section.charAt(j)));
                    sprite.setSize(w, h);
                    sprite.setPosition(Constants.gameX(x + interval * j, w), Constants.gameY(y, h));
                    sprites.add(sprite);
                }
            }
            y -= .071f;
        }

        return sprites;
    }

    /**
     * Returns an ArrayList of Imagebuttons with each containing a letter and a click event that
     *      plays the sound of the letter.
     *  @param string: given string to be converted into buttons.
     *  @param xPosition: x starting position of the buttons.
     *                  (Values being from 0 to 1, going left to right)
     *  @param yPosition: y starting position of the buttons.
     *                  (Values being from 0 to 1, going bottom to top)
     *  @param buttonSize: size of the buttons. Note: the values are not the same of xPosition and
     *                   yPosition. 150f is example value and an ideal one.
     * */
    public static ArrayList<ImageButton> convertToButtons(final Main main, final String string,
                                                          float xPosition, float yPosition,
                                                          float buttonSize) {
        ArrayList<ImageButton> imageButtons = new ArrayList<ImageButton>();
        ArrayList<String> dividedString = new ArrayList<String>();
        divideString(dividedString, string, buttonSize);
        float intervalX = buttonSize/screenWidth;
        float intervalY = buttonSize*1.25f/screenHeight;
        TextureAtlas buttonAtlas = new TextureAtlas("buttons/button.pack");
        for (int i = 0; i < dividedString.size(); i++) {
            String section = dividedString.get(i);
            for (int j = 0; j < section.length(); j++) {
                final String s = section.substring(j, j + 1).toUpperCase();
                if (!s.equals(" ")) {
                    ImageButton.ImageButtonStyle buttonStyle = new ImageButton.ImageButtonStyle();
                    buttonStyle.down = new TextureRegionDrawable(buttonAtlas.getRegions().get(1));
                    buttonStyle.up = new TextureRegionDrawable(buttonAtlas.getRegions().get(0));
                    Drawable image = AssetManager.getInstance().convertTextureToDrawable(s+".png"); //getTexRegionDrawable("English/Alphabet/" + s + ".png");
                    image.setMinWidth(buttonSize * .75f);
                    image.setMinHeight(buttonSize * .75f);
                    buttonStyle.imageUp = image;
                    ImageButton imageButton = new ImageButton(buttonStyle);
                    imageButton.setSize(buttonSize, buttonSize);
                    imageButton.setPosition(Constants.gameX(xPosition + intervalX * j, buttonSize),
                            Constants.gameY(yPosition - intervalY * i, buttonSize));
                    imageButton.addListener(new ClickListener() {
                        @Override
                        public void clicked(InputEvent event, float x, float y) {
                            main.musicManager.playMusic("Grapheme"+s+".mp3");
                        }
                    });
                    imageButtons.add(imageButton);
                }
            }
        }

        return imageButtons;
    }

    private static void divideString(ArrayList<String> dividedString, String string, float size) {
        int maxChar = (int) Math.floor((screenWidth/size) * 1f);
        while(string.length() > maxChar-1) {
            String tempString = string.substring(0, maxChar);
            int j = (tempString.lastIndexOf(" ") > maxChar/2) ?
                    tempString.lastIndexOf(" ") : maxChar;
            dividedString.add(string.substring(0, j));
            string = string.substring(j);
        }
        dividedString.add(string);
    }

}
