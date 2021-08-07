package byow.Core;

/**
 * Created by hug, modified by enor2017.
 */
import edu.princeton.cs.introcs.StdDraw;

public class KeyboardInputSource implements InputSource {

    @Override
    public char getNextKey() {
        return Character.toUpperCase(StdDraw.nextKeyTyped());
    }

    @Override
    public boolean possibleNextInput() {
        return StdDraw.hasNextKeyTyped();
    }

    @Override
    public boolean isDisplayable() {
        return true;
    }
}
