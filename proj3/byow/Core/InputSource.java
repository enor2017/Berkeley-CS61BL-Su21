package byow.Core;

/**
 * Created by hug.
 */
public interface InputSource {
    public char getNextKey();
    public boolean possibleNextInput();
    /**
     * whether this kind of inputSource need to display game window
     * @return true for Keyboard, false for String input
     */
    public boolean isDisplayable();
}
