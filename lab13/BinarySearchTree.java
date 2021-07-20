import java.util.ArrayList;

public class BinarySearchTree<T extends Comparable<T>> extends BinaryTree<T> {

    /* Creates an empty BST. */
    public BinarySearchTree() {
        super();
    }

    /* Creates a BST with root as ROOT. */
    public BinarySearchTree(TreeNode root) {
        super(root);
    }

    /* helper function: Array slicing, return subArray of [start, end) */
    private ArrayList<T> slicing (ArrayList<T> array, int start, int end) {
        ArrayList<T> subArray = new ArrayList<>();
        for(int i = start; i < end; ++i) {
            subArray.add(array.get(i));
        }
        return subArray;
    }

    /* helper function: return a tree node with given preorder and inorder traversal */
    private TreeNode traversalConstruct(ArrayList<T> pre, ArrayList<T> in) {
        if (pre.isEmpty()) {
            return null;
        }
        // find root node
        TreeNode root = new TreeNode(pre.get(0));
        // if length of pre, in is 1, return (no children)
        if (pre.size() == 1) {
            return root;
        }

        // get root node index in inOrder list
        int rootIndex = in.indexOf(root.item);
        // recursively construct left and right subtree
        root.left = traversalConstruct(slicing(pre, 1, 1 + rootIndex),
                slicing(in, 0, rootIndex));
        root.right = traversalConstruct(slicing(pre, 1 + rootIndex, pre.size()),
                slicing(in, 1 + rootIndex, in.size()));
        return root;
    }

    /* Creates a BST with preorder and inorder traversal */
    public BinarySearchTree(ArrayList<T> pre, ArrayList<T> in) {
        root = traversalConstruct(pre, in);
    }

    /* helper method: return true if given subtree contains given key */
    private boolean contains(TreeNode t, T key) {
        if (t == null) {
            return false;
        }
        if (key.compareTo(t.item) == 0) {
            return true;
        } else if (key.compareTo(t.item) < 0) {
            return contains(t.left, key);
        } else {
            return contains(t.right, key);
        }
    }

    /* Returns true if the BST contains the given KEY. */
    public boolean contains(T key) {
        return contains(root, key);
    }

    /* Add a node for KEY in the given subtree, return the root */
    private TreeNode add(TreeNode t, T key) {
        if (t == null) {
            return new TreeNode(key);
        }
        if (key.compareTo(t.item) < 0) {
            t.left = add(t.left, key);
        } else if (key.compareTo(t.item) > 0) {
            t.right = add(t.right, key);
        }
        return t;
    }

    /* Adds a node for KEY iff KEY isn't in the BST already. */
    public void add(T key) {
        root = add(root, key);
    }

    /* Deletes a node from the BST. 
     * Even though you do not have to implement delete, you 
     * should read through and understand the basic steps.
    */
    public T delete(T key) {
        TreeNode parent = null;
        TreeNode curr = root;
        TreeNode delNode = null;
        TreeNode replacement = null;
        boolean rightSide = false;

        while (curr != null && !curr.item.equals(key)) {
            if (curr.item.compareTo(key) > 0) {
                parent = curr;
                curr = curr.left;
                rightSide = false;
            } else {
                parent = curr;
                curr = curr.right;
                rightSide = true;
            }
        }
        delNode = curr;
        if (curr == null) {
            return null;
        }

        if (delNode.right == null) {
            if (root == delNode) {
                root = root.left;
            } else {
                if (rightSide) {
                    parent.right = delNode.left;
                } else {
                    parent.left = delNode.left;
                }
            }
        } else {
            curr = delNode.right;
            replacement = curr.left;
            if (replacement == null) {
                replacement = curr;
            } else {
                while (replacement.left != null) {
                    curr = replacement;
                    replacement = replacement.left;
                }
                curr.left = replacement.right;
                replacement.right = delNode.right;
            }
            replacement.left = delNode.left;
            if (root == delNode) {
                root = replacement;
            } else {
                if (rightSide) {
                    parent.right = replacement;
                } else {
                    parent.left = replacement;
                }
            }
        }
        return delNode.item;
    }
}