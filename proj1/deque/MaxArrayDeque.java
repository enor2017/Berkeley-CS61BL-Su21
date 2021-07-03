package deque;

import java.util.Comparator;

public class MaxArrayDeque<T> extends ArrayDeque<T>{

    private Comparator<T> comp;

    /**
     * Constructor by passing in a comparator
     */
    public MaxArrayDeque(Comparator<T> c) {
        super();
        comp = c;
    }

    /**
     * @return max element using private Comparator
     */
    public T max() {
        // let max item be the first item in deque
        T maxItem = get(0);

        for(int i = 1; i < size(); ++i) {
            T currItem = get(i);
            if(comp.compare(currItem, maxItem) > 0) {
                // if current Item > maxItem
                maxItem = currItem;
            }
        }

        return maxItem;
    }

    /**
     * @return max element using given Comparator
     */
    public T max(Comparator<T> c) {
        T maxItem = get(0);

        for(int i = 1; i < size(); ++i) {
            T currItem = get(i);
            if(c.compare(currItem, maxItem) > 0) {
                // if current Item > maxItem
                maxItem = currItem;
            }
        }

        return maxItem;
    }
}
