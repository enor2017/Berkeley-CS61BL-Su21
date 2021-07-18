package gitlet;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    // A linkedList to store staging files, each object is a hash value of a Blob
    private LinkedList<String> stage;
    // A linkedList to store REMOVED staging files
    private LinkedList<String> rmStage;
    // A commit tree
    private CommitTree commitTree;

    // check if current folder is a repo, this is a public static method, used in Main
    public static boolean isRepo() {
        return GITLET_DIR.exists();
    }

    // init an empty repo
    public Repository() {
        HEAD = null;
        stage = new LinkedList<>();
        rmStage = new LinkedList<>();
        commitTree = new CommitTree();
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
        // update head
        HEAD = hashVal;

        // write repo info into file
        writeRepoToFile();
    }

    /**
     * helper function:
     * return Commit object given its hash value, return null if cannot find
     * @param "SHA-1 hash value of a commit"
     */
    public static Commit findCommit(String hash) {
        File toFind = join(COMMIT_DIR, hash);
        // if file does not exist
        if(!toFind.exists()) {
            return null;
        }

        return readObject(toFind, Commit.class);
    }

    /**
     * helper function:
     * return blob object given its hash value, return null if cannot find
     * @param "SHA-1 hash value of a blob"
     */
    public static Blob findBlob(String hash) {
        File toFind = join(BLOB_DIR, hash);
        // if file does not exist
        if(!toFind.exists()) {
            return null;
        }

        return readObject(toFind, Blob.class);
    }

    public void add(String[] args) {
        // valid input
        checkOperand(args, 2);

        // check if file exists
        File toAdd = join(CWD, args[1]);
        if(!toAdd.isFile()) {
            System.out.println("File does not exist.");
            return;
        }

        // create a blob for the new file for the convenience of check
        Blob newBlob = new Blob(toAdd);

        // scan current commit, if identical,
        // don't add, and remove from staging area if exists
        LinkedList commitList = findCommit(HEAD).getHashOfBlobs();
        for(int i = 0; i < commitList.size(); ++i) {
            Blob thisBlob = findBlob((String) commitList.get(i));
            // check if identical to given file
            if(thisBlob.equals(newBlob)) {
                // remove from staging area if exists same FILENAME
                for(int j = 0; j < stage.size(); ++j) {
                    String stageFileName = findBlob(stage.get(j)).getFilename();
                    if(thisBlob.getFilename().equals(stageFileName)) {
                        stage.remove(j);
                    }
                }
                return;
            }
        }

        // scan staging area, if:
        // same filename and same content: return;
        // same filename but diff content: delete original one
        for(int i = 0; i < stage.size(); ++i) {
            Blob stageBlob = findBlob(stage.get(i));
            if(newBlob.equals(stageBlob)) {
                return;
            }
            if(newBlob.getFilename().equals(stageBlob.getFilename())) {
                stage.remove(i);
            }
        }
        // add newFile to staging area
        stage.add(newBlob.getHashValue());
        // write blob to file
        writeObject(join(BLOB_DIR, newBlob.getHashValue()), newBlob);

        // write repo info to the file
        writeRepoToFile();
    }

    public void commit(String[] args) {
        // valid input: different from the others
        if(args.length == 1) {
            System.out.println("Please enter a commit message.");
            return;
        } else {
            checkOperand(args, 2);
        }

        // anything to commit?
        if(stage.isEmpty() && rmStage.isEmpty()) {
            System.out.println("No changes added to the commit.");
            return;
        }

        // create a new commit
        Commit newCommit = new Commit(HEAD, args[1], stage, rmStage);
        String newHash = sha1obj(newCommit);
        // add commit to Commit Tree(just add to parent's child)
        Commit parentCommit = findCommit(HEAD);
        parentCommit.insertChild(newHash);
        // notice that after insert child, parent's hash value changed
        // we store it to a new file and delete original one
//        restrictedDelete(join(COMMIT_DIR, HEAD));
        String parentNewHash = sha1obj(parentCommit);
        // store commits to file
        writeObject(join(COMMIT_DIR, parentNewHash), parentCommit);
        writeObject(join(COMMIT_DIR, newHash), newCommit);
        // Update HEAD
        HEAD = newHash;
        // clear staging area
        stage = new LinkedList<>();
        rmStage = new LinkedList<>();

        // write repo info to the file
        writeRepoToFile();
    }

    /**
     * helper function: render a commit into log style

       ===
       commit abcdefg...
       Date: Thu Nov 9 20:00:05 2017 -0800
       A commit message.

     */
    private void renderCommit(Commit commit) {
        String hashValue = sha1obj(commit);
        System.out.println("===");
        System.out.println("commit " + hashValue);
        Date commitDate = commit.getCommitTime();
        SimpleDateFormat logFormat = new SimpleDateFormat("E MMM d HH:mm:ss yyyy ZZ");
        System.out.println("Date: " + logFormat.format(commitDate));
        System.out.println(commit.getMessage());
        System.out.println();
    }

    public void log(String[] args) {
        // valid input
        checkOperand(args, 1);

        Commit p = findCommit(HEAD);
        renderCommit(p);
        while(p.getParent() != null) {
            p = findCommit(p.getParent());
            renderCommit(p);
        }
    }

    public void rm(String[] args) {
        // valid input
        checkOperand(args, 2);

    }

    /**
     * helper function for :
     * - checkout --[filename]
     * - checkout --[commit id] --[filename]
     *
     * No need to check argument
     */
    private void checkoutFile(String filename, String commit) {
        Commit currentCommit = findCommit(commit);
        if(currentCommit == null) {
            System.out.println("No commit with that id exists.");
            return;
        }
        LinkedList<String> blobs = currentCommit.getHashOfBlobs();

        for(int i = 0; i < blobs.size(); ++i) {
            Blob thisBlob = findBlob(blobs.get(i));
            // find in commit blobs? put it in the working directory
            if(thisBlob.getFilename().equals(filename)) {
                writeContents(join(CWD, filename), thisBlob.getContents());
                return;
            }
        }
        // not found? error message!
        System.out.println("File does not exist in that commit.");
    }

    /**
     * helper function for:
     * - checkout [branch]
     *
     * no need to check argument
     */
    private void checkoutBranch() {

    }

    public void checkout(String[] args) {
        // valid argument, different from others
        if(args.length <= 1 || args.length >= 5) {
            System.out.println("Incorrect operands.");
            // exit with 0
            System.exit(0);
        }

        // call corresponding helper function
        if(args.length == 2) {
            checkoutBranch();
        } else if (args.length == 3) {
            checkoutFile(args[2], HEAD);
        } else {
            checkoutFile(args[3], args[1]);
        }
    }

}

/**
 * java gitlet.Main init
 * java gitlet.Main add test.txt
 * java gitlet.Main commit "no modified"
 * java gitlet.Main add test.txt
 * java gitlet.Main commit "modified"
 * java gitlet.Main log
 * java gitlet.Main checkout
 */
