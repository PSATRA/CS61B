package bstmap;

import java.util.Iterator;
import java.util.Set;


public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {
    private class BSTNode {
        public K key;
        public V value;
        public BSTNode left;
        public BSTNode right;

        public BSTNode(K k, V v) {
            key = k;
            value = v;
            left = null;
            right = null;
        }
    }
    private BSTNode root;
    private int size;

    public BSTMap() {
        root = null;
        size = 0;
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    /* helper method for containsKey. */
    private boolean containsKeyHelper(BSTNode node, K key) {
        if (node == null) {
            return false;
        }

        int cmp = key.compareTo(node.key);
        if (cmp > 0) {
            return containsKeyHelper(node.right, key);
        } else if (cmp < 0) {
            return containsKeyHelper(node.left, key);
        } else {
            return true;
        }
    }

    @Override
    public boolean containsKey(K key) {
        return containsKeyHelper(root, key);
    }

    private V getHelper(BSTNode node, K key) {
        if (node == null || !containsKey(key)) {
            return null;
        }

        int cmp = key.compareTo(node.key);

        if (cmp > 0) {
            return getHelper(node.right, key);
        } else if (cmp < 0) {
            return getHelper(node.left, key);
        } else {
            return node.value;
        }
    }

    @Override
    public V get(K key) {
        return getHelper(root, key);
    }

    @Override
    public int size() {
        return size;
    }

    /* Helper method for recur
    +.30sion. */
    private BSTNode putHelper(BSTNode node, K key, V value) {
        if (node == null) {
            return new BSTNode(key, value);
        }

        int cmp = key.compareTo(node.key);
        if (cmp > 0) {
            node.right = putHelper(node.right, key, value);
        } else if (cmp < 0) {
            node.left = putHelper(node.left, key, value);
        } else {
            node.value = value; // just update the current value
        }
        return node;
    }

    @Override
    public void put(K key, V value) {
        root = putHelper(root, key, value); //root doesn't change the address anymore
        size++;
    }

    private void printInOrderHelper(BSTNode node) {
        if (node == null) {
            return;
        }
        printInOrderHelper(node.left);
        System.out.println(node.key.toString() + " -> " + node.value.toString());
        printInOrderHelper(node.right);
    }
    // traverse the left tree, then the root, then the right

    public void printInOrder() {
        printInOrderHelper(root);
    }

    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }
}
