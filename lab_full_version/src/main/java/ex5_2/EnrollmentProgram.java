package ex5_2;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class EnrollmentProgram {
    private Node[] tables;
    private int CAPACITY;
    private int arrayFills;
    private static final double LOAD_FACTOR = 0.75;
    private int threshold;
    private Node firstNode;
    private Node lastNode;

    static class Node {
        private StudentInformation studentInformation;
        private Node[] nextNodes;

        public Node(StudentInformation studentInformation) {
            this.studentInformation = studentInformation;
            this.nextNodes = new Node[2];
        }

        public StudentInformation getStudentInformation() {
            return studentInformation;
        }

        public void setStudentInformation(StudentInformation studentInformation) {
            this.studentInformation = studentInformation;
        }

        public Node[] getNextNodes() {
            return nextNodes;
        }

        public void setNextNodes(Node[] nextNodes) {
            this.nextNodes = nextNodes;
        }
    }

    public EnrollmentProgram(int CAPACITY) {
        this.CAPACITY = CAPACITY;
        this.tables = new Node[CAPACITY];
        arrayFills = 0;
        threshold = (int) (CAPACITY * LOAD_FACTOR);
        this.firstNode = null;
        this.lastNode = null;
    }

    public boolean enrollNewStudent(StudentInformation studentInformation) {
        int index = getPositionToAdd(studentInformation.getId());
        Node newNode = new Node(studentInformation);
        if(threshold == arrayFills){
            rearrangeTables();
        }
        for (int i = 0; i < tables.length; i++) {
            if (i == index) {
                if (firstNode == null) {
                    tables[i] = newNode;
                    firstNode = newNode;
                    lastNode = firstNode;
                } else {
                    if (tables[i] == null) {
                        tables[i] = newNode;

                    } else {
                        Node pointer = tables[i];
                        if (pointer.nextNodes[0] == null && pointer.nextNodes[1] == null) {
                            pointer.nextNodes[0] = pointer.nextNodes[1] = newNode;
                        } else {
                            Node prev = pointer;
                            while (pointer != null) {
                                prev = pointer;
                                pointer = pointer.nextNodes[1];
                            }
                            prev.nextNodes[1] = newNode;
                        }
                    }
                    lastNode.nextNodes[0] = newNode;
                    lastNode = newNode;
                }
                arrayFills++;
                return true;
            }
        }
        return false;
    }

    private void rearrangeTables() {
        System.out.println("Expand capacity and rearrange elements");

        CAPACITY = CAPACITY * 2;
        arrayFills = 0;
        threshold = (int) (CAPACITY * LOAD_FACTOR);

        Node[] temp = tables;
        tables = new Node[CAPACITY];
        firstNode = null;

        for(Node node:temp){
            if(node != null){
                Node pointer = node;
                while (pointer != null){
                    enrollNewStudent(pointer.getStudentInformation());
                    pointer = pointer.nextNodes[1];
                }
            }
        }
    }

    public StudentInformation getStudentInformationByID(String studentID){
        int index = getPositionToAdd(studentID);
        Node nodes = tables[index];

        while (nodes != null){
            if(nodes.getStudentInformation().getId() == studentID){
                return nodes.getStudentInformation();
            }else {
                nodes = nodes.nextNodes[1];
            }
        }

        return null;
    }

    private int getPositionToAdd(String studentID) {
        return getHashCode(studentID) % CAPACITY;
    }

    private int getHashCode(String studentID) {
        int hash = 0;
        for (int i = 0; i < studentID.length(); i++) {
            int charValue = studentID.charAt(i);
            hash += charValue;
        }
        return hash;
    }

    public String toString() {
        String result = "[";
        for (int i = 0; i < tables.length; i++) {
            Node nodeAtIndex = tables[i];
            Node pointer = nodeAtIndex;
            result += " [";
            while (pointer != null) {
                result += "[" + pointer.studentInformation.toString() + "]";
                pointer = pointer.nextNodes[1];
            }
            result += "] ";
        }
        result += "]";
        return result;
    }

    static  void printOrder(Node head) {
        if(head != null) {
            while(head != null) {
                System.out.println(head.studentInformation);
                head = head.nextNodes[0];
            }
        }
    }
    public static void main(String[] args) throws ParseException {
        SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy");
        EnrollmentProgram enrollmentProgram = new EnrollmentProgram(5);

        System.out.println("Add three students first");
        StudentInformation studentHelen = new StudentInformation("Helen", "Auckland", date.parse("10/10/1997"));
        System.out.println(enrollmentProgram.enrollNewStudent(studentHelen));
        StudentInformation student2 = new StudentInformation("Thay", "Auckland", date.parse("03/08/1984"));
        System.out.println(enrollmentProgram.enrollNewStudent(student2));
        StudentInformation student3 = new StudentInformation("Sophia", "Christchurch", date.parse("03/08/2025"));
        System.out.println(enrollmentProgram.enrollNewStudent(student3));

        System.out.println("Print all the students in array");
        System.out.println(enrollmentProgram);

        System.out.println("Listing all students in the order in which they enrolled");
        printOrder(enrollmentProgram.firstNode);
        System.out.println();

        System.out.println("Get student 1 ID");
        System.out.println(studentHelen.getId());
        System.out.println("Get student information by Id");
        System.out.println(enrollmentProgram.getStudentInformationByID(studentHelen.getId()));
        System.out.println();

        System.out.println("Add new student and get rearrange elements");
        System.out.println(enrollmentProgram.enrollNewStudent(new StudentInformation("Rum", "Queenstown", date.parse("12/06/2012"))));
        System.out.println(enrollmentProgram.enrollNewStudent(new StudentInformation("Linh", "Wellington", date.parse("05/10/2003"))));
        System.out.println(enrollmentProgram);
        System.out.println();
        printOrder(enrollmentProgram.firstNode);
    }
}
