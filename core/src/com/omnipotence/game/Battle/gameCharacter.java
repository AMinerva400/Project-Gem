package com.omnipotence.game.Battle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.omnipotence.game.util.Constants;

/**
 * Copyright 2015, Omnipotence, LLC, All rights reserved.
 * Created by Omnipotence, LLC.
 * This class creates the hubs, the characters, and handles the functions regarding them.
 */

public class gameCharacter {
    private Sprite HUB;
    private Sprite[] HEARTS, GEMS, POINTS;
    private attackSpriteCol attacks;
    private Sprite characterAttack;
    private Animation idleAnim, attackAnim, jumpAnim, hurtAnim;
    private Animation[] characterAnim;
    private float xPosition, yPostion, cX, cY, cWidth, cHeight, jumpInterval;
    private int heartsLimit, gemsLimit, pointsLimits;
    private boolean flip;
    private float jumpLimit;
    private int state = 0;
    private float attackSpeed = 20f;

    /**
     * This is the Constructor.
     * @param cName: Name of the character to be made.
     * @param xPositon: The X position of where the character is meant to be drawn.
     * @param yPosition: The Y position of where the character is meant to be drawn.
     * @param hearts: The number of hearts to be drawn on the hub.
     * @param gems: The numbers of gems to be drawn on the hub.
     * @param points: The number of points to be drawn on the hub.
     * @param flip: The direction of where the character is meant to be facing. False for facing
     *            right.
     */
    public gameCharacter(String cName, float xPositon, float yPosition, int hearts, int gems,
                         int points, boolean flip) {
        this.xPosition = xPositon;
        this.yPostion = yPosition;
        setupCharacter(cName, xPositon, yPosition);
        this.heartsLimit = hearts;
        this.gemsLimit = gems;
        this.pointsLimits = points;
        this.flip = flip;
        this.jumpLimit = 4f;
        this.jumpInterval = .1f;
    }

    /**
     * This function sets up the character.
     */
    private void setupCharacter(String character, float xPosition, float yPosition) {
        TextureAtlas idleAtlas = new TextureAtlas(doesFileExist(character, "Idle"));
        TextureAtlas attackAtlas = new TextureAtlas(doesFileExist(character, "Attack"));
        TextureAtlas jumpAtlas = new TextureAtlas(doesFileExist(character, "Jumping"));
        TextureAtlas hurtAtlas = new TextureAtlas(doesFileExist(character, "Hurt"));
        this.idleAnim = new Animation(1/15f, idleAtlas.getRegions());
        this.attackAnim = new Animation(1/15f, attackAtlas.getRegions());
        this.jumpAnim = new Animation(1/15f, jumpAtlas.getRegions());
        this.hurtAnim = new Animation(1/15f, hurtAtlas.getRegions());
        this.characterAnim = new Animation[]{ this.idleAnim , this.attackAnim, this.jumpAnim,
                this.hurtAnim};
        this.cWidth = Constants.gameWidth(idleAnim.getKeyFrame(0).getRegionWidth());
        this.cHeight = Constants.gameHeight(idleAnim.getKeyFrame(0).getRegionHeight());
        this.cX = Constants.gameX(xPosition, cWidth);
        this.cY = Constants.gameY(yPosition, cHeight);
        //System.out.println("Character: "+ cWidth + " " +cHeight + " "+cX +" "+cY);
        attacks = new attackSpriteCol();
    }

    /**
     * This function checks if there is an animation for the character and action. It returns that
     * animation path. If one does not exist then the gemCollector animation for that action is
     * returned.
     */
    public FileHandle doesFileExist(String character, String action) {
        character = character.toLowerCase();
        FileHandle fileHandle = Gdx.files.internal("Characters/"+character+action+".txt");
        if(!fileHandle.exists()) {
            fileHandle = Gdx.files.internal("Characters/gemCollector" + action + ".txt");
        }
        return fileHandle;
    }

