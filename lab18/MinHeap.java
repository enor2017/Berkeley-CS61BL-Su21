import java.util.ArrayList;
import java.util.NoSuchElementException;

/* A MinHeap class of Comparable elements backed by an ArrayList. */
public class MinHeap<E extends Comparable<E>> {

    /* An ArrayList that stores the elements in this MinHeap. */
    private ArrayList<E> contents;
    private int size;

    /* Initializes an empty MinHeap. */
    public MinHeap() {
        contents = new ArrayList<>();
        contents.add(null);
    }

    /* Returns the element at index INDEX, and null if it is out of bounds. */
    private E getElement(int index) {
        if (index >= contents.size()) {
            return null;
        } else {
            return contents.get(index);
        }
    }

    /* Sets the element at index INDEX to ELEMENT. If the ArrayList is not big
       enough, add elements until it is the right size. */
    private void setElement(int index, E element) {
        while (index >= contents.size()) {
            contents.add(null);
        }
        contents.set(index, element);
    }

    /* Swaps the elements at the two indices. */
    private void swap(int index1, int index2) {
        E element1 = getElement(index1);
        E element2 = getElement(index2);
        setElement(index2, element1);
        setElement(index1, element2);
    }

    /* Prints out the underlying heap sideways. Use for debugging. */
    @Override
    public String toString() {
        return toStringHelper(1, "");
    }

    /* Recursive helper method for toString. */
    private String toStringHelper(int index, String soFar) {
        if (getElement(index) == null) {
            return "";
        } else {
            String toReturn = "";
            int rightChild = getRightOf(index);
            toReturn += toStringHelper(rightChild, "        " + soFar);
            if (getElement(rightChild) != null) {
                toReturn += soFar + "    /";
            }
            toReturn += "\n" + soFar + getElement(index) + "\n";
            int leftChild = getLeftOf(index);
            if (getElement(leftChild) != null) {
                toReturn += soFar + "    \\";
            }
            toReturn += toStringHelper(leftChild, "        " + soFar);
            return toReturn;
        }
    }

    /* Returns the index of the left child of the element at index INDEX. */
    private int getLeftOf(int index) {
        return index * 2;
    }

    /* Returns the index of the right child of the element at index INDEX. */
    private int getRightOf(int index) {
        return index * 2 + 1;
    }

    /* Returns the index of the parent of the element at index INDEX. */
    private int getParentOf(int index) {
        return index / 2;
    }

    /* Returns the index of the smaller element. At least one index has a
       non-null element. If the elements are equal, return either index. */
    private int min(int index1, int index2) {
        E item1 = getElement(index1);
        E item2 = getElement(index2);
        if (item1 == null) {
            return index2;
        }
        if(item2 == null) {
            return index1;
        }
        return (item1.compareTo(item2) < 0) ? index1 : index2;
    }

    /* Returns but does not remove the smallest element in the MinHeap. */
    public E findMin() {
        if(size == 0) {
            return null;
        }
        return getElement(1);
    }

    /* Bubbles up the element currently at index INDEX. */
    private void bubbleUp(int index) {
        // if index is the root, end process
        if(index == 1) {
            return;
        }
        int parent = getParentOf(index);
        E value = getElement(index);
        E parentValue = getElement(parent);
        if(value.compareTo(parentValue) < 0) {
            swap(index, parent);
            // recursively bubbling up
            bubbleUp(parent);
        }
    }

    /* Bubbles down the element currently at index INDEX. */
    private void bubbleDown(int index) {
        // if index has no children, end process
        if(getLeftOf(index) > size) {
            return;
        }
        // get smaller children
        int smallerChildIndex = min(getLeftOf(index), getRightOf(index));
        E value = getElement(index);
        E smallerChildValue = getElement(smallerChildIndex);
        if(smallerChildValue.compareTo(value) < 0) {
            swap(smallerChildIndex, index);
            // recursively bubbling down
            bubbleDown(smallerChildIndex);
        }
    }

    /* Returns the number of elements in the MinHeap. */
    public int size() {
        return size;
    }

    /* Inserts ELEMENT into the MinHeap. If ELEMENT is already in the MinHeap,
       throw an IllegalArgumentException.*/
    public void insert(E element) {
        // check if element already in heap
        if(contains(element)) {
            throw new IllegalArgumentException();
        }
        contents.add(element);
        // size is the index of newly inserted element
        size++;
        bubbleUp(size);
    }

    /* Returns and removes the smallest element in the MinHeap. */
    public E removeMin() {
        if(size == 0) {
            return null;
        }
        // swap root with right-most node
        swap(1, size);
        // record and remove the right-most node
        E toRemove = contents.get(size);
        contents.remove(size);
        size--;
        bubbleDown(1);
        return toRemove;
    }

    /* Replaces and updates the position of ELEMENT inside the MinHeap, which
       may have been mutated since the initial insert. If a copy of ELEMENT does
       not exist in the MinHeap, throw a NoSuchElementException. Item equality
       should be checked using .equals(), not ==. */
    public void update(E element) {
        if(!contains(element)) {
            throw new NoSuchElementException();
        }
        for(int i = 1; i <= size; ++i) {
            E item = contents.get(i);
            if(item.equals(element)) {
                // we are not sure if the element is increased or decreased,
                // so we need to perform both bubbling
                contents.set(i, element);
                bubbleDown(i);
                bubbleUp(i);
                return;
            }
        }
    }

    /* Returns true if ELEMENT is contained in the MinHeap. Item equality should
       be checked using .equals(), not ==. */
    public boolean contains(E element) {
        for(int i = 1; i <= size; ++i) {
            E item = contents.get(i);
            if(item.equals(element)) {
                return true;
            }
        }
        return false;
    }
}
