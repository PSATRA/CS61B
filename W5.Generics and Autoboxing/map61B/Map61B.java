package map61B;
import java.util.List;

public interface Map61B<K, V> {
    /** Checks if map contains the key. */
    boolean containsKey(K key);

    /** Returns value, assuming key exists., otherwise no defined behavior */
    V get(K key);

    /** Returns number of key-value mapping. */
    int size();

    /** Associate key with value. Replace the value if the key exists, otherwise insert. */
    public void put(K key, V value);

    /** Returns a list(ArrayList) of keys in this map, not array! */
    List<K> keys();
}
