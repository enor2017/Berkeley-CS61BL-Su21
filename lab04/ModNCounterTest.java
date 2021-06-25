import org.junit.Test;

import static org.junit.Assert.*;

public class ModNCounterTest {

    @Test
    public void testConstructor() {
        ModNCounter c = new ModNCounter(1);
        assertEquals(0, c.value());

        c = new ModNCounter(2);
        assertEquals(0, c.value());

        c = new ModNCounter(3);
        assertEquals(0, c.value());

        c = new ModNCounter(4);
        assertEquals(0, c.value());
    }

    @Test
    public void testIncrement() throws Exception {
        ModNCounter c = new ModNCounter(4);
        c.increment();
        assertEquals(1, c.value());
        c.increment();
        assertEquals(2, c.value());
        c.increment();
        assertEquals(3, c.value());
        c.increment();
        assertEquals(0, c.value());
        c.increment();
        assertEquals(1, c.value());
        c.increment();
        assertEquals(2, c.value());
        c.increment();
        assertEquals(3, c.value());
        c.increment();
        assertEquals(0, c.value());

        c = new ModNCounter(2);
        c.increment();
        assertEquals(1, c.value());
        c.increment();
        assertEquals(0, c.value());
        c.increment();
        assertEquals(1, c.value());
        c.increment();
        assertEquals(0, c.value());

        c = new ModNCounter(1);
        c.increment();
        assertEquals(0, c.value());
        c.increment();
        assertEquals(0, c.value());
    }

    @Test
    public void testReset() throws Exception {
        ModNCounter c = new ModNCounter(2);
        c.increment();
        c.reset();
        assertEquals(0, c.value());

        ModNCounter c2 = new ModNCounter(3);
        c2.increment();
        c2.reset();
        assertEquals(0, c2.value());
    }
}