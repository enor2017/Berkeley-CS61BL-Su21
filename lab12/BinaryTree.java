import com.sun.source.tree.Tree;

import javax.swing.tree.TreeNode;
import java.nio.file.Paths;

public class BinaryTree<T> {

    TreeNode<T> root;

    public BinaryTree() {
        root = null;
    }

    public BinaryTree(TreeNode<T> t) {
        root = t;
    }

    public TreeNode<T> getRoot() {
        return root;
    }

    /* Returns the height of the tree. */
    public int height() {
        if (root == null) {
            return 0;
        }
        return root.heightHelper();
    }

    /* Returns true if the tree's left and right children are the same height
       and are themselves completely balanced. */
    public boolean isCompletelyBalanced() {
        if (root == null) {
            return true;
        }
        return root.isCompletelyBalancedHelper();
    }

    /* Returns a BinaryTree representing the Fibonacci calculation for N. */
    public static BinaryTree<Integer> fibTree(int N) {
        BinaryTree<Integer> result = new BinaryTree<>();
        result.root = fibHelper(N);
        return result;
    }

    /**
     * fib Helper function: return a TreeNode with fib N
     */
    public static TreeNode<Integer> fibHelper(int n) {
        if (n == 0 || n == 1) {
            return new TreeNode<>(n, null, null);
        } else {
            TreeNode<Integer> left = fibHelper(n - 1);
            TreeNode<Integer> right = fibHelper(n - 2);
            return new TreeNode<>(left.item + right.item, left, right);
        }
    }


    /**
     * fib test helper function: check if two BinaryTree equals
     * For the sake of simplicity, we always assume Object is BinaryTree
     */
    @Override
    public boolean equals(Object t) {
        BinaryTree<T> tree = (BinaryTree<T>) t;
        if (t == null) {
            return false;
        } else {
            return this.root.equals(tree.root);
        }
    }

    /* Print the values in the tree in preorder: root value first, then values
       in the left subtree (in preorder), then values in the right subtree
       (in preorder). */
    public void printPreorder() {
        if (root == null) {
            System.out.println("(empty tree)");
        } else {
            root.printPreorder();
            System.out.println();
        }
    }

    /* Print the values in the tree in inorder: values in the left subtree
       first (in inorder), then the root value, then values in the first
       subtree (in inorder). */
    public void printInorder() {
        if (root == null) {
            System.out.println("(empty tree)");
        } else {
            root.printInorder();
            System.out.println();
        }
    }

    /* Prints out the contents of a BinaryTree with a description in both
       preorder and inorder. */
    private static void print(BinaryTree t, String description) {
        System.out.println(description + " in preorder");
        t.printPreorder();
        System.out.println(description + " in inorder");
        t.printInorder();
        System.out.println();
    }

    /* Fills this BinaryTree with values a, b, and c. DO NOT MODIFY. */
    public void sampleTree1() {
        root = new TreeNode("a", new TreeNode("b"), new TreeNode("c"));
    }

    /* Fills this BinaryTree with values a, b, and c, d, e, f. DO NOT MODIFY. */
    public void sampleTree2() {
        root = new TreeNode("a",
                  new TreeNode("b", new TreeNode("d", new TreeNode("e"),
                  new TreeNode("f")), null), new TreeNode("c"));
    }

    /* Fills this BinaryTree with the values a, b, c, d, e, f. DO NOT MODIFY. */
    public void sampleTree3() {
        root = new TreeNode("a", new TreeNode("b"), new TreeNode("c",
               new TreeNode("d", new TreeNode("e"), new TreeNode("f")), null));
    }

    /* Fills this BinaryTree with the same leaf TreeNode. DO NOT MODIFY. */
    public void sampleTree4() {
        TreeNode leafNode = new TreeNode("c");
        root = new TreeNode("a", new TreeNode("b", leafNode, leafNode),
                                 new TreeNode("d", leafNode, leafNode));
    }

    /* Creates two BinaryTrees and prints them out in inorder. */
    public static void main(String[] args) {
        BinaryTree t;
        t = new BinaryTree();
        print(t, "the empty tree");
        t.sampleTree1();
        print(t, "sample tree 1");
        t.sampleTree2();
        print(t, "sample tree 2");
    }

    /* Note: this class is public in this lab for testing purposes. However,
       in professional settings as well as the rest of your labs and projects,
       we recommend that you keep your inner classes private. */
    static class TreeNode<T> {

        private T item;
        private TreeNode left;
        private TreeNode right;

        TreeNode(T obj) {
            item = obj;
            left = null;
            right = null;
        }

        TreeNode(T obj, TreeNode<T> left, TreeNode<T> right) {
            item = obj;
            this.left = left;
            this.right = right;
        }

        public T getItem() {
            return item;
        }

        public TreeNode<T> getLeft() {
            return left;
        }

        public TreeNode<T> getRight() {
            return right;
        }

        void setItem(T item) {
            this.item = item;
        }

        void setLeft(TreeNode<T> left) {
            this.left = left;
        }

        void setRight(TreeNode<T> right) {
            this.right = right;
        }

        private void printPreorder() {
            System.out.print(item + " ");
            if (left != null) {
                left.printPreorder();
            }
            if (right != null) {
                right.printPreorder();
            }
        }

        private void printInorder() {
            if (left != null) {
                left.printInorder();
            }
            System.out.print(item + " ");
            if (right != null) {
                right.printInorder();
            }
        }

        /**
         * helper function for getting height
         */
        private int heightHelper() {
            if(item == null) {
                return 0;
            } else {
                int leftDepth = (left == null) ? 0 : left.heightHelper();
                int rightDepth = (right == null) ? 0 : right.heightHelper();
                return 1 + Math.max(leftDepth, rightDepth);
            }
        }

        /**
         * helper function for isCompletelyBalanced
         * if no nodes or one node, is balanced
         * else, iff height(left) == height(right), and both completely balanced
         */
        private boolean isCompletelyBalancedHelper() {
            if(item == null || (left == null && right == null)) {
                return true;
            } else {
                return (left.heightHelper() == right.heightHelper()) &&
                        left.isCompletelyBalancedHelper() &&
                        right.isCompletelyBalancedHelper();
            }
        }

        /**
         * equals helper function: whether two treenodes are equal
         * For the sake of simplicity, assume Object is always TreeNode
         */
        @Override
        public boolean equals(Object o) {
            TreeNode<T> t = (TreeNode<T>) o;
            if (item == t.item) {
                return true;
            } else {
                // if one's child is null but the other is not, return false
                if(left == null ^ t.left == null) {
                    return false;
                }
                if (right == null ^ t.right == null) {
                    return false;
                }

                if (left != null && !left.equals(t.left)) {
                    return false;
                }
                if (right != null && !right.equals(t.right)) {
                    return false;
                }
                // reaching here means both children are null.
                return true;
            }
        }
    }
}
