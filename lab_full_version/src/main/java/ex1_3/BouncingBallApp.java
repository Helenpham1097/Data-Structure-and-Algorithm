package ex1_3;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class BouncingBallApp implements Runnable, ActionListener {

    private final static String APP_TITLE = "Bouncing Ball";

    private final JFrame frame;
    private final BallPanel ballPanel;
    private final int fps = 60;
    private Timer clock;
    private int timeout = 1000 / fps;

    private static final List<Color> SUPPORT_COLORS = Arrays.asList(Color.PINK, Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW);

    private static final Random randomColor = new Random();
    private static final Random randomDelay = new Random();
    private static final Random randomMove = new Random();

    public BouncingBallApp() {

        frame = new JFrame();
        frame.setTitle(APP_TITLE);

        Container mainPanel = frame.getContentPane();
        mainPanel.setSize(720, 720);

        ballPanel = new BallPanel(720, 680);
        mainPanel.add(ballPanel, BorderLayout.CENTER);

        JPanel bottom = new JPanel();
        JButton addButton = new JButton("Add");
        JButton removeButton = new JButton("Remove");
        bottom.add(addButton);
        bottom.add(removeButton);

        addButton.addActionListener(e -> {
            Ball ball = new Ball(50,
                    Math.abs(randomMove.nextInt() % 30),
                    Math.abs(randomMove.nextInt() % 30),
                    SUPPORT_COLORS.get(Math.abs(randomColor.nextInt() % 5)),
                    Math.abs(randomDelay.nextInt() % 50) + 10);
            ballPanel.addBall(ball);
        });

        removeButton.addActionListener(e -> {
            ballPanel.removeFirstBall();
        });

        mainPanel.add(bottom, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new BouncingBallApp());
    }

    @Override
    public void run() {
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        clock = new Timer(timeout, this);
        clock.start();
    }

    public void actionPerformed(ActionEvent e) {
        ballPanel.repaint();
    }
}
