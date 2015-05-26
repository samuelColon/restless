package com.samuelColon.restless.Entity;

import java.awt.Graphics;
import java.awt.Color;
import java.util.ArrayList;

public class Player implements LivingEntity{

    /** Players location */
    private volatile double x;
    private volatile double y;

    /** temp dimension of player char */
    private final int BLOCK_SIZE = 10;

    /**
     * player stats
     */
    private int mMaxHealth     = 200;
    private int mCurrentHealth = 150;
    private int mArmor         = 2;
    private int mExp           = 0;

    /**
     * Health bar components
     */
    // positioning
    private int hx = 15;
    private int hy = 15;
    // dimensions
    private int hbWidth = 200;
    private int hbHeight = 10;


    /**
     * shooting
     */
    private long timeSinceLastShot       = 0;
    private ArrayList<Bullet> shotsFired = new ArrayList<>();

    /** TODO: once xml utility is complete. Create a way to switch a users weapon */
/*  shot interval bullet strength, and bullet speed and range will all be upgradable */
    private enum currentWeapon{}
    private int shotInterval   = 300;
    private int bulletStrength = 50;

    /**
     * The current direction the player is facing
     * TODO: convert this to enum
     */
    private volatile int directionFacing = 0;
    private final int FACING_LEFT        = 1;
    private final int FACING_UP          = 2;
    private final int FACING_RIGHT       = 3;
    private final int FACING_DOWN        = 4;

    /**
     * players current inventory
     */
    private Inventory inventory;

    public Player(double x, double y) {
        this.x = x;
        this.y = y;

        inventory = new Inventory();
    }

    public boolean shoot(ArrayList<BasicEnemy> enemies) {
        if (timeSinceLastShot + shotInterval < System.currentTimeMillis()) {
//            shotsFired.add(new Bullet(enemies, x, y, directionFacing));
            timeSinceLastShot = System.currentTimeMillis();
            return true;
        }
        return false;
    }

    public void draw(Graphics g) {
        /** draw player */
        g.setColor(Color.BLACK);
        g.fillRect((int)x, (int)y, BLOCK_SIZE, BLOCK_SIZE);

        /** draw health bar */
        g.fillRect(hx, hy, hbWidth , hbHeight);
        g.setColor(Color.RED);
//        g.fillRect(hx, hy, (mCurrentHealth / mMaxHealth) * 100, hbHeight);
        g.fillRect(hx, hy, mCurrentHealth , hbHeight);


        /** draw bullets */
//        ArrayList<Bullet> temp = new ArrayList<>(shotsFired);

//        for ( Bullet b : temp ) {
//            double bX = b.getX();
//            double bY = b.getY();
//            if ((bX > 0 && bY > 0 && bX < 600 && bY < 500)
//                    && !b.expired) {
//                b.draw(g);
//                b.move();
//            } else {
//                shotsFired.remove(b);
//            }
//        }
    }

    public void gainExp(int exp) {
        this.mExp += exp;
    }

    /**
     * inventory manager
     */
    public boolean addItem(Item i) {
        inventory.addItem(i);
        mCurrentHealth += 25;
        return true;
    }

    public int getHealth() {
        return mCurrentHealth;
    }

    public void setHealth(int health) {
        mCurrentHealth = health;
    }

    public boolean isAlive() { return mCurrentHealth > 0; }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setDirection(int directionFacing) {
        this.directionFacing = directionFacing;
    }

    public int getDirection() {
        return directionFacing;
    }
}