/** Array based list.
 *  @author Josh Hug
 */

//         0 1  2 3 4 5 6 7
// items: [6 9 -1 2 0 0 0 0 ...]
// size: 5

/* Invariants:
 addLast: The next item we want to add, will go into position size
 getLast: The item we want to return is in position size - 1
 size: The number of items in the list should be size.
*/

public class AList<Item> {
    private Item[] items;
    private int size;

    /** Creates an empty list. */
    public AList() {
        items = (Item[]) new Object[100]; // Special syntax, java doesn't allow (direct) generic Array.
        size = 0;
    }

    /** Prints out the entire list. */
    public void print() {
        for (int i = 0; i < size(); i += 1) {
            System.out.print(get(i) + " ");
        }
        System.out.println();
    }

    /** Resize the underlying array to a larger target capacity. */
    public void resize(int capacity) {
        Item[] a = (Item[]) new Object[capacity];
        System.arraycopy(items, 0, a, 0, size);
        items = a;
    }

    /**
     * Inserts X into the back of the list.
     * However, it turns out to be time-wasting, with a parabola, if just do:
     * resize(size + 1);
     *
     * @NOTICE: Good, but this can cause low usage rate, tweaking?
     */
    public void addLast(Item x) {
        // Resize work
        if (size == items.length) {
            resize(size * 4);
        }

        items[size] = x;
        size = size + 1;
    }

    /** Returns the item from the back of the list. */
    public Item getLast() {
        return items[size - 1];
    }
    /** Gets the ith item in the list (0 is the front). */
    public Item get(int i) {
        return items[i];
    }

    /** Returns the number of items in the list. */
    public int size() {
        return size;
    }

    /** Deletes item from back of the list and
     * returns deleted item. */
    public void removeLast() {
        items[size - 1] = null;
        /**
         * @NOTICE: Normally, we don't need to set the last element to zero, since the users cannot even reach there!!!
         *
         * @NOTICE: However, with generic objects, we do want to null out references to the objects that we're storing.
         * This is to avoid "loitering". Recall that Java garbage collector only destroys objects when the last reference has been
         * lost. If we fail to null out the reference, then Java will not garbage collect the objects that have been
         * added to the list.
         * I.e. since it(<Item>) is a reference, it will never be deleted without the statement!
         */
        size--;
    }
}
