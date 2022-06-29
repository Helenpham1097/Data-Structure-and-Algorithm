package ex3_1;

import java.util.NoSuchElementException;

public interface StackADT<E> {
    public void push(E element);
    public E pop() throws NoSuchElementException;
    public E peek() throws NoSuchElementException;
    public boolean isEmpty();
    public int size();
}
