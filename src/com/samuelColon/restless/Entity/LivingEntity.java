package com.samuelColon.restless.Entity;

import java.awt.*;

public interface LivingEntity extends Entity {
    public void draw ( Graphics g );

    public int getHealth ();

    public void setHealth ( int health );

    public void setX ( double x );

    public void setY ( double y );

    public double getX ();

    public double getY ();

    boolean isAlive();
}