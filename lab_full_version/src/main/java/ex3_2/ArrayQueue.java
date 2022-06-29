package ex3_2;

import java.util.Arrays;
import java.util.Collection;
import java.util.NoSuchElementException;

/**
 * Exercise 3.2 Implementing Queues with Circular Array
 */
public class ArrayQueue<E> implements QueueADT<E> {
    private final int CAPACITY = 5;
    int front, rear;
    E[] elements;
    int numElements;

    public ArrayQueue() {
        front = 0;
        rear = 0;
        elements = (E[]) (new Object[CAPACITY]);
        numElements = 0;
    }

    public ArrayQueue(Collection<? extends E> c){
        this();
        for (E element : c){
            enqueue(element);
        }
    }
    public boolean isFull() {
        return front == 0 && rear == CAPACITY - 1
                || front == rear + 1;
    }

    @Override
    public void enqueue(E element) {
     if(numElements >= elements.length){
         expandCapacity();
     }
     elements[rear] = element;
     numElements++;
     rear = (rear+1)% elements.length;
    }

    @Override
    public E dequeue() throws NoSuchElementException {
        if (!isEmpty()) {
            E removedElement = elements[front];
            elements[front] = null;
            numElements --;
            front = (front+1) %elements.length;
            return removedElement;
        } else {
            throw new NoSuchElementException();
        }
    }

    @Override
    public E first() throws NoSuchElementException {
        if (!isEmpty()) {
            return elements[front];
        } else {
            throw new NoSuchElementException();
        }
    }

    @Override
    public boolean isEmpty() {
        return numElements == 0;
    }

    @Override
    public int size() {
     return numElements;
    }

    public void expandCapacity(){
        E[] largerArray = (E[]) new Object[CAPACITY*2];
        for(int i = 0; i<elements.length;i++){
            largerArray[i] = elements[front];
            front = (front+1) % elements.length;
        }
        elements = largerArray;
        front = 0;
        rear = numElements;
    }

    public static void main(String[] args) {
        ArrayQueue<String> arrayQueue = new ArrayQueue<>();

        System.out.println("Enqueue A,B,C");
        arrayQueue.enqueue("A");
        arrayQueue.enqueue("B");
        arrayQueue.enqueue("C");
        System.out.println(Arrays.toString(arrayQueue.elements));
        System.out.println("Dequeue");
        arrayQueue.dequeue();
        System.out.println(Arrays.toString(arrayQueue.elements));
        System.out.println("Enqueue A,B,C");
        arrayQueue.enqueue("D");
        arrayQueue.enqueue("E");
        arrayQueue.enqueue("F");
        System.out.println(Arrays.toString(arrayQueue.elements));
        System.out.println("Dequeue");
        arrayQueue.dequeue();
        System.out.println(Arrays.toString(arrayQueue.elements));
        System.out.println("Enqueue G,H,K");
        arrayQueue.enqueue("G");
        arrayQueue.enqueue("H");
        arrayQueue.enqueue("K");
        System.out.println(Arrays.toString(arrayQueue.elements));
    }
}
