package com.samuelColon.restless.Entity;

import java.awt.*;

/**
 * TODO: enemy movement algorithm will determine if the player is further from the x or y plane
 * the enemy will than move one tick in that direction.
 */

public class BasicEnemy implements LivingEntity {
    /** Enemy stats*/
    private double maxHealth     = 200;
    private double currentHealth = maxHealth;
    private double x         = 250;
    private double y         = 250;
    public int dimensions = 20;
    private int exp       = 20; // experience player will gain by killing this particular enemy

    /** healthbar components */
    private int hx;
    private int hy;
    private int hbWidth  = 30;
    private int hbHeight = 4;
    private double percentage;

    /**
     * used to show when the enemy is hit by a projectile
     */
    private volatile Color currentColor = Color.red;

    public BasicEnemy(int x, int y) {
        this.x = x;
        this.y = y;
        hx     = x;
        hy     = y - 10;
        percentage = currentHealth / maxHealth;
    }

    public boolean isAlive() { return currentHealth > 0; }

    @Override
    public void draw(Graphics g) {
        g.setColor(currentColor);
        g.fillRect((int)x, (int)y, dimensions, dimensions);

        /** draw health bar */
        g.setColor(Color.black);
        g.fillRect(hx, hy, hbWidth , hbHeight);
        g.setColor(Color.RED);
        g.fillRect(hx, hy, (int)(hbWidth * percentage), hbHeight);

        /** set the enemy color back to the original red */
        if (currentColor != Color.RED) currentColor = Color.RED;
    }

    public Item getItem() {
        return new Item("Potion", (int)x,(int) y);
    }

    public boolean hasItem() { return true; }

    public int getHealth() {
        return (int) currentHealth;
    }

    public void setHealth(int damage) {
        currentHealth -= damage;
        percentage = currentHealth / maxHealth;
        currentColor = Color.white;
    }

    @Override
    public void setX(double x) {
        this.x = x;
    }

    @Override
    public void setY (double y) {
        this.y = y;
    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }
}