import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ArraySet<T> implements Iterable<T> {  // make the class support: for(T item : set)
    private T[] items;
    private int size; // the next item to be added will be at position size

    public ArraySet() {
        items = (T[]) new Object[100];
        size = 0;
    }

    /* Returns true if this map contains a mapping for the specified key. */
    public boolean contains(T x) {
        //if (x == null) {
        //    throw new IllegalArgumentException("You can't add null to an ArraySet.");
        //}
        for (int i = 0; i < size; i += 1) {
            if (items[i].equals(x)) {  // not ==, we only need value, not address
                return true;
            }
        }
        return false;
    }

    /* Associates the specified value with the specified key in this map. */
    public void add(T x) {
        if (x == null) {
            return;
        }
        if (contains(x)) {
            return;
        }
        items[size] = x;
        size += 1;
    }

    /* Returns the number of key-value mappings in this map. */
    public int size() {
        return size;
    }

    /** returns an iterator (a.k.a. seer) into ME */
    public Iterator<T> iterator() {
        return new ArraySetIterator();
    }

    public class ArraySetIterator implements Iterator<T> {
        private int wizardPos;
        public ArraySetIterator() {  // always remember the constructor !!!
            wizardPos = 0;
        }

        @Override
        public boolean hasNext() {
            return wizardPos < size;
        }

        @Override
        public T next() {
            T returnItem = items[wizardPos];
            wizardPos++;
            return returnItem;
        }
    }

    /*@Override - Cumbersome version using StringBuilder and append rather than join.
    public String toString() {
        /**
         * @NOTICE: Whenever you manipulate String or other immutable data type, reduce the modification times !!!
         *
        StringBuilder returnSB = new StringBuilder("{");
        for (int i = 0; i < this.size - 1; i++) {
            returnSB.append(items[i]); // returnSB += items[i] is slow
            returnSB.append(", ");
        }
        returnSB.append(items[size - 1]);
        returnSB.append("}");
        return returnSB.toString();
    }*/
    @Override
    public String toString() {
        List<String> listOfItems = new ArrayList<>();
        for (T item : this) {    // not (T item : items) !!!
            listOfItems.add(item.toString());
        }
        return "{" + String.join(", ", listOfItems) + "}";
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        /**
         * @NOTICE: When overriding `equal`, always remember the optimization, avoiding meaningless
         * iteration and checking for itself, like c++.
         */
        if (other == null) {
            return false;
        }
        if (other.getClass() != this.getClass()) {    // also inherit the Object class, returning the type of the instance
            return false;
        }
        ArraySet<T> o = (ArraySet<T>) other;
        if (o.size() != this.size()) {
            return false;
        }
        for (T item : this) {
            if (!o.contains(item)) {
                return false;
            }
        }
        return true;
    }

    public static <Glerp> ArraySet<Glerp> of(Glerp... stuff) {
        // static method doesn't know T, the generic type, so make the method generic and not the same as T.
        ArraySet<Glerp> returnSet = new ArraySet<>();
        for (Glerp x : stuff) {
            returnSet.add(x);
        }
        return returnSet;
    }

    /** Also to do:
    1. Make ArraySet implement the Iterable<T> interface.
    2. Implement a toString method.
    3. Implement an equals() method.
    */

    public static void main(String[] args) {
        ArraySet<Integer> aset = new ArraySet<>();
        aset.add(null);
        aset.add(5);
        aset.add(23);
        aset.add(42);
        //equals
        ArraySet<Integer> aset2 = new ArraySet<>();
        aset2.add(5);
        aset2.add(23);
        aset2.add(42);

        /* Doesn't need to call the toString manually, since the println() with automatically call toString() if
        its parameter is not a String type.
         */
        System.out.println(aset);

        System.out.println(aset.equals(aset2));
        System.out.println(aset.equals(null));
        System.out.println(aset.equals("fish"));
        System.out.println(aset.equals(aset));

        ArraySet<String> asetOfString = ArraySet.of("mind", "the", "blind static method");
        System.out.println(asetOfString);

        /**
         @NOTICE:
        // Ugly iterator
        Iterator<Integer> aseer = as.iterator();
        // need an iterator `type`, so build a nested class !!!
        // Let's start by thinking about what the compiler need to know in order to successfully compile.
        while (aseer.hasNext()) {
            System.out.println(aseer.next());
        }

         // The shorthand for the ugly iterator.
         for (Integer item : aset) {
         System.out.println(item);
         @NOTICE: It's not (T i : items), it's (T i : this/instance) !!!
         }
        */
    }

}