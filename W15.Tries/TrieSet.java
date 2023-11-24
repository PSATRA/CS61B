import java.util.HashSet;

public class TrieSet {
    private static class Node {
        private boolean isKey;
        private HashSet<Node> children; // a solution to the excess use of space
        private Node(boolean b) {
            isKey = b;
        }
    }

    //private static final int R = 128; // ASCII how many children stored at most

    private Node root;    // root of trie
}
