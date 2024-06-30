package inheritance;

public class RotatingSLList<Item> extends SLList<Item> {
    /** This is different from an interface. */
    /**
     * Suppose we want to build an inheritance.RotatingSLList that
     * has the same functionality as the SLList like addFirst, size,
     * etc., but with an additional rotateRight operation to bring
     * the last item to the front of the list.
     */

    /**
     * The `extends` keyword lets us `keep` the original functionality of SLList, while
     * enabling us to make `modifications` and `add` additional functionality.
     *
     * Counter-examples: private members and constructors cannot be inherited.
     * So there's a `super();` in the constructor.
     */

    public void rotateRight() {
        Item x = this.removeLast(); // this. can actually be omitted.
        this.addFirst(x);
    }
    /**
     * @NOTICE: Always remember the essential feature of linked list, try to only
     * modify the elements in the front and the end, so don't traverse and move
     * them consecutively.
     */
}
