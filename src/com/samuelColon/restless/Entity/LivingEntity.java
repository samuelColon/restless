package com.samuelColon.restless.Entity;

import java.awt.*;

public interface LivingEntity extends Entity {
    public void draw ( Graphics g );

    public int getHealth ();

    public void setHealth ( int health );

    public void setX ( int x );

    public void setY ( int y );

    public int getX ();

    public int getY ();
}