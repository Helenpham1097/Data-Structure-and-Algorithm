package ex4_4;

public class TaskPriorityQueue {

    static class Task<E> {
        private E element;
        private int priority;

        public Task(E element, int priority) {
            this.element = element;
            this.priority = priority;
        }

        public E getElement() {
            return element;
        }

        public int getPriority() {
            return priority;
        }
    }

    private Task[] queue;
    private int numberOfElements;
    private static int CAPACITY = 5;

    public TaskPriorityQueue() {
        this.queue = new Task[CAPACITY];
        this.numberOfElements = 0;
    }

    public int getParentPosition(int index) {
        return (index - 1) / 2;
    }

    public int getLeftChildPosition(int index) {
        return (2 * index + 1);
    }

    public int getRightChildPosition(int index) {
        return (2 * index + 2);
    }

    private void swap(int parent, int child) {
        Task temp = queue[child];
        queue[child] = queue[parent];
        queue[parent] = temp;
    }

    private void expandCapacity() {
        int newCapacity = CAPACITY * 2;
        Task[] newArray = new Task[newCapacity];

        for (int i = 0; i < numberOfElements; i++) {
            newArray[i] = queue[i];
        }
        queue = newArray;
    }

    public void enqueue(Task task) {
        if (numberOfElements == 0) {
            queue[numberOfElements] = task;
            numberOfElements++;
            return;
        }

        if (numberOfElements >= CAPACITY) {
            expandCapacity();
        }

        queue[numberOfElements] = task;
        int childPosition = numberOfElements;
        numberOfElements++;

        int parentPosition = getParentPosition(childPosition);
        while (parentPosition >= 0 && queue[childPosition].priority < queue[parentPosition].priority) {
            swap(parentPosition, childPosition);
            childPosition = parentPosition;
            parentPosition = getParentPosition(childPosition);
        }
    }

    public Task findMin() {
        return queue[0];
    }

    public void dequeueMin() {
        int furthestLeafPosition = numberOfElements - 1;
        Task furthestRightLeaf = queue[furthestLeafPosition];
        queue[furthestLeafPosition] = null;
        numberOfElements--;
        queue[0] = furthestRightLeaf;
        int position = 0;

        while (position <= getParentPosition(numberOfElements - 1)) {
            Task current = queue[position];
            int leftChildPosition = getLeftChildPosition(position);
            int rightChildPosition = getRightChildPosition(position);
            Task leftChild = queue[leftChildPosition];
            Task rightChild = queue[rightChildPosition];

            if (leftChild != null && rightChild == null) {
                if (current.priority > leftChild.priority) {
                    swap(position, leftChildPosition);
                    position = leftChildPosition;
                }
            }

            if (leftChild != null && rightChild != null) {
                if (leftChild.priority > rightChild.priority && current.priority > leftChild.priority) {
                    swap(position, rightChildPosition);
                    position = rightChildPosition;
                }
                if (rightChild.priority > leftChild.priority && current.priority > rightChild.priority) {
                    swap(position, leftChildPosition);
                    position = leftChildPosition;
                }
            }
        }
    }

    public int getSize() {
        return numberOfElements;
    }

    public String toString() {
        String result = "";
        for (int i = 0; i < numberOfElements; i++) {
            result += "(" + queue[i].element + ", " + queue[i].priority + ") ";
        }
        return result;
    }

    public static void main(String[] args) {
        TaskPriorityQueue queue = new TaskPriorityQueue();
        queue.enqueue(new Task("Task 1", 4));
        queue.enqueue(new Task("Task 2", 5));
        queue.enqueue(new Task("Task 3", 2));
        queue.enqueue(new Task("Task 4", 3));
        queue.enqueue(new Task("Task 5", 7));
        queue.enqueue(new Task("Task 6", 1));
        queue.enqueue(new Task("Task 7", 10));

        System.out.println("The queue after adding new tasks");
        System.out.println(queue);
        System.out.println();
        System.out.println("Get the Min");
        Task min = queue.findMin();
        System.out.println("(" + min.element + ", " + min.priority + ") ");
        System.out.println();
        System.out.println("The queue after calling dequeueMin and modifying the heap");
        queue.dequeueMin();
        System.out.println(queue);
    }
}
