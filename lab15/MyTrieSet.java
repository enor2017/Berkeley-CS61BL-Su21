import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class MyTrieSet implements TrieSet61BL {

    private TrieNode root;

    private static class TrieNode {
        // no need to store value, which is coded in map.
        private boolean isLeaf;
        private HashMap<Character, TrieNode> map;

        public TrieNode() {
            this.isLeaf = false;
            this.map = new HashMap<>();
        }

        public TrieNode(boolean isLeaf) {
            this.isLeaf = isLeaf;
            this.map = new HashMap<>();
        }
    }

    public MyTrieSet() {
        root = new TrieNode();
    }

    @Override
    public void clear() {
        root = new TrieNode();
    }

    /**
     * helper function get():
     * given a String key, return the leaf node of that string
     * even though the string ends with a non-leaf node, still return it
     * if does not exist, return null
     */
    private TrieNode get(String key) {
        if(key == null || key.length() < 1) {
            return null;
        }
        TrieNode curr = root;
        int n = key.length();
        for(int i = 0; i < n; ++i) {
            // get i-th char of the given string
            // if doesn't exist node with value c, the given string DNE.
            char c = key.charAt(i);
            if(!curr.map.containsKey(c)) {
                return null;
            }
            // if exist, travel to the node with value c and continue.
            curr = curr.map.get(c);
        }
        // we're reaching the last node, return it, no matter if it's a leaf,
        return curr;
    }

    @Override
    public boolean contains(String key) {
        TrieNode result = get(key);
        // true, only if result exists, and it is a leaf node
        return result != null && result.isLeaf;
    }

    @Override
    public void add(String key) {
        if(key == null || key.length() < 1) {
            return;
        }
        TrieNode curr = root;
        int n = key.length();
        for(int i = 0; i < n; ++i) {
            char c = key.charAt(i);
            // insert new node with val c to curr.map if doesn't exist a node with val c
            if(!curr.map.containsKey(c)) {
                curr.map.put(c, new TrieNode(false));
            }
            // iterate to next node(or newly inserted node).
            curr = curr.map.get(c);
        }
        // set last node to leaf
        curr.isLeaf = true;
    }

    /**
     * helper function: collect strings with given prefix, start at x
     */
    private void collect(TrieNode x, String prefix, List<String> results) {
        if(x == null) {
            return;
        }
        // if current node is a leaf node, means we've found a valid string
        if(x.isLeaf) {
            results.add(prefix);
        }
        for(char c : x.map.keySet()) {
            // find (prefix + c) in depth + 1
            String newPrefix = prefix + c;
            collect(x.map.get(c), newPrefix, results);
        }
    }

    @Override
    public List<String> keysWithPrefix(String prefix) {
        TrieNode prefixNode = get(prefix);
        List<String> results = new LinkedList<>();
        collect(prefixNode, prefix, results);
        return results;
    }

    /**
     * returns the length of longest prefix of given key, from node x
     * The first (length) characters have already matched, and
     * the length of the longest prefix we've ever found is (maxLength)
     * return -1 (init value of maxLength) if no match
     */
    private int longestPrefixOf(TrieNode x, String key, int length, int maxLength) {
        // if out of the tree, return maxLength we've found
        if(x == null) {
            return maxLength;
        }
        // if is leaf, means we've found another match, update maxLength
        if(x.isLeaf) {
            maxLength = length;
        }
        // if the prefix we're processing is the same length of given key, end!
        if(length == key.length()) {
            return maxLength;
        }
        // try to match next character
        char c = key.charAt(length);
        return longestPrefixOf(x.map.get(c), key, length + 1, maxLength);
    }

    @Override
    public String longestPrefixOf(String key) {
        if(key == null) {
            return null;
        }
        // initialize maxLength = -1.
        int prefixMaxLength = longestPrefixOf(root, key, 0, -1);
        // if not found, return null
        if(prefixMaxLength == -1) {
            return null;
        } else {
            return key.substring(0, prefixMaxLength);
        }
    }
}