    /**
     * This function sets up the hub.
     */
    public void setupHUB(float xPercent, float yPercent) {
        //HUB
        Texture hubTexture = new Texture(Gdx.files.internal("HUB/HUB-LEFT.png"));
        this.HUB = new Sprite(hubTexture);
        float width = Constants.gameWidth(HUB.getWidth());
        float height = Constants.gameHeight(HUB.getHeight());
        float x = Constants.gameX(xPercent, width);
        float y = Constants.gameY(yPercent, height);
        this.HUB.setBounds(flip ? x + width : x, y, flip ? -width : width, height);

        //HEARTS
        this.HEARTS = new Sprite[10];
        Texture heartsTexture = new Texture(Gdx.files.internal("HUB/HUB-HEART.png"));
        float heartsHeight = Constants.gameHeight(heartsTexture.getHeight());
        float heartsWidth = Constants.gameWidth(heartsTexture.getWidth());
        float heartsX = xPercent + ((flip) ? -.0214f : .0214f);
        float heartsY = yPercent - .002f;
        for(int i = 0; i < this.HEARTS.length; i++){
            float xInterval = .024f*(i%5);
            float yInterval = .025f*(i/5);
            this.HEARTS[i] = new Sprite(heartsTexture);
            this.HEARTS[i].setBounds(
                    Constants.gameX(heartsX + ((flip) ? -xInterval : xInterval), heartsWidth),
                    Constants.gameY(heartsY - yInterval, heartsHeight), heartsWidth, heartsHeight);
        }

        //GEMS
        this.GEMS = new Sprite[10];
        Texture gemsTexture = new Texture(Gdx.files.internal("HUB/HUB-GEM.png"));
        float gemsHeight = Constants.gameHeight(gemsTexture.getHeight());
        float gemsWidth = Constants.gameWidth(gemsTexture.getWidth());
        float gemsX = xPercent - ((flip) ? -.015f : .015f);
        float gemsY = yPercent - .067f;
        for(int i = 0; i < this.GEMS.length; i++){
            float xInterval = (.01375f*i);
            this.GEMS[i] = new Sprite(gemsTexture);
            this.GEMS[i].setBounds(
                    Constants.gameX(gemsX + ((flip) ? -xInterval : xInterval), gemsWidth),
                    Constants.gameY(gemsY, gemsHeight), gemsWidth, gemsHeight);
        }

        //POINTS
        this.POINTS = new Sprite[10];
        Texture pointsTexture = new Texture(Gdx.files.internal("HUB/HUB-METER.png"));
        float pointsHeight = Constants.gameHeight(pointsTexture.getHeight());
        float pointssWidth = Constants.gameWidth(pointsTexture.getWidth());
        float pointsX = xPercent + ((flip) ? -.0155f : .0155f);
        float pointsY = yPercent - .108f;
        for(int i = 0; i < this.POINTS.length; i++){
            float xInterval = (.0108f*i);
            this.POINTS[i] = new Sprite(pointsTexture);
            this.POINTS[i].setBounds(
                    Constants.gameX(pointsX + ((flip) ? -xInterval : xInterval), pointssWidth),
                    Constants.gameY(pointsY, pointsHeight), pointssWidth, pointsHeight);
        }
        this.POINTS[0] = new Sprite(new Texture(Gdx.files.internal("HUB/HUB-METER-"
                + ((flip) ? "RIGHT" : "LEFT") + "-EDGE.png")));
        this.POINTS[0].setBounds(
                Constants.gameX(pointsX, pointssWidth),
                Constants.gameY(pointsY, pointsHeight), pointssWidth, pointsHeight);
        this.POINTS[this.POINTS.length-1] = new Sprite(new Texture(
                Gdx.files.internal("HUB/HUB-METER-" + ((flip) ? "LEFT" : "RIGHT") + "-EDGE.png")));
        this.POINTS[this.POINTS.length-1].setBounds(
                Constants.gameX(pointsX + ((flip) ? -(.0108f*9) : (.0108f*9)), pointssWidth),
                Constants.gameY(pointsY, pointsHeight), pointssWidth, pointsHeight);
    }

