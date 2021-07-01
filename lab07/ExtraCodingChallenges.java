import java.util.HashMap;
import java.util.Map;

public class ExtraCodingChallenges {

    /**
     * Note this problem is not required for the lab, and will not be tested 
     * on Gradescope.
     * 
     * Return the string with the index closest to its duplicate in the array.
     * If there are no duplicates in the array, return null.
     * Given the strings,
     *
     *     "all", "work", "and", "no", "play", "makes", "for",
     *     "no", "work", "no", "fun", "and", "no", "results"
     *
     * nearestDuplicate would return "no" as the second and third "no" are the
     * closest duplicates based on their positions in the array. If there is a tie, 
     * i.e. there are two strings equally close, you may return either. 
     */
    public static String nearestDuplicate(String[] words) {
        int min = Integer.MAX_VALUE;
        String result = null;
        Map<String, Integer> map = new HashMap<>();
        for(int i = 0; i < words.length; ++i) {
            // if not contains current word, insert into map
            // if contains, check distance and update to new position
            if(!map.containsKey(words[i])) {
                map.put(words[i], i);
            } else {
                int lastPos = map.get(words[i]);
                if(i - lastPos < min) {
                    min = i - lastPos;
                    result = words[i];
                }
                map.put(words[i], i);
            }
        }
        return result;
    }
}