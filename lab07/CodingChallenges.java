import java.util.*;

public class CodingChallenges {

    /**
     * Return the missing number from an array of length N containing all the
     * values from 0 to N except for one missing number.
     */
    public static int missingNumber(int[] values) {
        Set<Integer> set = new HashSet<>();
        int n = values.length;
        for(int i = 0; i < n; ++i) {
            set.add(values[i]);
        }
        for(int i = 0; i <= n; ++i) {
            if(!set.contains(i)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Returns true if and only if two integers in the array sum up to n.
     * Assume all values in the array are unique.
     */
    public static boolean sumTo(int[] values, int n) {
        Set<Integer> set = new HashSet<>();
        for(int i = 0; i < values.length; ++i) {
            set.add(values[i]);
        }
        for(int i = 0; i < values.length; ++i) {
            // notice that we can't find itself
            if((values[i] != (n - values[i])) && set.contains(n - values[i])) {
                return true;
            }
        }
        return false;
    }

    /**
     * helper function: return a map where keys are characters
     * and values are the times that each character appears
     * @param :a string
     * @return: a map
     */
    private static Map<Character, Integer> getStringMap(String s) {
        Map<Character, Integer> map = new HashMap<>();
        for(int i = 0; i < s.length(); ++i) {
            char currentChar = s.charAt(i);
            // if map doesn't contains the key, put a new key pair
            // else, get old value and update with +1
            if(!map.containsKey(currentChar)) {
                map.put(currentChar, 1);
            } else {
                map.put(currentChar, map.get(currentChar) + 1);
            }
        }
        return map;
    }

    /**
     * Returns true if and only if s1 is a permutation of s2. s1 is a
     * permutation of s2 if it has the same number of each character as s2.
     */
    public static boolean isPermutation(String s1, String s2) {
        Map<Character, Integer> map1 = CodingChallenges.getStringMap(s1);
        Map<Character, Integer> map2 = CodingChallenges.getStringMap(s2);

        return map1.equals(map2);
    }
}