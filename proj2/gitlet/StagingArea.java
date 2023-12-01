package gitlet;

import java.io.File;
import java.io.Serializable;
import java.util.*;

import static gitlet.Repository.CWD;
import static gitlet.Utils.join;

public class StagingArea implements Serializable {
    /* The added files with path as key and SHA1 hash as value. */
    private final Map<String, String> added = new TreeMap<>();

    /* The removed files with path as key. */
    private final Set<String> removed = new TreeSet<>();

    public Map<String, String> getAdded() {
        return added;
    }

    public Set<String> getRemoved() {
        return removed;
    }

    /* add */
    /**
     * Adds    the file if it doesn't exist under .gitlet
     * Updates the file if it changes.
     * Remove  the file if it's unchanged.
     */
    public void add(String fileName) {
        File fileToBeAdded = join(CWD, fileName); // target file in CWD
        String contentID = Utils.sha1(Utils.serialize(fileToBeAdded));

        if (!added.containsKey(fileName)) {
            //Adds the file if it doesn't exist under .gitlet
            added.put(fileName, contentID);
        } else if (!Objects.equals(added.get(fileName), contentID)) {
            //Updates the file if it changes.
            added.put(fileName, contentID);
        } else {
            //Remove the file if it's unchanged.
            added.remove(fileName);
        }
    }

    public void unstageFile(String fileName) {
        // Unstage the file if it's currently staged for addition.
        added.remove(fileName);
    }

    public void stageToRemoved(String fileName) {
        removed.add(fileName);
    }
}
