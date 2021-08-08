package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;

/**
 * This is the class for handling all kinds of GUI things
 * usually called by Engine.java
 */
public class GameWindow {
    /* The tile world renderer */
    TERenderer ter = new TERenderer();
    /* The width and height of game screen (Map + Fonts) */
    public static final int WIDTH = 59;
    public static final int HEIGHT = 44;
    /* The width and height of MAP(maze) */
    public static final int MAP_WIDTH = 59;
    public static final int MAP_HEIGHT = 41;
    /* The width and height of start menu */
    public static final int MENU_WIDTH = 25;
    public static final int MENU_HEIGHT = 40;
    /* Some useful definitions for StdDraw */
    Font bigFont = new Font("Monaco", Font.BOLD, 30);
    Font medFont = new Font("Monaco", Font.BOLD, 22);
    Font smallFont = new Font("Monaco", Font.PLAIN,16);
    /* if need to change canvas size to map size, used for 1st time displaying map */
    boolean changeSize = true;

    /**
     * function to get a maze world with given seed
     * @param seed random seed to generate maze world
     * @return the world generated, in TETile[][]
     */
    public TETile[][] getWorld(String seed) {
        WorldGenerator worldGen = new WorldGenerator(MAP_WIDTH, MAP_HEIGHT, seed);
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
     * @param life the life that avatar left
     */
    public void displayGameWindow(TETile[][] tiles, int life) {
        // if 1st time display map, remember to change canvas size
        if(changeSize) {
            StdDraw.setCanvasSize(WIDTH * 16, HEIGHT * 16);
            StdDraw.setXscale(0, WIDTH);
            StdDraw.setYscale(0, HEIGHT);
            ter.initialize(MAP_WIDTH, MAP_HEIGHT);
            changeSize = false;
        }
        StdDraw.setPenColor(Color.WHITE);

        // display game maze
        ter.renderFrame(tiles);

        // display avatar life
        displayLife(life);
        StdDraw.show();
    }

    private void clearLeftFontArea() {
        StdDraw.setPenColor(Color.BLACK);
        StdDraw.filledRectangle(MAP_WIDTH / 4.0, (MAP_HEIGHT + HEIGHT) / 2.0,
                MAP_WIDTH / 4.0, (HEIGHT - MAP_HEIGHT) / 2.0);
        StdDraw.setPenColor(Color.WHITE);
    }

    private void clearRightFontArea() {
        StdDraw.setPenColor(Color.BLACK);
        StdDraw.filledRectangle(MAP_WIDTH * 0.75, (MAP_HEIGHT + HEIGHT) / 2.0,
                MAP_WIDTH * 0.25, (HEIGHT - MAP_HEIGHT) / 2.0);
        StdDraw.setPenColor(Color.WHITE);
    }

    private void displayLife(int life) {
        clearLeftFontArea();
        StdDraw.setFont(smallFont);
        StdDraw.textLeft(0, HEIGHT - 1.5, "Life: " + life);
        // StdDraw.show() is called in displayGameWindow();
    }

    public Position getMousePos() {
        int x = (int) StdDraw.mouseX();
        int y = (int) StdDraw.mouseY();
        // System.out.println("x: " + x + ", y: " + y);
        // delay 100ms avoid so many refreshing
//        StdDraw.pause(20);
        return new Position(x, y);
    }

    public void displayTileInfo(TETile tileType) {
        clearRightFontArea();
        StdDraw.setFont(smallFont);
        String message = "";
        if(tileType == Tileset.NOTHING) message = "oops! It seems nothing here.";
        else if(tileType == Tileset.FLOOR) message = "Here, you find a normal floor.";
        else if(tileType == Tileset.AVATAR) message = "Hey! Here is where you are!";
        else if(tileType == Tileset.WALL) message = "This is a wall, don't try to break it.";
        else message = "What the hell are you pointing at???";
        StdDraw.textRight(WIDTH, HEIGHT - 1.5, message);
        StdDraw.show();
    }
}
