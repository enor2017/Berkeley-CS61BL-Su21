package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;

/**
 * This is the class for handling all kinds of GUI things
 * usually called by Engine.java
 */
public class GameWindow {
    /* The tile world renderer */
    TERenderer ter = new TERenderer();
    /* The width and height of game screen */
    public static final int WIDTH = 59;
    public static final int HEIGHT = 41;
    /* The width and height of start menu */
    public static final int MENU_WIDTH = 25;
    public static final int MENU_HEIGHT = 40;
    /* Some useful definitions for StdDraw */
    Font bigFont = new Font("Monaco", Font.BOLD, 30);
    Font medFont = new Font("Monaco", Font.BOLD, 22);
    Font smallFont = new Font("Monoco", Font.PLAIN,16);

    /**
     * function to get a maze world with given seed
     * @param seed random seed to generate maze world
     * @return the world generated, in TETile[][]
     */
    public TETile[][] getWorld(String seed) {
        WorldGenerator worldGen = new WorldGenerator(WIDTH, HEIGHT, seed);
        return worldGen.convertToTile();
    }

    /**
     * Display main menu
     */
    public void displayMenu() {
        StdDraw.setCanvasSize(MENU_WIDTH * 16, MENU_HEIGHT * 16);
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.setXscale(0, MENU_WIDTH);
        StdDraw.setYscale(0, MENU_HEIGHT);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();

        // draw title
        StdDraw.setFont(bigFont);
        StdDraw.text(MENU_WIDTH / 2.0, MENU_HEIGHT * 0.7, "CS61B: THE GAME");

        // draw menu options
        StdDraw.setFont(medFont);
        StdDraw.text(MENU_WIDTH / 2.0, MENU_HEIGHT * 0.5, "New Game (N)");
        StdDraw.text(MENU_WIDTH / 2.0, MENU_HEIGHT * 0.4, "Load Game (L)");
        StdDraw.text(MENU_WIDTH / 2.0, MENU_HEIGHT * 0.3, "Quit (Q)");
        StdDraw.show();
    }

    /**
     * Display seed while user is entering random seed
     * @param seed the seed that user has entered so far
     */
    public void displaySeed(String seed) {
        StdDraw.clear(Color.BLACK);
        StdDraw.setFont(bigFont);
        StdDraw.text(MENU_WIDTH / 2.0, MENU_HEIGHT * 0.7, "Please Enter A Seed:");

        StdDraw.setFont(medFont);
        StdDraw.text(MENU_WIDTH / 2.0, MENU_HEIGHT * 0.5, seed);
        StdDraw.show();
    }

    /**
     * Display maze game window given map
     * @param tiles the maze map to display
     */
    public void displayGameWindow(TETile[][] tiles) {
        StdDraw.setCanvasSize(WIDTH * 16, HEIGHT * 16);
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.setXscale(0, WIDTH);
        StdDraw.setYscale(0, HEIGHT);
        StdDraw.clear(Color.BLACK);

        // display game maze
        ter.initialize(WIDTH, HEIGHT);
        ter.renderFrame(tiles);

        StdDraw.show();
    }
}
