package com.samuelColon.restless.Entity;

import java.awt.*;

public class Item implements Entity {
    public String itemType;
    public int x;
    public int y;

    public Item ( String itemType, int x, int y ) {
        this.itemType = itemType;
        this.x = x;
        this.y = y;
    }

    public String getItemType () {
        return itemType;
    }

    @Override
    public void draw ( Graphics g ) {
        g.setColor( Color.blue );
        g.fillRect( 250, 250, 5, 5 );
    }

    @Override
    public void setX ( int x ) {
    }

    @Override
    public void setY ( int y ) {
    }

    public int getX () {
        return x;
    }

    public int getY () {
        return y;
    }
}