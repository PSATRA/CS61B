package gitlet;

import java.io.File;
import java.util.Objects;

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
    /** The branch directory. */
    public static final File REFS_DIR = join(GITLET_DIR, "refs");
    /** The local branch directory. */
    public static final File HEADS_DIR = join(REFS_DIR, "heads");

    /** The staging area file. */
    public static final File INDEX = join(GITLET_DIR, "index");
    /** The head pointer. */
    public static final File HEAD = join(GITLET_DIR, "HEAD");


    /* init */
    public static void initRepo() {
        if (GITLET_DIR.exists()) {
            exit("A Gitlet version-control system " +
                    "already exists in the current directory.");
        } else {
            GITLET_DIR.mkdir();  // creates .gitlet dir
            OBJECTS_DIR.mkdir(); // creates objects dir
            REFS_DIR.mkdir();    // creates branch dir
            HEADS_DIR.mkdir();   // creates local branch dir

            Commit initCommit = new Commit();   // create initial Commit
            initCommit.writeCommitFile();
            initCommit.getTree().writeTreeFile();
            writeObject(HEAD, initCommit);
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
        StagingArea index;
        if (INDEX.exists()){
            index = readObject(INDEX, StagingArea.class);
        } else {
            index = new StagingArea();
        }
        index.add(fileName);
        if (!index.getAdded().isEmpty() || !index.getRemoved().isEmpty()) {
            writeObject(INDEX, index);
        } else if (INDEX.exists()){
            INDEX.delete();
        }
    }

    /* commit */
    public static void commitFile(String message) {
        if (!INDEX.exists()) {
            exit("No changes added to the commit.");
        }

        // Derive the parent commit info and pass to the current one.
        Commit parentCommit = readObject(HEAD, Commit.class);
        Commit newCommit = new Commit(message, parentCommit.getCommitID(),
                parentCommit.getTreeID());

        StagingArea currentStage = readObject(INDEX, StagingArea.class);
        for (String tempFileName : currentStage.getAdded().keySet()) {
            Blob blob = new Blob(tempFileName);
            newCommit.updateTree(blob);
        }
        for (String tempFileName : currentStage.getRemoved()) {
            Blob blob = new Blob(tempFileName);
            newCommit.untrackFile(blob);
        }
        newCommit.getTree().writeTreeFile();
        newCommit.writeCommitFile();
        INDEX.delete();
        writeObject(HEAD, newCommit); // move the head pointer
        //TODO: move the master pointer.(here or in the writeCommitFile())
    }

    /* rm */
    public static void removeFile(String fileName) {
        File file = join(CWD, fileName);
        StagingArea currentStage = readObject(INDEX, StagingArea.class);
        // Unstage the file if it is currently staged for addition.
        if (currentStage.getAdded().containsKey(fileName)) {
            currentStage.unstageFile(fileName);
            return;
        }
        // Derive the current commit.
        Commit currentCommit = readObject(HEAD, Commit.class);
        String contentID = sha1(readContents(file));
        boolean isCurrentlyTracked =
                currentCommit.getTree().getMap().containsKey(fileName)
                        && Objects.equals
                (currentCommit.getTree().getMap().get(fileName), contentID);
        // If the file is tracked in the current commit:
        // 1. Stage it for removal by stageToRemoved(fileName).
        // 2. Remove the file from the CWD if the user has not done so.
        if (isCurrentlyTracked) {
            currentStage.stageToRemoved(fileName);
            if (file.exists()) {
                file.delete();
            }
            return;
        }
        //If the file is neither staged nor tracked by the head commit,
        // print the error message.
        if (!currentStage.getAdded().containsKey(fileName)
                && !currentStage.getRemoved().contains(fileName)) {
            exit("No reason to remove the file.");
        }
    }

    /* log */
    public static void printLogMessage() {
        // Derive the current commit.
        Commit currentCommit = readObject(HEAD, Commit.class);
        while (true) {
            printHelper(currentCommit);
            if (currentCommit.getParentID() != null) {
                String parentID = currentCommit.getParentID();
                currentCommit = currentCommit.getCommitFromID(parentID);
            } else {
                break; // having printed the initial commit
            }
        }
        //TODO: handle the merge
    }

    public static void printHelper(Commit commit) {
        System.out.println("===");
    }


    /* Exit if no working directory exists. */
    public static void checkWorkingDir() {
        if (!GITLET_DIR.exists() && !GITLET_DIR.isDirectory()) {
            exit("Not in an initialized Gitlet directory.");
        }
    }
}
