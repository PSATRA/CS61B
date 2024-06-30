package inheritance;

/* Represent a list of stuff, where all the "list" work is delegated
 * to a naked recursive data structure. */


public class SLList<Item> implements List61B<Item> {
    private class Node {
        private final Item item;
        private Node next;

        Node(Item i, Node h) {
            item = i;
            next = h;
        }
    }

    private final Node sentinel;
    private int size;

    /** Creates an empty list. */
    public SLList() {
        size = 0;
        sentinel = new Node(null, null);
    }

    public SLList(Item x) {
        size = 1;
        sentinel = new Node(null, null);
        sentinel.next = new Node(x, null);
    }

    /** Adds an item of the front. */
    public void addFirst(Item x) {
        Node oldFrontNode = sentinel.next;
        Node newNode = new Node(x, oldFrontNode);
        sentinel.next = newNode;
        size += 1;
    }

    /** Gets the front item of the list. */
    public Item getFirst() {
        return sentinel.next.item;
    }

    /** Puts an item at the back of the list. */
    public void addLast(Item x) {
        size += 1;

        Node p = sentinel;

        /* Move p until it reaches the end. */
        while (p.next != null) {
            p = p.next;
        }

        p.next = new Node(x, null);
    }

    /** Returns the back node of our list. */
    private Node getLastNode() {
        Node p = sentinel;

        /* Move p until it reaches the end. */
        while (p.next != null) {
            p = p.next;
        }
        return p;
    }

    /** Returns last item */
    public Item getLast() {
        Node back = getLastNode();
        return back.item;
    }

    /** Deletes and returns last item. */
    public Item removeLast() {
        Node back = getLastNode();
        if (back == sentinel) {
            return null;
        }

        size = size - 1;
        Node p = sentinel;

        while (p.next != back) {
            p = p.next;
        }
        p.next = null;
        return back.item;
    }
    /** Or not use getLastNode():
     *      public Item removeLast() {
     *          size--;
     *          StuffNode p = sentinel;
     *          while (p.next != null) {
     *               p = p.next;
     *          }
     * @NOTICE: don't write 'p = null;'
     *
     *          StuffNode q = sentinel;
     *          while(q.next != p) {
     *              q = q.next;
     *          }
     *          q.next = null;
     *          return q.item;
     *      }
     */

    public int size() {
        return size;
    }

    /** Gets the positionth item of the list. */
    public Item get(int position) {
        if (position == 0) {
            return getFirst();
        }
        Node currentNode = sentinel.next.next;
        while (position > 1 && currentNode.next != null) {
            position -= 1;
            currentNode = currentNode.next;
        }

        return currentNode.item;
    }

    /** Inserts item into given position.
     * Code from discussion #3 */
    public void insert(Item item, int position) {
        if (sentinel.next == null || position == 0) {
            addFirst(item);
            return;
        }

        Node currentNode = sentinel.next.next;
        while (position > 1 && currentNode.next != null) {
            position -= 1;
            currentNode = currentNode.next;
        }

        Node newNode = new Node(item, currentNode.next);
        currentNode.next = newNode;
    }

    /** TODO: Add a print method that overrides List61B's inefficient print method. */
    @Override
    public void print() {
        if (size == 0) {
            System.out.println("null");
        }
        Node p = sentinel;
        while (p.next != null) {
            System.out.println(p.next.item);  // not `p.item`!
            p = p.next;
        }
    }

}