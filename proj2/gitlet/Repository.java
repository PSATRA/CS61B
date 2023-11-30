package gitlet;

import java.io.File;

import static gitlet.MyUtils.exit;
import static gitlet.Utils.*;

// TODO: any imports you need here

/** Represents a gitlet repository.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 *  @author Kingpin
 */
public class Repository {
    /**
     * TODO: add instance variables here.
     *
     * List all instance variables of the Repository class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided two examples for you.
     */

    /** The current working directory. */
    public static final File CWD = new File(System.getProperty("user.dir"));
    /** The .gitlet directory. */
    public static final File GITLET_DIR = join(CWD, ".gitlet");


    /**
     * Exit if no working directory exists.
     */
    public static void checkWorkingDir() {
        if (!GITLET_DIR.exists() && !GITLET_DIR.isDirectory()) {
            exit("Not in an initialized Gitlet directory.");
        }
    }

    /* init */
    public static void initRepo() {
        if (GITLET_DIR.exists()) {
            exit("A Gitlet version-control system " +
                    "already exists in the current directory.");
        } else {
            // create .gitlet dir
            GITLET_DIR.mkdir();
            // create initial Commit
            // TODO: menage the data structure
            Commit initCommit = new Commit();
            File initCommitFile = join(GITLET_DIR,
                    initCommit.commitFileName);
            writeObject(initCommitFile, initCommit);
            // TODO: initialize the branch master
        }
    }

    /* add */
    public static void addFile(String fileName) {
        /*File f = new File(fileName);
        byte[] fileContent = Utils.readContents(f);
        // convert the content to SHA-1 hash
        String sha = Utils.sha1(fileContent);
        System.out.println(sha);*/
    }
}
