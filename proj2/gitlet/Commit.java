package gitlet;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import static gitlet.Utils.*;

/** Represents a gitlet commit object.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 *  @author enor2017
 */
public class Commit implements Serializable {
    /**
     * TODO: add instance variables here.
     *
     * List all instance variables of the Commit class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided one example for `message`.
     */

    // The message of this Commit.
    private String message;
    // The time of submitting commit
    private Date commitTime;
    // The SHA-1 hash value of parent commit
    private String parent;
    // A linkedList to store files in current commit, store blob's hash values
    private LinkedList<String> hashOfBlobs;

    // this constructor only applies to initial commit
    public Commit () {
        message = "initial commit";
        commitTime = new Date(0);
        parent = null;
        hashOfBlobs = null;
    }
}
