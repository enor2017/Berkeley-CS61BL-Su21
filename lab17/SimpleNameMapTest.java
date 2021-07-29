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
        assertEquals(2, m.size());
        assertNull(m.remove("Haha"));
        assertEquals(2, m.size());
        m.put("Lol", "ooh");
        assertEquals("ooh", m.get("Lol"));
        assertEquals("ooh", m.remove("Lol"));
        assertNull(m.remove("Lol"));
        assertEquals(1, m.size());
    }

    @Test
    public void collisionTest() {
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
        assertEquals(2, m.size());
        assertNull(m.remove("Haha"));
        assertEquals(2, m.size());

        m.put("Lol", "ooh");
        assertEquals("ooh", m.get("Lol"));
        assertEquals(2, m.size());

        m.put("Lol", "uuu");
        assertEquals("uuu", m.get("Lol"));
        assertEquals(2, m.size());

        m.put("Lily", "ppp");
        assertEquals("ppp", m.get("Lily"));
        assertEquals(3, m.size());

        assertEquals("uuu", m.remove("Lol"));
        assertEquals(2, m.size());

        assertEquals("ppp", m.remove("Lily"));
        assertEquals(1, m.size());
    }

    @Test
    public void resizeTest() {
        SimpleNameMap m = new SimpleNameMap();
        int correctSize = 10;
        for(int i = 0; i < 10000; ++i) {
            m.put("" + i, "haha" + i);
            assertEquals(i + 1, m.size());
            assertEquals("haha" + i, m.get("" + i));
            // update correctSize
            if((i + 1) * 1.0 / correctSize > 0.75) {
                correctSize *= 2;
            }
            assertEquals(correctSize, m.arraySize());
        }
    }
}
