package dogCompare;

import java.util.Comparator;

public class Dog implements Comparable<Dog> {
    // java offers Comparable<T>, with compareTo(T) inside.
    /**
     * https://joshhug.gitbooks.io/hug61b/content/chap4/chap43.html
     *
     * https://www.youtube.com/watch?v=m2F-ekp_BRU
     * https://www.youtube.com/watch?v=nYPPbbkKF1w
     * https://www.youtube.com/watch?v=QRPVJ7Wxxtk
     * https://www.youtube.com/watch?v=dbdbcbhe3Jk&t=1s
     * https://www.youtube.com/watch?v=iQoN9bt8GJc
     * https://www.youtube.com/watch?v=1oow3NGoExg&t=614s
     */
    private final String name;
    private final int size;

    public Dog (String n, int s) {
        name = n;
        size = s;
    }

    public void bark() {
        System.out.println(name + " says: bark!");
    }

    /** Return a negative number if this dog is less than the dog pointed by 'obj', and so forth. */
    public int compareTo(Dog obj) {
        return this.size - obj.size;
    }

    private static class NameComparator implements Comparator<Dog> {
        public int compare(Dog a, Dog b) {
            return a.name.compareTo(b.name);
            /**
             * name.compareTo is not the compareTo method before, it belongs to Comparator.
             * Comparator provides compareTo that can compare different types like a.name/b.name.
             */
        }
    }

    public static Comparator<Dog> getNameComparator() {
        return new NameComparator();
    }
}
