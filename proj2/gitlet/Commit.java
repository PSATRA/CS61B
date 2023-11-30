package gitlet;

// TODO: any imports you need here
import java.io.File;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

import static gitlet.Repository.OBJECTS_DIR;
import static gitlet.Utils.join;
import static gitlet.Utils.writeObject;

/** Represents a gitlet commit object.
 *
 *  @author Kingpin
 */
public class Commit implements Serializable {
    /** The message of this Commit. */
    private final String m_message;

    /** The timestamp for this Commit. */
    private final Date m_timestamp;

    /** The SHA-1 hash of the parent commit. */
    private final String m_parentID;

    /** The SHA-1 hash of the commit. */
    private final String m_commitID;

    /** Constructor exclusively init command. */
    Commit() {
        m_message = "initial commit";
        m_timestamp = new Date(0); //TODO: verify this(print in log).
        m_parentID = null;
        m_commitID = Utils.sha1((Object) Utils.serialize(this));
    }

    /** Constructor for normal commits. */
    Commit(String message, String parentID) {
        m_message = message;
        m_timestamp = new Date(0); //TODO: fix this.
        m_parentID = parentID;
        m_commitID = Utils.sha1((Object) Utils.serialize(this));
    }

    /** Creates commit file in .gitlet/objects/../ */
    public void writeCommitFile() {
        File indexDir = join(OBJECTS_DIR, MyUtils.preCut(m_commitID));
        if (!indexDir.exists()) {
            indexDir.mkdir();
        }
        File initCommitFile = join(indexDir, MyUtils.postCut(m_commitID));
        writeObject(initCommitFile, this);
    }
}
