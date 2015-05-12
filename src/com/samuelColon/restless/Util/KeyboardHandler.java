package com.samuelColon.restless.Util;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardHandler implements KeyListener {
    /**
        private Game game;
        private InputMap  im;
        private ActionMap am;

        public KeyboardHandler(Game game) {
            this.game = game;
            im = game.getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW);
            am = game.getActionMap();
            initInputMap();
        }

    //    private class ActionHandler extends AbstractAction {
    //        public ActionHandler() { super(); }
    //
    //        @Override
    //        public void actionPerformed(ActionEvent e) {
    //            if(e.getActionCommand().equals("left")) {
    //                System.out.println("left");
    //            } else if(e.getActionCommand().equals("up")) {
    //                System.out.println("up");
    //            }
    //        }
    //    }

        private void initInputMap() {
            im.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT,  0), "left");
            im.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "right");
            im.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP,    0), "up");
            im.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN,  0), "down");
            im.put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0), "space");
            im.put(KeyStroke.getKeyStroke(KeyEvent.VK_I,     0), "inventory");
            im.put(KeyStroke.getKeyStroke(KeyEvent.VK_Q,     0), "juke");

    //        ActionHandler handler = new ActionHandler();

    //        am.put("left", new ActionHandler());
    //        am.put("up",   new ActionHandler());

            am.put("left", new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    game.player.directionFacing = player.FACING_LEFT;
                    x = player.getX();
                    player.setX( x -= MOVEMENT_SPEED);
                }
            });

            am.put("up", new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    player.directionFacing = player.FACING_UP;
                    y = player.getY();
                    player.setY(y -= MOVEMENT_SPEED);
                }
            });

            am.put("down", new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    player.directionFacing = player.FACING_DOWN;
                    y = player.getY();
                    player.setY(y += MOVEMENT_SPEED);
                }
            });

            am.put("right", new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    player.directionFacing = player.FACING_RIGHT;
                    x = player.getX();
                    player.setX(x += MOVEMENT_SPEED);
                }
            });

            am.put("juke", new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    player.juke();
                }
            });

            am.put("space", new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    player.shoot();
                }
            });

            am.put("inventory", new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (!game.gamePaused) {
                        SmBackground.pauseSound();
    //                    openInventoryScreen();
                    } else {
                        /** the line listener automatically closes when the music is paused
                         *  recreate the clip object
                        SmBackground.play();
                        closeInventoryScreen();
                    }
                    gamePaused = !gamePaused;
                }
            });
        } */

    private boolean[] keys;
    private int[] polled;

    public KeyboardHandler() {
        keys = new boolean[256];
        polled = new int[256];
    }

    /** is at least one key pressed? */
    public boolean keyDown(int keyCode) {
        return polled[keyCode] > 0;
    }

    public boolean keyDownOnce(int keyCode) {
        return polled[keyCode] == 1;
    }

    public synchronized void poll() {
        for(int i = 0; i < keys.length; i++) {
            if( keys[i]) {
                polled[i]++;
            } else {
                polled[i] = 0;
            }
        }
    }

    public synchronized void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if(keyCode >= 0 && keyCode < keys.length) {
            keys[keyCode] = true;
        }
    }

    public synchronized  void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if( keyCode >=0 && keyCode < keys.length) {
            keys[keyCode] = false;
        }
    }

    public void keyTyped(KeyEvent e) {
    }
}