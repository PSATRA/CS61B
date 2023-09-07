public class VengefulSLList<Item> extends SLListCopy<Item> {
    /** Can do everything SLList can do, but can also print all the items that have been banished by removeLast(). */

    SLList<Item> deletedItems;

    public VengefulSLList() {
        /** @NOTICE: If you have variables, definitely remember to add a constructor. */

        super(); // Must come first!
        /**
         * @Term: super(...): Put it at the top. Can be omitted, but if there are several new constructors, Java's
         * implicit call may not be what we intend to call. And, what if you need super(x) or just super(5)?
         * So just don't omit, call it explicitly!
         */

        deletedItems = new SLList<>();
    }
    public VengefulSLList(Item x) {  // Super(...) cannot be omitted!!!
        super(x);
        deletedItems = new SLList<>();
    }


    @Override
    public Item removeLast() {
        Item x = super.removeLast();
        deletedItems.addLast(x);
        return x;
    }
    /**
     * Overrides the removeLast() of the superclass. If you need the original implementation and
     * just want to do something on the basis of it, use `super.XXX()`.
     * @Term: super
     *
     * If you just copy the original code from superclass without using `super`, it normally won't
     * work, since you didn't inherit the private members in the original implementation.
     */

    public void printLostItems() {
        deletedItems.print();
    }
}
