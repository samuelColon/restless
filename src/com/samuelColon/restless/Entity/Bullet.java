package com.samuelColon.restless.Entity;

import java.awt.*;

/**
 * class receives the direction the player is currently facing
 * dimensions of bullet, origin and bullet range should be affected accordingly
 */
public class Bullet implements Entity {

    /**
     * Bullet specs
     */
    private final int BULLET_RANGE = 160;
    private final int WIDTH;
    private final int HEIGHT;
    private final double BULLET_SPEED = .5;
    public int bulletStrength         = 32;
    public volatile boolean expired   = false;

    /**
     * bullet positioning
     */
    private double x;
    private double y;
    private double origin;
    private double moveDistance;

    private final int DIRECTION_FACING;
    private final int FACING_LEFT  = 1;
    private final int FACING_UP    = 2;
    private final int FACING_RIGHT = 3;
    private final int FACING_DOWN  = 4;

    /**
     * misc
     */
    private Color mBulletColor = Color.BLACK;

    public Bullet(double x, double y, int directionFacing ) {
        this.x                = x;
        this.y                = y;
        this.DIRECTION_FACING = directionFacing;

        if (directionFacing == FACING_LEFT || directionFacing == FACING_RIGHT) {
            origin = x;
            WIDTH  = 5;
            HEIGHT = 1;
        } else {
            origin = y;
            WIDTH  = 1;
            HEIGHT = 5;
        }
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(mBulletColor);
        g.fillRect((int)x, (int)y, WIDTH, HEIGHT);
    }

    public void move(double delta) {
        moveDistance = BULLET_SPEED * delta;

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

    /**
     * up y- bullet speed
     * right x + bullet speed
     * down y + bullet speed
     * left x - bullet speed
     */
    public void moveUp() {
        if (origin - y < BULLET_RANGE) {
            y -= moveDistance;
        } else {
            expired = true;
        }
    }

    public void moveRight() {
        if (x - origin < BULLET_RANGE) {
            x += moveDistance;
        } else {
            expired = true;
        }
    }

    public void moveDown() {
        if (y < BULLET_RANGE + origin) {
            y += moveDistance;
        } else {
            expired = true;
        }
    }

    public void moveLeft() {
        if (x > origin - BULLET_RANGE) {
            x -= moveDistance;
        } else {
            expired = true;
        }
    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }

    @Override
    public void setY(double y) {
    }

    @Override
    public void setX(double x) {
    }
}