package com.samuelColon.restless;

import javax.swing.*;
import java.awt.*;

/**
 * this jpanel will show a visual of the current inventory
 * player will be able to equip weapons and armor
 * drop items, use potions look at maps etc.
 */
public class InventoryPanel extends JPanel {

    public InventoryPanel () {
        setSize( new Dimension( 300, 400 ) );
        setBackground( Color.blue );
        setVisible( false );
        requestFocus();
    }
}