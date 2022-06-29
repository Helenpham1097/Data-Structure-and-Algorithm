package practice;

import java.util.ListIterator;
import java.util.NoSuchElementException;

public class LinkedList<E extends Comparable<E>> {

    private Node<E> firstNode;
    private int numberOfElements;

    public LinkedList() {
        this.firstNode = null;
        this.numberOfElements = 0;
    }

    //add node
    public boolean add(E element) throws IllegalArgumentException {
        Node<E> newNode = new Node(element);
        Node<E> currentNode = firstNode;
        Node<E> previousNode = currentNode;

        if (firstNode == null) {
            firstNode = newNode;
            numberOfElements++;
            return true;
        }

        if (newNode.element.compareTo(firstNode.element) < 0) {
            newNode.next = firstNode;
            firstNode = newNode;
            numberOfElements++;
            return true;
        }

        while (currentNode != null && newNode.element.compareTo(currentNode.element) >= 0) {
            if (newNode.element.compareTo(currentNode.element) == 0) {
                throw new IllegalArgumentException("The node existed in the link");
            }
            previousNode = currentNode;
            currentNode = currentNode.next;
        }

        if (currentNode == null) {
            previousNode.next = newNode;
            newNode.previous = previousNode;
            numberOfElements++;
            return true;

        } else {
            previousNode.next = newNode;
            newNode.previous = previousNode;
            currentNode.previous = newNode;
            newNode.next = currentNode;
            numberOfElements++;
            return true;
        }
    }

    // remove node
    public boolean remove(E element) {
        Node<E> removedNode = new Node(element);
        Node<E> currentNode = firstNode;
//        Node<E> previousNode = currentNode;

        if (removedNode.element.compareTo(firstNode.element) == 0) {
            firstNode = firstNode.next;
            numberOfElements--;
            return true;
        }

        while (currentNode != null && removedNode.element.compareTo(currentNode.element) != 0) {
//            previousNode = currentNode;
            currentNode = currentNode.next;
        }
        if (currentNode != null) {

            if (currentNode.next != null) {
                currentNode.previous.next = currentNode.next;
                currentNode.next.previous = currentNode.previous;
                numberOfElements--;
                return true;

            } else {
                currentNode.previous.next = null;
                currentNode = null;
                numberOfElements--;
                return true;
            }
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        String result = "";
        Node<E> currentNode = firstNode;
        while (currentNode != null) {
            result += "[" + currentNode.element + "]";
            currentNode = currentNode.next;
        }
        return result;
    }

    public int size() {
        return numberOfElements;
    }

    public void clear() {
        firstNode = null;
        numberOfElements = 0;
    }

    public ListIterator<E> nextIterator() {
        return new LinkedIterator<E>(firstNode);
    }

    private class Node<E> {

        private E element;
        private Node<E> next;
        private Node<E> previous;

        public Node(E element) {
            this.element = element;
            this.next = null;
            this.previous = null;
        }
    }

    private class LinkedIterator<E> implements ListIterator<E> {

        private Node startNode;

        public LinkedIterator(Node<E> startNode) {
            this.startNode = startNode;
        }

        @Override
        public boolean hasNext() {
            return startNode != null;
        }

        @Override
        public E next() throws NoSuchElementException {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            E element = (E) startNode.element;
            startNode = startNode.next;
            return element;
        }

        @Override
        public boolean hasPrevious() {
            return startNode != null;
        }

        @Override
        public E previous() {
            if (!hasPrevious()) {
                throw new NoSuchElementException();
            }
            E element = (E) startNode.element;
            startNode = startNode.previous;
            return element;
        }

        @Override
        public int nextIndex() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public int previousIndex() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void set(E e) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void add(E e) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

    }

    public static void main(String[] args) {
        LinkedList doublyLinkList = new LinkedList();
        doublyLinkList.add("B");
        doublyLinkList.add("A");
        doublyLinkList.add("M");
        doublyLinkList.add("N");

        try {
            doublyLinkList.add("N");
        } catch (IllegalArgumentException e) {
            System.out.println("The node existed");
        }

        doublyLinkList.add("O");
        doublyLinkList.add("J");

        System.out.println(doublyLinkList.numberOfElements);
        System.out.println(doublyLinkList.toString());
        System.out.println();

        System.out.println(doublyLinkList.remove("Z"));
        System.out.println();

        System.out.println(doublyLinkList.numberOfElements);
        System.out.println(doublyLinkList.toString());

        ListIterator linkedIterator = (LinkedList.LinkedIterator) doublyLinkList.nextIterator();
        while (linkedIterator.hasNext()) {
            System.out.println(linkedIterator.next());
        }

    }

}
