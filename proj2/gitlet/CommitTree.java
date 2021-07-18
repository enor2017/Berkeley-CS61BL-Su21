package gitlet;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * A tree represents commit tree, only need to store the root node
 */
public class CommitTree implements Serializable {
    // A root
    private String root;

    public CommitTree () {
        root = null;
    }

    public CommitTree (String root) {
        this.root = root;
    }
}
