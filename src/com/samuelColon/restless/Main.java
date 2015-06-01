package com.samuelColon.restless;

import com.samuelColon.restless.Util.Window;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;

public class Main extends JFrame {

    /**
     * Current screen dimensions
     */

    private final int GAME_WIDTH = 600;
    private final int GAME_HEIGHT = 500;
    private final int MENU_WIDTH;
    private final int MENU_HEIGHT;

    /**
     * so I don't have to click new game every single damn time
     */
    private final boolean DEBUG_MODE = false;

    public Main () {
        Window.init();
        MENU_WIDTH = (int) (Window.getScreenWidth() / 2);
        MENU_HEIGHT = (int) (Window.getScreenHeight() / 2);
    }

    private void createAndShowGui () {
        setPreferredSize(new Dimension(MENU_WIDTH, MENU_HEIGHT));
        setTitle("Restless");
        setLayout(new GridLayout(1, 1));
        setLocation(0 - MENU_WIDTH / 2, 0 - MENU_HEIGHT / 2);
        setIgnoreRepaint(true);

        if (DEBUG_MODE) {
            new Game(GAME_WIDTH, GAME_HEIGHT);
        } else {
            MainMenu menu = new MainMenu(this); // send menu dimensions once window util is complete
            getContentPane().add(menu.getMenu());
        }

        pack();
        setVisible(true);
        //        setResizable( false );
    }

    /**
     * clean up resources
     */
    private static void onWindowClosing () {
        System.exit(0);
    }

    /**
     * Init Game window
     */
    public void swapFrames () {
        new Game(GAME_WIDTH, GAME_HEIGHT);
        getContentPane().removeAll();
        setFocusable(false);
        this.setVisible(false);
    }

    public static void main (String[] args) {
        final Main app = new Main();

        app.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing (WindowEvent e) {
                onWindowClosing();
            }
        });

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run () {
                app.createAndShowGui();
            }
        });
    }
}