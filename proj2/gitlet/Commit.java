package gitlet;

// TODO: any imports you need here
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

import java.util.Date; // TODO: You'll likely use this in this class

/** Represents a gitlet commit object.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 *  @author Kingpin
 */
public class Commit implements Serializable {
    /**
     * TODO: add instance variables here.
     *
     * List all instance variables of the Commit class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided one example for `message`.
     */

    /** The message of this Commit. */
    private final String message;

    /** The timestamp for this Commit. */
    private final Date timestamp;

    final String commitFileName = Utils.sha1(this); // TODO: shorten the filename

    /** Constructor exclusively for the init command. */
    Commit() {
        message = "initial commit";
        timestamp = new Date(0);
            // Date(long millisecond),
            // start from 00:00:00 UTC, Thursday, 1 January 1970
            // TODO: verify this.(print in log)
    }

}
