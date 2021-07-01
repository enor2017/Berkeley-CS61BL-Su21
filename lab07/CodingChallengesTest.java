import org.junit.Test;

import java.nio.charset.CoderMalfunctionError;

import static org.junit.Assert.*;

public class CodingChallengesTest {

    @Test
    public void missingNumberTest() {
        int[] arr = new int[]{8, 1, 2, 4, 3, 5, 7, 9, 6};
        assertEquals(0, CodingChallenges.missingNumber(arr));

        arr = new int[]{8, 1, 2, 4, 3, 5, 7, 0, 6};
        assertEquals(9, CodingChallenges.missingNumber(arr));

        arr = new int[]{8, 1, 2, 4, 3, 5, 7, 9, 0};
        assertEquals(6, CodingChallenges.missingNumber(arr));

        arr = new int[]{0};
        assertEquals(1, CodingChallenges.missingNumber(arr));

        arr = new int[]{1};
        assertEquals(0, CodingChallenges.missingNumber(arr));

        arr = new int[]{0, 1, 2, 4, 5, 6};
        assertEquals(3, CodingChallenges.missingNumber(arr));

        // pressure test
        final int BIG_NUM = 5000000;
        arr = new int[BIG_NUM];
        for(int i = 0; i < BIG_NUM; ++i) {
            arr[i] = BIG_NUM - i;
        }
        assertEquals(0, CodingChallenges.missingNumber(arr));
    }

    @Test
    public void sumToTest() {
        int[] arr = new int[]{8, 1, 2, 4, 3, 5, 7, 9, 0};
        assertFalse(CodingChallenges.sumTo(arr, 0));
        assertTrue(CodingChallenges.sumTo(arr, 1));
        assertTrue(CodingChallenges.sumTo(arr, 2));
        assertTrue(CodingChallenges.sumTo(arr, 3));
        assertTrue(CodingChallenges.sumTo(arr, 9));
        assertFalse(CodingChallenges.sumTo(arr, 19));

        arr = new int[]{8, 1, 2, 4, 3, 5, 7, 9, 10};
        assertFalse(CodingChallenges.sumTo(arr, 0));
        assertFalse(CodingChallenges.sumTo(arr, 1));
        assertFalse(CodingChallenges.sumTo(arr, 2));
        assertTrue(CodingChallenges.sumTo(arr, 3));
        assertTrue(CodingChallenges.sumTo(arr, 9));
        assertFalse(CodingChallenges.sumTo(arr, 22));

        arr = new int[]{7, 4, -3, 2, -6};
        assertFalse(CodingChallenges.sumTo(arr, 0));
        assertTrue(CodingChallenges.sumTo(arr, 1));
        assertFalse(CodingChallenges.sumTo(arr, 2));
        assertTrue(CodingChallenges.sumTo(arr, -2));
        assertTrue(CodingChallenges.sumTo(arr, -9));
        assertFalse(CodingChallenges.sumTo(arr, 13));

        arr = new int[]{0};
        assertFalse(CodingChallenges.sumTo(arr, 0));
        assertFalse(CodingChallenges.sumTo(arr, 1));
        assertFalse(CodingChallenges.sumTo(arr, 2));

        arr = new int[]{45555555, 54444444};
        assertFalse(CodingChallenges.sumTo(arr, 0));
        assertFalse(CodingChallenges.sumTo(arr, 1));
        assertFalse(CodingChallenges.sumTo(arr, 45555555));
        assertFalse(CodingChallenges.sumTo(arr, 54444444));
        assertTrue(CodingChallenges.sumTo(arr, 99999999));
    }

    @Test
    public void isPermutationTest() {
        String s1 = "aabbccdd11223344AAQQ";
        String s2 = "qwertyuiop";
        String s3 = "abcdabcdaAQQ11223344";
        String s4 = "123123abcdabcdaaq4q4";
        String s5 = "12Q31423abcd4abcdAAQ";

        assertFalse(CodingChallenges.isPermutation(s1, s2));
        assertFalse(CodingChallenges.isPermutation(s1, s3));
        assertFalse(CodingChallenges.isPermutation(s1, s4));
        assertTrue(CodingChallenges.isPermutation(s1, s5));

        assertFalse(CodingChallenges.isPermutation("0", ""));
        assertTrue(CodingChallenges.isPermutation("", ""));
        assertFalse(CodingChallenges.isPermutation("A", "a"));
        assertTrue(CodingChallenges.isPermutation("aa", "aa"));
        assertFalse(CodingChallenges.isPermutation("aa", "ab"));
        assertFalse(CodingChallenges.isPermutation("aa", "aaa"));
    }
}