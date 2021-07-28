import org.junit.Test;

import static org.junit.Assert.*;

public class SimpleNameMapTest {
    @Test
    public void simpleTest() {
        SimpleNameMap m = new SimpleNameMap();
        assertFalse(m.containsKey("Haha"));

        assertNull(m.get("Haha"));
        assertNull(m.remove("Haha"));
        assertEquals(0, m.size());

        m.put("Haha", "hehe");
        m.put("Lol", "hehe");
        m.put("Oops", "lol");
        assertEquals("hehe", m.get("Haha"));
        assertEquals("hehe", m.get("Lol"));
        assertEquals("lol", m.get("Oops"));
        assertEquals(3, m.size());
        assertEquals("hehe", m.remove("Haha"));
        assertNull(m.remove("Haha"));
        m.put("Lol", "ooh");
        assertEquals("ooh", m.get("Lol"));
        assertEquals("ooh", m.remove("Lol"));
        assertNull(m.remove("Lol"));
        assertEquals(1, m.size());
    }
}
