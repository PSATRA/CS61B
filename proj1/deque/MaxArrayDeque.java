package deque;

import java.util.Comparator;

public class MaxArrayDeque<T> extends ArrayDeque<T>{

    private Comparator<T> _c;

    public MaxArrayDeque(Comparator<T> c) {
        _c = c;
    }

    /**
     * Returns the maximum element in the deque as governed by the previously given Comparator.
     * If the MaxArrayDeque is empty, simply return null.
     * @return the maximum element, null if empty
     */
    public T max() {
        return max(_c);
    }

    /**
     * Returns the maximum element in the deque as governed by any Comparator c.
     * If the MaxArrayDeque is empty, simply return null.
     * @param c any given Comparator
     * @return the maximum element, null if empty
     */
    public T max(Comparator<T> c) {
        if (isEmpty()) {
            return null;
        }
        T maxItem = get(0);
        for (int i = 1; i < size(); i++) {
            if (c.compare(maxItem, get(i)) < 0) {
                maxItem = get(i);
            }
        }
        return maxItem;
    }
}
