package com.samuelColon.restless.Entity;

import java.awt.Graphics;

public interface Entity {
    public void draw ( Graphics g );

    public void setX ( double x );

    public void setY ( double y );

    public double getX ();

    public double getY ();
}
