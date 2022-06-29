package ex1_3;

import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class BallPanel extends JPanel {

    private final List<Ball> balls;
    private final Executor executor = Executors.newFixedThreadPool(100);

    public BallPanel(int width, int height) {
        this.setPreferredSize(new Dimension(width, height));
        balls = new ArrayList<>();
    }

    public boolean addBall(Ball ball) {
        ball.setContainer(this);
        executor.execute(ball);
        return balls.add(ball);
    }

    public void removeFirstBall() {
        if(balls.size() == 0) {
            return;
        }
        removeBall(balls.get(0));
    }

    public boolean removeBall(Ball ball) {
        ball.setContainer(null);
        return balls.remove(ball);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (Ball ball : balls) {
            ball.draw(g);
        }
    }
}