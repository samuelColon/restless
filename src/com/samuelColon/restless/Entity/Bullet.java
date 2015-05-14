package com.samuelColon.restless.Entity;

import java.awt.*;
import java.util.ArrayList;

/**
 * TODO: Remove Game engine and player reference. including game width and height.
 *      bullet class should not need to check for collision detection
 */
public class Bullet implements Entity {

    /**
     * Bullet specs
     */
    private final int BULLET_RANGE  = 196;
    private final int WIDTH;
    private final int HEIGHT;
    private final int mBULLET_SPEED = 3;
    private int bulletStrength      = 32;
    public volatile boolean expired = false;

    /**
     * bullet positioning
     */
    private int x;
    private int y;
    private int origin;
    private int delta;

    /**
     * misc
     */
    private Color mBulletColor = Color.BLACK;


    private final int DIRECTION_FACING;
    private final int FACING_LEFT  = 1;
    private final int FACING_UP    = 2;
    private final int FACING_RIGHT = 3;
    private final int FACING_DOWN  = 4;

    private ArrayList<BasicEnemy> enemies;

    /**
     * class receives the direction the player is currently facing
     * dimensions of bullet, origin and bullet range should be affected accordingly
     */
    public Bullet(ArrayList<BasicEnemy> enemies, int x, int y, int directionFacing ) {
        this.x                = x;
        this.y                = y;
        this.DIRECTION_FACING = directionFacing;
        this.enemies          = enemies;

        if (directionFacing == FACING_LEFT || directionFacing == FACING_RIGHT) {
            origin = x;
            WIDTH  = 6;
            HEIGHT = 2;
        } else {
            origin = y;
            WIDTH  = 2;
            HEIGHT = 6;
        }
    }

    @Override
    public void draw ( Graphics g ) {
        g.setColor(mBulletColor);
        g.fillRect(x, y, WIDTH, HEIGHT);
    }

    /**
     * TODO: modify so Game engine checks for collision
     * move the bullet if it has not yet expired, then check if it collided with any enemies
     * damage with @param bulletStrength
     */
    public void move () {
        switch (DIRECTION_FACING) {
            case FACING_LEFT:
                moveLeft();
                break;
            case FACING_UP:
                moveUp();
                break;
            case FACING_RIGHT:
                moveRight();
                break;
            case FACING_DOWN:
                moveDown();
                break;
            default:
                moveUp();
        }
    }

    /** TODO: modify so Game engine moves bullet
     * up y- bullet speed
     * right x + bullet speed
     * down y + bullet speed
     * left x - bullet speed
     */
    public void moveUp () {
        if (origin - y < BULLET_RANGE) {
            y -= mBULLET_SPEED;
            checkCollision();
        } else {
            expired = true;
        }
    }

    public void moveRight () {
        if (x - origin < BULLET_RANGE) {
            x += mBULLET_SPEED;
            checkCollision();
        } else {
            expired = true;
        }
    }

    public void moveDown() {
        if (y < BULLET_RANGE + origin) {
            y += mBULLET_SPEED;
            checkCollision();
        } else {
            expired = true;
        }
    }

    public void moveLeft() {
        if (x > origin - BULLET_RANGE) {
            x -= mBULLET_SPEED;
            checkCollision();
        } else {
            expired = true;
        }
    }

    public void checkCollision () {
        ArrayList<BasicEnemy> temp = new ArrayList<>(enemies);
        for (BasicEnemy e : temp) {
            int x = e.getX();
            int y = e.getY();
            int d = e.dimensions;

            if ( (this.x >= x && this.x <= x + d ) && ( this.y <= y + d && this.y >= y) ) {
                e.setHealth(bulletStrength);
                expired = true;
            }
        }
    }

    /** TODO: modify so Game engine chcks if within bounds */
    public boolean withinBounds () {
//        return ( ( x < GAME_WIDTH && x > 0 ) && ( y < GAME_HEIGHT && y > 0 ) );
        return true;
    }

    @Override
    public int getX () {
        return x;
    }

    @Override
    public int getY () {
        return y;
    }

    @Override
    public void setY ( int y ) {
    }

    @Override
    public void setX ( int x ) {
    }
}