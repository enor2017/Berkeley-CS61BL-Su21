package gitlet;
import java.io.File;
import java.io.Serializable;

import static gitlet.Utils.*;

/**
 * Class for Blob Object
 */
public class Blob implements Serializable {

    /**
     * variables:
     * * filename
     * * byte array for contents
     * * SHA-1 hash value of the file it represents
     */
    private String filename;
    private byte[] contents;
    private String hashValue;

    public Blob (File file) {
        filename = file.getName();
        contents = readContents(file);
        hashValue = sha1(file);
    }

    public byte[] getContents() {
        return contents;
    }

    public String getFilename() {
        return filename;
    }

    public String getHashValue() {
        return hashValue;
    }
}
