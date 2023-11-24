package hashmap;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
import java.util.HashSet;

/**
 *  A hash table-backed Map implementation. Provides amortized constant time
 *  access to elements via get(), remove(), and put() in the best case.
 *
 *  Assumes null keys will never be inserted, and does not resize down upon remove().
 *  @author Kingpin
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    /* Instance Variables */
    private Collection<Node>[] buckets;
    private final double maxLoadFactor;
    private Set<K> keySet = new HashSet<>();
    private int size = 0;
    private static final int DEFAULT_INITIAL_SIZE = 16;
    private static final double DEFAULT_MAX_LOAD_FACTOR = 0.75;

    /** Constructors */
    public MyHashMap() {
        buckets = createTable(DEFAULT_INITIAL_SIZE);
        maxLoadFactor = DEFAULT_MAX_LOAD_FACTOR;
    }

    public MyHashMap(int initialSize) {
        buckets = createTable(initialSize);
        maxLoadFactor = DEFAULT_MAX_LOAD_FACTOR;
    }

    /**
     * MyHashMap constructor that creates a backing array of initialSize.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialSize initial size of backing array
     * @param maxLoad maximum load factor
     */
    public MyHashMap(int initialSize, double maxLoad) {
        buckets = createTable(initialSize);
        maxLoadFactor = maxLoad;
    }

    /**
     * Returns a new node to be placed in a hash table bucket
     */
    private Node createNode(K key, V value) {
        return new Node(key, value);
    }

    /**
     * Returns a data structure to be a hash table bucket
     *
     * The only requirements of a hash table bucket are that we can:
     *  1. Insert items (`add` method)
     *  2. Remove items (`remove` method)
     *  3. Iterate through items (`iterator` method)
     *
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     *
     * Override this method to use different data structures as
     * the underlying bucket type
     *
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */
    protected Collection<Node> createBucket() {
        return new LinkedList<>();
    }

    /**
     * Returns a table to back our hash table. As per the comment
     * above, this table can be an array of Collection objects
     *
     * BE SURE TO CALL THIS FACTORY METHOD WHEN CREATING A TABLE SO
     * THAT ALL BUCKET TYPES ARE OF JAVA.UTIL.COLLECTION
     *
     * @param tableSize the size of the table to create
     */
    @SuppressWarnings("unchecked")
    private Collection<Node>[] createTable(int tableSize) {
        Collection<Node>[] table = new Collection[tableSize];
        for (int i = 0; i < tableSize; i++) {
            table[i] = createBucket();
        }
        return table;
    }

    @Override
    public void clear() {
        buckets = createTable(DEFAULT_INITIAL_SIZE);
        keySet = new HashSet<>();
        size = 0;
    }

    @Override
    public boolean containsKey(K key) {
        int bucketIndex = Math.floorMod(key.hashCode(), buckets.length);
        if (buckets[bucketIndex] == null) {
            return false;
        }
        for (Node n : buckets[bucketIndex]) {   // iterate through a bucket
            if (n.key.equals(key)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public V get(K key) {
        if (!containsKey(key)) {
            return null;
        }
        int bucketIndex = Math.floorMod(key.hashCode(), buckets.length);
        for (Node n : buckets[bucketIndex]) {   // iterate through a bucket
            if (n.key.equals(key)) {
                return n.value;
            }
        }
        return null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void put(K key, V value) {
        int bucketIndex = Math.floorMod(key.hashCode(), buckets.length);
        if (containsKey(key)){
            for (Node n : buckets[bucketIndex]) {   // iterate through a bucket
                if (n.key.equals(key)) {
                    n.value = value;
                }
            }
        } else {
            size++;
            keySet.add(key);
            buckets[bucketIndex].add(createNode(key, value));
        }

        // better define a helper method resize()
        if ((double) size / buckets.length > maxLoadFactor) {
            Collection<Node>[] newBuckets = createTable(buckets.length * 2);
            for (Collection<Node> c : buckets) {
                if (c != null) {
                    for (Node n : c) {
                        int newBucketIndex = Math.floorMod(n.key.hashCode(), buckets.length * 2);
                        newBuckets[newBucketIndex].add(createNode(key, value));
                    }
                }
            }
            buckets = newBuckets;
        }
    }

    @Override
    public Set<K> keySet() {
        return keySet;
    }

    @Override
    public V remove(K key) {
        if (!containsKey(key)) {
            return null;
        }
        int bucketIndex = Math.floorMod(key.hashCode(), buckets.length);
        V val = null;
        for (Node n : buckets[bucketIndex]) {   // iterate through a bucket
            if (n.key.equals(key)) {
                val = n.value;
                buckets[bucketIndex].remove(n);
                size--;
                keySet.remove(key);
            }
        }
        return val;
    }

    @Override
    public V remove(K key, V value) {
        if (!containsKey(key)) {
            return null;
        }
        int bucketIndex = Math.floorMod(key.hashCode(), buckets.length);
        V val = null;
        for (Node n : buckets[bucketIndex]) {   // iterate through a bucket
            if (n.key.equals(key)) {
                if (!n.value.equals(value)) {
                    return null;
                }
                val = n.value;
                buckets[bucketIndex].remove(n);
                size--;
                keySet.remove(key);
            }
        }
        return val;
    }

    @Override
    public Iterator<K> iterator() {
        return new MyHashMapIterator();
    }

    private class MyHashMapIterator implements Iterator<K> {
        private int wizardPos;
        public MyHashMapIterator() {  // always remember the constructor !!!
            wizardPos = 0;
        }

        @Override
        public boolean hasNext() {
            return wizardPos < size;
        }

        @Override
        public K next() {
            if (!hasNext()) {
                return null;
            }

            // since we can't derive the i-th element directly (by index)
            K returnKey = null;
            int posHelper = 0;
            for (K keyItem : keySet) {
                if (posHelper == wizardPos) {
                    returnKey = keyItem;
                    break;
                }
                posHelper++;
            }
            wizardPos++;
            return returnKey;
        }
    }

}
