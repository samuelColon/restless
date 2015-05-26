package com.samuelColon.restless;

import com.samuelColon.restless.Entity.BasicEnemy;
import com.samuelColon.restless.Entity.Bullet;
import com.samuelColon.restless.Entity.Item;
import com.samuelColon.restless.Entity.Player;
import com.samuelColon.restless.Util.FrameRate;
import com.samuelColon.restless.Util.KeyboardHandler;
import com.samuelColon.restless.Util.SoundManager;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.io.File;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Game extends JFrame implements Runnable {
    /**
     * Dimensions of the wrapper
     */
    private final int GAME_WIDTH;
    private final int GAME_HEIGHT;

    /**
     * The drawing component
     */
    private GameCanvas gameCanvas;
    private BufferStrategy bs;

    private final Color backGroundColor = Color.LIGHT_GRAY;
    private FrameRate frameRate;

    private Thread gameThread;

    private KeyboardHandler keyHandler;

    /**
     * sound files
     */
    private final String FILE_PATH             = "res/sounds";
    private final String BACKGROUND_MUSIC_FILE = "mainBackGroundMusic.wav";
    private final String SHOOTING_SOUND_FILE   = "gunShot.wav";

    private Player player;

    /** player coordinates */
    private double playerX = 265;
    private double playerY = 400;

    private final double PLAYERS_MOVEMENT_SPEED = .1; // for debug .1 otherwise .05

    public final int FACING_LEFT  = 1;
    public final int FACING_UP    = 2;
    public final int FACING_RIGHT = 3;
    public final int FACING_DOWN  = 4;

    private ArrayList<BasicEnemy> currentEnemies;
    private ArrayList<Bullet> currentBullets;

    public Game(int gameWidth, int gameHeight) {
        GAME_WIDTH  = gameWidth;
        GAME_HEIGHT = gameHeight;

        /** configure JFrame  */
        setPreferredSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));
        setLayout(new GridLayout(1, 1));
        setVisible(true);
        setFocusable(true);
        setResizable(false);

        /** Handle closing of the Game window */
        addWindowListener( new WindowAdapter() {
            @Override
            public void windowClosing (WindowEvent e) {
                onWindowClosing();
            }
        });

        /** init canvas */
        gameCanvas = new GameCanvas(GAME_WIDTH, GAME_HEIGHT);
        add(gameCanvas.getCanvas());
        bs = gameCanvas.getBS(2);
        pack();

        /** init key bindings */
        keyHandler = new KeyboardHandler();
        addKeyListener(keyHandler);

        /** Hide that hideous mouse when we're playing! */
