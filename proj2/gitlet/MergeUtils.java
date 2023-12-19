package gitlet;

import java.util.ArrayList;
import java.util.LinkedList;

import static gitlet.MyUtils.*;

public class MergeUtils {
    /** Return the commit at the split point by BFS. */
    public static Commit findSplit(Commit newCommit) {
        LinkedList<Commit> bfs = new LinkedList<>();
        ArrayList<Commit> markedCommits = new ArrayList<>();
        bfs.add(newCommit);
        markedCommits.add(newCommit);
        while (true) {
            Commit removed = bfs.removeFirst();
            Commit firstParent = getCommitFromID(removed.getParentID());
            Commit secParent = getCommitFromID(removed.getSecParentID());
            if (firstParent != null) {
                if (!markedCommits.contains(firstParent)) {
                    bfs.add(firstParent);
                    markedCommits.add(firstParent);
                } else {
                    return firstParent;
                }
            }
            if (secParent != null) {
                if (!markedCommits.contains(secParent)) {
                    bfs.add(secParent);
                    markedCommits.add(secParent);
                } else {
                    return secParent;
                }
            }
        }
    }
}
