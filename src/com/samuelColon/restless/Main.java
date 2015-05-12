package com.samuelColon.restless;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;

/**
 * Main class contains a frame that will initially display the main menu panel
 * upon user action the frame will be switched to the game panel after utilizing the appropriate response
 *
 * @param new game, @param load game
 */
public class Main extends JFrame {

    /**
     * Game screen dimensions
     */
    private final int GAME_WIDTH = 600;
    private final int GAME_HEIGHT = 500;

    private final boolean DEBUG_MODE = false;

    public Main () {
    }

    Canvas canvas;
    private void createAndShowGui () {
        setSize( new Dimension( GAME_WIDTH, GAME_HEIGHT ) );
        setTitle( "Shooter" );
        setLayout( new GridLayout( 1, 1 ) );
        setLocation( 0 - GAME_WIDTH / 2, 0 - GAME_HEIGHT / 2 );
        setIgnoreRepaint( true );
        setVisible( true );
        setResizable( false );

        canvas = new Canvas();
        //        canvas.setSize( GAME_WIDTH, GAME_HEIGHT);
        //        canvas.setBackground( Color.GREEN);
        //        canvas.setIgnoreRepaint(true);
        //        canvas.createBufferStrategy(2);
        //        canvas.setVisible( true );
        //        canvas.requestFocus();
        //        getContentPane().add(canvas);
//        new Game(GAME_WIDTH, GAME_HEIGHT, canvas);

        if ( DEBUG_MODE ) {
            getContentPane().add( new Game( GAME_WIDTH, GAME_HEIGHT, canvas ) );
        } else {
            MainMenu menu = new MainMenu(this);
            getContentPane().add(menu.getMenu());
        }
//        pack();
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
        getContentPane().removeAll();
        getContentPane().add( new Game( GAME_WIDTH, GAME_HEIGHT, canvas ) );
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