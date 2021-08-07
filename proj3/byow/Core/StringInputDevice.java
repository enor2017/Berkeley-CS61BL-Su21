package byow.Core;

/**
 * Created by hug.
 */
public class StringInputDevice implements InputSource {
    private String input;
    private int index;

    public StringInputDevice(String s) {
        index = 0;
        input = s;
    }

    @Override
    public char getNextKey() {
        char returnChar = input.charAt(index);
        index += 1;
        return returnChar;
    }

    @Override
    public boolean possibleNextInput() {
        return index < input.length();
    }

    @Override
    public boolean isDisplayable() {
        return false;
    }
}
