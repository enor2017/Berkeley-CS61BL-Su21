package gitlet;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * A tree represents commit tree, each elements is SHA-1 hash value of a commit
 */
public class CommitTree implements Serializable {
    // A root
    private String root;
    // Use a linkedList to store children's hash value
    private LinkedList<String> children;

    public CommitTree () {
        root = null;
        children = new LinkedList<>();
    }

    public CommitTree (String root) {
        this.root = root;
        children = new LinkedList<>();
    }

    public void insertChild (String commit) {
        if (commit == null) {
            return;
        }
        children.add(commit);
    }
}
