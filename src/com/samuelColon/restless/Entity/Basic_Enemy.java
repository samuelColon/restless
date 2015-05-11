package com.samuelColon.restless.Entity;

import com.samuelColon.restless.Game;
import java.awt.*;

/**
 * TODO: enemy movement algorithm will determine if the player is further from the x or y plane
 * the enemy will than move one tick in that direction.
 */

public class Basic_Enemy implements LivingEntity {

    /**
     * enemy specs
     */
    private int maxHealth = 200;
    private int currentHealth = maxHealth;
    private int hx;
    private int hy;

    private int x = 250;
    private int y = 250;
    public int dimensions = 20;
    private int exp = 20;

    /**
     * used to show when the enemy is hit by a projectile
     */
    private volatile Color currentColor = Color.red;

    /**
     * misc
     */
    private int element;
    private Game game;

    public Basic_Enemy ( Game game, int element ) {
        this.game = game;
        this.element = element;
        hx = x;
        hy = y;
    }

    public void isHit ( int damage ) {
        currentHealth -= damage;
        if ( currentHealth <= 0 )
            dieGracefully();

        currentColor = Color.white;
    }

    public void dieGracefully () {
        game.enemyKilled( element, exp );
        dropItem();
    }


    /**
     * add draw enemy healthbar
     */
    @Override
    public void draw ( Graphics g ) {
        g.setColor( currentColor );
        g.fillRect( x, y, dimensions, dimensions );

        /** draw enemy maxHealth bar */
        g.setColor( Color.black );
        g.fillRect( x - 4, ( y - 15 ), maxHealth / 10, 5 );

        g.setColor( Color.red );
        g.fillRect( x - 4, ( y - 15 ), currentHealth / 10, 5 );

        /** set the enemy color back to the original red */
        if ( currentColor != Color.RED )
            currentColor = Color.RED;
    }

    private void dropItem () {
        game.addItem( new Item( "Potion", x, y ) );
    }

    @Override
    public int getHealth () {
        return currentHealth;
    }

    @Override
    public void setHealth ( int health ) {
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