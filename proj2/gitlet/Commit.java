package gitlet;

// TODO: any imports you need here
import java.io.File;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import static gitlet.Repository.OBJECTS_DIR;
import static gitlet.Utils.join;
import static gitlet.Utils.writeObject;

/** Represents a gitlet commit object.
 *
 *  @author Kingpin
 */
public class Commit implements Serializable {
    /** The message of this Commit. */
    private final String message;

    /** The timestamp for this Commit. */
    private final Date timestamp;

    /** The SHA-1 hash of the parent commit. */
    private final String parentID;

    /** The file tree of the current commit. */
    private final Tree tree;

    /** The SHA-1 hash of the commit. */
    private String commitID = null;

    /** Constructor exclusively init command. */
    Commit() {
        this.message = "initial commit";
        this.timestamp = new Date(0); //TODO: verify this(print in log).
        this.parentID = null;
        this.tree = new Tree();
    }

    /** Constructor for normal commits. */
    Commit(String message, String parentID) {
        this.message = message;
        this.timestamp = new Date(0); //TODO: fix this.
        this.parentID = parentID;
        this.tree = new Tree(); // still the parent tree so far
        //TODO: inherit from the parent
    }

    /** Derive a commit by the given ID.
     * Can be used in checkout command. */
    Commit getCommitFromID(String ID) {
        String dirName = MyUtils.preCut(ID);
        String fileName = MyUtils.postCut(ID);
        File file = join(OBJECTS_DIR, dirName, fileName);
        return Utils.readObject(file, Commit.class);
    }

    public Tree getTree() {
        return tree;
    }

    /** Update or add to the tree from the parent. */
    public void updateTree(Blob blob) {
        tree.getMap().put(blob.getName(), blob.getID());
    }

    /** Remove(untrack) files from removed. */
    public void untrackFile(Blob blob) {
        tree.getMap().remove(blob.getName());
    }

    /** Creates commit file in .gitlet/objects/..  */
    public void writeCommitFile() {
        this.commitID = Utils.sha1((Object) Utils.serialize(this));
        File indexDir = join(OBJECTS_DIR, MyUtils.preCut(this.commitID));
        if (!indexDir.exists()) {
            indexDir.mkdir();
        }
        File initCommitFile = join(indexDir, MyUtils.postCut(this.commitID));
        writeObject(initCommitFile, this);
    }
}
