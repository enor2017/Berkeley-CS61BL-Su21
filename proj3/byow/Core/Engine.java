package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.io.File;
import java.io.IOException;
import java.nio.file.NoSuchFileException;

import static byow.Core.IOUtils.*;

/**
 * This class only handle game logics, all things about GUI are in GameWindow.java
 */
public class Engine {
    /* indicating whether game is end */
    private boolean isGameOver = false;
    /* The map that we're keeping */
    private TETile[][] map = null;
    /* GameWindow for handling GUI part */
    private GameWindow gameWindow = new GameWindow();
    /* Avatar's position */
    private Position avatar = null;
    /* Avatar's life left */
    private int life = 5;
    /* Save & load map from this file */
    private static final File CWD = new File(System.getProperty("user.dir"));
    private static final File SAVE_FILE = join(CWD, "savefile.txt");

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
            // mouse position, detect if already has a map
            if(map != null) {
                Position mousePos = gameWindow.getMousePos();
                // if mouse position out of bound, do nothing
                if(checkPositionInBound(mousePos)) {
                    gameWindow.displayTileInfo(map[mousePos.getX()][mousePos.getY()]);
                } else {
                    gameWindow.displayTileInfo(Tileset.NOTHING);
                }
            }
        }
    }

    /* =============================================== */
    /* ===============  Input handlers =============== */
    /* =============================================== */

    /**
     * handling all character inputs
     * @param input the input character
     * @param inputSource the inputSource of input
     */
    private void handleKeyboardInput(char input, InputSource inputSource) {
        switch (input) {
            case 'n', 'N':
                // NOTICE: if already generated map, don't do anything!
                if(map != null) break;
                // display enterSeed window
                if(inputSource.isDisplayable()) {
                    gameWindow.displaySeed("");
                }
                String seed = beginRecordSeed(inputSource);
                map = gameWindow.getWorld(seed);
                // also find the position of avatar, but this is not elegant :(
                // avatar can be directly passed from WorldGenerator, but that's also inelegant to implement.
                findAvatar();
                // after entering seed, display game window
                if(inputSource.isDisplayable()) {
                    gameWindow.displayGameWindow(map, life);
                }
                break;
            case 'l', 'L':
                // load map
                if(readMap()) {
                    findAvatar();
                    if(inputSource.isDisplayable()) {
                        gameWindow.displayGameWindow(map, life);
                    }
                }
                break;
            case ':':
                saveMap();
                System.out.println("Successfully save map!");
                isGameOver = true;
                break;
            case 'w', 'W':
                // move avatar, and check whether refresh display
                moveAvatar(new Position(0, 1));
                if(inputSource.isDisplayable()) gameWindow.displayGameWindow(map, life);
                break;
            case 'a', 'A':
                moveAvatar(new Position(-1, 0));
                if(inputSource.isDisplayable()) gameWindow.displayGameWindow(map, life);
                break;
            case 's', 'S':
                moveAvatar(new Position(0, -1));
                if(inputSource.isDisplayable()) gameWindow.displayGameWindow(map, life);
                break;
            case 'd', 'D':
                moveAvatar(new Position(1, 0));
                if(inputSource.isDisplayable()) gameWindow.displayGameWindow(map, life);
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

    /* ================================================== */
    /* ===============  All Avatar stuff  =============== */
    /* ================================================== */

    /**
     * find avatar's position in current map, store in this.avatar.
     */
    private void findAvatar() {
        for(int i = 0; i < GameWindow.MAP_WIDTH; ++i) {
            for(int j = 0; j < GameWindow.MAP_HEIGHT; ++j) {
                if(map[i][j] == Tileset.AVATAR) {
                    avatar = new Position(i, j);
                    return;
                }
            }
        }
    }

    /**
     * set avatar to the given position
     * @param pos new avatar position
     */
    private void setAvatarPos(Position pos) {
        // make original position to floor and new position to avatar
        map[avatar.getX()][avatar.getY()] = Tileset.FLOOR;
        avatar = pos;
        map[avatar.getX()][avatar.getY()] = Tileset.AVATAR;
    }

    /**
     * check if the given position is in board bound
     * @param pos position to be checked
     * @return true if in bound, false else
     */
    private boolean checkPositionInBound(Position pos) {
        int x = pos.getX();
        int y = pos.getY();
        return (x >= 0 && x < GameWindow.MAP_WIDTH) && (y >= 0 && y < GameWindow.MAP_HEIGHT);
    }

    /**
     * check if the given position is a floor
     * @param pos position to be checked
     * @return true if is floor, false else
     */
    private boolean isFloor(Position pos) {
        return map[pos.getX()][pos.getY()] == Tileset.FLOOR;
    }

    /**
     * Move avatar in direction: dire, and update gameMap.
     * @param dire which dire does avatar move
     */
    private void moveAvatar(Position dire) {
        Position newPos = avatar.add(dire);
        if(checkPositionInBound(newPos) && isFloor(newPos)) {
            setAvatarPos(newPos);
        }
    }

    /* ================================================== */
    /* ===============  Save & Load Map  ================ */
    /* ================================================== */

    private void saveMap() {
        // if savefile.txt does not exist, create a new one
        if(!SAVE_FILE.exists()) {
            try {
                SAVE_FILE.createNewFile();
            } catch (IOException e) {
                System.out.println("Error while creating savefile.txt");
            }
        }
        // write map into savefile
        writeObject(SAVE_FILE, map);
    }

    private boolean readMap() {
        // if savefile.txt does not exist, print error message
        if(!SAVE_FILE.exists()) {
            System.out.println("Map file does not exist.");
            return false;
        }
        map = readObject(SAVE_FILE, map.getClass());
        return true;
    }
}
