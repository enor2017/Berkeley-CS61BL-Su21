import org.junit.Test;
import static org.junit.Assert.*;

public class SLListTest {

    @Test
    public void testSLListAdd() {
        SLList test1 = SLList.of(1, 3, 5);
        SLList test2 = new SLList();

        test1.add(1, 2);
        test1.add(3, 4);
        assertEquals(5, test1.size());
        assertEquals(3, test1.get(2));
        assertEquals(4, test1.get(3));

        test2.add(1, 1);
        assertEquals(1, test2.get(0));
        assertEquals(1, test2.size());

        test2.add(10, 10);
        assertEquals(10, test2.get(1));
        test1.add(0, 0);
        assertEquals(SLList.of(0, 1, 2, 3, 4, 5), test1);
    }

    @Test
    public void testSLListReverse() {
        SLList test1 = SLList.of(1, 3, 5);
        test1.reverse();
        SLList test2 = new SLList();
        test2.reverse();
        SLList test3 = new SLList(3);
        test3.reverse();
        SLList test4 = SLList.of(7, 9, 13, 10);
        test4.reverse();

        assertEquals("Wrong result while SLList is empty","", test2.toString());
        assertEquals(0, test2.size());

        assertEquals("Wrong result while SLList has one item", "3", test3.toString());
        assertEquals(1, test3.size());

        assertEquals("5 3 1", test1.toString());
        assertEquals(3, test1.size());

        assertEquals("10 13 9 7", test4.toString());
        assertEquals(4, test4.size());
    }
}
