package deque;

import org.junit.Test;

import static org.junit.Assert.*;

/* Performs some basic array deque tests. */
public class ArrayDequeTest {

    /** You MUST use the variable below for all of your tests. If you test
     * using a local variable, and not this static variable below, the
     * autograder will not grade that test. If you would like to test
     * ArrayDeques with types other than Integer (and you should),
     * you can define a new local variable. However, the autograder will
     * not grade that test. */

    public static Deque<Integer> ad = new ArrayDeque<Integer>();
    
    @Test
    /** checks isEmpty() and size() */
    public void addIsEmptySizeTest() {
        assertTrue("A newly initialized adeque should be empty", ad.isEmpty());
        ad.addFirst(0);

        assertFalse("ad should now contain 1 item", ad.isEmpty());

        // Reset the linked list deque at the END of the test.
        ad = new LinkedListDeque<Integer>();
    }

    @Test
    public void addIsEmptySizeStringTest() {
        Deque<String> ad1 = new ArrayDeque<>();

        assertTrue("A newly initialized adeque should be empty", ad1.isEmpty());
        ad1.addFirst("front");

        assertEquals(1, ad1.size());
        assertFalse("ad1 should now contain 1 item", ad1.isEmpty());

        ad1.addLast("middle");
        assertEquals(2, ad1.size());
        assertFalse("ad1 should now contain 2 item", ad1.isEmpty());

        ad1.addLast("back");
        assertEquals(3, ad1.size());
        assertFalse("ad1 should now contain 3 item", ad1.isEmpty());
    }

    @Test
    /** Adds an item, then removes an item, and ensures that dll is empty afterwards. */
    public void addRemoveTest() {
        // should be empty
        assertTrue("ad should be empty upon initialization", ad.isEmpty());

        ad.addFirst(10);
        assertFalse("ad should contain 1 item", ad.isEmpty());

        ad.removeFirst();
        assertTrue("ad should be empty after removal", ad.isEmpty());

        // Reset the linked list deque at the END of the test.
        ad = new LinkedListDeque<Integer>();
    }

    @Test
    /** Tests removing from an empty deque */
    public void removeEmptyTest() {
        ad.addFirst(3);

        ad.removeLast();
        ad.removeFirst();
        ad.removeLast();
        ad.removeFirst();

        int size = ad.size();
        assertEquals(0, size);

        // Reset the linked list deque at the END of the test.
        ad = new LinkedListDeque<Integer>();
    }


    @Test
    /* check if null is return when removing from an empty LinkedListDeque. */
    public void emptyNullReturnTest() {
        assertEquals("Should return null when removeFirst is called on an empty Deque,", null, ad.removeFirst());
        assertEquals("Should return null when removeLast is called on an empty Deque,", null, ad.removeLast());

        // Reset the linked list deque at the END of the test.
        ad = new LinkedListDeque<Integer>();
    }

    @Test
    /* check get() */
    public void getTest() {
        assertEquals("Should return null when get(0) from empty list", null, ad.get(0));
        assertEquals("Should return null when get(1) from empty list", null, ad.get(1));

        ad.addFirst(3);
        assertEquals(3, (int)ad.get(0));
        assertEquals("Should return null when get(1) from list:[3]", null, ad.get(1));
        assertEquals("Should return null when get(4) from list:[3]", null, ad.get(4));
        assertEquals("Should return null when get(-5) from list:[3]", null, ad.get(-5));

        ad.addFirst(7);
        ad.addFirst(18);
        ad.addLast(6);
        ad.addFirst(19);
        ad.addLast(44);
        ad.addFirst(2);
        // ad: [2, 19, 18, 7, 3, 6, 44]
        // ad.printDeque();
        assertEquals(2, (int)ad.get(0));
        assertEquals(19, (int)ad.get(1));
        assertEquals(18, (int)ad.get(2));
        assertEquals(7, (int)ad.get(3));
        assertEquals(3, (int)ad.get(4));
        assertEquals(6, (int)ad.get(5));
        assertEquals(44, (int)ad.get(6));
        assertEquals("Should return null when get(7) from list:[2, 19, 18, 7, 3, 6, 44]", null, ad.get(7));
        assertEquals("Should return null when get(100) from list:[2, 19, 18, 7, 3, 6, 44]", null, ad.get(100));
        assertEquals("Should return null when get(-5) from list:[2, 19, 18, 7, 3, 6, 44]", null, ad.get(-5));

        // Reset the linked list deque at the END of the test.
        ad = new LinkedListDeque<Integer>();
    }

    @Test
    public void resizeTest() {
        assertEquals("Should return null when removeFirst is called on an empty Deque,", null, ad.removeFirst());
        assertEquals("Should return null when removeLast is called on an empty Deque,", null, ad.removeLast());
        for(int i = 0; i < 10000; ++i) {
            ad.addFirst(i);
            ad.addLast(20000 + i);
        }
        assertEquals(20000, ad.size());

        int countSize = 20000;
        for(int i = 0; i < 10000; ++i) {
            ad.removeFirst();
            assertEquals(--countSize, ad.size());
            ad.removeLast();
            assertEquals(--countSize, ad.size());
        }

        ad.removeFirst();
        assertEquals(0, ad.size());
        ad.removeLast();
        assertEquals(0, ad.size());


        // Reset the linked list deque at the END of the test.
        ad = new LinkedListDeque<Integer>();
    }

    @Test
    public void equalsTest() {
        ArrayDeque<Integer> test = new ArrayDeque<>();
        assertTrue(ad.equals(test));

        LinkedListDeque<Integer> lld = new LinkedListDeque<>();
        assertTrue("We consider them equal as long as they're both deque " +
                "and their contents are equal", ad.equals(lld));

        String str = "";
        assertFalse("String is not a deque, cannot equal!", ad.equals(str));

        test.addFirst(1);
        ad.addFirst(1);
        assertTrue(ad.equals(test));
        assertFalse(ad.equals(lld));
        lld.addLast(1);
        assertTrue(ad.equals(lld));

        test.addFirst(1);
        assertFalse(ad.equals(test));

        ad.addLast(1);
        assertTrue(ad.equals(test));

        test.addFirst(2);
        test.addLast(4);
        ad.addFirst(4);
        ad.addLast(2);
        assertFalse(ad.equals(test));

        // Reset the linked list deque at the END of the test.
        ad = new LinkedListDeque<Integer>();
    }
}
