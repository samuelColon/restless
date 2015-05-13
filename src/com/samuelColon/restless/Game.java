package com.samuelColon.restless;

import com.samuelColon.restless.Entity.BasicEnemy;
import com.samuelColon.restless.Entity.Item;
import com.samuelColon.restless.Entity.Player;
import com.samuelColon.restless.Util.FrameRate;
import com.samuelColon.restless.Util.KeyboardHandler;
import com.samuelColon.restless.Util.SoundManager;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Game extends JFrame implements Runnable {
    /** player coordinates */
    int x = 245;
    int y = 400;

    /**
     * players current movement speed, will change once time and space gets updated
     */
    private final int MOVEMENT_SPEED = 4;

    /**
     * Dimensions of the wrapper
     */
    private final int GAME_WIDTH;
    private final int GAME_HEIGHT;

    private KeyboardHandler keyHandler;

    /**
     * TODO: replace with background sprite(s)
     */
    private final Color backGroundColor = Color.GREEN;

    private FrameRate frameRate;

    /**
     * player entity
     */
    private Player player;

    public ArrayList<BasicEnemy> enemies = new ArrayList<>();
    private int amountOfEnemies = 0;

    /**
     * sound files
     */
    private final String FILE_PATH = "res/sounds";
    private final String BACKGROUND_MUSIC_FILE = "mainBackGroundMusic.wav";
    private final String SHOOTING_SOUND_FILE = "gunShot.wav";

    /**
     * The drawing component
     */
    private GameCanvas gameCanvas;
    private BufferStrategy bs;

    private Thread gameThread;

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
//        createInputMap();
        keyHandler = new KeyboardHandler();
        addKeyListener(keyHandler);

        /** Hide that hideous mouse when we're playing! */
        setCursor( getToolkit().createCustomCursor( new BufferedImage( 3, 3, BufferedImage.TYPE_INT_ARGB ), new Point( 0, 0 ), "null" ) );

        /** Note: this will replaced when Xml utility is complete */
        /** init player */
        player = new Player(x, y);

        /** init enemies */
        enemies.add( new BasicEnemy( this, amountOfEnemies++ ) );

        initSounds();

        /** Run the game */
        gameThread = new Thread( this );
        gameThread.start();
    }

    private SoundManager SmBackground;
    public SoundManager  SmGunshot;

    private void initSounds() {
        /** init background music */
        SmBackground = new SoundManager( new File( FILE_PATH, BACKGROUND_MUSIC_FILE ), true );
        /** init shooting sound */
        SmGunshot    = new SoundManager( new File( FILE_PATH, SHOOTING_SOUND_FILE ), false );
    }

    /** TODO: Use KeyboardHandler class when game is better structured */
/**    private void createInputMap () {
//        InputMap im = this.getInputMap( JPanel.WHEN_IN_FOCUSED_WINDOW );
//        ActionMap am = this.getActionMap();
//
//        im.put( KeyStroke.getKeyStroke( KeyEvent.VK_LEFT, 0 ), "left" );
//        im.put( KeyStroke.getKeyStroke( KeyEvent.VK_RIGHT, 0 ), "right" );
//        im.put( KeyStroke.getKeyStroke( KeyEvent.VK_UP, 0 ), "up" );
//        im.put( KeyStroke.getKeyStroke( KeyEvent.VK_DOWN, 0 ), "down" );
//        im.put( KeyStroke.getKeyStroke( KeyEvent.VK_SPACE, 0 ), "space" );
//        im.put( KeyStroke.getKeyStroke( KeyEvent.VK_I, 0 ), "inventory" );
//        im.put( KeyStroke.getKeyStroke( KeyEvent.VK_Q, 0 ), "juke" );
//
//        //        KeyboardHandler keyHandler = new KeyboardHandler(this);
//        //        am.put("left", keyHandler);
//
//        am.put( "left", new AbstractAction() {
//            @Override
//            public void actionPerformed ( ActionEvent e ) {
//                player.directionFacing = player.FACING_LEFT;
//                x = player.getX();
//                player.setX( x -= MOVEMENT_SPEED );
//            }
//        } );
//
//        am.put( "up", new AbstractAction() {
//            @Override
//            public void actionPerformed ( ActionEvent e ) {
//                player.directionFacing = player.FACING_UP;
//                y = player.getY();
//                player.setY( y -= MOVEMENT_SPEED );
//            }
//        } );
//
//        am.put( "down", new AbstractAction() {
//            @Override
//            public void actionPerformed ( ActionEvent e ) {
//                player.directionFacing = player.FACING_DOWN;
//                y = player.getY();
//                player.setY( y += MOVEMENT_SPEED );
//            }
//        } );
//
//        am.put( "right", new AbstractAction() {
//            @Override
//            public void actionPerformed ( ActionEvent e ) {
//                player.directionFacing = player.FACING_RIGHT;
//                x = player.getX();
//                player.setX( x += MOVEMENT_SPEED );
//            }
//        } );
//
//        am.put( "juke", new AbstractAction() {
//            @Override
//            public void actionPerformed ( ActionEvent e ) {
//                player.juke();
//            }
//        } );
//
//        am.put( "space", new AbstractAction() {
//            @Override
//            public void actionPerformed ( ActionEvent e ) {
//                player.shoot();
//            }
//        } );
*/
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

        /** begin background music */
