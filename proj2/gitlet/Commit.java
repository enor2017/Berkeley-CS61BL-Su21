package gitlet;

import java.awt.geom.Line2D;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
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
    // A hashMap to store files in current commit, (Blob hash value) -> (Blob fileName)
    private HashMap<String, String> trackedBlobs;
    // a linkedList to store children, here Commit forms a tree Structure
    private LinkedList<String> children;
    // a String represent which branch it in
    private String branch;

    // this constructor only applies to initial commit
    public Commit () {
        message = "initial commit";
        commitTime = new Date(0);
        parent = null;
        secondParent = null;
        trackedBlobs = new HashMap<>();
        children = new LinkedList<>();
        branch = "master";
    }

    // Constructor: create a commit object based on parent object
    // with given message, current time, and merged blobs linked list.
    public Commit(String parentCommit, String message, HashMap<String, String> stage,
                  HashMap<String, String> rmStage, String branch) {
        this.message = message;
        this.commitTime = new Date();
        parent = parentCommit;
        children = new LinkedList<>();
        this.branch = branch;

        // merge parent's blob list and staging area to new commit's blob list
        trackedBlobs = new HashMap<>();
        HashMap<String, String> parentCommitBlobs = findCommit(parentCommit).getTrackedBlobs();
        for(String parentBlobHash : parentCommitBlobs.keySet()) {
            Blob currentBlob = findBlob(parentBlobHash);
            String currentFileName = currentBlob.getFilename();
            // if current filename doesn't exist in stage area and rmStage area, add it
            if(!stage.containsValue(currentFileName) && !rmStage.containsValue(currentFileName)) {
                trackedBlobs.put(parentBlobHash, currentFileName);
            }
        }
        // add items in stage area to this commit's blob list
        for(String toAdd : stage.keySet()) {
            Blob currentBlob = findBlob(toAdd);
            trackedBlobs.put(toAdd, currentBlob.getFilename());
        }
    }

    // get blobs of current commit
    public HashMap<String, String> getTrackedBlobs() {
        return trackedBlobs;
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

    // get branch
    public String getBranch() {
        return branch;
    }
}
