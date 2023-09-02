public class SLList {
    /**
     * Advantage of setting an individual node class:
     * 1. Not a naked recursive class, easy to manipulate, like avoid using this, but "first"
     * @NOTICE: 2. Support epic customized members for ***caching*** - private int size
     *    You can also store the maximum, the minimum ...
     *    Basically, recursion and iteration are avoided, saving a BUNCH of time
     * 3. remember to add s to the first word if it's a verb
     */


    /**
     * Nested class or subordinate class
     * doesn't matter the order where you put it in a class
     * can be private
     * @NOTICE: ***static*** class: cannot access to the outer class, and save a little memory   --discuss later
     */
    private static class intNode {
        public int item;
        public intNode next;
        public intNode(int i, intNode n) {
            item = i;
            next = n;
        }
    }

    private int size; // create a ******CACHE******

    // private intNode first; // this
    // variable first is powerful, it prevents us from assigning this manually
    /**
     * @NOTICE: Set to ***private***: prevent other troublemaker class modify first, like:
     *  first.next.next = first.next; since it doesn't copy the value, but copies the reference
     * Also, other users don't need to manipulate first
     * A nice analogy: a car with public and private
     */
    private final intNode sentinel;
    /**
     * In order that the object is empty, bun much better than discussing in every method,
     *  notably the data structure is huge.
     * We can only manipulate/modify sentinel.next, rather than sentinel itself,
     * @NOTICE: that's why we add ***final*** !!!, but we can still modify sentinel.next
     */

    public SLList() {
        sentinel = new intNode(19999, null);
        size = 0;
    }
    public SLList(int x) {
        sentinel = new intNode(19999, null);
        sentinel.next = new intNode(x, null);
        size = 1;
    }

    /* Adds an item to the front of the list. */
    public void addFirst(int x) {
        sentinel.next = new intNode(x, sentinel.next);
        size++;
    }

    /* Adds an item to the end of the list. */
    public void addLast(int x) {
        /**
         * @NOTICE: a very common way to ***TRAVERSE*** the nodes
         */
        intNode p = sentinel; // this.first manage the same memory block!!!
        while (p.next != null) {
            p = p.next; // does not change first,
        }
        p.next = new intNode(x, null);
        /** @NOTICE: don't write p = ..., cuz the list isn't connected, see this in java visualizer. */
        size++;
    }

    /* Retrieves the ith item from the list. */
    public int get(int i) {
        if  (i >= size) {    // if out of range
            return 42;
        }
        intNode p = sentinel;
        for (int j = 0; j < i; j++) {
            p = p.next;
        }
        return p.next.item;
    }

    /**
     * Returns the size rapidly no matter how long the object is!
     * It's so much better than size()!
     */
    public int epicSize() {
        return size;
    }

    /**
    private static int size(intNode p) {    // Helper method: Returns the size of the list starting at intNode p.
        if (p.next == null) {
            return 1;
        }
        return 1 + size(p.next);
    }
    // Returns the number of items in the list using recursion.
    public int size() { return size(sentinel) - 1; }

         // This also can be done by iteration, without a helper method:
         * int out = 0;
         * intNode p = first;
         *    while (p != null) {
         *    p = p.next;
         *    out++; }
         * return out;
    */


    public static void main (String[] args) {
        SLList l = new SLList();
        l.addFirst(1);
        l.addLast(2);

        System.out.println(l.epicSize());

        // print the whole elements
        for (int i = 0; i < l.epicSize(); i++) {
            System.out.print(l.get(i) + " ");
        }
    }
}
