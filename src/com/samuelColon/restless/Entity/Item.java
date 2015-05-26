package com.samuelColon.restless.Entity;

import java.awt.*;

public class Item implements Entity {
    public String itemType;
    public double x;
    public double y;

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
        g.fillRect( (int)x, (int)y, 5, 5 );
    }

    @Override
    public void setX ( double x ) {
    }

    @Override
    public void setY ( double y ) {
    }

    public double getX () {
        return x;
    }

    public double getY () {
        return y;
    }
}