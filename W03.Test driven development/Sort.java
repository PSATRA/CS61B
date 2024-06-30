public class Sort {
    /**
     * @NOTICE: Selection sort algorithm:
     * 1. find the smallest item
     * 2. move it to the front
     * 3. selection sort the rest (using recursion?)
     */

    /** Returns the smallest string in x.
     * @source Got help with string compares from https://goo.gl/a7yBU5. */
    public static int findSmallest(String[] x, int start) {
        int smallestIndex = start;
        for (int i = start; i < x.length; i += 1) {
            int cmp = x[i].compareTo(x[smallestIndex]); // Here
            if (cmp < 0) {
                smallestIndex = i;
            }
        }
        return smallestIndex;
    }

    public static void swap(String[] x, int a, int b) {
        String temp = x[a];
        x[a] = x[b];
        x[b] = temp;
    }

    /**
     * @NOTICE: A typical solution is to create a private helper method
     * that has an additional parameter (or parameters) that delineate
     * which part of the array to consider. This approach is quite common
     * when trying to use recursion on a data structure that is not
     * inherently recursive, e.g. arrays.
     *
     * @param x: the data structure that is not inherently recursive.
     * @param start: delineate which part of the array to consider,
     *             here it is an index.
     */
    public static void sort(String[] x, int start) {
        // Helper method: sorts strings destructively starting from item start.
        if (start == x.length) {
            return;
        }
        int smallestIndex = findSmallest(x, start);
        swap(x, start, smallestIndex);
        sort(x, start + 1);
        /**
         * Doesn't need to increment start, it's similarly done by parameter passing.
         * As is shown in the compiler, start: start + 1.
         */
    }

    /** Final result. */
    public static void sort(String[] x) {
        sort(x, 0);
    }
}
