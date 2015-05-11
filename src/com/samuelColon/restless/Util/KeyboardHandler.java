package com.samuelColon.restless.Util;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class KeyboardHandler {
    //    private Game game;
    //    private InputMap  im;
    //    private ActionMap am;
    //
    //    public KeyboardHandler(Game game) {
    //        this.game = game;
    //        im = game.getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW);
    //        am = game.getActionMap();
    //        initInputMap();
    //    }
    //
    ////    private class ActionHandler extends AbstractAction {
    ////        public ActionHandler() { super(); }
    ////
    ////        @Override
    ////        public void actionPerformed(ActionEvent e) {
    ////            if(e.getActionCommand().equals("left")) {
    ////                System.out.println("left");
    ////            } else if(e.getActionCommand().equals("up")) {
    ////                System.out.println("up");
    ////            }
    ////        }
    ////    }
    //
    //    private void initInputMap() {
    //        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT,  0), "left");
    //        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "right");
    //        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP,    0), "up");
    //        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN,  0), "down");
    //        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0), "space");
    //        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_I,     0), "inventory");
    //        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_Q,     0), "juke");
    //
    ////        ActionHandler handler = new ActionHandler();
    //
    ////        am.put("left", new ActionHandler());
    ////        am.put("up",   new ActionHandler());
    //
    //        am.put("left", new AbstractAction() {
    //            @Override
    //            public void actionPerformed(ActionEvent e) {
    //                game.player.directionFacing = player.FACING_LEFT;
    //                x = player.getX();
    //                player.setX( x -= MOVEMENT_SPEED);
    //            }
    //        });
    //
    //        am.put("up", new AbstractAction() {
    //            @Override
    //            public void actionPerformed(ActionEvent e) {
    //                player.directionFacing = player.FACING_UP;
    //                y = player.getY();
    //                player.setY(y -= MOVEMENT_SPEED);
    //            }
    //        });
    //
    //        am.put("down", new AbstractAction() {
    //            @Override
    //            public void actionPerformed(ActionEvent e) {
    //                player.directionFacing = player.FACING_DOWN;
    //                y = player.getY();
    //                player.setY(y += MOVEMENT_SPEED);
    //            }
    //        });
    //
    //        am.put("right", new AbstractAction() {
    //            @Override
    //            public void actionPerformed(ActionEvent e) {
    //                player.directionFacing = player.FACING_RIGHT;
    //                x = player.getX();
    //                player.setX(x += MOVEMENT_SPEED);
    //            }
    //        });
    //
    //        am.put("juke", new AbstractAction() {
    //            @Override
    //            public void actionPerformed(ActionEvent e) {
    //                player.juke();
    //            }
    //        });
    //
    //        am.put("space", new AbstractAction() {
    //            @Override
    //            public void actionPerformed(ActionEvent e) {
    //                player.shoot();
    //            }
    //        });
    //
    //        am.put("inventory", new AbstractAction() {
    //            @Override
    //            public void actionPerformed(ActionEvent e) {
    //                if (!game.gamePaused) {
    //                    SmBackground.pauseSound();
    ////                    openInventoryScreen();
    //                } else {
    //                    /** the line listener automatically closes when the music is paused
    //                     *  recreate the clip object */
    //                    SmBackground.play();
    //                    closeInventoryScreen();
    //                }
    //                gamePaused = !gamePaused;
    //            }
    //        });
    //    }
}
