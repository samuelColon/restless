package com.samuelColon.restless.Util;

import javax.sound.sampled.*;
import java.io.*;

public class SoundManager {
    private final File file;
    private boolean loopStatus;

    public SoundManager ( File file, Boolean loopStatus ) {
        this.file = file;
        this.loopStatus = loopStatus;
    }

    private Clip clip;

    public void play () {
        try {
            clip = (Clip) AudioSystem.getLine( new Line.Info( Clip.class ) );
            clip.open( AudioSystem.getAudioInputStream( file ) );

            if ( loopStatus ) {
                clip.loop( Clip.LOOP_CONTINUOUSLY );
            }
            clip.addLineListener( new LineListener() {
                @Override
                public void update ( LineEvent event ) {
                    if ( event.getType() == LineEvent.Type.STOP ) {
                        clip.close();
//                        System.out.println("Closed from line listener");
                    }
                }
            } );

            clip.start();
        } catch ( Exception exc ) {
            exc.printStackTrace( System.out );
        }
    }

    /** TODO: update pause sound so a resume sound method can be implemented
     * (one that doesn't start sound from begining) */
    public void pauseSound () {
        clip.stop();
    }
}