package gitlet;

import java.io.File;
import java.util.Map;
import java.util.TreeMap;

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
        File fileToBeAdded = join(CWD, fileName);
        if (!fileToBeAdded.exists()) {
            // If the file does not exist in the CWD.
            exit("File does not exist.");
        }
        StagingArea index = new StagingArea();
        index.add(fileName);
        writeObject(INDEX, index);
    }

    /* commit */
    public static void commitFile(String message) {
        if (!INDEX.exists()) {
            exit("No changes added to the commit.");
        }
        StagingArea currentStage = readObject(INDEX, StagingArea.class);
        Commit newCommit = new Commit(message, null); //TODO: fix the parentID
        for (String tempFileName : currentStage.getAdded().keySet()) {
            Blob blob = new Blob(tempFileName);
            newCommit.updateTree(blob);
        }
        newCommit.getTree().writeTreeFile();
        newCommit.writeCommitFile();
        INDEX.delete();
        //TODO: move the head and master pointer.(here or in the writeCommitFile())
    }


    /* Exit if no working directory exists. */
    public static void checkWorkingDir() {
        if (!GITLET_DIR.exists() && !GITLET_DIR.isDirectory()) {
            exit("Not in an initialized Gitlet directory.");
        }
    }
}
