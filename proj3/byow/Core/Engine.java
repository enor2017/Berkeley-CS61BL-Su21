package byow.Core;

import byow.TileEngine.TETile;

/**
 * This class only handle game logics, all things about GUI are in GameWindow.java
 */
public class Engine {
    /* indicating whether game is end */
    private boolean isGameOver = false;
    /* The map that we're keeping */
    TETile[][] map = new TETile[GameWindow.WIDTH][GameWindow.HEIGHT];
    /* GameWindow for handling GUI part */
    GameWindow gameWindow = new GameWindow();

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

    /**
     * the main game loop, taking an input Source
     * @param inputSource Keyboard or String Input
     */
    private void gameLoop(InputSource inputSource) {
        // if keyboard input, display menu
        if(inputSource.isDisplayable()) {
            gameWindow.displayMenu();
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

    /**
     * handling all character inputs
     * @param input the input character
     * @param inputSource the inputSource of input
     */
    private void handleKeyboardInput(char input, InputSource inputSource) {
        switch (input) {
            case 'n', 'N':
                // display enterSeed window
                if(inputSource.isDisplayable()) {
                    gameWindow.displaySeed("");
                }
                String seed = beginRecordSeed(inputSource);
                map = gameWindow.getWorld(seed);
                // after entering seed, display game window
                if(inputSource.isDisplayable()) {
                    gameWindow.displayGameWindow(map);
                }
                break;
            default:
                System.out.println("Unknown input!");
        }
    }

    /**
     * After begin entering seed, use this function to handle
     * @param inputSource the inputSource of input
     * @return the random seed entered
     */
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
                        gameWindow.displaySeed(randomSeed);
                    }
                }
            }
        }
    }

}
