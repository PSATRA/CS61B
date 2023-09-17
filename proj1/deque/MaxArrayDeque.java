package deque;

import java.util.Comparator;

public class MaxArrayDeque<T> extends ArrayDeque<T>{
    public MaxArrayDeque(Comparator<T> c) {
    }

    /**
     * Returns the maximum element in the deque as governed by the previously given Comparator.
     * If the MaxArrayDeque is empty, simply return null.
     * @return the maximum element, null if empty
     */
    public T max() {
        return null;
    }

    /**
     * Returns the maximum element in the deque as governed by the parameter Comparator c.
     * If the MaxArrayDeque is empty, simply return null.
     * @param c the given Comparator
     * @return the maximum element, null if empty
     */
    public T max(Comparator<T> c) {
        return null;
    }
}
