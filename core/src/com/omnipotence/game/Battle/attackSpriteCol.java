package com.omnipotence.game.Battle;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Copyright 2015, Omnipotence, LLC, All rights reserved.
 * Created by Unknonwn. Modified by Omnipotence, LLC.
 * This class sets up the sprites for the character attacks.
 */

public class attackSpriteCol {

    private Sprite[] sprites;

    /**
     * This is the Constructor.
     */
    public attackSpriteCol() {
        initialize();
    }

    /**
     * This functions goes through all the images in the attacks folder and makes them into sprites
     * to be stored in the sprites[].
     */
    public void initialize(){
        FileHandle dirHandle;
        if (Gdx.app.getType() == Application.ApplicationType.Android ||
                Gdx.app.getType() == Application.ApplicationType.iOS ) {
            dirHandle = Gdx.files.internal("attacks/");
        } else {
            // ApplicationType.Desktop ..
            dirHandle = Gdx.files.internal("./bin/attacks/");
        }
        FileHandle[] fileHandles = dirHandle.list();
        sprites = new Sprite[fileHandles.length];
        for(int i = 0; i < sprites.length; i++) {
            sprites[i] = new Sprite(new Texture(fileHandles[i]));
        }
    }

    /**
     * This functions returns a random sprite from the sprites[].
     */
    public Sprite pickAttack() {
        return sprites[(int) (Math.random() * (sprites.length-1))];
    }

    /**
     * This function dispose all of the sprites in the sprites[].
     */
    public void dispose() {
        for (Sprite sprite: sprites) {
            sprite.getTexture().dispose();
        }
    }
}