//        setCursor(getToolkit().createCustomCursor(
//                new BufferedImage(3, 3, BufferedImage.TYPE_INT_ARGB), new Point(0, 0), "null") );

        /** init player */
        player = new Player(playerX, playerY);

        currentBullets = new ArrayList<>();

        /** init currentEnemies */
        currentEnemies = new ArrayList<>();
        currentEnemies.add( new BasicEnemy(250, 250));

        initSounds();

        /** Run the game */
        gameThread = new Thread(this);
        gameThread.start();
    }

    private SoundManager SmBackground;
    private SoundManager  SmGunshot;

    private void initSounds() {
        /** init background music */
        SmBackground = new SoundManager( new File(FILE_PATH, BACKGROUND_MUSIC_FILE), true); // true = loop sound
        /** init shooting sound */
        SmGunshot    = new SoundManager( new File(FILE_PATH, SHOOTING_SOUND_FILE), false);
    }

    private volatile boolean gameRunning;
    private volatile boolean gamePaused;
    private Random rand;

    @Override
    public void run() {
        gameRunning = true;
        gamePaused  = false;
        frameRate   = new FrameRate();
        frameRate.initialize();

        long curTime  = System.nanoTime();
        long lastTime = curTime;
        double nsPerFrame;

        rand = new Random();

//        SmBackground.play();

        while( gameRunning ) {
            if( !gamePaused ) {
                if (hasFocus()) {
                    curTime = System.nanoTime();
                    nsPerFrame = curTime - lastTime;
                    gameLoop( nsPerFrame / 1.0E6 );
                    lastTime = curTime;
                } else {
                    requestFocus();
                }
            }
        }
    }

    private void gameLoop(double delta) {
        checkForUserInput();
        update(delta);
        renderFrame();
        sleep(delta);
    }

    private boolean playerIsMoving;
    private void checkForUserInput() {
        playerIsMoving = false;
        keyHandler.poll();

        if(keyHandler.keyDownOnce(KeyEvent.VK_SPACE)) {
            if(player.shoot()) {
                SmGunshot.play();
                currentBullets.add(new Bullet(playerX, playerY, player.getDirection()));
            }
        }
        if(keyHandler.keyDown(KeyEvent.VK_LEFT)) {
            playerIsMoving = true;
            player.setDirection(FACING_LEFT);
        }
        if(keyHandler.keyDown(KeyEvent.VK_UP)) {
            playerIsMoving = true;
            player.setDirection(FACING_UP);
        }
        if(keyHandler.keyDown(KeyEvent.VK_RIGHT)) {
            playerIsMoving = true;
            player.setDirection(FACING_RIGHT);
        }
        if(keyHandler.keyDown(KeyEvent.VK_DOWN)) {
            playerIsMoving = true;
            player.setDirection(FACING_DOWN);
        }
    }

    private void update(double delta) {
        if (!player.isAlive()) gameOver();
        if (playerIsMoving) {
            movePlayer(delta);
            playerX = player.getX();
            playerY = player.getY();
        }

        ArrayList<Bullet> bullets = new ArrayList<>(currentBullets);
        for (Bullet b: bullets) {
            if (b.expired)
                currentBullets.remove(b);

            b.move(delta);

            if ( bulletCollision( b ))
                b.expired = true;
        }

        ArrayList<BasicEnemy> enemies = new ArrayList<>( currentEnemies );
        if (enemies.size() == 0) {
            currentEnemies.add(new BasicEnemy(getRandomX(), getRandomY()));
        } else {
            for ( BasicEnemy e : enemies ) {
                if ( ! e.isAlive() ) {
                    if ( e.hasItem() ) {
                        itemsOnMap.add( e.getItem() );
                        currentEnemies.remove( e );
                    }
                }
            }
        }

        ArrayList<Item> items = new ArrayList<>( itemsOnMap );
        for (Item i : items) {
            if ((playerX >= i.getX() && playerX <= i.getX() + 5) &&
                    (playerY <= i.getY() + 5 && playerY >= i.getY()) )
            {
                player.addItem(i);
                itemsOnMap.remove( i );
            }
        }
    }

    private void movePlayer(double delta) {
        double distance = delta * PLAYERS_MOVEMENT_SPEED;

        switch(player.getDirection()) {
            case FACING_LEFT:
                if (playerWithinBounds(playerX -= distance, playerY)) player.setX(playerX -= distance);
                break;
            case FACING_UP:
                if (playerWithinBounds(playerX, playerY -= distance)) player.setY(playerY -= distance);
                break;
            case FACING_RIGHT:
                if (playerWithinBounds(playerX += distance, playerY)) player.setX(playerX += distance);
                break;
            case FACING_DOWN:
                if (playerWithinBounds(playerX, playerY += distance)) player.setY(playerY += distance);
                break;
        }
    }

    private boolean playerWithinBounds(double x, double y) {
        return  (x > 0 && y > 0)
                  &&
                (x < GAME_WIDTH && y < GAME_HEIGHT);
    }

    private void renderFrame() {
        do {
            do {
                Graphics g = null;

                try {
                    g = bs.getDrawGraphics();
                    render(g);
                } finally {
                    if (g != null) {
                        g.dispose();
                    }
                }
            } while(bs.contentsRestored());
            bs.show();
        } while(bs.contentsLost());
    }

    /** drawing is done based on a copy of each array
     *  to avoid a Concurrent Modification Exception */
    private ArrayList<Item> itemsOnMap = new ArrayList<>();
    private void render(Graphics g) {
        g.setColor(backGroundColor);
        g.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT - 1);

        frameRate.calculate();
        g.setColor(Color.BLACK);
        g.drawString(frameRate.getFrameRate(), 540, 30);

        player.draw(g);

        ArrayList<BasicEnemy> enemies = new ArrayList<>( currentEnemies );
        for( BasicEnemy e: enemies ) {
            e.draw(g);
        }

        ArrayList<Bullet> bullets = new ArrayList<>(currentBullets);
        for(Bullet b: bullets) {
            b.draw(g);
        }

        ArrayList<Item> items = new ArrayList<>(itemsOnMap);
        for(Item i: items) {
            i.draw(g);
        }
    }

    private boolean bulletCollision ( Bullet b ) {
        double bx = b.getX();
        double by = b.getY();
        ArrayList<BasicEnemy> enemies = new ArrayList<>(currentEnemies);

        for(BasicEnemy e: enemies) {
            double ex = e.getX();
            double ey = e.getY();
            double d = e.dimensions;

            if ( (bx >= ex && bx <= ex + d ) &&
                    ( by <= ey + d && by >= ey) ) {
                e.setHealth(b.bulletStrength);
                return true;
            }
        }

        return false;
    }

    /**
     * 60 fps in milliseconds
     */
    private double targetFrameRate = 1000/60;
    /**
     * if rendering is ahead of schedule sleep thread, else continue
     */
    private void sleep(double delta) {
        if (delta < targetFrameRate ) {
            try {
                TimeUnit.MILLISECONDS.sleep( (long) (targetFrameRate - delta) );
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

   /**
    * for when the inventory panel is up and running
    * */
    public void openInventoryScreen() {
    }

    public void closeInventoryScreen() {
    }

    public void addItem(Item item) {
        itemsOnMap.add(item);
    }

    private int getRandomX() {
        return rand.nextInt(GAME_WIDTH);
    }

    private int getRandomY() {
        return rand.nextInt(GAME_HEIGHT);
    }

    private void gameOver() {
        gameRunning = false;
    }

    public void onWindowClosing(){
        /** clean up resources */
        gameCanvas.destroy();
        gameOver();
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }
}