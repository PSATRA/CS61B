package gitlet;


import java.io.File;
import java.io.Serializable;
import java.util.Date;

import static gitlet.Repository.OBJECTS_DIR;
import static gitlet.Utils.*;

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

    /** Constructor exclusively init command. */
    Commit() {
        this.message = "initial commit";
        this.timestamp = new Date(0); //TODO: verify this(print in log).
        this.parentID = null;
        this.tree = new Tree();
    }

    /** Constructor for normal commits. */
    Commit(String message, String parentID, String parentTreeID) {
        this.message = message;
        this.timestamp = new Date(0); //TODO: fix this.
        this.parentID = parentID;
        File parentTreeFile = join(OBJECTS_DIR,
                MyUtils.preCut(parentTreeID),
                MyUtils.postCut(parentTreeID));
        this.tree = readObject(parentTreeFile, Tree.class);
    }

    /** Derive a commit by the given ID. */
    public Commit getCommitFromID(String ID) {
        String dirName = MyUtils.preCut(ID);
        String fileName = MyUtils.postCut(ID);
        File file = join(OBJECTS_DIR, dirName, fileName);
        return readObject(file, Commit.class);
    }
    public String getMessage() {
        return message;
    }
    public Tree getTree() {
        return tree;
    }
    public String getTreeID() {
        return tree.getID();
    }
    public String getCommitID() {
        return Utils.sha1(Utils.serialize(this));
    }
    public String getParentID() {
        return parentID;
    }

    /** Update or add to the tree from the parent. */
    public void updateTree(Blob blob) {
        tree.getMap().put(blob.getName(), blob.getContentID());
    }

    /** Remove(untrack) files from removed. */
    public void untrackFile(Blob blob) {
        tree.getMap().remove(blob.getName());
    }

    /** Creates commit file in .gitlet/objects/..  */
    public void writeCommitFile() {
        File dir = join(OBJECTS_DIR, MyUtils.preCut(getCommitID()));
        if (!dir.exists()) {
            dir.mkdir();
        }
        File commitFile = join(dir, MyUtils.postCut(getCommitID()));
        writeObject(commitFile, this);
    }
}
