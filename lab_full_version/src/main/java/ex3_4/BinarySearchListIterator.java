package ex3_4;

import java.util.*;

public class BinarySearchListIterator<E extends Comparable> {
    private final List<E> elements;

    public BinarySearchListIterator(List<E> elements) {
        this.elements = elements;
    }

    public int search(E target) {
        int timesTraversals = 0;
        if (target == null) {
            throw new NullPointerException("Search target is null");
        }
        ListIterator<E> iterator = elements.listIterator();
        return search(target, 0, elements.size(), iterator, timesTraversals);
    }

    public int search(E target, int start, int end, ListIterator<E> iterator, int timesTraversals) {
        if (start >= end) {
            return -start-1;
        } else {
            int midpoint = (start + end) / 2;
            E midValue = null;
            while (iterator.hasNext()) {
                if (midpoint == iterator.nextIndex()) {
                    midValue = elements.get(midpoint);
                    break;
                }
                iterator.next();
            }
            int comparison = target.compareTo(midValue);
            if (comparison == 0) {
                return timesTraversals;
            } else if (comparison < 0) {
                timesTraversals++;
                return search(target, start, midpoint, elements.listIterator(), timesTraversals);
            } else {
                timesTraversals++;
                return search(target, midpoint + 1, end, elements.listIterator(), timesTraversals);
            }
        }
    }

    public static void main(String[] args) {
        LinkedList<String> pets = new LinkedList<>();
        pets.add("Cat");
        pets.add("Dog");
        pets.add("Rabbit");
        pets.add("Bird");
        pets.add("Hamster");
        pets.add("Fish");
        pets.add("Lion");
        pets.add("Snake");
        pets.add("Sophia");
        pets.add("Hubby");
        Collections.sort(pets);
        System.out.println(pets);
        String target = "Bird";
        BinarySearchListIterator binarySearchListIterator = new BinarySearchListIterator(pets);
        int result = binarySearchListIterator.search(target);
        if(result >= 0){
            System.out.println("The method needs "+ result + " traversals to move th iterator back or forth at each step of the recursion");
        }else{
            System.out.println("The target value is not available");
        }
    }
}