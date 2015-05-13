package com.samuelColon.restless;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**TODO: once xml util is updated show player stats on load game.
 * i.e Map # 1, 2k gold */
public class MainMenu extends JPanel {
    public MainMenu ( Main main ) {

        setPreferredSize( new Dimension( 500, 500 ) );
        setBackground( Color.cyan );
        setLayout( new GridLayout( 2, 1 ) );

        JLabel gameName  = new JLabel( "Restless" );
        JButton newGame  = new JButton( "New Game" );
        JButton loadGame = new JButton( "Load Game" );

        newGame.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed ( ActionEvent e ) {
                main.swapFrames();
            }
        } );

        add( gameName );
        add( newGame );
        add( loadGame );
        setVisible( true );
    }

    public JPanel getMenu() {
        return this;
    }
}