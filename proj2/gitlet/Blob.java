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
     */
    private String filename;
    private byte[] contents;

    public Blob (File file) {
        filename = file.getName();
        contents = readContents(file);
    }

    public byte[] getContents() {
        return contents;
    }

    public String getFilename() {
        return filename;
    }
}
