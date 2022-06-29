package assignment1.question3;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

public class SelfOrganizingArrayList<E> implements List<E> {

    private final int CAPACITY = 5;
    int numElements;
    private SortInternal<E>[] sortInternals;

    public SelfOrganizingArrayList() {
        this.sortInternals = (SortInternal<E>[]) Array.newInstance(SortInternal.class, CAPACITY);
        this.numElements = 0;
    }

    public List<E> toElements() {
        return Arrays.stream(sortInternals)
                .filter(Objects::nonNull)
                .map(e -> e.element)
                .collect(Collectors.toList());
    }

    @Override
    public int size() {
        return sortInternals.length;
    }

    @Override
    public boolean isEmpty() {
        return sortInternals.length == 0;
    }

    @Override
    public Iterator<E> iterator() {
        return new ElementIterator();
    }

    @Override
    public Object[] toArray() {
        Object[] objects = new Object[numElements];
        copyIdenticalTo(objects);
        return objects;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        if (a.length < numElements) {
            T[] objects = (T[]) new Object[numElements];
            copyIdenticalTo(objects);
            return objects;
        }

        copyIdenticalTo(a);

        if (a.length > numElements) {
            a[numElements] = null;
        }

        return a;
    }

    private <T> void copyIdenticalTo(T[] dest) {
        if (dest.length < numElements) {
            throw new IllegalStateException();
        }

        for (int i = 0; i < numElements; i++) {
            dest[i] = (T) sortInternals[i].element;
        }
    }

    @Override
    public boolean add(E e) {
        if (numElements >= sortInternals.length) {
            expandSize();
        }
        for (int i = 0; i < numElements; i++) {
            if (sortInternals[i].element.equals(e)) {
                return false;
            }
        }
        SortInternal<E> internal = new SortInternal<>(e, 1, 0);
        sortInternals[numElements] = internal;
        numElements++;
        return true;
    }

    public void expandSize() {
        if (numElements >= sortInternals.length) {
            SortInternal<E>[] newSortInternals = (SortInternal<E>[]) Array.newInstance(SortInternal.class, numElements * 2);
            for (int i = 0; i < sortInternals.length; i++) {
                newSortInternals[i] = sortInternals[i];
            }
            sortInternals = newSortInternals;
        }
    }

    @Override
    public boolean contains(Object o) {
        SortInternal<E> foundElement = null;
        for (int i = 0; i < numElements; i++) {
            if (sortInternals[i].element.equals(o)) {
                foundElement = sortInternals[i];
                break;
            }
        }
        if (foundElement != null) {
            foundElement.count++;
            foundElement.weight = 1;
            bubbleSort();
            return true;
        }
        return false;
    }

    public void bubbleSort() {
        for (int i = 0; i < numElements - 1; i++) {
            for (int j = 0; j < numElements - i - 1; j++) {
                if (sortInternals[j].compareTo(sortInternals[j + 1]) < 0) {
                    SortInternal<E> temp = sortInternals[j];
                    sortInternals[j] = sortInternals[j + 1];
                    sortInternals[j + 1] = temp;
                }
            }
        }
        for (int i = 0; i < numElements; i++) {
            sortInternals[i].weight = 0;
        }
    }

    public String toString() {
        return Arrays.toString(sortInternals);
    }

    @Override
    public boolean remove(Object o) {
        boolean found = false;
        int index = 0;
        for (int i = 0; i < numElements; i++) {
            if (sortInternals[i].element.equals(o)) {
                sortInternals[i] = null;
                index = i;
                found = true;
                numElements--;
                break;
            }
        }
        if (found == true) {
            for (int i = index; i < sortInternals.length - 1; i++) {
                sortInternals[i] = sortInternals[i + 1];
                sortInternals[i + 1] = null;
            }
        }
        return found;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        int objectChecked = c.size();
        for (Object o : c) {
            if (!contains(o)) {
                System.out.println("Object " + o + " is not in the list");
                objectChecked--;

            }
        }
        return objectChecked == c.size();
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        for (E e : c) {
            add(e);
        }
        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        throw new UnsupportedOperationException("Self - organizing list does not support to modify element at a specific position");
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        for (Object o : c) {
            for (int i = 0; i < numElements; i++) {
                if (!sortInternals[i].element.equals(o)) {
                    return false;
                }
            }
        }

        for (Object o : c) {
            remove(o);
        }
        return true;
    }

    private <T> void removeAll(T []elements) {
        for (T e:elements) {
            remove(e);
        }
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        int id = 0;
        Object[] removedElements = new Object[numElements];
        for(int i = 0; i< numElements; i++){
            if (!c.contains(sortInternals[i].element)){
                removedElements[id]= sortInternals[i].element;
                id++;
            }
        }
        removeAll(removedElements);
        return removedElements.length != numElements;
    }

    @Override
    public void clear() {
        for (int i = 0; i < numElements; i++) {
            sortInternals[i] = null;
        }
    }

    @Override
    public E get(int index) {
        E foundElement = null;
        if (index < sortInternals.length) {
            for (int i = 0; i < sortInternals.length; i++) {
                if (i == index) {
                    foundElement = sortInternals[i].element;
                    sortInternals[i].count++;
                    sortInternals[i].weight = 1;
                    break;
                }
            }
            bubbleSort();
        } else {
            throw new IndexOutOfBoundsException();
        }
        return foundElement;
    }

