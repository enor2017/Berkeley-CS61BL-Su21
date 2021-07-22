public class RedBlackTree<T extends Comparable<T>> {

    /* Root of the tree. */
    RBTreeNode<T> root;

    static class RBTreeNode<T> {

        final T item;
        boolean isBlack;
        RBTreeNode<T> left;
        RBTreeNode<T> right;

        /* Creates a RBTreeNode with item ITEM and color depending on ISBLACK
           value. */
        RBTreeNode(boolean isBlack, T item) {
            this(isBlack, item, null, null);
        }

        /* Creates a RBTreeNode with item ITEM, color depending on ISBLACK
           value, left child LEFT, and right child RIGHT. */
        RBTreeNode(boolean isBlack, T item, RBTreeNode<T> left,
                   RBTreeNode<T> right) {
            this.isBlack = isBlack;
            this.item = item;
            this.left = left;
            this.right = right;
        }
    }

    /* Creates an empty RedBlackTree. */
    public RedBlackTree() {
        root = null;
    }

    /* Creates a RedBlackTree from a given BTree (2-3-4) TREE. */
    public RedBlackTree(BTree<T> tree) {
        Node<T> btreeRoot = tree.root;
        root = buildRedBlackTree(btreeRoot);
    }

    /* Builds a RedBlackTree that has isometry with given 2-3-4 tree rooted at
       given node R, and returns the root node. */
    RBTreeNode<T> buildRedBlackTree(Node<T> r) {
        if (r == null) {
            return null;
        }

        if (r.getItemCount() == 1) {
            // becomes a single black node
            // we need to recursively build two children
            RBTreeNode<T> leftChild = r.getChildrenCount() > 0 ? buildRedBlackTree(r.getChildAt(0)) : null;
            RBTreeNode<T> rightChild = r.getChildrenCount() > 1 ? buildRedBlackTree(r.getChildAt(1)) : null;
            return new RBTreeNode<>(true, r.getItemAt(0), leftChild, rightChild);
        } else if (r.getItemCount() == 2) {
            // smaller item becomes black root, larger item becomes right child
            T smallerItem = r.getItemAt(0);
            T largerItem = r.getItemAt(1);
            RBTreeNode<T> leftChild = r.getChildrenCount() > 0 ? buildRedBlackTree(r.getChildAt(0)) : null;
            RBTreeNode<T> rightChild = new RBTreeNode<>(false, largerItem,
                    r.getChildrenCount() > 1 ? buildRedBlackTree(r.getChildAt(1)) : null,
                    r.getChildrenCount() > 2 ? buildRedBlackTree(r.getChildAt(2)) : null);
            return new RBTreeNode<>(true, smallerItem, leftChild, rightChild);
        } else {
            // middle item becomes black root, smaller item becomes left child, larger becomes right
            T middleItem = r.getItemAt(1);
            RBTreeNode<T> leftChild = new RBTreeNode<>(false, r.getItemAt(0),
                    r.getChildrenCount() > 0 ? buildRedBlackTree(r.getChildAt(0)) : null,
                    r.getChildrenCount() > 1 ? buildRedBlackTree(r.getChildAt(1)) : null);
            RBTreeNode<T> rightChild = new RBTreeNode<>(false, r.getItemAt(2),
                    r.getChildrenCount() > 2 ? buildRedBlackTree(r.getChildAt(2)) : null,
                    r.getChildrenCount() > 3 ? buildRedBlackTree(r.getChildAt(3)) : null);
            return new RBTreeNode<>(true, middleItem, leftChild, rightChild);
        }
    }

    /* Flips the color of NODE and its children. Assume that NODE has both left
       and right children. */
    void flipColors(RBTreeNode<T> node) {
        node.isBlack = !node.isBlack;
        node.left.isBlack = !node.left.isBlack;
        node.right.isBlack = !node.right.isBlack;
    }

    /* Rotates the given node NODE to the right. Returns the new root node of
       this subtree. */
    RBTreeNode<T> rotateRight(RBTreeNode<T> node) {
        RBTreeNode<T> leftChild = node.left;
        node.left = leftChild.right;
        leftChild.right = node;
        return leftChild;
    }

    /* Rotates the given node NODE to the left. Returns the new root node of
       this subtree. */
    RBTreeNode<T> rotateLeft(RBTreeNode<T> node) {
        RBTreeNode<T> rightChild = node.right;
        node.right = rightChild.left;
        rightChild.left = node;
        return rightChild;
    }

    public void insert(T item) {   
        root = insert(root, item);  
        root.isBlack = true;    
    }

    /* Inserts the given node into this Red Black Tree*/
    private RBTreeNode<T> insert(RBTreeNode<T> node, T item) {
        // Insert (return) new red leaf node.
        if (node == null) {
            return new RBTreeNode<>(false, item);
        }

        // Handle normal binary search tree insertion.
        int comp = item.compareTo(node.item);
        if (comp == 0) {
            return node; // do nothing.
        } else if (comp < 0) {
            node.left = insert(node.left, item);
        } else {
            node.right = insert(node.right, item);
        }

        // handle case C and "Right-leaning" situation.
        

        // handle case B
        

        // handle case A
        
        // TODO: YOUR CODE HERE
        return null; //fix this return statement
    }

    /* Returns whether the given node NODE is red. Null nodes (children of leaf
       nodes are automatically considered black. */
    private boolean isRed(RBTreeNode<T> node) {
        return node != null && !node.isBlack;
    }

}
