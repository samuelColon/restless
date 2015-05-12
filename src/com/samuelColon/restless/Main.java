package com.samuelColon.restless;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;

public class Main extends JFrame {

    /**
     * Current screen dimensions

     */
    private final int GAME_WIDTH  = 600;
    private final int GAME_HEIGHT = 500;

    /** TODO: create window util that will set values based on screen dimensions */
//    private final int MENU_WIDTH;
//    private final int MENU_HEIGHT;
//    private final int GAME_WIDTH;
//    private final int GAME_HEIGHT;

    /** so I don't have to click new game every single damn time */
    private final boolean DEBUG_MODE = false;

    public Main () {
        /** init window util
         *  this GAME_WIDTH = screen.getWidth();
         *
         * */
    }

    private void createAndShowGui () {
        setPreferredSize( new Dimension( GAME_WIDTH, GAME_HEIGHT ) );
        setTitle( "Restless" );
        setLayout( new GridLayout( 1, 1 ) );
        setLocation( 0 - GAME_WIDTH / 2, 0 - GAME_HEIGHT / 2 );
        setIgnoreRepaint( true );

        if ( DEBUG_MODE ) {
//            getContentPane().add( new Game( GAME_WIDTH, GAME_HEIGHT) );
            new Game(GAME_WIDTH, GAME_HEIGHT);
        } else {
            MainMenu menu = new MainMenu(this); // send menu dimensions once window util is complete
            getContentPane().add(menu.getMenu());
        }
        pack();
        setVisible( true );
//        setResizable( false );
    }

    /**
     * clean up resources
     */
    private static void onWindowClosing () {
        System.exit( 0 );
    }

    /**
     * change out main menu for Game panel
     */
    public void swapFrames () {
        /**test */
//        getContentPane().removeAll();
//        getContentPane().add( new Game( GAME_WIDTH, GAME_HEIGHT ) );
        new Game(GAME_WIDTH, GAME_HEIGHT);
        this.setVisible( false );
    }

    public static void main ( String[] args ) {
        final Main app = new Main();

        app.addWindowListener( new WindowAdapter() {
            @Override
            public void windowClosing ( WindowEvent e ) {
                onWindowClosing();
            }
        } );

        SwingUtilities.invokeLater( new Runnable() {
            @Override
            public void run () {
                app.createAndShowGui();
            }
        } );
    }
}