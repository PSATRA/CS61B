package trieSet;

import java.util.HashSet;

public class TrieSet {
    private static class Node {
        //private char ch; follow the 'a' edge, always wind up in 'a'
        private boolean isKey;
        private HashSet<Node> children; // a solution to the excess use of space
        //private DataIndexedCharMap next;
        private Node(boolean b, int R) {
            //ch = c;
            isKey = b;
            //next = new DataIndexedCharMap<Node>(R);
        }
    }

    private static final int R = 128; // ASCII how many children stored at most
    private Node root;    // root of trie
}
