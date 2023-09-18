package deque;

import java.util.Iterator;

public class ArrayDeque<T> implements Iterable<T>, Deque<T> {
    private T[] _items;
    private int _nextFirst;
    private int _nextLast;
    private int _size;

    public ArrayDeque() {
        _items = (T[]) new Object[8];
        _nextFirst = 3;
        _nextLast = 4;
        _size = 0;
    }

    /** Resize the underlying array to a larger target capacity. */
    private void resize(int capacity) {
        T[] newArr = (T[]) new Object[capacity];
        if (capacity > _items.length) {
            if (_nextFirst + 2 < _nextLast) {
                System.arraycopy(_items, 0, newArr, 4, _size);
            } else {
                System.arraycopy(_items, _nextLast, newArr, 4, _size - _nextLast);
                System.arraycopy(_items, 0, newArr, 4 + _size - _nextLast, _nextLast);
            }
            _nextFirst = 3;
            _nextLast = 4 + _size;
        } else {
            for (int i = 0; i < _size; i++) {
                newArr[i] = get(i);
            }
            _nextFirst = capacity - 1;
            _nextLast = _size;
        }
        _items = newArr;
    }

    private void shrinkSize() {
        if (isEmpty()) {
            resize(8);
        } else if (_items.length / 4 > _size && _size >= 4) {
            resize(_size * 2);
        }
    }

    @Override
    /**
     * Adds an item of type T to the front of the deque.
     * @param item: the item being added
     */
    public void addFirst(T item) {
        _items[_nextFirst] = item;
        _size++;
        if (_size == _items.length) {
            _nextFirst--;
            resize(_size * 2);
        } else if (_nextFirst == 0) {
            _nextFirst = _items.length - 1;
        } else {
            _nextFirst--;
        }
    }

    /**
     * Adds an item of type T to the back of the deque.
     * @param item: the item being added
     */
    @Override
    public void addLast(T item) {
        _items[_nextLast] = item;
        _size++;
        if (_size == _items.length) {
            _nextLast++;
            resize(_size * 2);
        } else if (_nextLast == _items.length - 1) {
            _nextLast = 0;
        } else {
            _nextLast++;
        }
    }

    /**
     * @return the number of items in the deque
     */
    @Override
    public int size() {
        return _size;
    }

    /**
     * Prints the items in the deque from first to last, separated by a space.
     * Once all the items have been printed, print out a new line.
     */
    @Override
    public void printDeque() {
        for (int i = 0; i < _size; i++) {
            System.out.print(get(i) + " ");
        }
        System.out.println();
    }

    /**
     * Removes and returns the item at the front of the deque. If no such item exists, returns null.
     * @return the item at the front of the deque, null if no such item exists
     */
    @Override
    public T removeFirst() {
        if (_size == 0) {
            return null;
        }
        T firstItem = get(0);
        if (_nextFirst == _items.length - 1) {
            _items[0] = null;
            _nextFirst = 0;
        } else {
            _items[_nextFirst + 1] = null;
            _nextFirst++;
        }
        _size--;
        shrinkSize();
        return firstItem;
    }

    /**
     * Removes and returns the item at the back of the deque. If no such item exists, returns null.
     * @return the item at the back of the deque, null if no such item exists
     */
    @Override
    public T removeLast() {
        if (_size == 0) {
            return null;
        }
        T lastItem = get(_size - 1);
        if (_nextLast == 0) {
            _items[_items.length - 1] = null;
            _nextLast = _items.length - 1;
        } else {
            _items[_nextLast - 1] = null;
            _nextLast--;
        }
        _size--;
        shrinkSize();
        return lastItem;
    }

    /**
     * Gets the item at the given index using iteration, where 0 is the front. If
     * no such item exists, returns null. Must not alter the deque!
     * @param index: the index of the desired item
     * @return the desired item
     */
    @Override
    public T get(int index) {
        if (index >= _size) {
            return null;
        }
        int fakeInd = (_nextFirst + 1 + index) % _items.length;
        return _items[fakeInd];
    }

    /**
     * The Deque objects we’ll make are iterable (i.e. Iterable<T>) so we must
     * provide this method to return an iterator.
     * @return an iterator of the deque
     */
    @Override
    public Iterator<T> iterator() {
        return new ArrayDequeIterator();
    }

    public class ArrayDequeIterator implements Iterator<T> {
        private int wizardPos;
        public ArrayDequeIterator() {
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
            T returnItem = get(wizardPos);
            wizardPos++;
            return returnItem;
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
        if (o == null) {
            return false;
        }
        if (this == o) {
            return true;
        }
        if (!(o instanceof Deque)) {
            return false;
        }
        Deque<T> testObj = (Deque<T>) o;
        if (testObj.size() != size()) {
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
        ArrayDeque<Integer> l = new ArrayDeque<>();

        l.addLast(3);
        l.addLast(4);
        l.addLast(5);
        l.addLast(6);
        l.addLast(7);
        l.addLast(8);
        //l.addLast(9);

        //l.addFirst(5);
        l.addFirst(2);
        l.addFirst(1);
        //l.addFirst(8);
        //l.addFirst(7);
        //l.addFirst(7);

        //System.out.println(l.removeFirst());
        //System.out.println(l.removeFirst());

        //System.out.println(l.removeLast());
        //System.out.println(l.removeFirst());

        l.printDeque();
        System.out.println(l.get(7));
        // l.printDeque();
    }*/
}
