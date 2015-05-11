package com.samuelColon.restless;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;
/**this is a test*/
/**
 * Main class contains a frame that will initially display the main menu panel
 * upon user action the frame will be switched to the game panel after utilizing the appropriate response
 *
 * @param new game, @param load game
 */
public class Main extends JFrame {
    /**
     * the main menu panel
     */
    private JPanel jpSplash;

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
            initSplash();
            getContentPane().add( jpSplash );
        }
//                pack();
    }

    private void initSplash () {
        jpSplash = new JPanel();
        jpSplash.setSize( new Dimension( GAME_WIDTH, GAME_HEIGHT ) );
        jpSplash.setBackground( Color.cyan );
        jpSplash.setLayout( new GridLayout( 2, 1 ) );

        JLabel gameName = new JLabel( "Restless" );
        JButton newGame = new JButton( "New Game" );
        JButton loadGame = new JButton( "Load Game" );

        newGame.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed ( ActionEvent e ) {
                swapFrames();
            }
        } );

        jpSplash.add( gameName );
        jpSplash.add( newGame );
        jpSplash.add( loadGame );
        jpSplash.setVisible( true );
    }

    /**
     * clean up resources
     */
    private static void onWindowClosing () {

        System.exit( 0 );
    }

    /**
     * switches jpanel's from splash to game panel
     */
    void swapFrames () {
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