package ex1_3;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;

public class Ball implements Runnable {

    private int x;
    private int y;
    private final int radius;

    private double speedX;
    private double speedY;

    private final int delay;

    private final Color color;

    private Container container;

    public Ball(int x, int y, int radius, double speedX, double speedY, Color color, int delay) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.speedX = speedX;
        this.speedY = speedY;
        this.color = color;
        this.delay = delay;
    }

    public Ball(int radius, double speedX, double speedY, Color color) {
        this(radius, speedX, speedY, color, 10);
    }

    public Ball(int radius, double speedX, double speedY, Color color, int delay) {
        this(0, 0 , radius, speedX, speedY, color, delay);
    }

    public void draw(Graphics g) {
        if (g.getColor() != color) {
            g.setColor(color);
        }

        g.fillOval(x, y, radius, radius);
    }

    @Override
    public void run() {

        while(container != null) {
            System.out.printf("Ball with radius %s with color %s is updated at thread: %s%n",
                    radius,
                    color.toString(),
                    Thread.currentThread().getName());

            x += speedX;
            y += speedY;

            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            double directionX = inBoundsX() ? 1 : -1;
            double directionY = inBoundsY() ? 1 : -1;

            speedX = speedX * directionX;
            speedY = speedY * directionY;
        }
    }

    public void setContainer(Container container) {
        this.container = container;
    }

    private boolean inBoundsX() {
        if(container == null) {
            return false;
        }
        return x > 0 && (x + radius < container.getWidth());
    }

    private boolean inBoundsY() {
        if(container == null) {
            return false;
        }
        return y > 0 && (y + radius < container.getHeight());
    }
}
