public class SLList<ArbiType> {
    /**
     * @NOTICE: Generic class: When instantiating: SLList<String> s = new SLList<>("bone");
     * However, can't replace primitive type. You need conversion.
     */

    /**
     * Advantage of setting an individual node class:
     * Not a naked recursive class, easy to manipulate, like avoid using this, but "first".
     *
     * @NOTICE: Support epic customized members for ***caching*** - private int size. You can also store the maximum,
     * the minimum ... Basically, recursion and iteration are avoided, saving a BUNCH of time.
     * @NOTICE: Anywhere or anything that you want to manipulate, just add a cash there. E.g. do addLast()
     * without iteration cache the last reference(node).
     */


    /**
     * Nested class or subordinate class.
     * Doesn't matter the order where you put it in a class.
     * Can be private.
     * @NOTICE: ***static*** class: cannot access to the outer class, and save a little memory. --discuss later
     */
    private class StuffNode {
        public ArbiType item;
        public StuffNode next;
        public StuffNode(ArbiType i, StuffNode n) {
            item = i;
            next = n;
        }
    }

    private int size; // create a ******CACHE******

    // private intNode first; // this
    // variable first is powerful, it prevents us from assigning this manually.
    /**
     * @NOTICE: Set to ***private***: prevent other troublemaker class modify first, like:
     *  first.next.next = first.next; since it doesn't copy the value, but copies the reference.
     * Also, other users don't need to manipulate variable first.
     * A nice analogy: a car with public and private.
     */
    private final StuffNode sentinel;
    /**
     * In order that the object is empty, bun much better than discussing in every method,
     *  notably the data structure is huge.
     * We can only manipulate/modify sentinel.next, rather than sentinel itself,
     * @NOTICE: that's why we add ***final*** !!!, but we can still modify sentinel.next .
     */

    public SLList() {
        sentinel = new StuffNode(null, null);
        size = 0;
    }
    public SLList(ArbiType x) {
        sentinel = new StuffNode(null, null);
        sentinel.next = new StuffNode(x, null);
        size = 1;
    }

    /** Adds an item to the front of the list. */
    public void addFirst(ArbiType x) {
        sentinel.next = new StuffNode(x, sentinel.next);
        size++;
    }

    /** Adds an item to the end of the list. */
    public void addLast(ArbiType x) {
        /**
         * @NOTICE: a very common way to ***TRAVERSE*** the nodes.
         */
        StuffNode p = sentinel; // this.first manage the same memory block!!!
        while (p.next != null) {
            p = p.next; // Does not change first.
        }
        p.next = new StuffNode(x, null);
        /** @NOTICE: don't write p = ..., cuz the list isn't connected, see this in java visualizer. */
        size++;
    }

    /** Retrieves the ith item from the list. */
    public ArbiType get(int i) {
        if  (i >= size) {    // If is out of range.
            return null;
        }
        StuffNode p = sentinel;
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
        SLList<Integer> l = new SLList<>();
        l.addFirst(1);
        l.addLast(2);

        System.out.println(l.epicSize());

        for (int i = 0; i < l.epicSize(); i++) {  // Print the whole elements.
            System.out.print(l.get(i) + " ");
        }
    }
}
