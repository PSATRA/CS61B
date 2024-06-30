package map61B;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.List;
import java.util.ArrayList;

/***
 * An array-based implementation of Map61B.
 ***/
public class ArrayMap<KK, VV> implements Map61B<KK, VV> {
    // <KK, VV> is generic symbol for a map, KK and VV are arbitrary names.

    private final KK[] keys;
    /**
     * @NOTICE: 'final' limits the memory location here,
     * since it's reference type.
     */

    private final VV[] values;

    int size;

    public ArrayMap() {
        keys = (KK[]) new Object[100];
        // Arrays can't be generic, and remember to set 100/...
        values = (VV[]) new Object[100];
        size = 0;
    }

    /** Returns the index of the key, if it exists. Otherwise returns -1. */
    private int keyIndex(KK key) {
        for (int i = 0; i < size; i++) {
            if (keys[i].equals(key)) {
                return i;
            }
        }
        return -1;
    }
    /**
     * @NOTICE: "==" for reference type is for checking whether they are pointing
     * the same address!!! But here we just want to check if they have the same
     * value.
     *
     * 'final' for reference type means the address is final, not the value, and
     * the reference cannot point to other address, e.g. `buffer` in proj1 guitar.
     */

    /** Checks if map contains the key. */
    public boolean containsKey(KK key) {
        return keyIndex(key) > -1;
    }

    /** Associate key with value. Replace the value if the key exists, otherwise insert. */
    public void put(KK key, VV value) {
        if (keyIndex(key) == -1) {
            keys[size] = key;
            values[size] = value;
            size += 1;
        } else {
            values[keyIndex(key)] = value;
        }
    }

    /** Returns value, assuming key exists. */
    public VV get(KK key) {
        return values[keyIndex(key)];
    }

    /** Returns number of keys. */
    public int size() {
        return size;
    }

    /** Return all the keys. */
    public List<KK> keys() {
        List<KK> keyList = new ArrayList<>();
        for (int i = 0; i < size; i++) {        // not i < keys.length, since it's 100!!!
            keyList.add(keys[i]);
        }
        // For loop can be replaced with: Collections.addAll(keyList, keys);
        return keyList;
    }

    @Test
    public void test() {
        ArrayMap<Integer, Integer> am = new ArrayMap<Integer, Integer>();
        am.put(2, 5);
        Integer expected = 5;
        assertEquals(expected, am.get(2));
    }
}