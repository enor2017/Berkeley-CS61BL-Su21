package deque;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Comparator;

public class MaxArrayDequeTest {
    private static class MinCompare implements Comparator<Integer> {
        /**
         * Reverse comparator: if a > b, then a LESS THAN b in this situation.
         */
        @Override
        public int compare(Integer a, Integer b) {
            return b - a;
        }
    }

    private static class MaxCompare implements Comparator<Integer> {
        /**
         * Normal comparator: if a > b, then a Greater than b.
         */
        @Override
        public int compare(Integer a, Integer b) {
            return a - b;
        }
    }

    private static class DistToFiveCompare implements Comparator<Integer> {
        /**
         * helper function: return absolute value
         */
        private int getAbs(Integer a) {
            return (a < 0) ? (-a) : a;
        }

        /**
         * a greater than b if a is more distant to 5
         */
        @Override
        public int compare(Integer a, Integer b) {
            return getAbs(a - 5) - getAbs(b - 5);
        }
    }

    Comparator<Integer> minComp = new MinCompare();
    Comparator<Integer> maxComp = new MaxCompare();
    Comparator<Integer> fiveComp = new DistToFiveCompare();

    @Test
    public void maxCompTest() {
        MaxArrayDeque<Integer> d = new MaxArrayDeque<>(maxComp);

        assertEquals(null, d.max());

        d.addFirst(9);
        assertEquals(9, (int)d.max());

        d.addFirst(3);
        assertEquals(9, (int)d.max());

        d.addFirst(0);
        assertEquals(9, (int)d.max());

        d.addFirst(12);
        assertEquals(12, (int)d.max());

        d.removeFirst();
        assertEquals(9, (int)d.max());
    }

    @Test
    public void minCompTest() {
        MaxArrayDeque<Integer> d = new MaxArrayDeque<>(minComp);

        assertEquals(null, d.max());

        d.addFirst(9);
        assertEquals(9, (int)d.max());

        d.addFirst(3);
        assertEquals(3, (int)d.max());

        d.addFirst(0);
        assertEquals(0, (int)d.max());

        d.addFirst(12);
        assertEquals(0, (int)d.max());

        d.removeFirst();
        assertEquals(0, (int)d.max());

        d.removeFirst();
        assertEquals(3, (int)d.max());
    }

    @Test
    public void distToFiveCompTest() {
        MaxArrayDeque<Integer> d = new MaxArrayDeque<>(fiveComp);

        assertEquals(null, d.max());

        d.addFirst(9);
        assertEquals(9, (int)d.max());

        d.addFirst(3);
        assertEquals(9, (int)d.max());

        d.addFirst(0);
        assertEquals(0, (int)d.max());

        d.addFirst(5);
        assertEquals(0, (int)d.max());

        d.addFirst(6);
        assertEquals(0, (int)d.max());

        d.addFirst(100);
        assertEquals(100, (int)d.max());

        d.addFirst(-100);
        assertEquals(-100, (int)d.max());

        d = new MaxArrayDeque<>(fiveComp);

        d.addFirst(4);
        d.addFirst(5);
        d.addFirst(6);
        assertTrue(d.max() == 4 || d.max() == 6);

        d.removeLast();
        assertEquals(6, (int)d.max());
    }

    @Test
    public void changeCompTest() {
        MaxArrayDeque<Integer> d = new MaxArrayDeque<>(maxComp);

        d.addFirst(0);
        d.addFirst(2);
        d.addFirst(5);
        d.addFirst(11);

        assertEquals(11, (int)d.max());
        assertEquals(11, (int)d.max(maxComp));
        assertEquals(0, (int)d.max(minComp));
        assertEquals(11, (int)d.max(fiveComp));

        d.removeLast();
        d.removeFirst();
        assertEquals(5, (int)d.max());
        assertEquals(5, (int)d.max(maxComp));
        assertEquals(2, (int)d.max(minComp));
        assertEquals(2, (int)d.max(fiveComp));
    }
}
