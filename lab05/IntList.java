/** A data structure to represent a Linked List of Integers.
 * Each IntList represents one node in the overall Linked List.
 *
 * @author Maurice Lee and Wan Fung Chui
 */

public class IntList {

    /** The integer stored by this node. */
    public int item;
    /** The next node in this IntList. */
    public IntList next;

    /** Constructs an IntList storing ITEM and next node NEXT. */
    public IntList(int item, IntList next) {
        this.item = item;
        this.next = next;
    }

    /** Constructs an IntList storing ITEM and no next node. */
    public IntList(int item) {
        this(item, null);
    }

    /** Returns an IntList consisting of the elements in ITEMS.
     * IntList L = IntList.list(1, 2, 3);
     * System.out.println(L.toString()) // Prints 1 2 3 */
    public static IntList of(int... items) {
        /** Check for cases when we have no element given. */
        if (items.length == 0) {
            return null;
        }
        /** Create the first element. */
        IntList head = new IntList(items[0]);
        IntList last = head;
        /** Create rest of the list. */
        for (int i = 1; i < items.length; i++) {
            last.next = new IntList(items[i]);
            last = last.next;
        }
        return head;
    }

    /**
     * Returns [position]th item in this list. Throws IllegalArgumentException
     * if index out of bounds.
     *
     * @param position, the position of element.
     * @return The element at [position]
     */
    public int get(int position) {
        // if negative position, throw exception
        if(position < 0) {
            throw new IllegalArgumentException("negative position, cannot get!");
        }
        IntList p = this;
        for(int i = 0; i < position; ++i){
            if(p.next == null){
                throw new IllegalArgumentException("position out of bound, cannot get!");
            }
            p = p.next;
        }
        return p.item;
    }

    /**
     * Returns the string representation of the list. For the list (1, 2, 3),
     * returns "1 2 3".
     *
     * @return The String representation of the list.
     */
    public String toString() {
        String res = "";
        IntList p = this;
        while(p != null){
            res = res + p.item;
            // if p is not the last element, append a space
            if(p.next != null) {
                res += " ";
            }
            // move onto next
            p = p.next;
        }
        return res;
    }

    /**
     * Returns whether this and the given list or object are equal.
     *
     * NOTE: A full implementation of equals requires checking if the
     * object passed in is of the correct type, as the parameter is of
     * type Object. This also requires we convert the Object to an
     * IntList, if that is legal. The operation we use to do this is called
     * casting, and it is done by specifying the desired type in
     * parenthesis. An example of this is on line 84.
     *
     * @param obj, another list (object)
     * @return Whether the two lists are equal.
     */
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof IntList)) {
            return false;
        }
        IntList otherLst = (IntList) obj;
        IntList thisLst = this;
        while(thisLst != null){
            // if different lengths
            if(otherLst == null) {
                return false;
            }
            // if different items
            if(thisLst.item != otherLst.item){
                return false;
            }
            // otherwise, move onto next
            thisLst = thisLst.next;
            otherLst = otherLst.next;
        }
        // if B longer than A: (different lengths)
        if(otherLst != null) {
            return false;
        }
        return true;
    }

    /**
     * Adds the given value at the end of the list.
     *
     * @param value, the int to be added.
     */
    public void add(int value) {
        // in IntList, the list will never be empty
        IntList p = this;
        while(p.next != null) {
            p = p.next;
        }
        p.next = new IntList(value);
    }

    /**
     * Returns the smallest element in the list.
     *
     * @return smallest element in the list
     */
    public int smallest() {
        int res = 99999999;
        IntList p = this;
        while(p != null) {
            if(res > p.item) {
                res = p.item;
            }
            p = p.next;
        }
        return res;
    }

    /**
     * Returns the sum of squares of all elements in the list.
     *
     * @return The sum of squares of all elements.
     */
    public int squaredSum() {
        int res = 0;
        IntList p = this;
        while(p != null) {
            res += (p.item) * (p.item);
            p = p.next;
        }
        return res;
    }

    /**
     * Destructively squares each item of the list.
     *
     * @param L list to destructively square.
     */
    public static void dSquareList(IntList L) {
        while (L != null) {
            L.item = L.item * L.item;
            L = L.next;
        }
    }

    /**
     * Returns a list equal to L with all elements squared. Non-destructive.
     *
     * @param L list to non-destructively square.
     * @return the squared list.
     */
    public static IntList squareListIterative(IntList L) {
        if (L == null) {
            return null;
        }
        IntList res = new IntList(L.item * L.item, null);
        IntList ptr = res;
        L = L.next;
        while (L != null) {
            ptr.next = new IntList(L.item * L.item, null);
            L = L.next;
            ptr = ptr.next;
        }
        return res;
    }

    /** Returns a list equal to L with all elements squared. Non-destructive.
     *
     * @param L list to non-destructively square.
     * @return the squared list.
     */
    public static IntList squareListRecursive(IntList L) {
        if (L == null) {
            return null;
        }
        return new IntList(L.item * L.item, squareListRecursive(L.next));
    }

    /**
     * Returns a new IntList consisting of A followed by B,
     * destructively.
     *
     * @param A list to be on the front of the new list.
     * @param B list to be on the back of the new list.
     * @return new list with A followed by B.
     */
    public static IntList dcatenate(IntList A, IntList B) {
        if(A == null) {
            return B;
        } else if (B == null) {
            return A;
        }
        // move to last item in A
        IntList p = A;
        while(p.next != null) {
            p = p.next;
        }
        while(B != null) {
            p.next = new IntList(B.item);
            p = p.next;
            B = B.next;
        }
        return A;
    }

    /**
     * Returns a new IntList consisting of A followed by B,
     * non-destructively.
     *
     * @param A list to be on the front of the new list.
     * @param B list to be on the back of the new list.
     * @return new list with A followed by B.
     */
    /**
     * Note by enor2017:
     * I cannot afford a recursive method, but if it's ok to attach B to the end
     * of A without making a full copy a B, a possible solution is attached below
     *
     * I've already modified test to strictly check whether B is fully copied,
     * to test the recursive method below, comment out the last two lines of testCatenate()
     */
//    public static IntList catenate(IntList A, IntList B) {
//        if(A != null) {
//            return new IntList(A.item, catenate(A.next, B));
//        } else {
//            return new IntList(B.item, B.next);
//        }
//    }
     public static IntList catenate(IntList A, IntList B) {
        if(A == null && B == null) {
            return null;
        } else if (A == null) {
            return catenate(B, null);
        }
        IntList newList = new IntList(A.item, null);
        IntList p = newList;
        A = A.next;
        while(A != null) {
            p.next = new IntList(A.item, null);
            A = A.next;
            p = p.next;
        }
        while(B != null) {
            p.next = new IntList(B.item, null);
            B = B.next;
            p = p.next;
        }
        return newList;
     }
}