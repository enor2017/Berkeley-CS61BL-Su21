package deque;

import org.junit.Test;
import static org.junit.Assert.*;


/** Performs some basic linked list deque tests. */
public class LinkedListDequeTest {

    /** You MUST use the variable below for all of your tests. If you test
     * using a local variable, and not this static variable below, the
     * autograder will not grade that test. If you would like to test
     * LinkedListDeques with types other than Integer (and you should),
     * you can define a new local variable. However, the autograder will
     * not grade that test. */

    public static Deque<Integer> lld = new LinkedListDeque<Integer>();

    @Test
    /** checks isEmpty() and size() */
    public void addIsEmptySizeTest() {
		assertTrue("A newly initialized LLDeque should be empty", lld.isEmpty());
		lld.addFirst(0);

        assertFalse("lld should now contain 1 item", lld.isEmpty());

        // Reset the linked list deque at the END of the test.
        lld = new LinkedListDeque<Integer>();
    }

    @Test
    public void addIsEmptySizeStringTest() {
        Deque<String> lld1 = new LinkedListDeque<>();

        assertTrue("A newly initialized LLDeque should be empty", lld1.isEmpty());
        lld1.addFirst("front");

        assertEquals(1, lld1.size());
        assertFalse("lld1 should now contain 1 item", lld1.isEmpty());

        lld1.addLast("middle");
        assertEquals(2, lld1.size());
        assertFalse("lld1 should now contain 2 item", lld1.isEmpty());

        lld1.addLast("back");
        assertEquals(3, lld1.size());
        assertFalse("lld1 should now contain 3 item", lld1.isEmpty());
    }

    @Test
    /** Adds an item, then removes an item, and ensures that dll is empty afterwards. */
    public void addRemoveTest() {
        // should be empty
        assertTrue("lld should be empty upon initialization", lld.isEmpty());

        lld.addFirst(10);
        assertFalse("lld should contain 1 item", lld.isEmpty());

        lld.removeFirst();
        assertTrue("lld should be empty after removal", lld.isEmpty());

        // Reset the linked list deque at the END of the test.
        lld = new LinkedListDeque<Integer>();
    }

    @Test
    /** Tests removing from an empty deque */
    public void removeEmptyTest() {
        lld.addFirst(3);

        lld.removeLast();
        lld.removeFirst();
        lld.removeLast();
        lld.removeFirst();

        int size = lld.size();
        assertEquals(0, size);

        // Reset the linked list deque at the END of the test.
        lld = new LinkedListDeque<Integer>();
    }


    @Test
    /* check if null is return when removing from an empty LinkedListDeque. */
    public void emptyNullReturnTest() {
        assertEquals("Should return null when removeFirst is called on an empty Deque,", null, lld.removeFirst());
        assertEquals("Should return null when removeLast is called on an empty Deque,", null, lld.removeLast());

        // Reset the linked list deque at the END of the test.
        lld = new LinkedListDeque<Integer>();
    }

    @Test
    /* check get() */
    public void getTest() {
        assertEquals("Should return null when get(0) from empty list", null, lld.get(0));
        assertEquals("Should return null when get(1) from empty list", null, lld.get(1));

        lld.addFirst(3);
        assertEquals(3, (int)lld.get(0));
        assertEquals("Should return null when get(1) from list:[3]", null, lld.get(1));
        assertEquals("Should return null when get(4) from list:[3]", null, lld.get(4));
        assertEquals("Should return null when get(-5) from list:[3]", null, lld.get(-5));

        lld.addFirst(7);
        lld.addFirst(18);
        lld.addLast(6);
        lld.addFirst(19);
        lld.addLast(44);
        lld.addFirst(2);
        // lld: [2, 19, 18, 7, 3, 6, 44]
        // lld.printDeque();
        assertEquals(2, (int)lld.get(0));
        assertEquals(19, (int)lld.get(1));
        assertEquals(18, (int)lld.get(2));
        assertEquals(7, (int)lld.get(3));
        assertEquals(3, (int)lld.get(4));
        assertEquals(6, (int)lld.get(5));
        assertEquals(44, (int)lld.get(6));
        assertEquals("Should return null when get(7) from list:[2, 19, 18, 7, 3, 6, 44]", null, lld.get(7));
        assertEquals("Should return null when get(100) from list:[2, 19, 18, 7, 3, 6, 44]", null, lld.get(100));
        assertEquals("Should return null when get(-5) from list:[2, 19, 18, 7, 3, 6, 44]", null, lld.get(-5));

        // Reset the linked list deque at the END of the test.
        lld = new LinkedListDeque<Integer>();
    }

    @Test
    /* check getRecursive() */
    public void getRecursiveTest() {
        assertEquals("Should return null when getRecursive(0) from empty list", null, lld.getRecursive(0));
        assertEquals("Should return null when getRecursive(1) from empty list", null, lld.getRecursive(1));

        lld.addFirst(3);
        assertEquals(3, (int)lld.getRecursive(0));
        assertEquals("Should return null when getRecursive(1) from list:[3]", null, lld.getRecursive(1));
        assertEquals("Should return null when getRecursive(4) from list:[3]", null, lld.getRecursive(4));
        assertEquals("Should return null when getRecursive(-5) from list:[3]", null, lld.getRecursive(-5));

        lld.addFirst(7);
        lld.addFirst(18);
        lld.addLast(6);
        lld.addFirst(19);
        lld.addLast(44);
        lld.addFirst(2);
        // lld: [2, 19, 18, 7, 3, 6, 44]
        assertEquals(2, (int)lld.getRecursive(0));
        assertEquals(19, (int)lld.getRecursive(1));
        assertEquals(18, (int)lld.getRecursive(2));
        assertEquals(7, (int)lld.getRecursive(3));
        assertEquals(3, (int)lld.getRecursive(4));
        assertEquals(6, (int)lld.getRecursive(5));
        assertEquals(44, (int)lld.getRecursive(6));
        assertEquals("Should return null when getRecursive(7) from list:[2, 19, 18, 7, 3, 6, 44]", null, lld.getRecursive(7));
        assertEquals("Should return null when getRecursive(100) from list:[2, 19, 18, 7, 3, 6, 44]", null, lld.getRecursive(100));
        assertEquals("Should return null when getRecursive(-5) from list:[2, 19, 18, 7, 3, 6, 44]", null, lld.getRecursive(-5));

        // Reset the linked list deque at the END of the test.
        lld = new LinkedListDeque<Integer>();
    }

    @Test
    public void equalsTest() {
        LinkedListDeque<Integer> test = new LinkedListDeque<>();
        assertTrue(lld.equals(test));

        ArrayDeque<Integer> ad = new ArrayDeque<>();
        assertTrue("We consider them equal as long as they're both deque " +
                "and their contents are equal", lld.equals(ad));

        String str = "";
        assertFalse("String is not a deque, cannot equal!", lld.equals(str));

        test.addFirst(1);
        lld.addFirst(1);
        assertTrue(lld.equals(test));
        assertFalse(lld.equals(ad));
        ad.addLast(1);
        assertTrue(lld.equals(ad));

        test.addFirst(1);
        assertFalse(lld.equals(test));

        lld.addLast(1);
        assertTrue(lld.equals(test));

        test.addFirst(2);
        test.addLast(4);
        lld.addFirst(4);
        lld.addLast(2);
        assertFalse(lld.equals(test));

        // Reset the linked list deque at the END of the test.
        lld = new LinkedListDeque<Integer>();
    }

}
