package deque;

import java.util.Iterator;

public class LinkedListDeque<T> implements Iterable<T> {

    private class Node {
        public Node _prev;
        public T _item;
        public Node _next;
        public Node(Node prev, T item, Node next) {
            _prev = prev;
            _item = item;
            _next = next;
        }

        public T getHelper(int index) {
            if (index == 0) {
                T ret =  _recurUse._next._item;
                _recurUse = _sentinel;
                return ret;
            }
            _recurUse = _recurUse._next;
            return _recurUse.getHelper(index - 1);
        }
    }

    private int _size;
    private final Node _sentinel;
    private Node _recurUse;

    public LinkedListDeque() {
        _size = 0;
        _sentinel = new Node(null, null, null);
        _recurUse = _sentinel;
        _sentinel._prev = _sentinel;
        _sentinel._next = _sentinel;
    }


    /**
     * Adds an item of type T to the front of the deque.
     * @param item: the item being added
     */
    public void addFirst(T item) {
        _sentinel._next = new Node(_sentinel, item, _sentinel._next);
        _sentinel._next._next._prev = _sentinel._next;
        _size++;
    }

    /**
     * Adds an item of type T to the back of the deque.
     * @param item: the item being added
     */
    public void addLast(T item) {
        _sentinel._prev = new Node(_sentinel._prev, item, _sentinel);
        _sentinel._prev._prev._next = _sentinel._prev;
        _size++;
    }

    /**
     * @return true if deque is empty, false otherwise
     */
    public boolean isEmpty() {
        return _size == 0;
    }

    /**
     * @return the number of items in the deque
     */
    public int size() {
        return _size;
    }

    /**
     * Prints the items in the deque from first to last, separated by a space.
     * Once all the items have been printed, print out a new line.
     */
    public void printDeque() {
        Node p = _sentinel;
        while (p._next != _sentinel) {
            System.out.print(p._next._item + " ");
            p = p._next;
        }
        System.out.println();
    }

    /**
     * Removes and returns the item at the front of the deque. If no such item exists, returns null.
     * @return the item at the front of the deque, null if no such item exists
     */
    public T removeFirst() {
        if (_size == 0) {
            return null;
        }
        T firstItem = _sentinel._next._item;

        Node newFirstNode = _sentinel._next._next;
        newFirstNode._prev = _sentinel;
        _sentinel._next = newFirstNode;

        _size--;
        return firstItem;
    }

    /**
     * Removes and returns the item at the back of the deque. If no such item exists, returns null.
     * @return the item at the back of the deque, null if no such item exists
     */
    public T removeLast() {
        if (_size == 0) {
            return null;
        }
        T lastItem = _sentinel._prev._item;

        Node newLastNode = _sentinel._prev._prev;
        newLastNode._next = _sentinel;
        _sentinel._prev = newLastNode;
        /**
         * @NOTICE: Doesn't need to null out the node manually, with no reference, its automatically deleted !!! Remember
         * that the Java garbage collector will “delete” things for us if and only if there are no pointers to that object.
         */

        _size--;
        return lastItem;
    }

    /**
     * Gets the item at the given index using iteration, where 0 is the front. If no such item exists,
     * returns null. Must not alter the deque!
     * @param index: the index of the desired item
     * @return the desired item
     */
    public T get(int index) {
        if (index >= _size) {
            return null;
        }
        Node p = _sentinel._next;
        for (int i = 0; i < index; i++) {
            p = p._next;
        }
        return p._item;
    }

    /**
     * Same as get, but uses recursion. Must not alter the deque!
     * @param index: the index of the desired item
     * @return the desired item
     */
    public T getRecursive(int index) {
        if (index >= _size) {
            return null;
        }
        return _recurUse.getHelper(index);
    }

    /**
     * The Deque objects we’ll make are iterable (i.e. Iterable<T>) so we must provide this method to return an iterator.
     * @return an iterator of the deque
     */
    @Override
    public Iterator<T> iterator() {
        return new LinkedListDequeIterator();
    }

    public class LinkedListDequeIterator implements Iterator<T> {
        private int wizardPos;
        public LinkedListDequeIterator() {
            wizardPos = 0;
        }

        @Override
        public boolean hasNext() {
            return wizardPos < _size;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                return null;
            }
            Node p = _sentinel._next;
            for (int i = 0; i < wizardPos; i++) {
                p = p._next;
            }
            wizardPos++;
            return p._item;
        }
    }

    /**
     * Returns whether or not the parameter o is equal to the Deque. o is considered equal if it is
     * a Deque and if it contains the same contents in the same order.
     * @param o: another object to be compared
     * @return whether or not the parameter o is equal to the Deque
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (!(o instanceof LinkedListDeque)) {
            return false;
        }
        LinkedListDeque<T> testObj = (LinkedListDeque<T>) o;
        if (testObj.size() != this.size()) {
            return false;
        }
        for (int i = 0; i < _size; i++) {
            if (testObj.get(i) != get(i)) {
                return false;
            }
        }
        return true;
    }

    /*public static void main(String[] args) {
        LinkedListDeque<Integer> l = new LinkedListDeque<>();
        l.addLast(7);
        l.addLast(6);
        l.addLast(5);

        l.addFirst(7);
        l.addFirst(6);
        l.addFirst(5);

        l.printDeque();
        System.out.println(l.getRecursive(4));
       // l.printDeque();
    }*/
}