//        SmBackground.play();

        while( gameRunning ) {
            if( !gamePaused ) {
                /** record time */
                curTime    = System.nanoTime();
                nsPerFrame = curTime - lastTime;
                gameLoop( nsPerFrame / 1.0E6 );
                lastTime   = curTime;
            }
        }
    }

    private void gameLoop( double delta ) {
        checkForUserInput();
        update(delta);
        renderFrame();
        sleep(delta);
    }

    /** cmon you can do better than that Sam. */
    private void checkForUserInput() {
        keyHandler.poll();

        if(keyHandler.keyDownOnce( KeyEvent.VK_SPACE)) {
            System.out.println("space");
        }
        if(keyHandler.keyDown(KeyEvent.VK_UP)) {
            System.out.println("up");
        }
        if(keyHandler.keyDown(KeyEvent.VK_DOWN)) {
            System.out.println("down");
        }
        if(keyHandler.keyDown(KeyEvent.VK_LEFT)) {
            System.out.println("left");
        }
        if(keyHandler.keyDown(KeyEvent.VK_RIGHT)) {
            System.out.println("Right");
        }
    }

    private void renderFrame() {
        do {
            do {
                Graphics g = null;

                try {
                    g = bs.getDrawGraphics();
                    g.clearRect(0, 0, getWidth(), getHeight());
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

    private void render(Graphics g) {
        /** background */
        g.setColor(backGroundColor);
        g.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT - 1);

        /** calculate and display frame rate */
        frameRate.calculate();
        g.setColor(Color.BLACK);
        g.drawString(frameRate.getFrameRate(), 540, 30);

        /** draw players, bullets and enemies */
        player.draw(g);

        ArrayList<BasicEnemy> temp1 = new ArrayList<>(enemies);
        for( BasicEnemy badGuys: temp1 ) {
            badGuys.draw(g);
        }

        ArrayList<Item> temp2 = new ArrayList<>(items);
        for( Item invn: temp2) {
            invn.draw(g);
        }
    }

    /**
     * target frames per milli second (60 fps)
     */
    private double targetFpms = 1000 / 60;

    /**
     * if rendering is ahead of schedule sleep thread, else continue
     */
    private void sleep(double delta) {
        if (delta < targetFpms) {
            try {
                Thread.sleep( (long) (targetFpms - delta) );
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void gameOver() {
        gameRunning = false;
    }

    public void enemyKilled(int element, int exp) {
        enemies.remove(element);
        player.gainExp(exp);
        System.out.println("Gained " + exp + " experience");
        amountOfEnemies--;
    }

    /**
     * TODO: write sprite manager class and give these guys some damn textures....damnit
       TODO: When world size is created use delta for movement
     */
    private ArrayList<Item> items = new ArrayList<>();

    public void update(double delta) {
        /** check if alive */
        if ( player.getHealth() <= 0 ) {
            gameOver();
        }

        /**TODO: Game should tell player where to move, not the other way around. */
        /** update players location */
        x = player.getX();
        y = player.getY();

        ArrayList<Item> temp = new ArrayList<>(items);

        /** did you snatch an item? */
        /** TODO: replace constants with item dimensions */
        for ( Item i : temp ) {
            if ( ( x >= i.getX() && x <= i.getX() + 5 ) && ( y <= i.getY() + 5 && y >= i.getY() ) ) {
                player.addItem( i );
                items.remove( i );
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

    public void addItem( Item item ) {
        items.add( item );
    }

    public ArrayList<BasicEnemy> getEnemies() {
        return enemies;
    }

    protected void onWindowClosing(){
        /** clean up resources */
        gameCanvas.destroy();
        System.exit(0);
    }
}