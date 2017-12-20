package com.omnipotence.game.util;

import com.badlogic.gdx.Gdx;

/**
 * Ignore this class.
 */


public class RendererManager {
    private static RendererManager instance;
    private String ClassName    = "RenderManager";
    public int basicWidth       = 1920;
    public int basicHeight      = 1200;
    public int scaleVariant     = 1;
    public String scaleFolder   = "1920_1200";
    public float realWidth      = 1920;
    public float realHeight     = 1200;

    public static RendererManager getInstance() {
        if(null == instance){
            instance = new RendererManager();
        }
        return instance;
    }

    public RendererManager() {
        Gdx.app.log(ClassName, "Init");
        realWidth = Gdx.graphics.getWidth();
        realHeight = Gdx.graphics.getHeight();
        Gdx.app.log(ClassName, "Display size " + Float.toString(realWidth) + "x" + Float.toString(realHeight));
        if (realWidth > 1000) {
            scaleVariant = 1;
            scaleFolder = "1920_1200";
        } else if (realWidth > 640) {
            scaleVariant = 2;
            scaleFolder = "960_600";
            basicWidth = basicWidth / 2;
            basicHeight = basicHeight / 2;
        } else {
            scaleVariant = 3;
            scaleFolder = "640_400";
            basicWidth = basicWidth / 3;
            basicHeight = basicHeight / 3;
        }
        Gdx.app.log(ClassName, "Camera size " + Float.toString(basicWidth) + "x" + Float.toString(basicHeight));
    }

    public int getScaled(int PosData) {
        return Math.round(PosData / scaleVariant);
    }

    public int invertedY(int Ypos) {
        return (1200 - Ypos);
    }

    public int scaledInvertedY(int Ypos) {
        return Math.round((1200 - Ypos) / scaleVariant);
    }

}