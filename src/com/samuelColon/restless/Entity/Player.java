package com.samuelColon.restless.Entity;

import com.samuelColon.restless.Game;
import java.awt.*;
import java.util.ArrayList;

public class Player implements LivingEntity{

    /**
     * UI components
     */
    private Game game;

    private volatile int x;
    private volatile int y;
    private final int BLOCK_SIZE = 10;
    private final int GAME_WIDTH;
    private final int GAME_HEIGHT;

    /**
     * Health bar components
     */
    int hx = 15;
    int hy = 15;
    private int mMaxHealth = 200;
    private int currentHealth = 150;

    /**
     * shooting
     */
    private long timeSinceLastShot = 0;
    private transient int shotInterval = 200;
    private ArrayList< Bullet > shotsFired = new ArrayList<>();
    private int bulletStrength = 50;

    /**
     * player stats
     */
    private int exp = 0;

    /**
     * The current direction the player is facing
     */
    public volatile int directionFacing = 0;
    public final int FACING_LEFT = 1;
    public final int FACING_UP = 2;
    public final int FACING_RIGHT = 3;
    public final int FACING_DOWN = 4;

    private final int jukeLength = 16;

    /**
     * players current inventory
     */
    private Inventory inventory;

    public Player ( Game game, int x, int y, int gameWidth, int gameHeight ) {
        this.game = game;
        this.x = x;
        this.y = y;
        this.GAME_WIDTH = gameWidth;
        this.GAME_HEIGHT = gameHeight;

        /** init inventory system */
        inventory = new Inventory();
    }

    /**
     * called when space button is pressed, adds bullets to array list
     */
    public void shoot () {
        if ( timeSinceLastShot + shotInterval < System.currentTimeMillis() ) {
            game.SmGunshot.play();
            shotsFired.add( new Bullet( this, game.getEnemies(), x, y, directionFacing ) );
            timeSinceLastShot = System.currentTimeMillis();
        }
    }

    public void draw ( Graphics g ) {
        /** draw player */
        g.setColor( Color.BLACK );
        g.fillRect( x, y, BLOCK_SIZE, BLOCK_SIZE );

        /** draw health bar */
        g.fillRect( hx, hy, mMaxHealth / 4, 10 );
        g.setColor( Color.RED );
        g.fillRect( hx, hy, currentHealth / 4, 10 );

        /** draw bullets */
        ArrayList< Bullet > temp = new ArrayList<>( shotsFired );

        for ( Bullet bullets : temp ) {
            if ( bullets.withinBounds() && ! bullets.expired ) {
                bullets.draw( g );
                bullets.move();
            } else {
                shotsFired.remove( bullets );
            }
        }
    }

    public void gainExp ( int exp ) {
        /** first increment experience than check if the player has leveled */
        this.exp += exp;
        /**  TODO: check array list of where xp is located that level is now current level
         *  subtract current xp i.e 787 from level 3 xp 700, current xp is now 87
         * */
    }

    /**
     * inventory manager
     */
    public boolean addItem ( Item i ) {
        inventory.addItem( i );
        return true;
    }

    /** test case methods */
    //    public ArrayList<Bullets> getShots() { return shotsFired; }
    //    public void shootAll() {
    //        shotsFired.add(new Bullets(this, game, x, y, FACING_LEFT));
    //        shotsFired.add(new Bullets(this, game, x, y, FACING_UP));
    //        shotsFired.add(new Bullets(this, game, x, y, FACING_RIGHT));
    //        shotsFired.add(new Bullets(this, game, x, y, FACING_DOWN));
    //    }

    /**
     * end test methods
     */

    public int getHealth () {
        return currentHealth;
    }

    public void setHealth ( int health ) {
        currentHealth = health;
    }

    public int getX () {
        return x;
    }

    public int getY () {
        return y;
    }

    public void move ( int directionFacing ) {
        this.directionFacing = directionFacing;
    }

    private boolean didCollide () {
        return true;
    }

    /**
     * TODO: refactor so collision detection and movement methods are seperate
     * not check if can move x than move
     * need this to implement juke better
     */

    public void setX ( int x ) {
        if ( x + BLOCK_SIZE - 1 >= GAME_WIDTH || x <= 0 ) {
            // illegal move
        } else {
            this.x = x;
        }
    }

    public void setY ( int y ) {
        if ( y <= 0 || y + BLOCK_SIZE >= GAME_WIDTH ) {
            // illegal move
        } else {
            this.y = y;
        }
    }

    public int getGameWidth () {
        return GAME_WIDTH;
    }

    public int getGameHeight () {
        return GAME_HEIGHT;
    }

    public int getDirection () {
        return directionFacing;
    }

    /**
     * juke works as expected with the exception that it does not check for collision
     */
    public void juke () {
        switch ( directionFacing ) {
            case FACING_LEFT:
                setX( x -= jukeLength );
                break;
            case FACING_UP:
                setY( y -= jukeLength );
                break;
            case FACING_RIGHT:
                setX( x += jukeLength );
                break;
            case FACING_DOWN:
                setY( y += jukeLength );
                break;
        }
    }

    public void printInventory () {
        inventory.printItems();
    }

    public void doAction ( int a ) {
        inventory.doAction( a );
    }
}