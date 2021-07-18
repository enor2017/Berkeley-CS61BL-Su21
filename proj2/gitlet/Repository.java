package gitlet;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.LinkedList;

import static gitlet.Utils.*;

/** Represents a gitlet repository.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 *  @author enor2017
 */
public class Repository implements Serializable {

    /** The current working directory. */
    public static final File CWD = new File(System.getProperty("user.dir"));
    /** The .gitlet directory. */
    public static final File GITLET_DIR = join(CWD, ".gitlet");
    /** The directory for commit objects. */
    public static final File COMMIT_DIR = join(GITLET_DIR, "commits");
    /** The directory for blobs objects. */
    public static final File BLOB_DIR = join(GITLET_DIR, "blobs");
    /** The file for storing repo info */
    public static final File REPO_INFO = join(GITLET_DIR, "repoInfo");

    // The HEAD pointer, stores the hash value of a commit
    private String HEAD = "";
    // A linkedList to store staging files, each object is a Blob
    private LinkedList<Blob> stage;
    // A linkedList to store blobs
    private LinkedList<Blob> blobs;
    // A commit tree
    private CommitTree commitTree;

    // check if current folder is a repo, this is a public static method, used in Main
    public static boolean isRepo() {
        return GITLET_DIR.exists();
    }

    // init an empty repo
    public Repository() {
        HEAD = null;
        stage = null;
        blobs = null;
        commitTree = null;
    }

    // output the repo to the file
    private void writeRepoToFile () {
        writeObject(REPO_INFO, this);
    }

    // check if user input has correct number of operands
    public void checkOperand (String[] args, int num) {
        if (args.length != num) {
            System.out.println("Incorrect operands.");
            // exit with 0
            System.exit(0);
        }
    }

    public void init(String[] args) {
        // valid input
        checkOperand(args, 1);
        // if already a repo
        if (isRepo()) {
            System.out.println("A Gitlet version-control system already exists in the current directory.");
            return;
        }

        // make required directories
        GITLET_DIR.mkdir();
        COMMIT_DIR.mkdir();
        BLOB_DIR.mkdir();

        // create first commit, write commit to commits, update commit tree
        Commit firstCommit = new Commit();
        String hashVal = sha1obj(firstCommit);
        writeObject(join(COMMIT_DIR, hashVal), firstCommit);
        commitTree = new CommitTree(hashVal);

        // write repo info into file
        writeRepoToFile();
    }

}
