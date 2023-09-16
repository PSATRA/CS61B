package map61B;

import java.util.List;

public class MapHelper {
    /**
     * @NOTICE: Instead of making the class generic, we make methods generic, before the return type.
     */

    /* Returns the value corresponding to the given key in the map if it exists, otherwise null. */
    public static <K, V> V get(Map61B<K, V> m, K key) {
        if (m.containsKey(key)) {
            return m.get(key);
        }
        return null;
    }

    /* Returns the maximum of all keys in the given ArrayMap. Works only if keys can be compared. */
    public static <K extends Comparable<K>, V> K maxKey(Map61B<K, V> m) {
        /**
         * Mind the extends syntax here, it actually means must be a subtype of.
         * @Term: The keyword extends here is used as a 'type upper bound'.
         *
         * When used with generics (like in generic method headers), extends imposes a constraint
         * rather than grants new abilities. Extends simply states a fact: You must be a subclass
         * of whatever you're extending.
         */
        List<K> keyList = m.keys(); // Doesn't need new, cuz we just want a representative.
        K max = keyList.get(0);

        /** @NOTICE: How to traverse the elements in a list. */
        for (K k: keyList) {
            if (k.compareTo(max) > 0) {  // compareTo
                max = k;
            }
        }
        return max;
        /**
         * @NOTICE: We can't do k > max, so we need compareTo method, where we need to extend the comparable<T>.
         */
    }
}
