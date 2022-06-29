package assignment1.question2;

import java.util.HashSet;
import java.util.Set;

public class LinkRetainRemoveSet<E extends Comparable<E>> extends LinkedSet<E> {

    public LinkRetainRemoveSet() {
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

    public Set<E> remove(E start, E end) {
        Set<E> removeElements = new HashSet<>();
        Node<E> currentNode = firstNode;
        Node<E> previousNode = firstNode;

        if (start == null && end == null) {
            for (int i = 0; i < numElements; i++) {
                removeElements.add(currentNode.element);
                currentNode = currentNode.next;
            }
            firstNode = null;
            numElements = 0;
            return removeElements;

        }else if(start!= null && end != null && start.compareTo(end)>=0){
            throw new IllegalArgumentException("The start point and end point must be different and start must be less than end");

        } else if (start == null || currentNode.element == start) {
            while (currentNode.element != end) {
                removeElements.add(currentNode.element);
                currentNode = currentNode.next;
                firstNode = currentNode;
                numElements--;
            }
            return removeElements;

        } else if (end == null) {
            int count = 0;
            while (currentNode.element != start) {
                count++;
                previousNode = currentNode;
                currentNode = currentNode.next;
            }
            numElements = count;
            while (currentNode != null) {
                removeElements.add(currentNode.element);
                currentNode = currentNode.next;
            }
            previousNode.next = null;
            return removeElements;

        } else {
            int count = numElements;

            while (currentNode != null && currentNode.element != start) {
                previousNode = currentNode;
                currentNode = currentNode.next;
            }

            if (currentNode == null) {
                throw new IllegalArgumentException("The start point is not in the set");
            }

            while (currentNode != null && currentNode.element != end) {
                removeElements.add(currentNode.element);
                currentNode = currentNode.next;
                count--;
            }

            if (currentNode == null) {
                throw new IllegalArgumentException("The end point is not in the set");
            }

            previousNode.next = currentNode;
            numElements = count;
            return removeElements;
        }
    }

    public Set<E> retain(E start, E end) {
        int oldNumElements = numElements;
        Set<E> removeElements = new HashSet<>();
        Node<E> currentNode = firstNode;
        Node<E> previousNode = firstNode;
        Node<E> newNode;

        if (start != null && end == null) {
            int count = 0;

            while (currentNode.element != start) {
                removeElements.add(currentNode.element);
                currentNode = currentNode.next;
                firstNode = currentNode;
                count++;
            }

            numElements = oldNumElements - count;
            return removeElements;

        } else if (start != null && start.compareTo(end) >= 0) {
            throw new IllegalArgumentException("The start point and end point must be different and start must be less than end");

        } else if (start == null && end != null) {
            int count = 0;

            while (currentNode.element != end) {
                previousNode = currentNode;
                currentNode = currentNode.next;
                count++;
            }

            while (currentNode != null) {
                removeElements.add(currentNode.element);
                currentNode = currentNode.next;
            }

            previousNode.next = null;
            numElements = count;
            return removeElements;

        } else if (start == null) {
            return removeElements;

        } else {
            int count = 0;

            while (currentNode != null && currentNode.element != start) {
                removeElements.add(currentNode.element);
                currentNode = currentNode.next;
            }

            if (currentNode == null) {
                throw new IllegalArgumentException("The start point is not in the set");
            }

            Node<E> tempFistNode = currentNode;
            newNode = tempFistNode;

            while (currentNode != null && currentNode.element != end) {
                newNode = currentNode;
                currentNode = currentNode.next;
                count++;
            }

            if (currentNode == null) {
                throw new IllegalArgumentException("The end point is not in the set");
            }

            firstNode = tempFistNode;

            while (currentNode != null) {
                removeElements.add(currentNode.element);
                currentNode = currentNode.next;
            }

            numElements = count;
            newNode.next = null;
            return removeElements;
        }
    }

    public static void main(String[] args) {

        System.out.println("--------------------TEST CASES FOR ADD METHOD--------------------");
        System.out.println("TEST CASE 1: TEST ADD METHOD WITH THESE INTEGER 10, 500, 1, 55, 30, 70");
        LinkRetainRemoveSet<Integer> testCase1 = new LinkRetainRemoveSet<>();
        testCase1.add(10);
        testCase1.add(500);
        testCase1.add(1);
        testCase1.add(55);
        testCase1.add(30);
        testCase1.add(70);
        System.out.println(testCase1);
        System.out.println();


        System.out.println("--------------------TEST CASES FOR REMOVE METHOD--------------------");
        System.out.println("ADD NUMBERS 1,2,3,4,5,6,7 TO THE SET");
        LinkRetainRemoveSet<Integer> testCase2 = new LinkRetainRemoveSet<>();
        for (int i = 1; i <= 7; i++) {
            testCase2.add(i);
        }
        System.out.println(testCase2);
        System.out.println("TEST CASE 2: REMOVE(NULL, NULL)");
        System.out.println("Removed elements set is ");
        System.out.println(testCase2.remove(null,null));
        System.out.println("The set after removed is");
        System.out.println(testCase2);
        System.out.println(testCase2.numElements);
        System.out.println();


        System.out.println("ADD NUMBERS 1,2,3,4,5,6,7 TO THE SET");
        LinkRetainRemoveSet<Integer> testCase3 = new LinkRetainRemoveSet<>();
        for (int i = 1; i <= 7; i++) {
            testCase3.add(i);
        }
        System.out.println(testCase3);
        System.out.println("TEST CASE 3: REMOVE(4, NULL)");
        System.out.println("Removed elements set is ");
        System.out.println(testCase3.remove(4,null));
        System.out.println("The set after removed is");
        System.out.println(testCase3);
        System.out.println(testCase3.numElements);
        System.out.println();


        System.out.println("ADD NUMBERS 1,2,3,4,5,6,7 TO THE SET");
        LinkRetainRemoveSet<Integer> testCase4 = new LinkRetainRemoveSet<>();
        for (int i = 1; i <= 7; i++) {
            testCase4.add(i);
        }
        System.out.println(testCase4);
        System.out.println("TEST CASE 4: REMOVE(NULL, 4)");
        System.out.println("Removed elements set is ");
        System.out.println(testCase4.remove(null,4));
        System.out.println("The set after removed is");
        System.out.println(testCase4);
        System.out.println(testCase4.numElements);
        System.out.println();


        System.out.println("ADD NUMBERS 1,2,3,4,5,6,7 TO THE SET");
        LinkRetainRemoveSet<Integer> testCase5 = new LinkRetainRemoveSet<>();
        for (int i = 1; i <= 7; i++) {
            testCase5.add(i);
        }
        System.out.println(testCase5);
        System.out.println("TEST CASE 5: REMOVE(2,6)");
        System.out.println("Removed elements set is ");
        System.out.println(testCase5.remove(2,6));
        System.out.println("The set after removed is");
        System.out.println(testCase5);
        System.out.println(testCase5.numElements);
        System.out.println();


        System.out.println("ADD NUMBERS 1,2,3,4,5,6,7 TO THE SET");
        LinkRetainRemoveSet<Integer> testCase6 = new LinkRetainRemoveSet<>();
        for (int i = 1; i <= 7; i++) {
            testCase6.add(i);
        }
        System.out.println(testCase6);
        System.out.println("TEST CASE 6: REMOVE(4,4)");
        System.out.println("Removed elements set will throw exception ");
        try {
            testCase6.remove(4, 4);
        }catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
        System.out.println("The set after removed is");
        System.out.println(testCase6);
        System.out.println(testCase6.numElements);
        System.out.println();


        System.out.println("ADD NUMBERS 1,2,3,4,5,6,7 TO THE SET");
        LinkRetainRemoveSet<Integer> testCase7 = new LinkRetainRemoveSet<>();
        for (int i = 1; i <= 7; i++) {
            testCase7.add(i);
        }
        System.out.println(testCase7);
        System.out.println("TEST CASE 7: REMOVE(6,100)");
        System.out.println("Removed elements set will throw exception ");
        try {
            testCase7.remove(6, 100);
        }catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
        System.out.println("The set after removed is");
        System.out.println(testCase7);
        System.out.println(testCase7.numElements);
        System.out.println();


        System.out.println("ADD NUMBERS 1,2,3,4,5,6,7 TO THE SET");
        LinkRetainRemoveSet<Integer> testCase8 = new LinkRetainRemoveSet<>();
        for (int i = 1; i <= 7; i++) {
            testCase8.add(i);
        }
        System.out.println(testCase8);
        System.out.println("TEST CASE 8: REMOVE(9,10)");
        System.out.println("Removed elements set will throw exception ");
        try {
            testCase7.remove(9,10);
        }catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
        System.out.println("The set after removed is");
        System.out.println(testCase7);
        System.out.println(testCase7.numElements);
        System.out.println();


        System.out.println("--------------------TEST CASES FOR RETAIN METHOD--------------------");
        System.out.println("ADD NUMBERS 1,2,3,4,5,6,7 TO THE SET");
        LinkRetainRemoveSet<Integer> testCase9 = new LinkRetainRemoveSet<>();
        for (int i = 1; i <= 7; i++) {
            testCase9.add(i);
        }
        System.out.println(testCase9);
        System.out.println("TEST CASE 9: RETAIN(NULL, NULL)");
        System.out.println("Removed elements set is ");
        System.out.println(testCase9.retain(null,null));
        System.out.println("The set after retained is");
        System.out.println(testCase9);
        System.out.println(testCase9.numElements);
        System.out.println();


        System.out.println("ADD NUMBERS 1,2,3,4,5,6,7 TO THE SET");
        LinkRetainRemoveSet<Integer> testCase10 = new LinkRetainRemoveSet<>();
        for (int i = 1; i <= 7; i++) {
            testCase10.add(i);
        }
        System.out.println(testCase10);
        System.out.println("TEST CASE 10: RETAIN(4, NULL)");
        System.out.println("Removed elements set is ");
        System.out.println(testCase10.retain(4,null));
        System.out.println("The set after retained is");
        System.out.println(testCase10);
        System.out.println(testCase10.numElements);
        System.out.println();


        System.out.println("ADD NUMBERS 1,2,3,4,5,6,7 TO THE SET");
        LinkRetainRemoveSet<Integer> testCase11 = new LinkRetainRemoveSet<>();
        for (int i = 1; i <= 7; i++) {
            testCase11.add(i);
        }
        System.out.println(testCase11);
        System.out.println("TEST CASE 11: RETAIN(NULL, 4)");
        System.out.println("Removed elements set is ");
        System.out.println(testCase11.retain(null,4));
        System.out.println("The set after retained is");
        System.out.println(testCase11);
        System.out.println(testCase11.numElements);
        System.out.println();


        System.out.println("ADD NUMBERS 1,2,3,4,5,6,7 TO THE SET");
        LinkRetainRemoveSet<Integer> testCase12 = new LinkRetainRemoveSet<>();
        for (int i = 1; i <= 7; i++) {
            testCase12.add(i);
        }
        System.out.println(testCase12);
        System.out.println("TEST CASE 12: RETAIN(2,6)");
        System.out.println("Removed elements set is ");
        System.out.println(testCase12.retain(2,6));
        System.out.println("The set after retained is");
        System.out.println(testCase12);
        System.out.println(testCase12.numElements);
        System.out.println();


        System.out.println("ADD NUMBERS 1,2,3,4,5,6,7 TO THE SET");
        LinkRetainRemoveSet<Integer> testCase13 = new LinkRetainRemoveSet<>();
        for (int i = 1; i <= 7; i++) {
            testCase13.add(i);
        }
        System.out.println(testCase13);
        System.out.println("TEST CASE 13: RETAIN(4,4)");
        System.out.println("Removed elements set will throw exception ");
        try {
            testCase13.retain(4, 4);
        }catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
        System.out.println("The set after removed is");
        System.out.println(testCase13);
        System.out.println(testCase13.numElements);
        System.out.println();


        System.out.println("ADD NUMBERS 1,2,3,4,5,6,7 TO THE SET");
        LinkRetainRemoveSet<Integer> testCase14 = new LinkRetainRemoveSet<>();
        for (int i = 1; i <= 7; i++) {
            testCase14.add(i);
        }
        System.out.println(testCase14);
        System.out.println("TEST CASE 14: RETAIN(6,100)");
        System.out.println("Removed elements set will throw exception ");
        try {
            testCase14.retain(6,100);
        }catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
        System.out.println("The set after retained is");
        System.out.println(testCase14);
        System.out.println(testCase14.numElements);
        System.out.println();


        System.out.println("ADD NUMBERS 1,2,3,4,5,6,7 TO THE SET");
        LinkRetainRemoveSet<Integer> testCase15 = new LinkRetainRemoveSet<>();
        for (int i = 1; i <= 7; i++) {
            testCase15.add(i);
        }
        System.out.println(testCase15);
        System.out.println("TEST CASE 15: RETAIN(100,6)");
        System.out.println("Removed elements set will throw exception ");
        try {
            testCase15.retain(100,6);
        }catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
        System.out.println("The set after retained is");
        System.out.println(testCase15);
        System.out.println(testCase15.numElements);
        System.out.println();


        System.out.println("ADD NUMBERS 1,2,3,4,5,6,7 TO THE SET");
        LinkRetainRemoveSet<Integer> testCase16 = new LinkRetainRemoveSet<>();
        for (int i = 1; i <= 7; i++) {
            testCase16.add(i);
        }
        System.out.println(testCase16);
        System.out.println("TEST CASE 16: RETAIN(9,10)");
        System.out.println("Removed elements set will throw exception ");
        try {
            testCase16.retain(9,10);
        }catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
        System.out.println("The set after retained is");
        System.out.println(testCase16);
        System.out.println(testCase16.numElements);
        System.out.println();

    }
}
