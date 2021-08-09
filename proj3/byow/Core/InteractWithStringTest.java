package byow.Core;

import byow.TileEngine.TETile;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * This is a really simple test of InteractWithStringTest,
 * basically follow what autograder does.
 */
public class InteractWithStringTest {
    @Test
    public void phase1Test() {
        Engine e = new Engine();
        TETile[][] map = e.interactWithInputString("Nberkeley61blS");
        String firstMap = TETile.toString(map);
        // create the same world
        e = new Engine();
        map = e.interactWithInputString("Nberkeley61blS");
        String sameMap = TETile.toString(map);
        assertEquals(firstMap, sameMap);
        // create a diff world
        e = new Engine();
        map = e.interactWithInputString("NAnything123S");
        String diffMap = TETile.toString(map);
        assertNotEquals(firstMap, diffMap);

        System.out.println("=== Please ensure no StdDraw Windows pop out. ===");
    }

    @Test
    public void phase2Test() {
        Engine e = new Engine();
        TETile[][] map = e.interactWithInputString("N123Swwwwdddssssaasawwdsas");
        String firstMap = TETile.toString(map);
        // create the same world
        e = new Engine();
        map = e.interactWithInputString("N123Swwwwdddssssaasawwdsas");
        String sameMap = TETile.toString(map);
        assertEquals(firstMap, sameMap);
        // create a diff world
        e = new Engine();
        map = e.interactWithInputString("N456Swwwwdddssssaasawwdsas");
        String diffMap = TETile.toString(map);
        assertNotEquals(firstMap, diffMap);
        // save and load world
        e = new Engine();
        map = e.interactWithInputString("NlalalaSqwertyuiopasdfghjlzxcvzbnm:q");
        String saveMap = TETile.toString(map);
        e = new Engine();
        map = e.interactWithInputString("l");
        String loadMap = TETile.toString(map);
        assertEquals(saveMap, loadMap);

        System.out.println("=== Please ensure no StdDraw Windows pop out. ===");
    }
}