    @Override
    public E set(int index, E element) {
        throw new UnsupportedOperationException("Self - organizing list does not support to modify element at a specific position");
    }

    @Override
    public void add(int index, E element) {
        throw new UnsupportedOperationException("Self - organizing list does not support to modify element at a specific position");
    }

    @Override
    public E remove(int index) {
        if (index >= numElements) {
            throw new IllegalArgumentException("Index must be less than number of elements");
        }
        E found;
        found = sortInternals[index].element;
        numElements--;

        if (found != null) {
            for (int i = index; i < sortInternals.length - 1; i++) {
                sortInternals[i] = sortInternals[i + 1];
                sortInternals[i + 1] = null;
            }
        }

        return found;
    }

    @Override
    public int indexOf(Object o) {
        try {
            for (int i = 0; i < numElements; i++) {
                if (sortInternals[i].element.equals(o)) {
                    sortInternals[i].count++;
                    sortInternals[i].weight =1;
                    bubbleSort();
                    return i;
                }
            }
        } catch (IllegalArgumentException exception) {
            System.out.println("Object are not found in the list");
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        try {
            for (int i = 0; i < numElements; i++) {
                if (sortInternals[i].element.equals(o)) {
                    sortInternals[i].count++;
                    sortInternals[i].weight =1;
                    bubbleSort();
                    return i;
                }
            }
        } catch (IllegalArgumentException exception) {
            System.out.println("Object are not found in the list");
        }
        return -1;
    }

    @Override
    public ListIterator<E> listIterator() {
        return new ListIteratorImpl();
    }

    @Override
    public ListIterator<E> listIterator(int index) {

        if (index < 0 || index >= numElements) {
            throw new IllegalArgumentException("The index value must be less than the number of elements ");
        }
        return new ListIteratorImpl(index);
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        if (fromIndex >= toIndex) {
            throw new IllegalArgumentException("From index must be less than to index");
        }

        if (fromIndex < 0 || fromIndex >= numElements) {
            throw new IllegalArgumentException("From index must be larger than 0 or less then num of elements");
        }

        if (toIndex >= numElements) {
            throw new IllegalArgumentException("To index must be less then num of elements");
        }

        SelfOrganizingArrayList<E> subList = new SelfOrganizingArrayList<>();
        for (int i = fromIndex; i < toIndex; i++) {
            E subElement = this.sortInternals[i].element;
            int count = this.sortInternals[i].count;
            subList.addToSubList(subElement, count);
        }
        return subList;
    }

    public void addToSubList(E e, int count) {
        SortInternal<E> internal = new SortInternal<>(e, count, 0);
        sortInternals[numElements] = internal;
        numElements++;
    }

    static class SortInternal<T> implements Comparable<SortInternal<T>> {
        private final T element;
        private int count;
        private int weight;

        public SortInternal(T element, int count, int weight) {
            this.element = element;
            this.count = count;
            this.weight = weight;
        }

        @Override
        public int compareTo(SortInternal<T> o) {
            if (count > o.count) {
                return 1;
            } else if (count == o.count) {
                if (weight == o.weight) {
                    return 0;
                }
                return weight > o.weight ? 1 : -1;
            }
            return -1;
        }

        @Override
        public String toString() {
            return element + "(" + count + ")";
        }
    }

    class ListIteratorImpl implements ListIterator<E> {

        private int cursor;
        private int previousCursor;
        private int lastReturn = -1;

        public ListIteratorImpl(int index) {
            cursor = index;
        }

        public ListIteratorImpl() {
            cursor = 0;
            previousCursor = numElements-1;
        }

        @Override
        public boolean hasNext() {
            return cursor < numElements;
        }

        @Override
        public E next() {
            int i = cursor;
            if (i >= numElements)
                throw new NoSuchElementException();

            SortInternal[] elements = SelfOrganizingArrayList.this.sortInternals;
            if (i >= elements.length)
                throw new ConcurrentModificationException();

            lastReturn = i;
            cursor++;
            return (E) elements[lastReturn].element;
        }

        @Override
        public boolean hasPrevious() {
            return (previousCursor< numElements && previousCursor>= 0);
        }

        @Override
        public E previous() {
            int i = previousCursor;
            if (i < 0)
                throw new NoSuchElementException("Cursor is at the head already");
            SortInternal[] elements = SelfOrganizingArrayList.this.sortInternals;
            if (i >= elements.length)
                throw new ConcurrentModificationException();
            previousCursor--;
            return (E) elements[i].element;
        }

        @Override
        public int previousIndex() {
            return cursor - 1;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void set(E e) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void add(E e) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int nextIndex() {
            return cursor;
        }
    }

    class ElementIterator implements Iterator<E> {
        private int cursor;

        public ElementIterator() {
            cursor = 0;
        }

        @Override
        public boolean hasNext() {
            return cursor < numElements;
        }

        @Override
        public E next() {
            if (this.hasNext()) {
                int i = cursor;
                cursor = i + 1;
                return sortInternals[i].element;
            } else {
                return null;
            }
        }
    }
}
