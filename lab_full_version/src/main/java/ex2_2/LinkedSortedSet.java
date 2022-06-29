package ex2_2;

import java.util.Set;

public class LinkedSortedSet<E extends Comparable<E>> extends LinkedSet<E> {

    public LinkedSortedSet() {
        super();
    }

    @Override
    public boolean add(E o) {
        Node<E> newNode = new Node<E>(o);

        if (firstNode == null || newNode.element.compareTo(firstNode.element) < 0) {
            newNode.next = firstNode;
            firstNode = newNode;
        } else {
            Node<E> currentNode = firstNode;
            Node<E> prevNode = firstNode;
            while (currentNode != null && newNode.element.compareTo(currentNode.element) >= 0) {
                if (newNode.element.compareTo(currentNode.element) == 0) {
                    return false;
                }
                prevNode = currentNode;
                currentNode = currentNode.next;
            }
            prevNode.next = newNode;
            newNode.next = currentNode;
        }

        numElements++;
        return true;
    }

    public static void main(String[] args) {
        Set<String> linkedSortedSet = new LinkedSortedSet<>();
        linkedSortedSet.add("Helen");
        linkedSortedSet.add("Thay");
        linkedSortedSet.add("Sophia");
        linkedSortedSet.add("Winnie");
        linkedSortedSet.add("Helen");
        linkedSortedSet.add("Winnie");

        System.out.println(linkedSortedSet);

    }


}
