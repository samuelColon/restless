package com.samuelColon.restless;

import com.samuelColon.restless.Entity.BasicEnemy;
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
import java.awt.image.BufferedImage;
import java.io.File;
import javax.swing.*;
import java.util.ArrayList;
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

    private final Color backGroundColor = Color.GREEN;

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

    private final double PLAYERS_MOVEMENT_SPEED = .085;

    public final int FACING_LEFT  = 1;
    public final int FACING_UP    = 2;
    public final int FACING_RIGHT = 3;
    public final int FACING_DOWN  = 4;

    private ArrayList<BasicEnemy> enemies;
    
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
        setCursor(getToolkit().createCustomCursor(
                new BufferedImage(3, 3, BufferedImage.TYPE_INT_ARGB), new Point(0, 0), "null") );

        /** Note: this will replaced when Xml utility is complete */
        /** init player */
        player = new Player( playerX, playerY );

        /** init enemies */
        enemies = new ArrayList<>();
        enemies.add(new BasicEnemy(250, 250));

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

    @Override
    public void run() {
        gameRunning = true;
        gamePaused  = false;
        frameRate   = new FrameRate();
        frameRate.initialize();

        long curTime  = System.nanoTime();
        long lastTime = curTime;
        double nsPerFrame;

//        SmBackground.play();

        while( gameRunning ) {
            if( !gamePaused ) {
                curTime    = System.nanoTime();
                nsPerFrame = curTime - lastTime;
                gameLoop( nsPerFrame / 1.0E6 );
                lastTime   = curTime;
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
            if(player.shoot(enemies)) {
                SmGunshot.play();
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

        ArrayList<Item> temp = new ArrayList<>(items);

        /** did you snatch an item? */
        /** TODO: replace constants with item dimensions */
        for (Item i : temp) {
            if ((playerX >= i.getX() && playerX <= i.getX() + 5) &&
                    (playerY <= i.getY() + 5 && playerY >= i.getY()) )
            {
                player.addItem(i);
                items.remove( i );
            }
        }
    }

    /** player movement is now based off how much time has passed between frames
     *   however for some reason moving up and to the left is faster than moving down or to the right.
     *     maybe being rounded up and down respectively. Please investigate.
     *
     *            - ~
     *           (o.0)
     *              `p   <- * Sherlocks pipe *
     *
     *  May 14th, 2015
     */

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

    /**
     * TODO: write sprite manager class and give these guys some damn textures....damnit
     */
    private ArrayList<Item> items = new ArrayList<>();
    private void render(Graphics g) {
        g.setColor(backGroundColor);
        g.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT - 1);

        frameRate.calculate();
        g.setColor(Color.BLACK);
        g.drawString(frameRate.getFrameRate(), 540, 30);

        player.draw(g);

        ArrayList<BasicEnemy> temp1 = new ArrayList<>(enemies);
        for( BasicEnemy e: temp1 ) {
            if(e.isAlive()) {
                e.draw(g);
            } else {
                items.add(e.droppedItem());
                enemies.remove(e);
            }
        }

        ArrayList<Item> temp2 = new ArrayList<>(items);
        for(Item invn: temp2) {
            invn.draw(g);
        }
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
        items.add( item );
    }

    private void gameOver() {
        gameRunning = false;
    }

    protected void onWindowClosing(){
        /** clean up resources */
        gameCanvas.destroy();
        gameRunning = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }
}