import org.junit.Test;
import static org.junit.Assert.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

public class BinaryTreeTest {
    @Test
    public void treeFormatTest() {
        BinarySearchTree<String> x = new BinarySearchTree<String>();
        x.add("C");
        x.add("A");
        x.add("E");
        x.add("B");
        x.add("D");
        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream oldOut = System.out;
        System.setOut(new PrintStream(outContent));
        BinaryTree.print(x, "x");
        System.setOut(oldOut);
        assertEquals("x in preorder\nC A B E D \nx in inorder\nA B C D E \n\n".trim(),
                     outContent.toString().trim());
    }

    @Test
    public void traversalConstructor() {
        ArrayList<String> preOrder = new ArrayList<>();
        preOrder.add("C");
        preOrder.add("A");
        preOrder.add("B");
        preOrder.add("E");
        preOrder.add("D");
        ArrayList<String> inOrder = new ArrayList<>();
        inOrder.add("A");
        inOrder.add("B");
        inOrder.add("C");
        inOrder.add("D");
        inOrder.add("E");
        BinarySearchTree<String> x = new BinarySearchTree<>(preOrder, inOrder);
        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream oldOut = System.out;
        System.setOut(new PrintStream(outContent));
        BinaryTree.print(x, "x");
        System.setOut(oldOut);
        assertEquals("x in preorder\nC A B E D \nx in inorder\nA B C D E \n\n".trim(),
                outContent.toString().trim());
    }

    @Test
    public void containEdgeTest() {
        BinarySearchTree<String> x = new BinarySearchTree<String>();

        assertFalse(x.contains("A"));
        assertFalse(x.contains(" "));
        assertFalse(x.contains(""));

        x.add("A");

        assertTrue(x.contains("A"));
        assertFalse(x.contains(" "));
        assertFalse(x.contains(""));

        x.add("B");
        x.add("C");
        x.add("D");
        x.add("E");

        assertTrue(x.contains("A"));
        assertFalse(x.contains(" "));
        assertFalse(x.contains(""));
        assertTrue(x.contains("B"));
        assertTrue(x.contains("C"));
        assertTrue(x.contains("D"));
        assertTrue(x.contains("E"));
    }

    @Test
    public void containsTest() {
        BinarySearchTree<String> x = new BinarySearchTree<String>();
        x.add("C");
        x.add("A");
        x.add("E");
        x.add("B");
        x.add("D");

        assertTrue(x.contains("A"));
        assertTrue(x.contains("B"));
        assertTrue(x.contains("C"));
        assertTrue(x.contains("D"));
        assertTrue(x.contains("E"));
        assertFalse(x.contains("F"));
        assertFalse(x.contains(" "));
    }
}