package com.samuelColon.restless.Util;

import javax.sound.sampled.*;
import java.io.*;

/** TODO: allow the user to change the volume of background music and sound effects */
public class SoundManager {
    private Clip clip;

    public SoundManager ( File file, Boolean loopAudio ) {
        try {
            clip = (Clip) AudioSystem.getLine( new Line.Info( Clip.class ) );
            clip.open( AudioSystem.getAudioInputStream( file ) );

            if( loopAudio ) {
                clip.loop( Clip.LOOP_CONTINUOUSLY );
            }
            clip.addLineListener( new LineListener() {
                @Override
                public void update ( LineEvent event ) {
                    if ( event.getType() == LineEvent.Type.STOP ) {
                        clip.close();
                    }
                }
            });
        } catch ( Exception exc ) {
            exc.printStackTrace( System.out );
        }
    }

    public void play() {
        clip.start();
    }

    /** TODO: once inventory panel is up. Opening the window will reduce the volume and no longer
     * stop the music.
     * */
    public void pauseSound () {
        clip.stop();
    }
}