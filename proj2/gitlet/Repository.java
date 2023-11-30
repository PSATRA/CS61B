package gitlet;

import java.io.File;

import static gitlet.MyUtils.exit;
import static gitlet.Utils.*;


/** Represents a gitlet repository.
 *
 *  @author Kingpin
 */
public class Repository {
    /**
     * List all instance variables of the Repository class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided two examples for you.
     */

    /** The current working directory. */
    public static final File CWD = new File(System.getProperty("user.dir"));
    /** The .gitlet directory. */
    public static final File GITLET_DIR = join(CWD, ".gitlet");
    /** The commit directory. */
    public static final File OBJECTS_DIR = join(GITLET_DIR, "objects");
    /** The staging area file. */
    public static final File INDEX = join(GITLET_DIR, "index");


    /* init */
    public static void initRepo() {
        if (GITLET_DIR.exists()) {
            exit("A Gitlet version-control system " +
                    "already exists in the current directory.");
        } else {
            GITLET_DIR.mkdir();  // creates .gitlet dir
            OBJECTS_DIR.mkdir(); // creates objects dir
            //TODO: create other dirs under /.gitlet

            Commit initCommit = new Commit();   // create initial Commit
            initCommit.writeCommitFile();

            //TODO: initialize the branch master
        }
    }

    /* add */
    public static void addFile(String fileName) {
        File fileToBeAdded = join(CWD, fileName); // target file in CWD
        if (!fileToBeAdded.exists()) {
            // If the file does not exist in the CWD.
            exit("File does not exist.");
        }
        StagingArea index = new StagingArea();
        index.add(fileToBeAdded);
        writeObject(INDEX, index);
    }

    /* commit */
    public static void commitFile(String fileName) {
        //TODO: derive the parent commit ID.
        //TODO: read from the staging area by calling
        //      readObject(Repository.INDEX, StagingArea.class).
        //TODO: create new commit according to the staging area.
        //TODO: save the new commit using writeCommitFile().
        //TODO: delete/clear the staging area.
    }


    /* Exit if no working directory exists. */
    public static void checkWorkingDir() {
        if (!GITLET_DIR.exists() && !GITLET_DIR.isDirectory()) {
            exit("Not in an initialized Gitlet directory.");
        }
    }
}
