import org.junit.Test;
import static org.junit.Assert.*;

public class MinHeapTest {

    @Test
    public void basicTest() {
        MinHeap<Integer> h = new MinHeap<>();
        assertFalse(h.contains(1));
        assertFalse(h.contains(0));
        assertNull(h.removeMin());
        assertNull(h.findMin());
        assertEquals(0, h.size());

        h.insert(4);
        assertTrue(h.contains(4));
        assertEquals(4, (int) h.findMin());
        assertEquals(1, h.size());
        assertEquals(4, (int) h.removeMin());

        // throw exception for duplicate item
        h.insert(4);
        try {
            h.insert(4);
        } catch (IllegalArgumentException e) {}

        h.insert(5);
        h.insert(6);
        assertEquals(4, (int) h.findMin());
        assertEquals(3, h.size());

        h.insert(3);
        assertEquals(3, (int) h.findMin());
        assertEquals("\n" +
                "        6\n" +
                "    /\n" +
                "3\n" +
                "    \\\n" +
                "        4\n" +
                "            \\\n" +
                "                5\n", h.toString());

        h.insert(2);
        assertEquals(2, (int) h.findMin());
        assertEquals("\n" +
                "        6\n" +
                "    /\n" +
                "2\n" +
                "    \\\n" +
                "                4\n" +
                "            /\n" +
                "        3\n" +
                "            \\\n" +
                "                5\n", h.toString());

        h.insert(1);
        assertEquals(1, (int) h.findMin());
        assertEquals("\n" +
                "        2\n" +
                "            \\\n" +
                "                6\n" +
                "    /\n" +
                "1\n" +
                "    \\\n" +
                "                4\n" +
                "            /\n" +
                "        3\n" +
                "            \\\n" +
                "                5\n", h.toString());

        assertEquals(1, (int) h.removeMin());
        assertEquals("\n" +
                "        6\n" +
                "    /\n" +
                "2\n" +
                "    \\\n" +
                "                4\n" +
                "            /\n" +
                "        3\n" +
                "            \\\n" +
                "                5\n", h.toString());

        assertEquals(2, (int) h.removeMin());
        assertEquals("\n" +
                "        6\n" +
                "    /\n" +
                "3\n" +
                "    \\\n" +
                "        4\n" +
                "            \\\n" +
                "                5\n", h.toString());

        // add lots of elements
        h = new MinHeap<>();
        for(int i = 20; i > 0; --i) {
            h.insert(i);
            assertEquals(21 - i, h.size());
            assertEquals(i, (int) h.findMin());
        }
        assertEquals("\n" +
                "                        9\n" +
                "                    /\n" +
                "                8\n" +
                "                    \\\n" +
                "                        16\n" +
                "            /\n" +
                "        7\n" +
                "            \\\n" +
                "                        15\n" +
                "                    /\n" +
                "                10\n" +
                "                    \\\n" +
                "                        19\n" +
                "    /\n" +
                "1\n" +
                "    \\\n" +
                "                        13\n" +
                "                    /\n" +
                "                3\n" +
                "                    \\\n" +
                "                        12\n" +
                "                            \\\n" +
                "                                18\n" +
                "            /\n" +
                "        2\n" +
                "            \\\n" +
                "                                6\n" +
                "                            /\n" +
                "                        5\n" +
                "                            \\\n" +
                "                                17\n" +
                "                    /\n" +
                "                4\n" +
                "                    \\\n" +
                "                                14\n" +
                "                            /\n" +
                "                        11\n" +
                "                            \\\n" +
                "                                20\n", h.toString());
    }
}
