package test.com.samuelColon.restless.Window;

import com.samuelColon.restless.Game;
import org.junit.Test;
import static org.junit.Assert.*;

public class WindowTest {

    @Test
    public void windowObjectNullAfterClosing(){
        Game game = new Game(500, 500);
        game.onWindowClosing();

        try {
            System.out.println(game);
            assertEquals( null, game );
        } catch ( Exception e ) {
            e.printStackTrace();
        }
    }

}