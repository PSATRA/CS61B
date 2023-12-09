package gitlet;

import java.io.File;
import java.util.*;

import static gitlet.MyUtils.*;
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
    /** The commit directory. */
    public static final File COMMIT_DIR = join(OBJECTS_DIR, "commits");
    /** The tree directory. */
    public static final File TREE_DIR = join(OBJECTS_DIR, "trees");
    /** The branch directory. */
    public static final File REFS_DIR = join(GITLET_DIR, "refs");
    /** The local branch directory. */
    public static final File HEADS_DIR = join(REFS_DIR, "heads");

    /** The staging area file. */
    public static final File INDEX = join(GITLET_DIR, "index");
    /** The head pointer. */
    public static final File HEAD = join(GITLET_DIR, "HEAD");
    /** The current branch. */
    public static final File CURRENT_BRANCH = join(REFS_DIR, "current-branch");


    /** init */
    public static void initRepo() {
        if (GITLET_DIR.exists()) {
            exit("A Gitlet version-control system " +
                    "already exists in the current directory.");
        }
        GITLET_DIR.mkdir();
        OBJECTS_DIR.mkdir();
        COMMIT_DIR.mkdir();
        TREE_DIR.mkdir();
        REFS_DIR.mkdir();  // creates branch dir
        HEADS_DIR.mkdir(); // creates local branch dir

        Commit initCommit = new Commit();   // create initial Commit
        initCommit.writeCommitFile();
        initCommit.getTree().writeTreeFile();
        writeObject(HEAD, initCommit);

        Branch masterBranch = new Branch("master", initCommit);
        writeObject(CURRENT_BRANCH, masterBranch);
    }

    /** add */
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

    /** commit */
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

        Branch currentBranch = readObject(CURRENT_BRANCH, Branch.class);
        String currentBranchName = currentBranch.getName();
        Branch branch = new Branch(currentBranchName, newCommit);
        writeObject(CURRENT_BRANCH, branch);
    }

    /** rm */
    public static void removeFile(String fileName) {
        File file = join(CWD, fileName);
        StagingArea currentStage;
        if (!INDEX.exists()) {
            currentStage = new StagingArea();
            writeObject(INDEX, currentStage);
        } else {
            currentStage = readObject(INDEX, StagingArea.class);
        }
        // Unstage the file if it is currently staged for addition.
        if (currentStage.getAdded().containsKey(fileName)) {
            currentStage.unstageFile(fileName);
            if (currentStage.getAdded().isEmpty()
                    && currentStage.getRemoved().isEmpty()) {
                INDEX.delete();
            }
            return;
        }
        // If the file is tracked in the current commit:
        // 1. Stage it for removal by stageToRemoved(fileName).
        // 2. Remove the file from the CWD if the user has not done so.
        Commit currentCommit = readObject(HEAD, Commit.class);
        boolean isCurrentlyTracked =
                currentCommit.getTree().getMap().containsKey(fileName);
        if (isCurrentlyTracked) {
            currentStage.stageToRemoved(fileName);
            if (file.exists()) {
                restrictedDelete(file);
            }
            return;
        }
        //If the file is neither staged nor tracked by the current commit,
        // print the error message.
        if (!currentStage.getRemoved().contains(fileName)) {
            //This is the only restriction left, since it's obviously
            // not tracked or staged for addition.
            if (currentStage.getAdded().isEmpty()
                    && currentStage.getRemoved().isEmpty()) {
                INDEX.delete();
            }
            exit("No reason to remove the file.");
        }
    }

    /** log */
    public static void printLog() {
        // Derive the current commit.
        Commit currentCommit = readObject(HEAD, Commit.class);
        while (true) {
            assert currentCommit != null;
            printHelper(currentCommit);
            if (currentCommit.getParentID() != null) {
                String parentID = currentCommit.getParentID();
                currentCommit = getCommitFromID(parentID);
            } else {
                break; // having printed the initial commit
            }
        }
        //TODO: handle the merge
    }

    /** global-log */
    public static void printGlobalLog() {
        List<String> commitGross = plainFilenamesIn(COMMIT_DIR);
        assert commitGross != null;
        for (String commitID : commitGross) {
            Commit currentCommit = getCommitFromID(commitID);
            assert currentCommit != null;
            printHelper(currentCommit);
        }
    }

    /** helper method for two log commands */
    private static void printHelper(Commit commit) {
        System.out.println("===");
        System.out.println("commit " + commit.getCommitID());

        Date currentDate = commit.getDate();
        Formatter formatter = new Formatter();
        formatter.format("%ta %tb %td %tT %tY %tz", currentDate, currentDate,
                currentDate, currentDate, currentDate, currentDate);
        System.out.println("Date: " + formatter);
        formatter.close();

        System.out.println(commit.getMessage());
        System.out.println();
    }

    /** find */
    public static void findCorrCommits(String givenMessage) {
        List<String> commitGross = plainFilenamesIn(COMMIT_DIR);
        assert commitGross != null;
        boolean existsSuchCommit = false;
        for (String commitID : commitGross) {
            Commit currentCommit = getCommitFromID(commitID);
            assert currentCommit != null;
            if (Objects.equals(currentCommit.getMessage(), givenMessage)) {
                existsSuchCommit = true;
                System.out.println(currentCommit.getCommitID());
            }
        }
        if (!existsSuchCommit) {
            exit("Found no commit with that message.");
        }
    }

    /** status */
    public static void printStatus() {
        System.out.println("=== Branches ===");
        List<String> branchNames = plainFilenamesIn(HEADS_DIR);
        if (branchNames == null) {
            branchNames = new ArrayList<>();
        }
        // Convert the type of branchNames from AbstractList to ArrayList.
        branchNames = new ArrayList<>(branchNames);
        Branch currentBranch = readObject(CURRENT_BRANCH, Branch.class);
        branchNames.add(currentBranch.getName());
        Collections.sort(branchNames);
        for (String branchName : branchNames) {
            if (Objects.equals(branchName, currentBranch.getName())) {
                System.out.print("*");
            }
            System.out.println(branchName);
        }
        System.out.println();

        System.out.println("=== Staged Files ===");
        if (INDEX.exists()) {
            StagingArea currentStage = readObject(INDEX, StagingArea.class);
            Set<String> fileNameSet = currentStage.getAdded().keySet();
            List<String> fileNameList = new ArrayList<>(fileNameSet);
            Collections.sort(fileNameList);
            for (String fileName : fileNameList) {
                System.out.println(fileName);
            }
        }
        System.out.println();

        System.out.println("=== Removed Files ===");
        if (INDEX.exists()) {
            StagingArea currentStage = readObject(INDEX, StagingArea.class);
            Set<String> fileNameSet = currentStage.getRemoved();
            List<String> fileNameList = new ArrayList<>(fileNameSet);
            Collections.sort(fileNameList);
            for (String fileName : fileNameList) {
                System.out.println(fileName);
            }
        }
        System.out.println();

        System.out.println("=== Modifications Not Staged For Commit ===");
        //TODO
        System.out.println();

        System.out.println("=== Untracked Files ===");
        //TODO
        System.out.println();
    }

    /** checkout -- [file name] */
    public static void checkoutFile(String fileName) {
        // derive the target version in the head commit
        Commit headCommit = readObject(HEAD, Commit.class);
        if (!commitConsistsFile(headCommit, fileName)) {
            exit("File does not exist in that commit.");
        }
        byte[] headContent = headCommit.getTree().getMap().get(fileName);
        // Derive the current version.
        Blob currentBlob = new Blob(fileName);
        // Create or overwrite, casting for type confusion.
        writeContents(currentBlob.getFile(), (Object) headContent);
    }

    /** checkout [commit id] -- [file name] */
    public static void checkoutFile(String commitID, String fileName) {
        // derive the target version in given commit
        Commit targetCommit = getCommitFromID(commitID);
        if (targetCommit == null) {
            exit("No commit with that id exists.");
        }
        assert targetCommit != null;
        if (!commitConsistsFile(targetCommit, fileName)) {
            exit("File does not exist in that commit.");
        }
        byte[] targetContent = targetCommit.getTree().getMap().get(fileName);
        // Derive the current version.
        Blob currentBlob = new Blob(fileName);
        // Create or overwrite, casting for type confusion.
        writeContents(currentBlob.getFile(), (Object) targetContent);
    }

    /** checkout [branch name] */
    public static void checkoutBranch(String branchName) {
        Branch checkedBranch = getBranchFromName(branchName);
        Branch currentBranch = readObject(CURRENT_BRANCH, Branch.class);
        if (Objects.equals(branchName, currentBranch.getName())) {
            exit("No need to checkout the current branch.");
        }
        if (checkedBranch == null) {
            exit("No such branch exists.");
        }

        /**
         * Change the file version. If a working file is untracked in the
         * current branch and would be overwritten by the checkout, print
         * the error.
         *
         * This check is performed before doing anything else, and does
         * not change the CWD!
         */
        assert checkedBranch != null;
        Commit checkedCommit = checkedBranch.getCommit();
        Commit currentCommit = currentBranch.getCommit();
        Map<String, byte[]> checkedFiles = checkedCommit.getTree().getMap();
        Map<String, byte[]> currentFiles = currentCommit.getTree().getMap();
        for (String fileName : checkedFiles.keySet()) {
            if (!currentFiles.containsKey(fileName)) {
                exit("There is an untracked file in the way; delete " +
                        "it, or add and commit it first.");
            }
        }
        for (String fileName : checkedFiles.keySet()) {
            // Derive the current version.
            Blob blob = new Blob(fileName);
            // Derive the target version in the checked-out branch.
            byte[] content = checkedFiles.get(fileName);
            // Create or overwrite, casting for type confusion.
            writeContents(blob.getFile(), (Object) content);
        }

        // Any files that are tracked in the current branch but are
        //  not present in the checked-out branch are deleted.
        for (String fileName : currentFiles.keySet()) {
            if (!checkedFiles.containsKey(fileName)) {
                restrictedDelete(fileName);
            }
        }

        // Set this branch to the current branch and update HEAD.
        currentBranch.writeBranchFile();
        writeObject(CURRENT_BRANCH, checkedBranch);
        writeObject(HEAD, checkedCommit);

        // Check the staging area, delete it, unless it's the
        //  current branch to be checked-out(failure cases).
        if (INDEX.exists()){
            INDEX.delete();
        }
    }

    /** branch */
    public static void createNewBranch(String branchName) {
        Branch currentBranch = readObject(CURRENT_BRANCH, Branch.class);
        Commit currentCommit = readObject(HEAD, Commit.class);

        List<String> branchNames = plainFilenamesIn(HEADS_DIR);
        if (branchNames == null) {
            branchNames = new ArrayList<>();
        }
        // Convert the type of branchNames from AbstractList to ArrayList.
        branchNames = new ArrayList<>(branchNames);
        branchNames.add(currentBranch.getName());
        for (String names : branchNames) {
            if (Objects.equals(names, branchName)) {
                exit("A branch with that name already exists.");
            }
        }

        Branch newBranch = new Branch(branchName, currentCommit);
        newBranch.writeBranchFile();
    }

    /** rm-branch */
    public static void deleteBranch(String branchName) {
        Branch currentBranch = readObject(CURRENT_BRANCH, Branch.class);
        if (Objects.equals(branchName, currentBranch.getName())) {
            exit("Cannot remove the current branch.");
        }
        List<String> branchNames = plainFilenamesIn(HEADS_DIR);
        if (branchNames == null || !branchNames.contains(branchName)) {
            exit("A branch with that name does not exist.");
        }
        assert branchNames != null;
        for (String names : branchNames) {
            if (Objects.equals(names, branchName)) {
                File branch = join(HEADS_DIR, branchName);
                branch.delete();
                return;
            }
        }
    }

    /** reset [commit id] */
    public static void reset(String commitID) {
        Commit targetCommit = getCommitFromID(commitID);
        Commit currentCommit = readObject(HEAD, Commit.class);
        if (targetCommit == null) {
            exit("No commit with that id exists.");
        }
        assert targetCommit != null;
        Map<String, byte[]> targetFiles = targetCommit.getTree().getMap();
        Map<String, byte[]> currentFiles = currentCommit.getTree().getMap();
        /**
         * Checks out all the files tracked by the given commit.
         *
         * If a working file is untracked in the current branch and would
         * be overwritten by the reset, print the error.
         *
         * This check is performed before doing anything else, and does
         * not change the CWD!
         */
        for (String fileName : targetFiles.keySet()) {
            if (!currentFiles.containsKey(fileName)) {
                exit("There is an untracked file in the way; " +
                        "delete it, or add and commit it first.");
            }
        }
        // checkout with no errors
        for (String fileName : targetFiles.keySet()) {
            byte[] targetContent = targetFiles.get(fileName);
            // Derive the current version.
            Blob currentBlob = new Blob(fileName);
            // Create or overwrite, casting for type confusion.
            writeContents(currentBlob.getFile(), (Object) targetContent);
        }

        /** Any files that are tracked in the current commit but are not
         * present in the target commit are deleted.
         */
        for (String fileName : currentFiles.keySet()) {
            if (!targetFiles.containsKey(fileName)) {
                restrictedDelete(fileName);
            }
        }

        Branch currentBranch = readObject(CURRENT_BRANCH, Branch.class);
        Branch movedBranch = new
                Branch(currentBranch.getName(), targetCommit);
        writeObject(CURRENT_BRANCH, movedBranch);
        writeObject(HEAD, targetCommit);
        if (INDEX.exists()){
            INDEX.delete();
        }
    }

    /** merge [branch name] */
    public static void merge(String branchName) {
        Branch firstBranch = readObject(CURRENT_BRANCH, Branch.class);
        Branch secBranch = getBranchFromName(branchName);
        Commit firstCommit = firstBranch.getCommit();
        mergeFailures(branchName, firstBranch, secBranch);
        assert secBranch != null;
        Commit secCommit = secBranch.getCommit();
    }

    /** Handle the possible failure cases for merge. */
    private static void mergeFailures(String branchName,
                                      Branch firstBranch,
                                      Branch secBranch) {
        if (INDEX.exists()) {
            exit("You have uncommitted changes.");
        }
        if (secBranch == null) {
            exit("A branch with that name does not exist.");
        }
        if (Objects.equals(branchName, firstBranch.getName())) {
            exit("Cannot merge a branch with itself.");
        }
        //TODO: If an untracked file in the current commit would be
        // overwritten or deleted by the merge, print "There is an
        // untracked file in the way; delete it, or add and commit
        // it first." and exit; perform this check before doing anything else.
    }


    /* Exit if no working directory exists. */
    public static void checkWorkingDir() {
        if (!GITLET_DIR.exists() && !GITLET_DIR.isDirectory()) {
            exit("Not in an initialized Gitlet directory.");
        }
    }
}
