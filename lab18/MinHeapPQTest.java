import org.junit.Test;
import static org.junit.Assert.*;

public class MinHeapPQTest {

    @Test
    public void verySimpleTest() {
        MinHeapPQ<Integer> pq = new MinHeapPQ<>();
        assertEquals(0, pq.size());
        assertNull(pq.peek());
        assertNull(pq.poll());

        pq.insert(42, 10.0);
        pq.insert(32, 20.0);
        pq.insert(52, 15.0);
        assertEquals(3, pq.size());
        assertEquals(42, (int) pq.peek());

        pq.changePriority(32, 28.0);
        assertEquals(3, pq.size());
        assertEquals(42, (int) pq.peek());

        pq.changePriority(32, 9.0);
        assertEquals(3, pq.size());
        assertEquals(32, (int) pq.peek());

        pq.changePriority(52, 1.89);
        assertEquals(3, pq.size());
        assertEquals(52, (int) pq.peek());

        pq.changePriority(52, 15);
        assertEquals(3, pq.size());
        assertEquals(32, (int) pq.peek());

        pq.changePriority(52, 2.34);
        assertEquals(3, pq.size());
        assertEquals(52, (int) pq.peek());

        assertEquals(52, (int) pq.poll());
        assertEquals(2, pq.size());
        assertEquals(32, (int) pq.poll());
        assertEquals(1, pq.size());
        assertEquals(42, (int) pq.poll());
        assertEquals(0, pq.size());
    }
}
