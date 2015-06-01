package com.samuelColon.restless.Util.graphics;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Place holder class
 */
public class SpriteFactory {

    private BufferedImage sprite;

    public SpriteFactory () {
    }

    public void initSprite (File file) {
        sprite = null;
        try {
            sprite = ImageIO.read(file);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public BufferedImage getSprite () {
        return sprite;
    }
}