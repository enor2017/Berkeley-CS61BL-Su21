package gitlet;

import java.awt.geom.Line2D;
import java.io.Serializable;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import static gitlet.Utils.*;

/** Represents a gitlet commit object.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 *  @author enor2017
 */
public class Commit implements Serializable {

    // The message of this Commit.
    private String message;
    // The time of submitting commit
    private Date commitTime;
    // The SHA-1 hash value of parent commit, may have 2nd parent for branch
    private String parent, secondParent;
    // A linkedList to store files in current commit, store blob's hash values
    private LinkedList<String> hashOfBlobs;
    // a linkedList to store children, here Commit forms a tree Structure
    private LinkedList<String> children;

    // this constructor only applies to initial commit
    public Commit () {
        message = "initial commit";
        commitTime = new Date(0);
        parent = null;
        secondParent = null;
        hashOfBlobs = new LinkedList<>();
        children = new LinkedList<>();
    }

    // Constructor: create a commit object based on parent object
    // with given message, current time, and merged blobs linked list.
    public Commit(String parentCommit, String message, LinkedList<String> stage, LinkedList<String> rmStage) {
        this.message = message;
        this.commitTime = new Date();
        parent = parentCommit;
        children = new LinkedList<>();

        // merge parent's blob list and staging area to new commit's blob list
        hashOfBlobs = new LinkedList<>();
        LinkedList<String> parentCommitBlobs = findCommit(parentCommit).getHashOfBlobs();
        for(int i = 0; i < parentCommitBlobs.size(); ++i) {
            String currentFileName = findBlob(parentCommitBlobs.get(i)).getFilename();
            // if current filename doesn't exist in stage area and rmStage area, add it
            if(findFile(stage, currentFileName) == -1 && findFile(rmStage, currentFileName) == -1) {
                hashOfBlobs.add(parentCommitBlobs.get(i));
            }
        }
        // add items in stage area to this commit's blob list
        for(int i = 0; i < stage.size(); ++i) {
            hashOfBlobs.add(stage.get(i));
        }
    }

    // get blobs of current commit
    public LinkedList<String> getHashOfBlobs() {
        return hashOfBlobs;
    }

    // insert children to current commit
    public void insertChild(String child) {
        children.add(child);
    }

    // get parent of this commit
    public String getParent() {
        return parent;
    }

    // get commit message
    public String getMessage() {
        return message;
    }

    // get commit time
    public Date getCommitTime() {
        return commitTime;
    }
}
