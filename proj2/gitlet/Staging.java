package gitlet;

import java.util.HashMap;

/**
 * This class is the staging area for each branch
 * Notice that each branch should have a independent staging area
 */
public class Staging {
    // A hashmap to store staging files, (Blob hash value) -> (Blob fileName)
    private HashMap<String, String> stage;
    // A hashmap to store REMOVED staging files, (Blob hash value) -> (Blob fileName)
    private HashMap<String, String> rmStage;

    public Staging() {
        stage = new HashMap<>();
        rmStage = new HashMap<>();
    }
}
