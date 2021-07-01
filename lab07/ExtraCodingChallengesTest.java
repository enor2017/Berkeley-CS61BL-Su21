import org.junit.Test;

import static org.junit.Assert.*;

public class ExtraCodingChallengesTest {
    @Test
    public void nearestDuplicateTest() {
        String[] arr = new String[]{"all", "work", "and", "no", "play", "makes", "for",
        "no", "work", "no", "fun", "and", "no", "results"};
        assertEquals("no", ExtraCodingChallenges.nearestDuplicate(arr));

        arr = new String[]{};
        assertEquals(null, ExtraCodingChallenges.nearestDuplicate(arr));

        arr = new String[]{"aa"};
        assertEquals(null, ExtraCodingChallenges.nearestDuplicate(arr));

        arr = new String[]{"aa", "AA", "bb", "cc"};
        assertEquals(null, ExtraCodingChallenges.nearestDuplicate(arr));

        arr = new String[]{"aa", "bb", "bb"};
        assertEquals("bb", ExtraCodingChallenges.nearestDuplicate(arr));

        // multiple solutions, can equals to any of them
        arr = new String[]{"aa", "aa", "bb", "bb"};
        assertTrue(ExtraCodingChallenges.nearestDuplicate(arr) == "aa" ||
                ExtraCodingChallenges.nearestDuplicate(arr) == "bb");

        arr = new String[]{"aa", "bb", "cc", "bb", "aa", "aa"};
        assertEquals("aa", ExtraCodingChallenges.nearestDuplicate(arr));

        arr = new String[]{"aa", "bb", "cc", "aa", "dd", "ee", "dd"};
        assertEquals("dd", ExtraCodingChallenges.nearestDuplicate(arr));

        arr = new String[]{"aa", "bb", "cc", "aa", "dd", "ee", "dd", "aa", "aa"};
        assertEquals("aa", ExtraCodingChallenges.nearestDuplicate(arr));
    }
}
