package com.samuelColon.restless.Entity;

import java.awt.*;

public interface Entity {
    public void draw ( Graphics g );

    public void setX ( int x );

    public void setY ( int y );

    public int getX ();

    public int getY ();
}
