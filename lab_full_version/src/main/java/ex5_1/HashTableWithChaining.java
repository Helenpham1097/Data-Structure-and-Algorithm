package ex5_1;

import java.util.Arrays;

public class HashTableWithChaining {
    private Node[] bins;
    private int CAPACITY;
    private static final double LOAD_FACTOR = 0.75;
    private int threshold;
    private int binFills;

    static class Node {
        private Student studentValue;
        private Node next;

        public Node(Student studentValue) {
            this.studentValue = studentValue;
            this.next = null;
        }

        public Student getStudentValue() {
            return studentValue;
        }

        public void setStudentValue(Student studentValue) {
            this.studentValue = studentValue;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }
    }

    public HashTableWithChaining(int CAPACITY) {
        this.CAPACITY = CAPACITY;
        this.bins = new Node[CAPACITY];
        this.threshold = (int) (CAPACITY * LOAD_FACTOR);
        this.binFills = 0;
    }

    public int getIndexOfNode(Student student){
        return student.hashCode() % CAPACITY;
    }

    public boolean addNode(Student student){
        boolean added = false;
        int nodeIndex = getIndexOfNode(student);
        if(threshold == binFills){
            reArrangeElements();
        }
        for(int i = 0; i < CAPACITY; i++){
            if(i == nodeIndex){

                if(bins[i] == null){
                    bins[i] = new Node(student);

                }else{
                    Node currentNode = bins[i];
                    bins[i] =new Node(student);
                    bins[i].next = currentNode;

                }
                binFills ++;
                added = true;
            }
        }
        return added;
    }

    private void reArrangeElements() {
        System.out.println("Expand capacity and rearrange elements");

        CAPACITY = CAPACITY * 2;
        binFills = 0;
        threshold = (int) (CAPACITY * LOAD_FACTOR);

        Node[] temp = bins;
        bins = new Node[CAPACITY];
        for(Node node : temp){
            if(node != null){
                Node firstNode = node;
                while (firstNode != null){
                    addNode(firstNode.studentValue);
                    firstNode = firstNode.next;
                }
            }
        }
    }

    @Override
    public String toString() {
        String result = "";
        for(Node node : bins){
            result += "[ ";
            Node firstNode = node;
            while (firstNode != null){
                result +=  "[" + firstNode.getStudentValue().getName() + "]";
                firstNode = firstNode.next;
            }
            result += " ],";
        }
        return result +"]";
    }

    public static void main(String[]args){
        HashTableWithChaining hashTableWithChaining = new HashTableWithChaining(5);
        hashTableWithChaining.addNode(new Student("Helen"));
        hashTableWithChaining.addNode(new Student("Thay"));
        hashTableWithChaining.addNode(new Student("Sophia"));
        System.out.println("Before expand and re-arrange element");
        System.out.println(hashTableWithChaining);
        System.out.println("After expand and re-arrange element");
        hashTableWithChaining.addNode(new Student("Lisa"));
        System.out.println(hashTableWithChaining);
    }
}
