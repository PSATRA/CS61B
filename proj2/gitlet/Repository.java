package gitlet;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static gitlet.MyUtils.*;
import static gitlet.Utils.*;
import static gitlet.MergeUtils.*;


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

    /** Handles a given commit, applying to both normal and merge commits. */
    public static void commitHelper(Commit newCommit) {
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

    /** commit */
    public static void commit(String message) {
        if (!INDEX.exists()) {
            exit("No changes added to the commit.");
        }

        // Derive the parent commit info and pass to the current one.
        Commit parentCommit = readObject(HEAD, Commit.class);
        Commit newCommit = new Commit(message, parentCommit.getCommitID(),
                parentCommit.getTreeID());
        commitHelper(newCommit);
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
        // 1. Stage it for removal by stageForRemoved(fileName).
        // 2. Remove the file from the CWD if the user has not done so.
        Commit currentCommit = readObject(HEAD, Commit.class);
        boolean isCurrentlyTracked =
                currentCommit.getTree().getMap().containsKey(fileName);
        if (isCurrentlyTracked) {
            currentStage.stageForRemoval(fileName);
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

        if (commit.getSecParentID() != null) {
            String firstSevenDigits = commit.getParentID().substring(0, 7);
            String secSevenDigits = commit.getSecParentID().substring(0, 7);
            System.out.printf("Merge: %s %s\n", firstSevenDigits, secSevenDigits);
        }

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
        System.out.println();

        System.out.println("=== Untracked Files ===");
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
        Commit newCommit = new Commit(firstBranch.getName(),
                                      secBranch.getName(),
                                      firstCommit.getCommitID(),
                                      secCommit.getCommitID());
        Commit splitCommit = findSplit(newCommit);
        //Handle the first two cases:
        if (Objects.equals(splitCommit.getCommitID(), secCommit.getCommitID())) {
            exit("Given branch is an ancestor of the current branch.");
        }
        if (Objects.equals(splitCommit.getCommitID(), firstCommit.getCommitID())) {
            checkoutBranch(secBranch.getName());
            exit("Current branch fast-forwarded.");
        }
        //Create a set of all filenames in the three commits:
        Set<String> fileNameSet = new TreeSet<>();
        Map<String, byte[]> splitFiles = splitCommit.getTree().getMap();
        Map<String, byte[]> firstFiles = firstCommit.getTree().getMap();
        Map<String, byte[]> secFiles = secCommit.getTree().getMap();
        fileNameSet.addAll(splitFiles.keySet());
        fileNameSet.addAll(firstFiles.keySet());
        fileNameSet.addAll(secFiles.keySet());
        /* If an untracked file in the current commit would be overwritten or
        deleted by the merge, print "There is an untracked file in the way;
        delete it, or add and commit it first." and exit; perform this check
        before doing anything else.*/
        for (String fileName : fileNameSet) {
            if (firstFiles.containsKey(fileName)) {
                continue;
            }
            boolean isOverwritten_1 = splitFiles.containsKey(fileName)
                    && secFiles.containsKey(fileName)
                    && Arrays.equals(firstFiles.get(fileName), splitFiles.get(fileName))
                    && !Arrays.equals(secFiles.get(fileName), splitFiles.get(fileName));
            boolean isOverwritten_6 = splitFiles.containsKey(fileName)
                    && !secFiles.containsKey(fileName)
                    && Arrays.equals(firstFiles.get(fileName), splitFiles.get(fileName));
            boolean isOverwritten_8 = (splitFiles.containsKey(fileName)
                    && !Arrays.equals(firstFiles.get(fileName), splitFiles.get(fileName))
                    && !Arrays.equals(secFiles.get(fileName), splitFiles.get(fileName))
                    && !Arrays.equals(firstFiles.get(fileName), secFiles.get(fileName)))
                    || (!splitFiles.containsKey(fileName)
                    && !Arrays.equals(firstFiles.get(fileName), secFiles.get(fileName)));
            if (isOverwritten_1 || isOverwritten_6 || isOverwritten_8) {
                exit("There is an untracked file in the way; " +
                        "delete it, or add and commit it first.");
            }
        }
        //For each file, determine which case it applies to and add it to the newCommit:
        for (String fileName : fileNameSet) {
            File file = join(CWD, fileName);

            /* Only when the version in the HEAD(now) is different from
             * the result should we stage is for addition or removal! E.g.,
             * the file is staged in rule 1 and 6 but not in rule 2. */

            /*1. Any files that have been modified in the given branch, but
            not modified in the current branch should be changed to their
            versions in the given branch (checked out from the commit at
            the front of the given branch). These files should then all
            be automatically staged.*/
            if (splitFiles.containsKey(fileName)
                    && firstFiles.containsKey(fileName)
                    && secFiles.containsKey(fileName)
                    && Arrays.equals(firstFiles.get(fileName), splitFiles.get(fileName))
                    && !Arrays.equals(secFiles.get(fileName), splitFiles.get(fileName))) {
                checkoutFile(secCommit.getCommitID(), fileName);
                addFile(fileName);
                continue;
            }

            /*2. Any files that have been modified in the current branch, but
            not in the given branch since the split point should stay as
            they are. (opposite of rule 1)*/

            /*3. Any files that have been modified in both the current and
            given branch in the same way (i.e., both files now have the same
            content or were BOTH REMOVED) are left unchanged by the merge.
            If a file was removed from both the current and given branch,
            but a file of the same name is present in the working directory,
            it is left alone and continues to be absent (not tracked nor
            staged) in the merge.*/

            /*4. Any files that were not present at the split point and are
            present only in the current branch should remain as they are.*/

            /*5. Any files that were not present at the split point and are
            present only in the given branch should be checked out in the
            given branch and staged.*/
            if (!splitFiles.containsKey(fileName) && !firstFiles.containsKey(fileName)
                    && secFiles.containsKey(fileName)) {
                checkoutFile(secCommit.getCommitID(), fileName);
                addFile(fileName);
                continue;
            }

            /*6. Any files present at the split point, unmodified in the current branch,
            and absent in the given branch should be removed (and untracked). Since the
            file tree of the new commit is empty and no INDEX exists, there is no need
            to explicitly stage it for removal.*/
            if (splitFiles.containsKey(fileName) && !secFiles.containsKey(fileName)
                    && Arrays.equals(firstFiles.get(fileName), splitFiles.get(fileName))) {
                removeFile(fileName);
                continue;
            }

            /*7. Any files present at the split point, unmodified in the given branch,
            and absent in the current branch should remain absent. */

            /*8. Any files modified in different ways in the current and given branches
            are in conflict. “Modified in different ways” can mean that:
            1. The contents of both are changed and different from other;
            2. The contents of one are changed and the other file is deleted;
            3. The file was absent at the split point and has different contents in
            the given and current branches.
            Replace the contents of the conflicted file with... and stage the result.*/
            if ((splitFiles.containsKey(fileName)
                    && !Arrays.equals(firstFiles.get(fileName), splitFiles.get(fileName))
                    && !Arrays.equals(secFiles.get(fileName), splitFiles.get(fileName))
                    && !Arrays.equals(firstFiles.get(fileName), secFiles.get(fileName)))
                 || (!splitFiles.containsKey(fileName)
                    && !Arrays.equals(firstFiles.get(fileName), secFiles.get(fileName))))
            {
                String currentContent = readContentsAsString(file);
                String givenContent = new String(secFiles.get(fileName),
                        StandardCharsets.UTF_8);
                StringBuilder newContent = new StringBuilder();
                newContent.append("<<<<<<< HEAD")
                        .append(currentContent)
                        .append("=======")
                        .append(givenContent)
                        .append(">>>>>>>");
                writeContents(file, newContent);
            }
        }
        /* Automatically commits with the log message..., and update the
        HEAD and the current branch (not necessarily master)*/
        commitHelper(newCommit);
    }

    /** Handle the possible failure cases for merge. */
    private static void mergeFailures(String branchName,
                                      Branch firstBranch,
                                      Branch secBranch) {
        if (INDEX.exists()) {
            exit("You have uncommitted changes.");
        }
        if (Objects.equals(branchName, firstBranch.getName())) {
            exit("Cannot merge a branch with itself.");
        }
        if (secBranch == null) {
            exit("A branch with that name does not exist.");
        }
    }


    /* Exit if no working directory exists. */
    public static void checkWorkingDir() {
        if (!GITLET_DIR.exists() && !GITLET_DIR.isDirectory()) {
            exit("Not in an initialized Gitlet directory.");
        }
    }
}
