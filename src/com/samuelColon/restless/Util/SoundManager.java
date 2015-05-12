package com.samuelColon.restless.Util;

import javax.sound.sampled.*;
import java.io.*;

/** TODO: allow the user to change the volume of background music and sound effects */
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
                    }
                }
            } );

            clip.start();
        } catch ( Exception exc ) {
            exc.printStackTrace( System.out );
        }
    }

    /** TODO: once inventory panel is up. Replace pause will adjust volume. Opening the window will reduce the volume and no longer
     * stop the music.
     * */
    public void pauseSound () {
        clip.stop();
    }
}