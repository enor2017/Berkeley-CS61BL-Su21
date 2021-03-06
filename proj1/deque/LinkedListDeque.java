package deque;

import java.util.List;

public class LinkedListDeque<T> implements Deque<T>{

    private class ListNode{
        private T value;
        private ListNode prev, next;

        public ListNode(T value){
            this.value = value;
            prev = next = null;
        }

        public ListNode(T value, ListNode prev, ListNode next){
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }

    private ListNode sentinel;
    private int size;

    /**
     * Constructor: Creates an empty linked list deque.
     */
    public LinkedListDeque(){
        size = 0;
        sentinel = new ListNode(null, null, null);
        sentinel.next = sentinel.prev = sentinel;
    }

    @Override
    public void addFirst(T item) {
        if(item == null) {
            return;
        }
        ListNode newNode = new ListNode(item, sentinel, sentinel.next);
        sentinel.next.prev = newNode;
        sentinel.next = newNode;
        size++;
    }

    @Override
    public void addLast(T item) {
        if(item == null) {
            return;
        }
        ListNode newNode = new ListNode(item, sentinel.prev, sentinel);
        sentinel.prev.next = newNode;
        sentinel.prev = newNode;
        size++;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        String res = "";
        ListNode p = sentinel.next;
        while(p.next != sentinel) {
            res = res + p.value + " ";
            p = p.next;
        }
        res = res + p.value;
        System.out.println(res);
    }

    @Override
    public T removeFirst() {
        if(isEmpty()) {
            return null;
        }
        ListNode delNode = sentinel.next;
        sentinel.next = sentinel.next.next;
        sentinel.next.prev = sentinel;
        size--;
        return delNode.value;
    }

    @Override
    public T removeLast() {
        if(isEmpty()) {
            return null;
        }
        ListNode delNode = sentinel.prev;
        sentinel.prev = sentinel.prev.prev;
        sentinel.prev.next = sentinel;
        size--;
        return delNode.value;
    }

    @Override
    public T get(int index) {
        if(index >= size || index < 0) {
            return null;
        }

        ListNode p = sentinel.next;
        for(int i = 0; i < index; ++i) {
            p = p.next;
        }
        return p.value;
    }

    /**
     * Gets the item at the given index. (Using recursion)
     *
     * helper function: getNodeRecursive(start, index)
     * get index-th Node starting from (start).
     */
    private ListNode getNodeRecursive(ListNode start, int index){
        if(index == 0) {
            return start;
        }else {
            return getNodeRecursive(start.next, index - 1);
        }
    }
    @Override
    public T getRecursive(int index){
        if(index >= size || index < 0) {
            return null;
        }

        return getNodeRecursive(sentinel.next, index).value;
    }

    /**
     * check whether two deque is equal.
     * Even if two deque belongs to different subclass,
     * as long as their items are the same, we'll consider them equal
     */
    @Override
    public boolean equals(Object o) {
        // if null, return false
        if (o == null) {
            return false;
        }

        // if not deque, return false
        if(!(o instanceof Deque)) {
            return false;
        }

        // cast object to Deque
        Deque<T> d = (Deque<T>) o;

        // if size mismatch, return false
        if(size != d.size()) {
            return false;
        }

        for(int i = 0; i < size; ++i) {
            // if not equals, return false
            if(!get(i).equals(d.get(i))) {
                return false;
            }
        }

        return true;
    }
}
