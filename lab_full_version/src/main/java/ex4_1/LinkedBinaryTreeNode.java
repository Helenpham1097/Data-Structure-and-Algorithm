package ex4_1;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

// count number of element
// add node in a position
public class LinkedBinaryTreeNode {
    private Node root;
    private int numberOfElements;

    public Node getRoot() {
        return this.root;
    }

    public void setRoot(Node root) {
        this.root = root;
    }

    protected static class Node {
        private MutableTreeNode element;
        private Node left;
        private Node right;
        private Node parent;

        public Node() {
            this.element = null;
            this.left = null;
            this.right = null;
            this.parent = null;
        }

        public Node(MutableTreeNode element) {
            this.element = element;
            this.left = null;
            this.right = null;
            this.parent = null;
        }

        public MutableTreeNode getElement() {
            return element;
        }

        public Node getLeft() {
            return left;
        }

        public Node getRight() {
            return right;
        }

        public Node getParent() {
            return parent;
        }

        public void setElement(MutableTreeNode element) {
            this.element = element;
        }

        public void setLeft(Node left) {
            this.left = left;
        }

        public void setRight(Node right) {
            this.right = right;
        }

        public void setParent(Node parent) {
            this.parent = parent;
        }
    }

    public LinkedBinaryTreeNode() {
        this.root = null;
        numberOfElements = 0;
    }

    public LinkedBinaryTreeNode(Node root) {
        this.root = root;
        numberOfElements = 1;
    }

    public boolean addNewNode(Node newNode) {

        if (root == null) {
            root = newNode;
            numberOfElements++;
            return true;

        } else {
            Queue<Node> queue = new LinkedList<>();
            queue.add(root);

            while (!queue.isEmpty()) {
                Node currentNode = queue.remove();

                if (currentNode.left != null && currentNode.right != null) {
                    queue.add(currentNode.left);
                    queue.add(currentNode.right);

                } else {
                    if (currentNode.left == null) {
                        currentNode.left = newNode;
                        currentNode.left.parent = currentNode;
                        queue.add(currentNode.left);
                        numberOfElements++;
                        return true;

                    }
                    currentNode.right = newNode;
                    currentNode.right.parent = currentNode;
                    queue.add(currentNode.right);
                    numberOfElements++;
                    return true;
                }
            }
        }
        return false;
    }

    public Node findNode(MutableTreeNode element) {
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            Node currentNode = queue.remove();

            if (currentNode.element == element) {
                return currentNode;
            } else {

                if (currentNode.left != null) {
                    if (currentNode.left.element == element) {
                        return currentNode.left;
                    } else {
                        queue.add(currentNode.left);
                    }
                }

                if (currentNode.right != null) {
                    if (currentNode.right.element == element) {
                        return currentNode.right;
                    } else {
                        queue.add(currentNode.right);
                    }
                }
            }
        }
        return null;
    }

    public int getSize(){
        return numberOfElements++;
    }

    public boolean isLeaf(Node node) throws IllegalArgumentException {
        Node foundNode = findNode(node.element);
        if (foundNode != null) {
            return foundNode.left == null && foundNode.right == null;
        }
        throw new IllegalArgumentException("Node cannot be found in the Linked Binary Tree");
    }

    //if removed node is not a leaf, then find the most right node to replace the remove node. if the most right node is not a leaf, then move left node to it position and move it to the remove node
    public boolean remove(Node removedNode) throws IllegalArgumentException {
        Node foundNode = findNode(removedNode.element);
        if (foundNode != null) {
            if (isLeaf(foundNode)) {
                if (foundNode.parent.left == foundNode) {
                    foundNode.parent.left = null;
                }
                if (foundNode.parent.right == foundNode) {
                    foundNode.parent.right = null;
                }

            } else {
                Node mostRightBottom = findMostRightBottom(root);

                if (isLeaf(mostRightBottom)) {
                    foundNode.element = mostRightBottom.element;
                    mostRightBottom.parent.left = null;
                    mostRightBottom.element = null;
                } else {
                    MutableTreeNode replaceNode = mostRightBottom.left.element;
                    foundNode.element = mostRightBottom.element;
                    mostRightBottom.element = replaceNode;
                    mostRightBottom.left = null;
                }
            }
            numberOfElements--;
            return true;
        }
        throw new IllegalArgumentException("Node cannot be found in the Linked Binary Tree");
    }

    private Node findMostRightBottom(Node startedNode) {
        if (startedNode.right != null) {
            return findMostRightBottom(startedNode.right);
        } else {
            return startedNode;
        }
    }

    public Node getParent(MutableTreeNode element) throws IllegalArgumentException {
        Node foundNode = findNode(element);
        if (foundNode != null) {
            return foundNode.parent;
        }
        throw new IllegalArgumentException("Node cannot be found in the Linked Binary Tree");
    }

    public List<MutableTreeNode> getChildNodes(MutableTreeNode element) {
        List<MutableTreeNode> childNodes = new ArrayList<>();
        Node foundNode = findNode(element);
        if (foundNode != null) {
            if (foundNode.left.element != null) {
                childNodes.add(foundNode.left.element);
            }
            if (foundNode.right.element != null) {
                childNodes.add(foundNode.right.element);
            }
        }
        return childNodes;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            Node node = queue.remove();
            result.append("[").append(node.element).append("] ");

            if (node.left != null) {
                queue.add(node.left);
            }

            if (node.right != null) {
                queue.add(node.right);
            }
        }
        return result.toString();
    }

    public static void main(String[] args) {
        Node root = new Node(new DefaultMutableTreeNode("A"));
        LinkedBinaryTreeNode linkedBinaryTreeNode = new LinkedBinaryTreeNode(root);

        Node removedNode = new Node(new DefaultMutableTreeNode("B"));
        linkedBinaryTreeNode.addNewNode(removedNode);

        linkedBinaryTreeNode.addNewNode(new Node(new DefaultMutableTreeNode("C")));

        linkedBinaryTreeNode.addNewNode(new Node(new DefaultMutableTreeNode("D")));
        linkedBinaryTreeNode.addNewNode(new Node(new DefaultMutableTreeNode("E")));
        linkedBinaryTreeNode.addNewNode(new Node(new DefaultMutableTreeNode("F")));

        Node removedLeafNode = new Node(new DefaultMutableTreeNode("G"));
        linkedBinaryTreeNode.addNewNode(removedLeafNode);

        System.out.println("Linked Binary Tree Node after adding A B C D E F G");
        System.out.println(linkedBinaryTreeNode);
        System.out.println();

        System.out.println("Get Parent of Node G");
        System.out.println(linkedBinaryTreeNode.getParent(removedLeafNode.element).element);
        System.out.println();

        System.out.println("Get children of node B");
        List<MutableTreeNode> nodes = linkedBinaryTreeNode.getChildNodes(removedNode.element);
        for(MutableTreeNode element : nodes){
            System.out.println(element);
        }
        System.out.println();

        System.out.println("Remove a node G which is a leaf ");
        System.out.println(linkedBinaryTreeNode.remove(removedLeafNode));
        System.out.println(linkedBinaryTreeNode);
        System.out.println();

        System.out.println("Remove a node B which is not a leaf ");
        System.out.println(linkedBinaryTreeNode.remove(removedNode));
        System.out.println(linkedBinaryTreeNode);
        System.out.println();

        System.out.println("The size of the tree is ");
        System.out.println(linkedBinaryTreeNode.getSize());

    }
}
