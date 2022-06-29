package assignment1.question1;

import java.util.ArrayList;
import java.util.List;

public class Element implements Runnable {

    private final List<Element> neighbours;
    private double currentTemp;
    private final double heatConstant;
    private volatile boolean stopRequested;

    public Element(double heatConstant) {
        this(0, heatConstant);
    }

    public Element(double currentTemp, double heatConstant) {

        if (heatConstant <= 0.0 || heatConstant > 1.0) {
            throw new IllegalArgumentException("heatConstant should be between 0.0 (exclusive) and 1.0");
        }

        this.neighbours = new ArrayList<>();
        this.currentTemp = currentTemp;
        this.heatConstant = heatConstant;
    }

    public void addNeighbour(Element e) {
        neighbours.add(e);
    }

    public void applyTempToElement(double appliedTemp) {
        synchronized (this) {
            currentTemp += (appliedTemp - currentTemp) * heatConstant;
        }
    }

    @Override
    public void run() {
        while (!stopRequested) {

            double averageTemps = neighbours.stream().map(Element::getCurrentTemp)
                    .reduce(0.0, Double::sum) / neighbours.size();

            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            synchronized (this) {
                currentTemp += (averageTemps - currentTemp) *heatConstant;
            }
        }
    }

    public double getCurrentTemp() {
       return currentTemp;
    }

    public void start() {
        new Thread(this).start();
    }

    public void stop() {
        stopRequested = true;
    }

    @Override
    public String toString() {
        return "Element{" +
                "currentTemp=" + currentTemp +
                ", heatConstant=" + heatConstant +
                '}';
    }
}
