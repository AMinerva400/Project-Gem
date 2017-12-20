package com.omnipotence.game;

/**
 * Copyright 2015, Omnipotence, LLC, All rights reserved.
 * Created by Omnipotence, LLC.
 * This class is used to create Story pages.
 */

public class page {

    private String image;
    private String text;

    /**
     * This is the Constructor.
     * @param image: The name of the image for the story page.
     * @param text: The story dialog of the story page.
     */
    public page(String image, String text) {
        this.image = image;
        this.text = text;
    }

    /**
     * This function returns the image name.
     */
    public String getImage() {
        return image;
    }

    /**
     * This function returns the text.
     */
    public String getText() {
        return text;
    }
}
