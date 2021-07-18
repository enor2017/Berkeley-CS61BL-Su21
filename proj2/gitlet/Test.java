package gitlet;

import java.util.LinkedList;

import static gitlet.Utils.*;

public class Test {
    public static void main(String[] args) {
        Blob b1 = readObject(join(Repository.BLOB_DIR, "58e1daf06590c66923c2918523c67faf7ff22654"),
                Blob.class);
        Blob b2 = readObject(join(Repository.BLOB_DIR, "d8f7f5c15eb849265a492c77a6c96eb7ff51afc0"),
                Blob.class);
        writeContents(join(Repository.BLOB_DIR, "b1"), b1.getContents());
        writeContents(join(Repository.BLOB_DIR, "b2"), b2.getContents());


        Commit no = readObject(join(Repository.COMMIT_DIR, "5b674f47c6589b501e1db7e887ef2a51c2919916"),
                Commit.class);
        Commit mod = readObject(join(Repository.COMMIT_DIR, "a0a9071f21a7ef3500ff47b8b14c642d2fc5a793"),
                Commit.class);
        LinkedList<String> noblobs = no.getHashOfBlobs();
        System.out.println("--- no modified commit: --- ");
        for(int i = 0; i < noblobs.size(); ++i) {
            System.out.println(noblobs.get(i));
        }

        LinkedList<String> modblobs = mod.getHashOfBlobs();
        System.out.println("--- modified commit: --- ");
        for(int i = 0; i < modblobs.size(); ++i) {
            System.out.println(modblobs.get(i));
        }
    }
}
