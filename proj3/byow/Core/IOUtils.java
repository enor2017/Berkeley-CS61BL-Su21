package byow.Core;

import java.io.*;
import java.nio.file.Paths;

/**
 * This class mainly handle File IO stuff
 * write/read Object, fileIO APIs
 * adapted from Project 2: Gitlet "Utils.java", @author P. N. Hilfinger
 */
public class IOUtils {

    /* read a serializable object from given file */
    static Object readObject(File file) {
        try {
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            return ois.readObject();
        } catch (Exception e) {
            System.out.println("Error while reading object.");
        }
        // redundant return
        return null;
    }

    /** Write OBJ to FILE. */
    static void writeObject(File file, Serializable obj) {
        try {
            FileOutputStream fis = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fis);
            oos.writeObject(obj);
        } catch (Exception e) {
            System.out.println("Error while writing object.");
        }
    }

    /** Return the concatentation of FIRST and OTHERS into a File designator,
     *  analogous to the {@link "java.nio.file.Paths.#get(String, String[])"}
     *  method. */
    static File join(File first, String... others) {
        return Paths.get(first.getPath(), others).toFile();
    }
}
