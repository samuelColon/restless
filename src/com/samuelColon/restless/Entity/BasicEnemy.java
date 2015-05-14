package com.samuelColon.restless.Entity;

import java.awt.*;

/**
 * TODO: enemy movement algorithm will determine if the player is further from the x or y plane
 * the enemy will than move one tick in that direction.
 */

public class BasicEnemy implements LivingEntity {

    /**
     * enemy specs
     */

    private int maxHealth     = 200;
    private int currentHealth = maxHealth;
    private int hx;
    private int hy;

    private int x         = 250;
    private int y         = 250;
    public int dimensions = 20;
    private int exp       = 20; // experience player will gain by killing this particular enemy

    /**
     * used to show when the enemy is hit by a projectile
     */
    private volatile Color currentColor = Color.red;

    /**
     * misc
     */

    public BasicEnemy(int x, int y) {
        this.x = x;
        this.y =y;
        hx = x;
        hy = y;
    }

    public boolean isAlive() { return currentHealth > 0; }

    /**
     * draw enemy health bar
     */
    @Override
    public void draw(Graphics g) {
        g.setColor(currentColor);
        g.fillRect(x, y, dimensions, dimensions);

        /** draw enemy maxHealth bar */
        g.setColor(Color.black);
        g.fillRect(x - 4, (y - 15), maxHealth / 10, 5);

        g.setColor(Color.red);
        g.fillRect(x - 4, (y - 15), currentHealth / 10, 5);

        /** set the enemy color back to the original red */
        if (currentColor != Color.RED) currentColor = Color.RED;
    }

    public Item droppedItem () {
        return new Item("Potion", x, y);
    }

    @Override
    public int getHealth () {
        return currentHealth;
    }

    @Override
    public void setHealth ( int damage ) {
        currentHealth -= damage;
        currentColor = Color.white;
    }

    @Override
    public void setX ( int x ) {
        this.x = x;
    }

    @Override
    public void setY ( int y ) {
        this.y = y;
    }

    @Override
    public int getX () {
        return x;
    }

    @Override
    public int getY () {
        return y;
    }
}