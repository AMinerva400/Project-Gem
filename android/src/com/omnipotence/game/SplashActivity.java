package com.omnipotence.game;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.badlogic.gdx.backends.android.AndroidApplication;

/**
 * Copyright 2015, Omnipotence, LLC, All rights reserved.
 * Created by Omnipotence, LLC on 9/20/16.
 *
 *
 */

public class SplashActivity extends AndroidApplication {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //This handler calls isUsersignedIn method after 3 seconds delay.
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                isUsersignedIn();
            }
        }, 3000);
    }

    /**
     * This method checks if there is already a user token and if there is token then the user is
     * taken to the mainActivity else the user is taken to the login screen.
     */
    public void isUsersignedIn() {
        Intent intent = new Intent(this, AndroidLauncher.class);
        startActivity(intent);
        finish();
    }
}