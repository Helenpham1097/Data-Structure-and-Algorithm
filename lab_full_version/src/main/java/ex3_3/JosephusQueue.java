package ex3_3;


import java.util.*;

public class JosephusQueue {

    public void solveProblem(int remove, int soldiers) {
        LinkedList<Integer> josephusQueue = new LinkedList<>();
        System.out.println("Adding these soldiers: ");
        for (int i = 1; i <= soldiers; i++) {
            josephusQueue.add(i);
        }
        System.out.println(Arrays.toString(josephusQueue.toArray()));
        while (josephusQueue.size() != 0) {
            for (int i = 1; i <= remove; i++) {
                int element = josephusQueue.get(0);
                if (i == remove) {
                    josephusQueue.removeFirst();
                    if(josephusQueue.size() == 0) {
                        System.out.println("The last soldier standing is "+ element);
                    }else{
                        System.out.println("Remove soldier "+element);
                    }
                } else {
                    josephusQueue.removeFirst();
                    josephusQueue.add(element);
                }
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the number of soldiers in the Josephus problem");
        int soldiers = scanner.nextInt();
        System.out.println("Enter the number to remove soldier");
        int remove = scanner.nextInt();
        JosephusQueue josephusQueue = new JosephusQueue();
        long begin = System.nanoTime();
        josephusQueue.solveProblem(remove, soldiers);
        System.out.println("TIME TO REMOVE ALL SOLDIERS USING JOSEPHUS QUEUE");
        System.out.println(System.nanoTime()-begin);


        begin = System.nanoTime();
        List<Integer> list = new ArrayList<>();
        for(int i = 1; i<=soldiers; i++){
            list.add(i);
        }
        list.clear();
        System.out.println("TIME TO REMOVE ALL SOLDIERS USING ARRAY LIST ");
        System.out.println(System.nanoTime()-begin);
    }
}