    /**
     * This function sets up the attack sprite for the character.
     */
    public void setupAttack() {
        characterAttack = attacks.pickAttack();
        float attackWidth = Constants.gameWidth(characterAttack.getTexture().getWidth());
        float attackHeight = Constants.gameHeight(characterAttack.getTexture().getHeight());
        float attackX = Constants.gameX(xPosition, attackWidth);
        float attackY = Constants.gameY(yPostion, attackHeight);
        characterAttack.setBounds(attackX, attackY, attackWidth, attackHeight);
    }

    /**
     * Ingore.
     */
    public Animation makeAnimation(String path) {
        return new Animation(1/15f, (new TextureAtlas(
                Gdx.files.internal(path))).getRegions());
    }

    /**
     * This function draws the idle animation of the character.
     */
    public void drawCharacterIdle(SpriteBatch spriteBatch, float time) {
        TextureRegion textureRegion = idleAnim.getKeyFrame(time);
        cWidth = Constants.gameWidth(textureRegion.getRegionWidth());
        cHeight = Constants.gameHeight(textureRegion.getRegionHeight());
        spriteBatch.draw(idleAnim.getKeyFrame(time, true),
                !flip ? cX + cWidth : cX, cY, !flip ? -cWidth : cWidth, cHeight);
    }

    /**
     * This function draws the attack animation of the character.
     */
    public void drawCharacterAttack(SpriteBatch spriteBatch, float time, float speed) {
        TextureRegion textureRegion = attackAnim.getKeyFrame(time);
        float width = Constants.gameWidth(textureRegion.getRegionWidth());
        float height = Constants.gameHeight(textureRegion.getRegionHeight());
        spriteBatch.draw(attackAnim.getKeyFrame(time, false),
                !flip ? cX + width : cX, cY, !flip ? -width : width, height);

        if(attackAnim.isAnimationFinished(time * 2)) {
            characterAttack.draw(spriteBatch);
            characterAttack.setX(characterAttack.getX() + speed);
        }
    }

    /**
     * This function draws the jump animation of the character.
     */
    public int drawCharacterJump(SpriteBatch spriteBatch, float time) {
        TextureRegion textureRegion = jumpAnim.getKeyFrame(time/6.4f, false);
        cWidth = Constants.gameWidth(textureRegion.getRegionWidth());
        cHeight = Constants.gameHeight(textureRegion.getRegionHeight());
        if(time < jumpLimit) {
            cY = Constants.gameY(yPostion+(jumpInterval*time), cHeight);
        } else if(time < jumpLimit*2) {
            cY = Constants.gameY(
                    (yPostion+(jumpInterval*jumpLimit))-(jumpInterval*(time%jumpLimit+1)), cHeight);
        }
        spriteBatch.draw(textureRegion, !flip ? cX + cWidth : cX, cY, !flip ? -cWidth : cWidth,
                cHeight);
        if(time < jumpLimit*2) {
            return 1;
        }
        //System.out.println("Jumped!");
        changePoints(-1);
        return 0;
    }

    /**
     * This function draws the damage taken animation of the character.
     */
    public void drawCharacterHurt(SpriteBatch spriteBatch, float time) {
        TextureRegion textureRegion = hurtAnim.getKeyFrame(time, false);
        cWidth = Constants.gameWidth(textureRegion.getRegionWidth());
        cHeight = Constants.gameHeight(textureRegion.getRegionHeight());
        spriteBatch.draw(textureRegion, !flip ? cX + cWidth : cX, cY, !flip ? -cWidth : cWidth,
                cHeight);
    }

