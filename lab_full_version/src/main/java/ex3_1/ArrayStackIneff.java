package ex3_1;

import java.util.Collection;
import java.util.NoSuchElementException;

/**
 * Exercise 3.1 Inefficient Stack Implementation
 */
public class ArrayStackIneff<E> implements StackADT<E> {
    private final int CAPACITY = 20;
    protected int numElements;
    protected E[] elements;

    public ArrayStackIneff() {
        this.numElements = 0;
        this.elements = (E[]) (new Object[CAPACITY]);
    }

    public ArrayStackIneff(Collection<? extends E> c) {
        this();
        for (E element : c) {
            push(element);
        }
    }

    public void push(E element) {
        if (numElements >= elements.length) {
            expandCapacity();
        }
        if (isEmpty()) {
            elements[numElements] = element;
        } else {
            for (int i = numElements - 1; i >= 0; i--) {
                elements[i + 1] = elements[i];
                if (i == 0) {
                    elements[i] = element;
                }
            }
        }
        numElements++;
    }

    public E pop() throws NoSuchElementException {
        if (numElements > 0) {
            E removeElements = elements[0];
            for (int i = 0; i <= numElements - 1; i++) {
                elements[i] = elements[i + 1];
                if (i == numElements - 1) {
                    elements[i] = null;
                }
            }
            numElements--;
            return removeElements;
        } else {
            throw new NoSuchElementException();
        }
    }

    @Override
    public E peek() throws NoSuchElementException {
        return null;
    }

    @Override
    public boolean isEmpty() {
        return numElements == 0;
    }

    @Override
    public int size() {
        return 0;
    }

    private void expandCapacity() {
        E[] largerArray = (E[]) (new Object[elements.length * 2]);
        for (int i = 0; i < numElements; i++)
            largerArray[i] = elements[i];
        elements = largerArray;
    }

    public static void main(String[] args) {
        int numElements = 100000;
        StackADT<Integer> goodStack = new ArrayStack<>();
        System.out.println("Pushing on "+ numElements + " onto Normal Stack");
        long begin = System.currentTimeMillis();
        for(int i = 0; i < numElements; i++){
            goodStack.push(i);
        }
        System.out.println("Now popping all values from Good Stack - LIFO");
        while (!goodStack.isEmpty()){
            goodStack.pop();
        }
        System.out.println();
        System.out.println("Properly implemented stack took " + (System.currentTimeMillis()-begin) + " milliseconds");

        StackADT<Integer> badStack = new ArrayStackIneff<>();
        System.out.println("Pushing on "+ numElements + " onto Bad Stack");
        begin = System.currentTimeMillis();
        for(int i = 0; i < numElements; i++){
            badStack.push(i);
        }
        System.out.println("Now popping all values from Bad Stack - LIFO");
        while (!badStack.isEmpty()){
            badStack.pop();
        }
        System.out.println();
        System.out.println("Inefficient implemented stack took " + (System.currentTimeMillis()-begin) + " milliseconds");
    }
}
