package com.omnipotence.game.Stage;

import com.badlogic.gdx.utils.Json;

/**
 * Copyright 2015, Omnipotence, LLC, All rights reserved.
 * Created by Omnipotence, LLC.
 */

public class scoreData {
    public int p1correct, p1missed, p1countered;
    public int p2correct, p2missed, p2countered;

    private Json json;

    public scoreData(){
        p1correct = 0;
        p1missed = 0;
        p1countered = 0;
        p2correct = 0;
        p2missed = 0;
        p2countered = 0;
        json = new Json();
    }

}
