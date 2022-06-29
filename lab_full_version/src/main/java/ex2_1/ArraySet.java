package ex2_1;

import java.util.*;

public class ArraySet<E> extends AbstractSet<E> {
    private final int INITIAL_CAPACITY = 100;
    protected int numElements;
    protected E[] elements;

    public ArraySet() {
        super();
        numElements = 0;
        elements = (E[]) (new Object[INITIAL_CAPACITY]);
    }

    public ArraySet(Collection<? extends E> c) {
        this();
        for (E element : c) {
            add(element);
        }
    }

    public boolean add(E o) {

        if (numElements >= elements.length) {
            expandCapacity();
        }

        if (!contain(o)) {
            elements[numElements] = o;
            numElements++;
            return true;
        } else
            return false;
    }

    public boolean contain(Object o){
        for(int i = 0; i< numElements; i++){
            if (elements[i].equals(o)){
                return true;
            }
        }
        return false;
    }

    public boolean remove(Object o) {
        int index = 0;
        boolean found = false;
        for (int i = 0; i < numElements && !found; i++) {
            if ((elements[i] == null && o == null) || (elements[i] != null && elements[i].equals(o))) {
                index = i;
                found = true;
            }
        }
        if (found) {
            elements[index] = elements[numElements - 1];
            elements[numElements - 1] = null;
            numElements--;
        }
        return found;
    }

    public Iterator<E> iterator() {
        return new ArrayIterator<E>(elements, numElements);
    }

    public int size() {
        return numElements;
    }

    public void clear() {
        for (int i = 0; i < numElements; i++)
            elements[i] = null;
        numElements = 0;
    }

    private void expandCapacity() {
        E[] largerArray = (E[]) (new Object[elements.length * 2]);
        for (int i = 0; i < numElements; i++) {
            largerArray[i] = elements[i];
        }
        elements = largerArray;
    }

    private class ArrayIterator<E> implements Iterator<E> {
        private int numElements;
        private E[] elements;
        private int nextIndex;

        public ArrayIterator(E[] elements, int numElements) {
            if (numElements > elements.length)
                numElements = elements.length;
            this.numElements = numElements;
            this.elements = elements;
            nextIndex = 0;
        }

        public boolean hasNext() {
            return (nextIndex < numElements);
        }

        public E next() throws NoSuchElementException {
            if (!hasNext())
                throw new NoSuchElementException();
            return elements[nextIndex++];
        }

        public void remove() throws UnsupportedOperationException {
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String[] args) {
        System.out.println("TEST ARRAY SET HOLING DIFFERENT NUMBER OF ELEMENTS");

        List<Integer> nValues = List.of(1000,2000,3000,4000);

        for (int n : nValues) {
            System.out.println("AN ARRAY WITH "+ n + " ELEMENTS");
            ArraySet<Integer> set = new ArraySet<>();

            long begin = System.nanoTime();
            for (int i = 0; i < n; i++) {
                set.add(i);
            }
            System.out.println("Time taken in nano seconds to add " + n + " elements: " + (System.nanoTime() - begin));

            begin = System.nanoTime();
            set.contains(n/2);
            System.out.println("Time taken in nano seconds to retrieve element " + n / 2 + " in the array is: " + (System.nanoTime() - begin));

            begin = System.nanoTime();
            set.remove(n/2);
            System.out.println("Time taken in nano seconds to retrieve element " + n / 2 + " in the array is: " + (System.nanoTime()  - begin));
            System.out.println();
        }
    }
}
