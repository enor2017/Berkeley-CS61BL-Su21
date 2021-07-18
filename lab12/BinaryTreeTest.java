import org.junit.Test;

import static org.junit.Assert.*;

/**
 * very simple test for binary tree
 */
public class BinaryTreeTest {

    BinaryTree t = new BinaryTree();

    /**
     *   a
     */
    public void sampleTree0() {
        t.root = new BinaryTree.TreeNode("a", null, null);
    }

    /**
     *      a
     *     / \
     *    b  c
     */
    public void sampleTree1() {
        t.root = new BinaryTree.TreeNode("a", new BinaryTree.TreeNode("b"), new BinaryTree.TreeNode("c"));
    }

    /**
     *         a
     *       /  \
     *      b   c
     *     /
     *    d
     *   / \
     *  e  f
     */
    public void sampleTree2() {
        t.root = new BinaryTree.TreeNode("a",
                new BinaryTree.TreeNode("b", new BinaryTree.TreeNode("d", new BinaryTree.TreeNode("e"),
                        new BinaryTree.TreeNode("f")), null), new BinaryTree.TreeNode("c"));
    }

    /**
     *         a
     *       /  \
     *      b   c
     *         /
     *        d
     *       / \
     *      e  f
     */
    public void sampleTree3() {
        t.root = new BinaryTree.TreeNode("a", new BinaryTree.TreeNode("b"), new BinaryTree.TreeNode("c",
                new BinaryTree.TreeNode("d", new BinaryTree.TreeNode("e"), new BinaryTree.TreeNode("f")), null));
    }

    /**
     *       a
     *     /  \
     *    b    d
     *   / \  / \
     *  c  c c  c
     */
    public void sampleTree4() {
        BinaryTree.TreeNode leafNode = new BinaryTree.TreeNode("c");
        t.root = new BinaryTree.TreeNode("a", new BinaryTree.TreeNode("b", leafNode, leafNode),
                new BinaryTree.TreeNode("d", leafNode, leafNode));
    }

    @Test
    public void heightTest() {
        t = new BinaryTree();
        assertEquals(0, t.height());

        sampleTree0();
        assertEquals(1, t.height());

        sampleTree1();
        assertEquals(2, t.height());

        sampleTree2();
        assertEquals(4, t.height());

        sampleTree3();
        assertEquals(4, t.height());

        sampleTree4();
        assertEquals(3, t.height());
    }

    @Test
    public void completelyBalancedTest() {
        t = new BinaryTree();
        assertTrue(t.isCompletelyBalanced());

        sampleTree0();
        assertTrue(t.isCompletelyBalanced());

        sampleTree1();
        assertTrue(t.isCompletelyBalanced());

        sampleTree2();
        assertFalse(t.isCompletelyBalanced());

        sampleTree3();
        assertFalse(t.isCompletelyBalanced());

        sampleTree4();
        assertTrue(t.isCompletelyBalanced());
    }

    /**
     * Remember to implement .equals() method before using this test.
     */
    @Test
    public void fibTest() {
        BinaryTree<Integer> fib0 = BinaryTree.fibTree(0);
        BinaryTree<Integer> fib0Ans = new BinaryTree<>(
                new BinaryTree.TreeNode<>(0, null, null)
        );
        assertEquals(fib0Ans, fib0);

        BinaryTree<Integer> fib1 = BinaryTree.fibTree(1);
        BinaryTree<Integer> fib1Ans = new BinaryTree<>(
                new BinaryTree.TreeNode<>(1, null, null)
        );
        assertEquals(fib1Ans, fib1);

        BinaryTree<Integer> fib2 = BinaryTree.fibTree(2);
        BinaryTree<Integer> fib2Ans = new BinaryTree<>(
                new BinaryTree.TreeNode<>(1,
                        new BinaryTree.TreeNode<>(1, null, null),
                        new BinaryTree.TreeNode<>(0, null, null)
                )
        );
        assertEquals(fib2Ans, fib2);

        BinaryTree<Integer> fib3 = BinaryTree.fibTree(3);
        BinaryTree<Integer> fib3Ans = new BinaryTree<>(
                new BinaryTree.TreeNode<>(2,
                        new BinaryTree.TreeNode<>(1,
                                new BinaryTree.TreeNode<>(1, null, null),
                                new BinaryTree.TreeNode<>(0, null, null)
                        ),
                        new BinaryTree.TreeNode<>(1, null, null)
                )
        );
        assertEquals(fib3Ans, fib3);

        BinaryTree<Integer> fib4 = BinaryTree.fibTree(4);
        BinaryTree<Integer> fib4Ans = new BinaryTree<>(
                new BinaryTree.TreeNode<>(3,
                        new BinaryTree.TreeNode<>(2,
                                new BinaryTree.TreeNode<>(1,
                                        new BinaryTree.TreeNode<>(1, null, null),
                                        new BinaryTree.TreeNode<>(0, null, null)
                                ),
                                new BinaryTree.TreeNode<>(1, null, null)
                        ),
                        new BinaryTree.TreeNode<>(1,
                                new BinaryTree.TreeNode<>(1, null, null),
                                new BinaryTree.TreeNode<>(0, null, null)
                        )
                )
        );
        assertEquals(fib4Ans, fib4);

        BinaryTree<Integer> fib5 = BinaryTree.fibTree(5);
        BinaryTree<Integer> fib5Ans = new BinaryTree<>(
                new BinaryTree.TreeNode<>(5,
                        new BinaryTree.TreeNode<>(3,
                                new BinaryTree.TreeNode<>(2,
                                        new BinaryTree.TreeNode<>(1,
                                                new BinaryTree.TreeNode<>(1, null, null),
                                                new BinaryTree.TreeNode<>(0, null, null)
                                        ),
                                        new BinaryTree.TreeNode<>(1, null, null)
                                ),
                                new BinaryTree.TreeNode<>(1,
                                        new BinaryTree.TreeNode<>(1, null, null),
                                        new BinaryTree.TreeNode<>(0, null, null)
                                )
                        ),
                        new BinaryTree.TreeNode<>(2,
                                new BinaryTree.TreeNode<>(1,
                                        new BinaryTree.TreeNode<>(1, null, null),
                                        new BinaryTree.TreeNode<>(0, null, null)
                                ),
                                new BinaryTree.TreeNode<>(1, null, null)
                        )
                )
        );
        assertEquals(fib5Ans, fib5);
    }
}
