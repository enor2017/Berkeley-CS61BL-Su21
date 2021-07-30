package PlusWorld;
import org.junit.Test;
import static org.junit.Assert.*;

import byowTools.TileEngine.TERenderer;
import byowTools.TileEngine.TETile;
import byowTools.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world consisting of plus shaped regions.
 */
public class PlusWorld {

    private static final int WIDTH = 50;
    private static final int HEIGHT = 30;

    public static void fillWithNothing(TETile[][] tiles) {
        int height = tiles[0].length;
        int width = tiles.length;
        for (int x = 0; x < width; x += 1) {
            for (int y = 0; y < height; y += 1) {
                tiles[x][y] = Tileset.NOTHING;
            }
        }
    }

    public static void main(String[] args) {
        // initialize the tile rendering engine with a window of size WIDTH x HEIGHT
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        TETile[][] tiles = new TETile[WIDTH][HEIGHT];
        fillWithNothing(tiles);

        // draw a plus sign
        Plus plusSign = new Plus(new Point(20, 5), 5, Tileset.FLOWER);
        plusSign.drawPlus(tiles);

        // display the world
        ter.renderFrame(tiles);
    }

}
