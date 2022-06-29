package assignment1.question1;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TwoConcurrentElements {
    public static void main(String[] args) {
        Element e1 = new Element(100, 0.000001);
        Element e2 = new Element(0, 0.0000001);

        e1.addNeighbour(e2);
        e2.addNeighbour(e1);

        e1.start();
        e2.start();

        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(10);

        executorService.scheduleAtFixedRate(() -> {
            System.out.println(e1);
            System.out.println(e2);
            System.out.println(".....");
        }, 0, 20, TimeUnit.MILLISECONDS );

    }
}
