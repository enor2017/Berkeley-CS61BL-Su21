package deque;

public class ArrayDeque<T> implements Deque<T>{

    private int size;
    private T array[];
    // start: where next first should be inserted.
    // end:   where next last should be inserted.
    private int start, end;

    /**
     * helper function: resize(capacity)
     * move elements into an new array, with length = capacity.
     *
     * original: [0, 0(start), 10, 20, 30, 40, 0(end), 0]
     * newArray: [0(start), 10, 20, 30, 40, 0(end) .... ]
     *
     * original: [10, 20, 30, 0(end), 0, 0(start), 40, 50]
     * newArray: [0(start), 40, 50, 10, 20, 30, 0(end) .... ]
     */
    private void resize(int capacity){
        T newArray[] = (T[]) new Object[capacity];

        int counter = 0;
        for(int i = start + 1; i <= start + size; ++i){
            newArray[++counter] = array[i % array.length];
        }

        // update array[], start, end
        array = newArray;
        start = 0;
        end = counter + 1;
    }

    /**
     * Constructor: blank deque
     */
    public ArrayDeque(){
        array = (T[]) new Object[8];
        size = start = 0;
        end = 1;
    }

    /**
     * Adds an item of type T to the front of the deque.
     * (item not null)
     */
    @Override
    public void addFirst(T item){
        // if the array is full, resize it
        if(size == array.length){
            resize(size * 2);
        }

        array[start--] = item;
        // if (start < 0), move it to the back of array
        if(start < 0){
            start += array.length;
        }
        size++;
    }

    @Override
    public void addLast(T item) {
        if(size == array.length){
            resize(size * 2);
        }

        array[end++] = item;
        if(end >= array.length){
            end -= array.length;
        }
        size++;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        String result = "";
        for(int i = start + 1; i <= start + size; ++i){
            result = result + array[i % array.length];
            // if not the last item, append a space
            if(i != start + size) {
                result = result + " ";
            }
        }
        System.out.println(result);
    }

    @Override
    public T removeFirst() {
        if(isEmpty()) {
            return null;
        }

        // if (size < capacity / 4), resize to (capacity / 4 + 1)
        // IMPORTANT: if resize to (capacity / 4), you'll have trouble
        // since we spare two spaces to store (start) and (end).
        if ((size < array.length / 4) && (size > 4)) {
            resize(array.length / 4 + 1);
        }

        // the index of toRemove item
        int toRemoveIndex = (start + 1) % array.length;
        T toRemove = array[toRemoveIndex];
        // removed_item = null
        array[toRemoveIndex] = null;
        // move start to right
        start = toRemoveIndex;

        size--;
        return toRemove;
    }

    @Override
    public T removeLast() {
        if(isEmpty()) {
            return null;
        }

        // if (size < capacity / 4), resize to (capacity / 4 + 1)
        if ((size < array.length / 4) && (size > 4)) {
            resize(array.length / 4 + 1);
        }

        int toRemoveIndex = (end - 1) % array.length;
        if(toRemoveIndex < 0) {
            toRemoveIndex += array.length;
        }

        T toRemove = array[toRemoveIndex];
        array[toRemoveIndex] = null;
        end = toRemoveIndex;

        size--;
        return toRemove;
    }

    @Override
    public T get(int index) {
        if(index >= size || index < 0) {
            return null;
        }

        return array[(start + 1 + index) % array.length];
    }

    @Override
    public boolean equals(Object o) {
        // if null, or not same class type, return false
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        // safely cast object to ArrayDeque type
        ArrayDeque<T> ad = (ArrayDeque<T>) o;

        // if size mismatch, return false
        if(size != ad.size) {
            return false;
        }

        for(int i = 0; i < size; ++i) {
            if(!get(i).equals(ad.get(i))) {
                return false;
            }
        }

        return true;
    }

}
