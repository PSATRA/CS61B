package gitlet;

import java.io.File;
import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

import static gitlet.Repository.TREE_DIR;
import static gitlet.Utils.*;

public class Tree implements Serializable {
    private final Map<String, byte[]> treeMap = new TreeMap<>();
    private final String treeID = sha1(serialize(this));


    /** Return the treeMap. */
    public Map<String, byte[]> getMap() {
        return treeMap;
    }

    /** Return the treeID. */
    public String getID() {
        return treeID;
    }

    /** Creates tree file in .gitlet/objects/..  */
    public void writeTreeFile() {
        File treeFile = join(TREE_DIR, this.treeID);
        writeObject(treeFile, this);
    }
}
