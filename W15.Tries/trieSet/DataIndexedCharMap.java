package trieSet;

public class DataIndexedCharMap<V> {
    // stores the items of all children in an array
    private V[] items;
    public DataIndexedCharMap(int R) {
        items = (V[]) new Object[R];
        // in java, you can't create a generic array, instead, use casting.
    }
}
