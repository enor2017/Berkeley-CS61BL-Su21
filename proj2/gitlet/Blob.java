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
        hashValue = sha1(contents);
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

    /**
     * helper function:
     * check if two blobs are identical iff filenames and hash values are the same
     *
     * for the sake of simplicity, assume Object is always a Blob
     */
    @Override
    public boolean equals (Object o) {
        if (o.getClass() != Blob.class) {
            throw new IllegalArgumentException();
        }
        Blob b = (Blob) o;
        String thisName = filename;
        String objectName = b.getFilename();
        String thisContent = hashValue;
        String objectContent = b.getHashValue();
        return (thisName.equals(objectName) && thisContent.equals(objectContent));
    }
}
