package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;

public class Engine {
    TERenderer ter = new TERenderer();
    /* The width and height of game screen */
    private static final int WIDTH = 59;
    private static final int HEIGHT = 41;
    /* The width and height of start menu */
    private final int MENU_WIDTH = 25;
    private final int MENU_HEIGHT = 40;
    /* indicating whether game is end */
    private boolean isGameOver = false;
    /* Some useful definitions for StdDraw */
    Font bigFont = new Font("Monaco", Font.BOLD, 30);
    Font medFont = new Font("Monaco", Font.BOLD, 22);
    Font smallFont = new Font("Monoco", Font.PLAIN,16);
    /* The map that we're keeping */
    TETile[][] map = new TETile[WIDTH][HEIGHT];

    /**
     * Method used for exploring a fresh world. This method should handle all inputs,
     * including inputs from the main menu.
     */
    public void interactWithKeyboard() {
        InputSource keyboardInput = new KeyboardInputSource();
        gameLoop(keyboardInput);
    }

    /**
     * Method used for autograding and testing your code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The engine should
     * behave exactly as if the user typed these characters into the engine using
     * interactWithKeyboard.
     *
     * Recall that strings ending in ":q" should cause the game to quite save. For example,
     * if we do interactWithInputString("n123sss:q"), we expect the game to run the first
     * 7 commands (n123sss) and then quit and save. If we then do
     * interactWithInputString("l"), we should be back in the exact same state.
     *
     * In other words, both of these calls:
     *   - interactWithInputString("n123sss:q")
     *   - interactWithInputString("lww")
     *
     * should yield the exact same world state as:
     *   - interactWithInputString("n123sssww")
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] interactWithInputString(String input) {
        InputSource inputSource = new StringInputDevice(input);
        gameLoop(inputSource);
        return map;
    }

    private void handleKeyboardInput(char input, InputSource inputSource) {
        switch (input) {
            case 'n', 'N':
                // display enterSeed window
                if(inputSource.isDisplayable()) {
                    displaySeed("");
                }
                String seed = beginRecordSeed(inputSource);
                WorldGenerator worldGen = new WorldGenerator(WIDTH, HEIGHT, seed);
                map = worldGen.convertToTile();
                // after entering seed, display game window
                if(inputSource.isDisplayable()) {
                    displayGameWindow(map);
                }
                break;
            default:
                System.out.println("Unknown input!");
        }
    }

    /**
     * the main game loop, taking an input Source
     * @param inputSource Keyboard or String Input
     */
    private void gameLoop(InputSource inputSource) {
        // if keyboard input, display menu
        if(inputSource.isDisplayable()) {
            displayMenu();
        }

        while(!isGameOver) {
            // For String inputSource, no next input means game over
            if(!inputSource.isDisplayable() && inputSource.possibleNextInput()) {
                break;
            }
            // for each time, interact with keyboard and mouse
            if(inputSource.possibleNextInput()) {
                // get the map after handling the input
                handleKeyboardInput(inputSource.getNextKey(), inputSource);
            }
            // mouse position
//            double mouseX = StdDraw.mouseX();
//            double mouseY = StdDraw.mouseY();
//            System.out.println("Mouse position: " + mouseX + ", " + mouseY);
        }
    }

    private String beginRecordSeed(InputSource inputSource) {
        String randomSeed = "";
        while(true) {
            if(inputSource.possibleNextInput()) {
                char c = inputSource.getNextKey();
                if(c == 's' || c == 'S') {
                    // end entering seed and return the seed.
                    return randomSeed;
                } else {
                    randomSeed += c;
                    // for keyboard input, display seed window
                    if(inputSource.isDisplayable()) {
                        displaySeed(randomSeed);
                    }
                }
            }
        }
    }

    private void displayMenu() {
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

    private void displaySeed(String seed) {
        StdDraw.clear(Color.BLACK);
        StdDraw.setFont(bigFont);
        StdDraw.text(MENU_WIDTH / 2.0, MENU_HEIGHT * 0.7, "Please Enter A Seed:");

        StdDraw.setFont(medFont);
        StdDraw.text(MENU_WIDTH / 2.0, MENU_HEIGHT * 0.5, seed);
        StdDraw.show();
    }

    private void displayGameWindow(TETile[][] tiles) {
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
