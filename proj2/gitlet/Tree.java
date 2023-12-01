package gitlet;

import java.io.File;
import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

import static gitlet.Repository.OBJECTS_DIR;
import static gitlet.Utils.*;

public class Tree implements Serializable {
    private final Map<String, String> treeMap = new TreeMap<>();
    private final String treeID = sha1((Object) serialize(this));


    /** Return the treeMap. */
    public Map<String, String> getMap() {
        return treeMap;
    }

    /** Return the treeID. */
    public String getID() {
        return treeID;
    }

    /** Creates tree file in .gitlet/objects/..  */
    public void writeTreeFile() {
        File indexDir = join(OBJECTS_DIR, MyUtils.preCut(this.treeID));
        if (!indexDir.exists()) {
            indexDir.mkdir();
        }
        File initCommitFile = join(indexDir, MyUtils.postCut(this.treeID));
        writeObject(initCommitFile, this);
    }
}
