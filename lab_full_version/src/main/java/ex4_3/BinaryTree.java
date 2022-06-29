package ex4_3;

import ex4_1.LinkedBinaryTreeNode;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.Arrays;
import java.util.List;

public class BinaryTree extends LinkedBinaryTreeNode {

    public BinaryTree() {
        super();
    }

    public void leftRotationOfRoot() {
        Node root = getRoot();
        Node oldRightNode = root.getRight();
        Node detachedNode = oldRightNode.getLeft();
        detachedNode.setParent(null);
        root.setRight(detachedNode);
        Node newRight = oldRightNode.getRight();

        this.setRoot(oldRightNode);
        this.getRoot().setLeft(root);
        this.getRoot().setRight(newRight);
    }

    public void rightRotationOfRoot(){
        Node root = getRoot();
        Node oldLeftNode = root.getLeft();
        Node detachedNode = root.getLeft().getRight();
        detachedNode.setParent(null);

        root.setLeft(detachedNode);
        this.setRoot(oldLeftNode);
        this.getRoot().setRight(root);
    }

    public static void main(String[] args) {
        BinaryTree binaryTree = new BinaryTree();
        List<String> listNode = Arrays.asList("A", "B", "C", "D", "E", "F", "G", "H", "I", "K", "L", "M", "N", "O", "P");
        for (String s : listNode) {
            binaryTree.addNewNode(new Node(new DefaultMutableTreeNode(s)));
        }
        System.out.println("BINARY TREE BEFORE ROTATION");
        System.out.println(binaryTree);
        System.out.println("BINARY TREE AFTER RIGHT ROTATION");
        binaryTree.rightRotationOfRoot();
        System.out.println(binaryTree);
        System.out.println("BINARY TREE AFTER LEFT ROTATION");
        binaryTree.leftRotationOfRoot();
        System.out.println(binaryTree);

    }
}
