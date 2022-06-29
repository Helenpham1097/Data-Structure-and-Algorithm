package datastructure.assignment2.exercise3;

import java.util.Arrays;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class HashSetWithChaining<E> implements Set<E> {

    private final double loadFactor;

    private int capacity;

    public static final double DEFAULT_LOAD_FACTOR = .75;

    private Node<E>[] buckets;

    private int threshold;

    private int size = 0;
    private int bucketFills = 0;

    private int structureChanges = 0;

    static class Node<V> {
        V value;

        Node<V> next;

        Node(V value, Node<V> next) {
            this.value = value;
            this.next = next;
        }

        boolean addToTail(Node<V> node) {
            Node<V> current = this;
            Node<V> prev = this;
            while (current != null && !current.value.equals(node.value)) {
                prev = current;
                current = current.next;
            }

            // value is found
            if (current != null) {
                return false;
            }

            prev.next = node;
            return true;
        }

        @Override
        public String toString() {
            Node<V> current = this;
            String s = "";
            while (current != null) {
                s += current.value;
                if (current.next != null) {
                    s += "-->";
                }
                current = current.next;
            }
            return s;
        }
    }

    public HashSetWithChaining(final int initCapacity,
                               final double loadFactor) {
        this.loadFactor = loadFactor;
        this.capacity = initCapacity;

        threshold = (int) (initCapacity * loadFactor);
        buckets = new Node[initCapacity];
    }


    public HashSetWithChaining(final int initCapacity) {
        this(initCapacity, DEFAULT_LOAD_FACTOR);
    }

    public HashSetWithChaining() {
        this(100);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(final Object o) {
        int hash = getHash(o);
        if (buckets[hash] == null) {
            return false;
        }

        Node<E> firstNode = buckets[hash];
        while (firstNode != null && !firstNode.value.equals(o)) {
            firstNode = firstNode.next;
        }

        return firstNode != null;
    }

    @Override
    public Iterator<E> iterator() {
        return new ChainIterator();
    }

    @Override
    public Object[] toArray() {
        Object[] results = new Object[size];
        Iterator<E> iterator = iterator();

        int i = 0;
        while (iterator.hasNext()) {
            results[i] = iterator.next();
            i++;
        }
        return results;
    }

    @Override
    public <T> T[] toArray(final T[] a) {

        T[] results = a.length >= size ? a :
                (T[]) java.lang.reflect.Array
                        .newInstance(a.getClass().getComponentType(), size);

        Iterator<E> iterator = iterator();
        int i = 0;
        while (iterator.hasNext()) {
            results[i] = (T) iterator.next();
            i++;
        }

        return results;
    }

    @Override
    public boolean add(final E e) {

        if (bucketFills >= threshold) {
            reArrangeElements();
        }

        int hash = getHash(e);

        if (buckets[hash] == null) {
            Node<E> firstNode = new Node<>(e, null);
            buckets[hash] = firstNode;
            bucketFills++;
            size++;
            structureChanges++;
            return true;
        }

        boolean result = buckets[hash].addToTail(new Node<>(e, null));
        if (result) {
            structureChanges++;
            size++;
        }

        return result;
    }

    private void reArrangeElements() {
        System.out.println("Load capacity exceeded, expanding capacity");
        bucketFills = 0;
        size = 0;
        capacity *= 2;
        threshold = (int) (capacity * loadFactor);

        Node<E>[] temp = buckets;
        buckets = new Node[capacity];

        for (Node<E> e : temp) {
            if (e != null) {
                Node<E> firstNode = e;
                while (firstNode != null) {
                    add(firstNode.value);
                    firstNode = firstNode.next;
                }
            }
        }
    }

    @Override
    public boolean remove(final Object o) {
        int hash = getHash(o);
        if (buckets[hash] == null) {
            return false;
        }

        if (buckets[hash].value.equals(o)) {
            buckets[hash] = buckets[hash].next;
            size--;
            structureChanges++;
            return true;
        }

        Node<E> current = buckets[hash];
        Node<E> prev = buckets[hash];

        while (current != null && !current.value.equals(o)) {
            prev = current;
            current = current.next;
        }

        if (current == null) {
            return false;
        }

        prev.next = current.next;
        size--;
        structureChanges++;
        return true;
    }

    private int getHash(Object o) {
        return Math.abs(o.hashCode()) % capacity;
    }

    @Override
    public boolean containsAll(final Collection<?> c) {
        for (Object o : c) {
            if (!contains(o)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean addAll(final Collection<? extends E> c) {
        boolean result = true;
        for (E e : c) {
            if (!add(e)) {
                result = false;
            }
        }
        return result;
    }

    @Override
    public boolean retainAll(final Collection<?> c) {
        Objects.requireNonNull(c);
        boolean modified = false;
        Iterator<E> it = iterator();
        while (it.hasNext()) {
            if (!c.contains(it.next())) {
                it.remove();
                modified = true;
            }
        }
        return modified;
    }

    @Override
    public boolean removeAll(final Collection<?> c) {
        boolean result = true;
        for (Object o : c) {
            if (!remove(o)) {
                result = false;
            }
        }
        return result;
    }

    @Override
    public void clear() {
        Arrays.fill(buckets, null);
        size = 0;
        bucketFills = 0;
    }

    @Override
    public String toString() {
        String s = "";
        for (int i = 0; i < buckets.length; i++) {
            if (buckets[i] == null) {
                s += String.format("Row %s:", i);
            } else {
                s += String.format("Row %s: %s", i, buckets[i].toString());
            }
            s += "\n";
        }

        s += String.format("Size is: %s", size);
        return s;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final HashSetWithChaining<?> chaining = (HashSetWithChaining<?>) o;
        return size == chaining.size && containsAll(Arrays.stream(chaining.toArray()).collect(Collectors.toList()));
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(size);
        for (Node<E> bucket : buckets) {
            if (bucket != null) {
                Node<E> pointer = bucket;
                while (pointer != null) {
                    result = 31 * result + pointer.value.hashCode();
                    pointer = pointer.next;
                }
            }
        }

        return result;
    }

    class ChainIterator implements Iterator<E> {

        private Node<E> current, next = null;

        private int index;

        private int expectedStructureChanges;

        ChainIterator() {
            expectedStructureChanges = structureChanges;
            Node<E>[] t = buckets;
            index = 0;
            if (t != null && size > 0) {
                while (index < t.length && (next = t[index]) == null) {
                    index++;
                }
            }
        }

        @Override
        public void remove() {
            if (current == null) {
                throw new IllegalStateException();
            }

            if (expectedStructureChanges != structureChanges) {
                throw new ConcurrentModificationException();
            }

            HashSetWithChaining.this.remove(current.value);
            current = null;
            expectedStructureChanges = structureChanges;
        }

        @Override
        public boolean hasNext() {
            return next != null;
        }

        @Override
        public E next() {
            Node<E>[] t;
            Node<E> e = next;
            if (expectedStructureChanges != structureChanges) {
                throw new ConcurrentModificationException();
            }
            if (e == null) {
                throw new NoSuchElementException();
            }

            E value = e.value;
            current = next;
            next = e.next;
            if (next == null && (t = buckets) != null) {
                ++index;
                while (index < t.length && (next = t[index]) == null) {
                    index++;
                }
            }
            return value;
        }
    }

    public static void main(String[] args) {
        Set<String> chains = new HashSetWithChaining<>(6);
        chains.add("Seth");
        chains.add("Bob");
        chains.add("Adam");
        chains.add("Ian");
        System.out.println(chains);

        chains.add("Seth");
        chains.add("Jill");
        chains.add("Bob");
        chains.add("Amy");
        chains.add("Nat");
        chains.add("Simon");
        chains.add("Andy");
        System.out.println(chains);

        System.out.println("Contains Simon and Andy:" + chains.containsAll(List.of("Andy", "Simon")));
        System.out.println("Contains Naveed and Andy:" + chains.containsAll(List.of("Andy", "Naveed")));
        System.out.println("Contains Naveed:" + chains.contains("Naveed"));

        Iterator<String> iterator = chains.iterator();
        String s = "";
        while (iterator.hasNext()) {
            s += iterator.next();
            s += ",";
        }
        System.out.println(s);
    }
}
