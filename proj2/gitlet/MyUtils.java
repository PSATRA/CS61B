package gitlet;

import java.io.File;

import static gitlet.Repository.COMMIT_DIR;
import static gitlet.Repository.HEADS_DIR;
import static gitlet.Utils.join;
import static gitlet.Utils.readObject;

public class MyUtils {
    /**
     * Prints the message and exit.
     * @param error The error message to be printed
     */
    public static void exit(String error) {
        System.out.println(error);
        System.exit(0);
    }


    /** Derive a commit by the given ID.
     * @return null if there is no commit with such ID
     */
    public static Commit getCommitFromID(String ID) {
        File file = join(COMMIT_DIR, ID);
        if (!file.exists()) {
            return null;
        }
        return readObject(file, Commit.class);
    }


    /** Derive a non-current branch by the given branchName.
     * @return null if there is no branch with such name.
     */
    public static Branch getBranchFromName(String branchName) {
        File branch = join(HEADS_DIR, branchName);
        if (!branch.exists()){
            return null;
        }
        return readObject(branch, Branch.class);
    }


    /** Check if a file exists in a given commit.
     * Only consider the file name here.
     * @param commit The searching spectrum
     * @param fileName Name of the target file
     * @return true if the  file exists in the given commit
     */
    public static boolean commitConsistsFile(
            Commit commit, String fileName) {
        return commit.getTree().getMap().containsKey(fileName);
    }


    /**
     * Cuts out the first two bytes of SHA-1.
     * @param sha1 The SHA-1 to be cut out
     * @return the first two bytes as the dir name
     */
    /*public static String preCut(String sha1) {
        return sha1.substring(0, 2);
    }*/

    /**
     * Cuts out the left bytes of SHA-1.
     * @param sha1 The SHA-1 to be cut out
     * @return the left bytes as the file name
     */
    /*public static String postCut(String sha1) {
        return sha1.substring(2);
    }*/
}
