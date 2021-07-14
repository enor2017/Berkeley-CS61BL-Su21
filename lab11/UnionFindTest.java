import org.junit.Test;

import static org.junit.Assert.*;

public class UnionFindTest {

    @Test
    public void initParentTest() {
        UnionFind u = new UnionFind(10);
        for(int i = 0; i < 10; ++i) {
            assertEquals(-1, u.parent(i));
        }
        // try out of bound
        try {
            assertEquals(-1, u.parent(10));
        } catch (IllegalArgumentException e) {

        }
    }

    @Test
    public void initSizeOfTest() {
        UnionFind u = new UnionFind(10);
        for(int i = 0; i < 10; ++i) {
            assertEquals(1, u.sizeOf(i));
        }
        // try out of bound
        try {
            assertEquals(-1, u.sizeOf(10));
        } catch (IllegalArgumentException e) {

        }
    }

    @Test
    public void UnionFindTest() {
        UnionFind u = new UnionFind(10);
        for(int i = 0; i < 10; ++i) {
            assertEquals("after initialization, parent should be itself", i, u.find(i));
        }

        u.union(1, 1);
        for(int i = 0; i < 10; ++i) {
            assertEquals("after union 1 to 1, parents should not change", i, u.find(i));
        }

        u.union(3, 3);
        for(int i = 0; i < 10; ++i) {
            assertEquals("after union 3 to 3, parents should not change", i, u.find(i));
        }

        u.union(1, 2);
        for(int i = 0; i < 10; ++i) {
            assertEquals((i == 1) ? 2 : i, u.find(i));
            assertEquals((i == 1 || i == 2) ? 2 : 1, u.sizeOf(i));
        }

        u.union(1, 2);
        for(int i = 0; i < 10; ++i) {
            assertEquals((i == 1) ? 2 : i, u.find(i));
            assertEquals((i == 1 || i == 2) ? 2 : 1, u.sizeOf(i));
        }

        u.union(1, 3);
        for(int i = 0; i < 10; ++i) {
            assertEquals((i == 1 || i == 3) ? 2 : i, u.find(i));
            assertEquals((i >= 1 && i <= 3) ? 3 : 1, u.sizeOf(i));
        }

        u.union(4, 5);
        u.union(4, 6);
        u.union(5, 6);
        u.union(6, 5);
        u.union(7, 8);

        // {0} {1, 2, 3} {4, 5, 6} {7, 8} {9}

        for(int i = 0; i < 10; ++i) {
            if (i >= 1 && i <= 3) {
                assertEquals(2, u.find(i));
                assertEquals(3, u.sizeOf(i));
            } else if (i >= 4 && i <= 6) {
                assertEquals(5, u.find(i));
                assertEquals(3, u.sizeOf(i));
            } else if (i == 7 || i == 8) {
                assertEquals(8, u.find(i));
                assertEquals(2, u.sizeOf(i));
            } else {
                assertEquals(i, u.find(i));
                assertEquals(1, u.sizeOf(i));
            }
        }

        // test connected()
        assertTrue(u.connected(1, 2));
        assertTrue(u.connected(1, 3));
        assertTrue(u.connected(2, 3));

        assertFalse(u.connected(0, 2));
        assertTrue(u.connected(2, 2));
        assertFalse(u.connected(0, 1));

        assertFalse(u.connected(1, 4));
        assertTrue(u.connected(4, 5));
        assertTrue(u.connected(5, 6));
        assertTrue(u.connected(4, 6));
        assertFalse(u.connected(7, 4));
        assertFalse(u.connected(7, 2));
        assertFalse(u.connected(8, 5));
        assertFalse(u.connected(8, 2));
        assertTrue(u.connected(7, 8));
        assertTrue(u.connected(8, 7));

        u.union(0, 1);
        u.union(0, 0);
        u.union(0, 7);

        // {0, 1, 2, 3, 7, 8} {4, 5, 6} {9}

        for(int i = 0; i < 10; ++i) {
            if ((i >= 0 && i <= 3) || (i == 7) || (i == 8)) {
                assertEquals(2, u.find(i));
                assertEquals(6, u.sizeOf(i));
            } else if (i >= 4 && i <= 6) {
                assertEquals(5, u.find(i));
                assertEquals(3, u.sizeOf(i));
            } else {
                assertEquals(i, u.find(i));
                assertEquals(1, u.sizeOf(i));
            }
        }

        assertTrue(u.connected(1, 2));
        assertTrue(u.connected(1, 3));
        assertTrue(u.connected(0, 1));
        assertTrue(u.connected(0, 2));
        assertTrue(u.connected(0, 7));
        assertTrue(u.connected(0, 8));
        assertTrue(u.connected(2, 8));
        assertTrue(u.connected(8, 7));
        assertTrue(u.connected(7, 3));

        assertFalse(u.connected(0, 4));
        assertFalse(u.connected(4, 7));
        assertFalse(u.connected(5, 3));
        assertFalse(u.connected(5, 8));
        assertFalse(u.connected(5, 9));

    }
}