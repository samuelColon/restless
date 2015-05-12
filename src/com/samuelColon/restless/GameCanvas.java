package com.samuelColon.restless;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class GameCanvas extends Canvas {

    private Color mBackGroundColor = Color.GREEN;

    public GameCanvas(int gameWidth, int gameHeight) {
        super();
        setSize( gameWidth, gameHeight );
        setBackground( mBackGroundColor );
        setIgnoreRepaint( true );
        requestFocus();
        setVisible( true );
    }

    public Canvas getCanvas() {
        return this;
    }

    public BufferStrategy getBS(int n) {
        initBufferStrategy(n);
        return getBufferStrategy();
    }

    private void initBufferStrategy(int n) {
        createBufferStrategy(n);
    }

    /**TODO: will implement after game world is updated */
    public void onScreenResize() {
    }
}