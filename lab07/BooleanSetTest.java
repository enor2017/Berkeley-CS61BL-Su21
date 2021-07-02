import org.junit.Test;
import static org.junit.Assert.*;

public class BooleanSetTest {

    @Test
    public void testBasics() {
        BooleanSet aSet = new BooleanSet(100);
        assertEquals(0, aSet.size());
        for (int i = 0; i < 100; i += 2) {
            aSet.add(i);
            assertTrue(aSet.contains(i));
        }
        assertEquals(50, aSet.size());

        // add 0 ~ 99 again, size should still be 50
        for (int i = 0; i < 100; i += 2) {
            aSet.add(i);
            assertTrue(aSet.contains(i));
        }
        assertEquals(50, aSet.size());

        for (int i = 0; i < 100; i += 2) {
            aSet.remove(i);
            assertFalse(aSet.contains(i));
        }
        assertTrue(aSet.isEmpty());
        assertEquals(0, aSet.size());

        // remove again, should still be empty
        for (int i = 0; i < 100; i += 2) {
            aSet.remove(i);
            assertFalse(aSet.contains(i));
        }
        assertTrue(aSet.isEmpty());
        assertEquals(0, aSet.size());
    }

    @Test
    public void testToIntArray() {
        BooleanSet aSet = new BooleanSet(100);
        assertArrayEquals(new int[]{}, aSet.toIntArray());

        aSet.add(1);
        assertArrayEquals(new int[]{1}, aSet.toIntArray());

        aSet.add(1);
        assertArrayEquals(new int[]{1}, aSet.toIntArray());

        aSet.add(2);
        assertArrayEquals(new int[]{1, 2}, aSet.toIntArray());

        aSet.add(3);
        assertArrayEquals(new int[]{1, 2, 3}, aSet.toIntArray());

        aSet.add(1);
        assertArrayEquals(new int[]{1, 2, 3}, aSet.toIntArray());

        aSet.add(2);
        assertArrayEquals(new int[]{1, 2, 3}, aSet.toIntArray());

        aSet.add(3);
        assertArrayEquals(new int[]{1, 2, 3}, aSet.toIntArray());

        aSet.remove(1);
        assertArrayEquals(new int[]{2, 3}, aSet.toIntArray());

        aSet.remove(1);
        assertArrayEquals(new int[]{2, 3}, aSet.toIntArray());
    }
}