package byow.Core;

import byow.InputDemo.InputSource;
import byow.InputDemo.StringInputDevice;
import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;

public class Engine {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 49;
    public static final int HEIGHT = 25;

    /**
     * Method used for exploring a fresh world. This method should handle all inputs,
     * including inputs from the main menu.
     */
    public void interactWithKeyboard() {
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
        // TODO: Fill out this method so that it run the engine using the input
        // passed in as an argument, and return a 2D tile representation of the
        // world that would have been drawn if the same inputs had been given
        // to interactWithKeyboard().
        //
        // See proj3.byow.InputDemo for a demo of how you can make a nice clean interface
        // that works for many different input types.

        InputSource inputSource = new StringInputDevice(input);
        TETile[][] finalWorldFrame = handleAllInputs(inputSource);
        // ter.initialize(WIDTH, HEIGHT);
        // ter.renderFrame(finalWorldFrame);
        return finalWorldFrame;
    }

    private TETile[][] handleAllInputs(InputSource inputSource) {
        // record whether the map has been generated,
        // since before and after map generation, some keys perform diff functions
        boolean isMapGenerated = false;
        // The map that we're keeping
        TETile[][] map = new TETile[49][25];

        while (inputSource.possibleNextInput()) {
            char c = inputSource.getNextKey();
            switch (c) {
                case 'n', 'N':
                    map = beginRecordSeed(inputSource);
                    break;
                default:
                    System.out.println("Unknown input!");
            }
        }

        return map;
    }

    private TETile[][] beginRecordSeed(InputSource inputSource) {
        String randomSeed = "";
        while (inputSource.possibleNextInput()) {
            char c = inputSource.getNextKey();
            if(c == 's' || c == 'S') {
                // end entering seed, generate a world and return.
                WorldGenerator gen = new WorldGenerator(WIDTH, HEIGHT, randomSeed);
                return gen.convertToTile();
            } else {
                randomSeed += c;
            }
        }
        // redundant return statement(never happen for valid input)
        return null;
    }
}
