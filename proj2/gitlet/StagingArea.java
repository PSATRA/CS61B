package gitlet;

import java.io.File;
import java.io.Serializable;
import java.util.*;

public class StagingArea implements Serializable {
    /* The added files with path as key and SHA1 hash as value. */
    private final Map<String, String> added = new HashMap<>();

    /* The removed files with path as key. */
    private final Set<String> removed = new HashSet<>();


    /* add */
    /**
     * Adds    the file if it doesn't exist under .gitlet
     * Updates the file if it changes.
     * Remove  the file if it's unchanged.
     */
    public void add(File file) {
        String filePath = file.getAbsolutePath();
        String contentID = Utils.sha1((Object) Utils.serialize(file));

        if (!added.containsKey(filePath)) {
            //Adds the file if it doesn't exist under .gitlet
            added.put(filePath, contentID);
        } else if (!Objects.equals(added.get(filePath), contentID)) {
            //Updates the file if it changes.
            added.put(filePath, contentID);
        } else {
            //Remove the file if it's unchanged.
            added.remove(filePath);
        }
    }
}
