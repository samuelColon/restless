package com.samuelColon.restless.Util;

import java.awt.*;

public class Window {

    private static Dimension screen;

    public Window() {
    }

    public static void init () {
        screen = Toolkit.getDefaultToolkit().getScreenSize();
    }

    public static double getScreenWidth() {
        return screen.getWidth();
    }

    public static double getScreenHeight() {
        return screen.getHeight();
    }

    /**
     * This is for future use. When the screen is re-sized the game update window rebuild game world.
     * */
    public void onScreenResize() {
        screen = null;
        screen = Toolkit.getDefaultToolkit().getScreenSize();
    }
}