import static org.junit.Assert.*;
import org.junit.Test;
import java.util.List;

/**
 * Created by Jenny Huang on 3/12/19.
 */
public class TestMyTrieSet {

    // assumes add/contains work
    @Test
    public void sanityClearTest() {
         MyTrieSet t = new MyTrieSet();
         for (int i = 0; i < 455; i++) {
             t.add("hi" + i);
             //make sure put is working via contains
             assertTrue(t.contains("hi" + i));
         }
         t.clear();
         for (int i = 0; i < 455; i++) {
             assertFalse(t.contains("hi" + i));
         }
    }

    // assumes add works
    @Test
    public void sanityContainsTest() {
        MyTrieSet t = new MyTrieSet();
        assertFalse(t.contains("waterYouDoingHere"));
        t.add("waterYouDoingHere");
        assertTrue(t.contains("waterYouDoingHere"));

        // Add a few simple tests.
        assertFalse(t.contains("waterYouDoingHer"));
        assertFalse(t.contains("aterYouDoingHere"));

        t.add("a");
        assertTrue(t.contains("a"));
        assertFalse(t.contains(""));
        assertFalse(t.contains("wa"));
        assertFalse(t.contains("aa"));

        t.add("abc");
        assertTrue(t.contains("a"));
        assertTrue(t.contains("abc"));
        assertFalse(t.contains("ab"));
        assertFalse(t.contains("c"));
        assertFalse(t.contains("aa"));
    }

    // assumes add works
    @Test
    public void sanityPrefixTest() {
         String[] saStrings = new String[]{"same", "sam", "sad", "sap"};
         String[] otherStrings = new String[]{"a", "awls", "hello"};

         MyTrieSet t = new MyTrieSet();
         for (String s: saStrings) {
             t.add(s);
         }
         for (String s: otherStrings) {
             t.add(s);
         }

         List<String> keys = t.keysWithPrefix("sa");
         for (String s: saStrings) {
             assertTrue(keys.contains(s));
         }
         for (String s: otherStrings) {
             assertFalse(keys.contains(s));
         }
    }

    @Test
    public void longestPrefixOfTest() {
        MyTrieSet t = new MyTrieSet();

        t.add("lalala");
        t.add("lala");
        t.add("la");
        assertEquals("lalala", t.longestPrefixOf("lalala"));
        assertEquals("lala", t.longestPrefixOf("lala"));
        assertEquals("la", t.longestPrefixOf("la"));
        assertEquals("la", t.longestPrefixOf("lal"));
        assertNull(t.longestPrefixOf("b"));
        assertNull(t.longestPrefixOf("a"));
        assertNull(t.longestPrefixOf("al"));

        t.add("sa");
        t.add("sad");
        t.add("sadly");
        assertEquals("sa", t.longestPrefixOf("sap"));
        assertEquals("sa", t.longestPrefixOf("sa"));
        assertEquals("sad", t.longestPrefixOf("sad"));
        assertEquals("sad", t.longestPrefixOf("sadl"));
        assertEquals("sad", t.longestPrefixOf("sady"));
        assertEquals("sadly", t.longestPrefixOf("sadly"));
        assertEquals("lalala", t.longestPrefixOf("lalalasadly"));
        assertEquals("sadly", t.longestPrefixOf("sadlylalala"));
    }
}
