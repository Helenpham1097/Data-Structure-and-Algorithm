package assignment1.question3;

import java.util.Arrays;
import java.util.Iterator;
import java.util.ListIterator;

public class DemoSelfOrganizeList {

    public static void main(String[] args) {
        System.out.println("DEMO ADD METHODS WITH THESE ELEMENTS: A, B, C, D, E");
        demoAdd();
        System.out.println();

        System.out.println("DEMO CONTAIN METHOD");
        demoContain();
        System.out.println();

        System.out.println("DEMO REMOVE METHOD WITH OBJECT");
        demoRemoveByObject();
        System.out.println();

        System.out.println("DEMO REMOVE WITH INDEX");
        demoRemoveByIndex();
        System.out.println();

        System.out.println("DEMO RETAIN METHOD  A, E");
        demoRetain();
        System.out.println();

        System.out.println("DEMO LIST ITERATOR WITH NEXT");
        demoListIteratorNext();
        System.out.println();

        System.out.println("DEMO LIST ITERATOR WITH PREVIOUS");
        demoListIteratorPrevious();
        System.out.println();

        System.out.println("DEMO ITERATOR");
        demoIterator();
        System.out.println();

        System.out.println("DEMO CONTAINS ALL ");
        demoContainAll();
        System.out.println();

        System.out.println("DEMO INDEX OF ");
        demoIndexOf();
        System.out.println();
    }

    private static void demoAdd() {
        SelfOrganizingArrayList<String> demoList = new SelfOrganizingArrayList<>();
        demoList.add("A");
        demoList.add("B");
        demoList.add("C");
        demoList.add("D");
        demoList.add("E");
        System.out.println(demoList);
    }

    private static void demoContain(){
        SelfOrganizingArrayList<String> demoList = new SelfOrganizingArrayList<>();
        demoList.add("A");
        demoList.add("B");
        demoList.add("C");
        demoList.add("D");
        demoList.add("E");
        System.out.println("The list before invoke contain method");
        System.out.println(demoList);
        System.out.println("Contain E");
        System.out.println(demoList.contains("E"));
        System.out.println(demoList);
        System.out.println("Contain A");
        System.out.println(demoList.contains("A"));
        System.out.println(demoList);
        System.out.println("Contain E");
        System.out.println(demoList.contains("E"));
        System.out.println(demoList);
        System.out.println("Contain G: NOT IN THE LIST");
        System.out.println(demoList.contains("G"));
    }

    private static void demoRemoveByObject(){
        SelfOrganizingArrayList<String> demoList = new SelfOrganizingArrayList<>();
        demoList.add("A");
        demoList.add("B");
        demoList.add("C");
        demoList.add("D");
        demoList.add("E");
        System.out.println("The list before invoking remove");
        System.out.println(demoList);
        System.out.println("Remove A");
        System.out.println(demoList.remove("A"));
        System.out.println(demoList);
        System.out.println("Remove F: NOT IN THE LIST");
        System.out.println(demoList.remove("F"));
        System.out.println("Contain D");
        System.out.println(demoList.contains("D"));
        System.out.println(demoList);
    }

    private static void demoRemoveByIndex(){
        SelfOrganizingArrayList<String> demoList = new SelfOrganizingArrayList<>();
        demoList.add("A");
        demoList.add("B");
        demoList.add("C");
        demoList.add("D");
        demoList.add("E");
        System.out.println("The list before removing index 3");
        System.out.println(demoList);
        System.out.println("Remove index 3");
        demoList.remove(3);
        System.out.println(demoList);
    }

    private static void demoRetain(){
        SelfOrganizingArrayList<String> demoList = new SelfOrganizingArrayList<>();
        demoList.add("A");
        demoList.add("B");
        demoList.add("C");
        demoList.add("D");
        demoList.add("E");
        System.out.println("The list before calling retain(A,E)");
        System.out.println(demoList);
        System.out.println("Calling contain E");
        System.out.println(demoList.contains("E"));
        System.out.println(demoList);
        System.out.println("Calling retain(A, E)");
        System.out.println(demoList.retainAll(Arrays.asList("A","E")));
        System.out.println(demoList);
    }
    private static void demoListIteratorNext(){
        SelfOrganizingArrayList<String> demoList = new SelfOrganizingArrayList<>();
        demoList.add("A");
        demoList.add("B");
        demoList.add("C");
        demoList.add("D");
        demoList.add("E");
        ListIterator<String> list = demoList.listIterator();
        while(list.hasNext()){
            System.out.println(list.next());
        }
    }

    private static void demoListIteratorPrevious(){
        SelfOrganizingArrayList<String> demoList = new SelfOrganizingArrayList<>();
        demoList.add("A");
        demoList.add("B");
        demoList.add("C");
        demoList.add("D");
        demoList.add("E");
        ListIterator<String> list = demoList.listIterator();
        while(list.hasPrevious()){
            System.out.println(list.previous());
        }
    }

    private static void demoIterator(){
        SelfOrganizingArrayList<String> demoList = new SelfOrganizingArrayList<>();
        demoList.add("A");
        demoList.add("B");
        demoList.add("C");
        demoList.add("D");
        demoList.add("E");
        Iterator<String> iterator = demoList.iterator();
        while(iterator.hasNext()){
            System.out.println(iterator.next());
        }
    }

    private static void demoContainAll(){
        SelfOrganizingArrayList<String> demoList = new SelfOrganizingArrayList<>();
        demoList.add("A");
        demoList.add("B");
        demoList.add("C");
        demoList.add("D");
        demoList.add("E");
        System.out.println("The list is");
        System.out.println(demoList);
        System.out.println("Calling containsAll(A, E, D)");
        System.out.println(demoList.containsAll(Arrays.asList("A", "E", "D")));
        System.out.println(demoList);
        System.out.println("Calling containsAll(B,F,A)");
        System.out.println(demoList.containsAll(Arrays.asList("B","F","A")));
        System.out.println(demoList);
    }

    private static void demoIndexOf(){
        SelfOrganizingArrayList<String> demoList = new SelfOrganizingArrayList<>();
        demoList.add("A");
        demoList.add("B");
        demoList.add("C");
        demoList.add("D");
        demoList.add("E");
        System.out.println("The list is");
        System.out.println(demoList);
        System.out.println("Calling containsAll(A, E, D, A)");
        System.out.println(demoList.containsAll(Arrays.asList("A", "E", "D","A")));
        System.out.println(demoList);
        System.out.println("Calling indexOf(E)");
        System.out.println("The position of element E is");
        System.out.println(demoList.indexOf("E"));
        System.out.println("List after calling indexOf element E");
        System.out.println(demoList);
    }
}
