import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Iterator;

public class BST<T> {

    BSTNode<T> root;

    public BST(LinkedList<T> list) {
        root = sortedIterToTree(list.iterator(), list.size());
    }

    /**
     * helper function: convert given iterator into arrayList
     */
    private ArrayList<T> getArrayFromIt(Iterator<T> iter) {
        ArrayList<T> list = new ArrayList<>();
        while(iter.hasNext()) {
            list.add(iter.next());
        }
        return list;
    }

    /**
     * helper function: slicing given arrayList from [start, end)
     * Assume arguments always legal
     */
    private ArrayList<T> slicing(ArrayList<T> array, int start, int end) {
        ArrayList<T> list = new ArrayList<>();
        for(int i = start; i < end; ++i) {
            list.add(array.get(i));
        }
        return list;
    }

    /**
     * helper function: build a balanced BST given sorted Arraylist
     */
    private BSTNode<T> sortedArrayToTree(ArrayList<T> array) {
        int N = array.size();
        if(N <= 0) {
            return null;
        }
        int mid = N / 2;
        // set mid item of array to be root
        BSTNode<T> root = new BSTNode<>(array.get(mid));
        // recursively build left and right subtree
        root.left = sortedArrayToTree(slicing(array, 0, mid));
        root.right = sortedArrayToTree(slicing(array, mid + 1, N));
        return root;
    }

    /* Returns the root node of a BST (Binary Search Tree) built from the given
       iterator ITER  of N items. ITER will output the items in sorted order,
       and ITER will contain objects that will be the item of each BSTNode. */
    private BSTNode<T> sortedIterToTree(Iterator<T> iter, int N) {
        ArrayList<T> array = getArrayFromIt(iter);
        return sortedArrayToTree(array);
    }

    /* Prints the tree represented by ROOT. */
    public void print() {
        print(root, 0);
    }

    private void print(BSTNode<T> node, int d) {
        if (node == null) {
            return;
        }
        for (int i = 0; i < d; i++) {
            System.out.print("  ");
        }
        System.out.println(node.item);
        print(node.left, d + 1);
        print(node.right, d + 1);
    }

    class BSTNode<T> {
        T item;
        BSTNode<T> left;
        BSTNode<T> right;

        BSTNode(T item) {
            this.item = item;
        }
    }
}
