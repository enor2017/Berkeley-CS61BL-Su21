import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class MeasurementTest {
    @Test
    public void testConstructoGet() {
        Measurement m = new Measurement();
        Assert.assertEquals(0, m.getFeet());
        Assert.assertEquals(0, m.getInches());

        m = new Measurement(22);
        Assert.assertEquals(22, m.getFeet());
        Assert.assertEquals(0, m.getInches());

        m = new Measurement(22, 33);
        Assert.assertEquals(22, m.getFeet());
        Assert.assertEquals(33, m.getInches());
    }

    @Test
    public void testPlus() {
        Measurement m1 = new Measurement();
        Measurement m2 = new Measurement();
        Measurement res = m1.plus(m2);
        Assert.assertEquals(0, res.getFeet());
        Assert.assertEquals(0, res.getInches());

        m1 = new Measurement(1);
        m2 = new Measurement(2);
        res = m1.plus(m2);
        Assert.assertEquals(3, res.getFeet());
        Assert.assertEquals(0, res.getInches());

        m1 = new Measurement(1, 3);
        m2 = new Measurement(2, 4);
        res = m1.plus(m2);
        Assert.assertEquals(3, res.getFeet());
        Assert.assertEquals(7, res.getInches());

        m1 = new Measurement(1, 11);
        m2 = new Measurement(2, 9);
        res = m1.plus(m2);
        Assert.assertEquals(4, res.getFeet());
        Assert.assertEquals(8, res.getInches());

        m1 = new Measurement(0, 120);
        m2 = new Measurement(0, 2000);
        res = m1.plus(m2);
        Assert.assertEquals(176, res.getFeet());
        Assert.assertEquals(8, res.getInches());
    }

    @Test
    public void testMinus() {
        Measurement m1 = new Measurement();
        Measurement m2 = new Measurement();
        Measurement res = m1.minus(m2);
        Assert.assertEquals(0, res.getFeet());
        Assert.assertEquals(0, res.getInches());

        m1 = new Measurement(2);
        m2 = new Measurement(1);
        res = m1.minus(m2);
        Assert.assertEquals(1, res.getFeet());
        Assert.assertEquals(0, res.getInches());

        m1 = new Measurement(3, 4);
        m2 = new Measurement(2, 4);
        res = m1.minus(m2);
        Assert.assertEquals(1, res.getFeet());
        Assert.assertEquals(0, res.getInches());

        m1 = new Measurement(2, 9);
        m2 = new Measurement(1, 10);
        res = m1.minus(m2);
        Assert.assertEquals(0, res.getFeet());
        Assert.assertEquals(11, res.getInches());

        m1 = new Measurement(700, 2000);
        m2 = new Measurement(6, 3000);
        res = m1.minus(m2);
        Assert.assertEquals(610, res.getFeet());
        Assert.assertEquals(8, res.getInches());

        m1 = new Measurement(0, 0);
        m2 = new Measurement(0, 0);
        res = m1.minus(m2);
        Assert.assertEquals(0, res.getFeet());
        Assert.assertEquals(0, res.getInches());
    }

    @Test
    public void testMultiple() {
        Measurement m1 = new Measurement();
        Measurement res = m1.multiple(0);
        Assert.assertEquals(0, res.getFeet());
        Assert.assertEquals(0, res.getInches());

        m1 = new Measurement();
        res = m1.multiple(2);
        Assert.assertEquals(0, res.getFeet());
        Assert.assertEquals(0, res.getInches());

        m1 = new Measurement(2);
        res = m1.multiple(0);
        Assert.assertEquals(0, res.getFeet());
        Assert.assertEquals(0, res.getInches());

        m1 = new Measurement(2);
        res = m1.multiple(2);
        Assert.assertEquals(4, res.getFeet());
        Assert.assertEquals(0, res.getInches());

        m1 = new Measurement(3, 2);
        res = m1.multiple(3);
        Assert.assertEquals(9, res.getFeet());
        Assert.assertEquals(6, res.getInches());

        m1 = new Measurement(2, 0);
        res = m1.multiple(4);
        Assert.assertEquals(8, res.getFeet());
        Assert.assertEquals(0, res.getInches());

        m1 = new Measurement(4, 5);
        res = m1.multiple(4);
        Assert.assertEquals(17, res.getFeet());
        Assert.assertEquals(8, res.getInches());

        m1 = new Measurement(0, 100);
        res = m1.multiple(8);
        Assert.assertEquals(66, res.getFeet());
        Assert.assertEquals(8, res.getInches());

        m1 = new Measurement(34781, 593728);
        res = m1.multiple(142);
        Assert.assertEquals(4938902 + 7025781, res.getFeet());
        Assert.assertEquals(4, res.getInches());
    }

    @Test
    public void testToString(){
        Measurement m1 = new Measurement();
        Assert.assertEquals("0'0\"", m1.toString());

        m1 = new Measurement(0);
        Assert.assertEquals("0'0\"", m1.toString());

        m1 = new Measurement(0, 0);
        Assert.assertEquals("0'0\"", m1.toString());

        m1 = new Measurement(3);
        Assert.assertEquals("3'0\"", m1.toString());

        m1 = new Measurement(3, 5);
        Assert.assertEquals("3'5\"", m1.toString());

        m1 = new Measurement(0, 5);
        Assert.assertEquals("0'5\"", m1.toString());

        m1 = new Measurement(5, 5);
        Assert.assertEquals("5'5\"", m1.toString());
    }
}