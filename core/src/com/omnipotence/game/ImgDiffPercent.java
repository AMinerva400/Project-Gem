package com.omnipotence.game;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * Ignore this class.
 */

public class ImgDiffPercent
{
    public ImgDiffPercent() {

    }

    public double compareImages(FileHandle handle1, FileHandle handle2) {
        if(!handle1.exists() && !handle2.exists()) {
            return -1;
        }
        double percentDiff = 0.0;
        BufferedImage img1 = null;
        BufferedImage img2 = null;
        try {
            img1 = ImageIO.read(handle1.file().toURI().toURL());
            img2 = ImageIO.read(handle2.file().toURI().toURL());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(img1 == null || img2 == null) {
            return -1;
        }
        int width1 = img1.getWidth(null);
        int width2 = img2.getWidth(null);
        int height1 = img1.getHeight(null);
        int height2 = img2.getHeight(null);
        if ((width1 != width2) || (height1 != height2)) {
            System.err.println("Error: Images dimensions mismatch");
            return -1;
        }
        long diff = 0;
        for (int y = 0; y < height1; y++) {
            for (int x = 0; x < width1; x++) {
                int rgb1 = img1.getRGB(x, y);
                int rgb2 = img2.getRGB(x, y);
                int r1 = (rgb1 >> 16) & 0xff;
                int g1 = (rgb1 >>  8) & 0xff;
                int b1 = (rgb1      ) & 0xff;
                int r2 = (rgb2 >> 16) & 0xff;
                int g2 = (rgb2 >>  8) & 0xff;
                int b2 = (rgb2      ) & 0xff;
                diff += Math.abs(r1 - r2);
                diff += Math.abs(g1 - g2);
                diff += Math.abs(b1 - b2);
            }
        }
        double n = width1 * height1 * 3;
        percentDiff = diff / n / 255.0;
        return percentDiff;
    }
    public static void main(String args[])
    {
        BufferedImage img1 = null;

        BufferedImage img2 = null;
        try {
            URL url1 = new URL("http://rosettacode.org/mw/images/3/3c/Lenna50.jpg");
            URL url2 = new URL("http://rosettacode.org/mw/images/b/b6/Lenna100.jpg");
            img1 = ImageIO.read(url1);
            img2 = ImageIO.read(url2);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int width1 = img1.getWidth(null);
        int width2 = img2.getWidth(null);
        int height1 = img1.getHeight(null);
        int height2 = img2.getHeight(null);
        if ((width1 != width2) || (height1 != height2)) {
            System.err.println("Error: Images dimensions mismatch");
            System.exit(1);
        }
        long diff = 0;
        for (int y = 0; y < height1; y++) {
            for (int x = 0; x < width1; x++) {
                int rgb1 = img1.getRGB(x, y);
                int rgb2 = img2.getRGB(x, y);
                int r1 = (rgb1 >> 16) & 0xff;
                int g1 = (rgb1 >>  8) & 0xff;
                int b1 = (rgb1      ) & 0xff;
                int r2 = (rgb2 >> 16) & 0xff;
                int g2 = (rgb2 >>  8) & 0xff;
                int b2 = (rgb2      ) & 0xff;
                diff += Math.abs(r1 - r2);
                diff += Math.abs(g1 - g2);
                diff += Math.abs(b1 - b2);
            }
        }
        double n = width1 * height1 * 3;
        double p = diff / n / 255.0;
        System.out.println("diff percent: " + (p * 100.0));
    }
}