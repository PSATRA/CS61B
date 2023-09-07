public class VengefulSLList<Item> extends SLListCopy<Item> {
    /** Can do everything SLList can do, but can also print all the items that have been banished by removeLast(). */

    SLList<Item> deletedItems;

    public VengefulSLList() {
        /** @NOTICE: If you have variables, definitely remember to add a constructor. */
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
     */

    public void printLostItems() {
        deletedItems.print();
    }
}
