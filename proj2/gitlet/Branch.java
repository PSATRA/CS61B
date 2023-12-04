package gitlet;

import java.io.File;
import java.io.Serializable;

import static gitlet.Repository.COMMIT_DIR;
import static gitlet.Repository.HEADS_DIR;
import static gitlet.Utils.join;
import static gitlet.Utils.writeObject;

public class Branch implements Serializable {
    private final String branchName;
    private final Commit commit;

    public Branch(String branchName, Commit commit) {
        this.branchName = branchName;
        this.commit = commit;
    }

    public String getName() {
        return branchName;
    }
    public Commit getCommit() {
        return commit;
    }

    /** Creates branch file in .gitlet/refs/heads/ beyond the current
     * branch. The current branch is in .gitlet/refs/ .
     */
    public void writeBranchFile() {
        File branchFile = join(HEADS_DIR, branchName);
        writeObject(branchFile, this);
    }
}
