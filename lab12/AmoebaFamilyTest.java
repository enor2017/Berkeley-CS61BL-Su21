import org.junit.Test;
import static org.junit.Assert.*;

/**
 * A really simple test for AmoebaFamily
 */
public class AmoebaFamilyTest {
    @Test
    public void longestNameTest1() {
        AmoebaFamily family = new AmoebaFamily("Amos McCoy");
        family.addChild("Amos McCoy", "mom/dad");
        family.addChild("Amos McCoy", "auntie");
        family.addChild("mom/dad", "me");
        family.addChild("mom/dad", "Fred");
        family.addChild("mom/dad", "Wilma");
        family.addChild("me", "Mike");
        family.addChild("me", "Homer");
        family.addChild("me", "Marge");
        family.addChild("Mike", "Bart");
        family.addChild("Mike", "Lisa");
        family.addChild("Marge", "Bill");
        family.addChild("Marge", "Hilary");

        assertEquals("Amos McCoy", family.longestName());
    }

    @Test
    public void longestNameTest2() {
        AmoebaFamily family = new AmoebaFamily("Amos McCoy");
        family.addChild("Amos McCoy", "mom/dad/mummy/daddy");
        family.addChild("Amos McCoy", "auntie");
        family.addChild("mom/dad/mummy/daddy", "me");
        family.addChild("mom/dad/mummy/daddy", "Fred");
        family.addChild("mom/dad/mummy/daddy", "Wilma");
        family.addChild("me", "Mike");
        family.addChild("me", "Homer");
        family.addChild("me", "Marge");
        family.addChild("Mike", "Bart");
        family.addChild("Mike", "Lisa");
        family.addChild("Marge", "Bill");
        family.addChild("Marge", "Hilary");

        assertEquals("mom/dad/mummy/daddy", family.longestName());
    }

    @Test
    public void longestNameTest3() {
        AmoebaFamily family = new AmoebaFamily("Amos McCoy");
        family.addChild("Amos McCoy", "mom/dad");
        family.addChild("Amos McCoy", "auntie");
        family.addChild("mom/dad", "me");
        family.addChild("mom/dad", "Fred");
        family.addChild("mom/dad", "Wilma");
        family.addChild("me", "Mike");
        family.addChild("me", "Homer");
        family.addChild("me", "Marge");
        family.addChild("Mike", "Bart");
        family.addChild("Mike", "LisaLisaLisaHaHa");
        family.addChild("Marge", "Bill");
        family.addChild("Marge", "Hilary");

        assertEquals("LisaLisaLisaHaHa", family.longestName());
//        assertEquals(16, family.longestNameLength());
    }

    @Test
    public void longestNameTest4() {
        AmoebaFamily family = new AmoebaFamily("Amos McCoy");
        family.addChild("Amos McCoy", "mom/dad");
        family.addChild("Amos McCoy", "auntie");
        family.addChild("mom/dad", "mememememememe");
        family.addChild("mom/dad", "Fred");
        family.addChild("mom/dad", "Wilma");
        family.addChild("mememememememe", "Mike");
        family.addChild("mememememememe", "Homer");
        family.addChild("mememememememe", "Marge");
        family.addChild("Mike", "Bart");
        family.addChild("Mike", "LisaLisa");
        family.addChild("Marge", "Bill");
        family.addChild("Marge", "Hilary");

        assertEquals("mememememememe", family.longestName());
    }
}