    /**
     * Ignore.
     */
    public void drawCharacter(SpriteBatch spriteBatch, float time) {
        TextureRegion textureRegion = characterAnim[state]
                .getKeyFrame(time/((state == 2) ? 6.4f: 1f), state == 0);
        cWidth = Constants.gameWidth(textureRegion.getRegionWidth());
        cHeight = Constants.gameHeight(textureRegion.getRegionHeight());
        spriteBatch.draw(textureRegion, !flip ? cX + cWidth : cX, cY, !flip ? -cWidth : cWidth,
                cHeight);
        if(state == 1) {
            if(characterAnim[state].isAnimationFinished(time * 2)) {
                characterAttack.draw(spriteBatch);
                characterAttack.setX(characterAttack.getX() + attackSpeed);
            }
        } else if(state == 2) {
            if(time < jumpLimit) {
                cY = Constants.gameY(yPostion+(jumpInterval*time), cHeight);
            } else if(time < jumpLimit*2) {
                cY = Constants.gameY(
                        (yPostion+(jumpInterval*jumpLimit))-(jumpInterval*(time%jumpLimit+1)), cHeight);
            }
            spriteBatch.draw(textureRegion, !flip ? cX + cWidth : cX, cY, !flip ? -cWidth : cWidth,
                    cHeight);
            if(time >= jumpLimit*2) {
                state = 0;
                changePoints(-1);
            }
        }
    }

    /**
     * This function draws the hub.
     */
    public void drawHUB(SpriteBatch spriteBatch) {
        //Draw HUB.
        HUB.draw(spriteBatch);

        //Draw HEARTS.
        for(int i = 0; i < heartsLimit; i++){
            HEARTS[i].draw(spriteBatch);
        }

        //Draw GEMS.
        for(int i = 0; i < gemsLimit; i++){
            GEMS[i].draw(spriteBatch);
        }

        //Draw POINTS.
        for(int i = 0; i < pointsLimits; i++){
           POINTS[i].draw(spriteBatch);
        }
    }

    /**
     * This function returns the number of hearts left.
     */
    public int getHeartsLimit() {
        return this.heartsLimit;
    }

    /**
     * This function returns the number of gems left.
     */
    public int getGemsLimit() {
        return this.gemsLimit;
    }

    /**
     * This function returns the number of points left.
     */
    public int getPointsLimits() {
        return this.pointsLimits;
    }

    /**
     * This function returns the X position of the attack sprite.
     */
    public float getAttackX() {
        return characterAttack.getX();
    }

    /**
     * This function returns the Y position of the attack sprite.
     */
    public float getAttackY() {
        return characterAttack.getY();
    }

    /**
     * This function returns the height of the attack sprite.
     */
    public float getAttackHeight() {
        return characterAttack.getHeight();
    }

    /**
     * This function returns the X position of the Character.
     */
    public float getXPosition() {
        return xPosition;
    }

    /**
     * This function returns the original Y position of the Character.
     */
    public float getYPosition() {
        return yPostion;
    }

    /**
     * This function returns the Y position of the Character.
     */
    public float getCharacterY() {
        return cY;
    }

    /**
     * This function returns the width of the Character.
     */
    public float getCharacterWidth() {
        return cWidth;
    }

    /**
     * This function returns the height of the Character.
     */
    public float getCharacterHeight() {
        return cHeight;
    }

    /**
     * Ignore.
     */
    public int getState() {
        return state;
    }

    /**
     * Ignore.
     */
    public void setState(int newState) {
        state = newState;
    }

    /**
     * This functions sets the Character hearts.
     */
    public void changeHearts(int change) {
        this.heartsLimit += change;
    }

    /**
     * This functions sets the Character gems.
     */
    public void changeGems(int change) {
        this.gemsLimit += change;
    }

    /**
     * This functions sets the Character points.
     */
    public void changePoints(int change) {
        this.pointsLimits += change;
    }

    /**
     * This functions sets the Character Y position.
     */
    public void setY(float yChange) {
        cY += yChange;
    }

    /**
     * This functions disposes the assets.
     */
    public void characterDispose() {
        this.HUB.getTexture().dispose();
        for(int i = 0; i < 10; i++) {
            this.HEARTS[i].getTexture().dispose();
            this.GEMS[i].getTexture().dispose();
            this.POINTS[i].getTexture().dispose();
        }
        attacks.dispose();
        characterAttack.getTexture().dispose();
        for(TextureRegion regions : attackAnim.getKeyFrames()) {
           regions.getTexture().dispose();
        }
    }

}
