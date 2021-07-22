import java.util.LinkedList;

public class BSTTest {

    public static void main(String[] args) {
        LinkedList<Integer> list = new LinkedList<>();

        BST<Integer> bst = new BST<>(list);
        bst.print();
        System.out.println("====\nNothing should be printed above.\n===\n");

        list.add(1);
        bst = new BST<>(list);
        bst.print();
        System.out.println("====\nCorrect if print: \n1 \n===\n");

        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        list.add(6);
        list.add(7);
        list.add(8);
        bst = new BST<>(list);
        bst.print();
        System.out.println("====\nCorrect if print: \n5\n  3\n    2\n      1\n    4\n  7\n    6\n    8\nNotice: There can be different stuctures that also correct.\n===\n");
    }
}
