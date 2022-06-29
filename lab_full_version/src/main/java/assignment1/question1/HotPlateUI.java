package assignment1.question1;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.*;

public class HotPlateUI extends JFrame implements ActionListener {

    private int appliedTemp = 1000;
    private double heatConstant = 0.1;

    private final HotCellPanel panel;

    public static void main(String[] args) {
        new HotPlateUI();
    }

    public HotPlateUI() {
        super("HOT PLATE UI, ASSIGNMENT 1 - EXERCISE 1");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        panel = new HotCellPanel(20, 20);
        add(panel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(2, 1));

        JPanel temperaturePanel = new JPanel();
        temperaturePanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Temperature Slider 0 - 1000"),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        JSlider temperatureSlider = new JSlider(JSlider.HORIZONTAL, 0, 1000, appliedTemp);

        temperatureSlider.addChangeListener(e -> {
            JSlider slider = (JSlider) e.getSource();
            if (!slider.getValueIsAdjusting()) {
                appliedTemp = slider.getValue();
            }
        });

        temperatureSlider.setPaintTicks(true);
        temperatureSlider.setMajorTickSpacing(50);
        temperatureSlider.setMinorTickSpacing(10);
        temperaturePanel.add(temperatureSlider);
        bottomPanel.add(temperaturePanel);

        JPanel heatConstantPanel = new JPanel();
        heatConstantPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("HeatConstant Slider 0.01 - 1.0"),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        JSlider heatSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, (int) (heatConstant * 100));

        heatSlider.addChangeListener(e -> {
            JSlider slider = (JSlider) e.getSource();
            if (!slider.getValueIsAdjusting()) {
                heatConstant = (double) slider.getValue() / 100;
            }
        });

        heatSlider.setPaintTicks(true);
        heatSlider.setMajorTickSpacing(5);
        heatSlider.setMinorTickSpacing(1);
        heatConstantPanel.add(heatSlider);
        bottomPanel.add(heatConstantPanel);

        add(bottomPanel, BorderLayout.SOUTH);
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);

        Timer clock = new Timer(0, this);
        clock.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        panel.repaint();
    }

    class HotCellPanel extends JPanel {

        private HotCell[][] hotCells;

        public HotCellPanel(int width, int height) {
            setLayout(new GridBagLayout());
            hotCells = new HotCell[width][height];

            GridBagConstraints gbc = new GridBagConstraints();
            for (int row = 0; row < width; row++) {
                for (int col = 0; col < height; col++) {
                    gbc.gridx = col;
                    gbc.gridy = row;

                    HotCell hotCell = new HotCell();
                    hotCells[row][col] = hotCell;

                    Border border;
                    border = new MatteBorder(1, 1, 0, 0, Color.GRAY);
                    hotCell.setBorder(border);
                    add(hotCell, gbc);
                }
            }
            updateNeighbors();
            startAllCells();
        }

        private void startAllCells() {
            for (HotCell[] hotCell : hotCells) {
                for (HotCell cell : hotCell) {
                    cell.element.start();
                }
            }
        }

        private void updateNeighbors() {
            for (int row = 0; row < hotCells.length; row++) {
                for (int col = 0; col < hotCells[row].length; col++) {
                    HotCell hotCell = hotCells[row][col];

                    // add neighbor next row
                    if (row + 1 < hotCells.length) {
                        hotCell.element.addNeighbour(hotCells[row + 1][col].element);
                        // add neighbor next col
                        if (col + 1 < hotCells[row].length) {
                            hotCell.element.addNeighbour(hotCells[row + 1][col + 1].element);
                        }
                        if (col - 1 >= 0) {
                            hotCell.element.addNeighbour(hotCells[row + 1][col - 1].element);
                        }
                    }

                    // add neighbor upper row
                    if (row - 1 >= 0) {
                        hotCell.element.addNeighbour(hotCells[row - 1][col].element);
                        if (col + 1 < hotCells[row].length) {
                            hotCell.element.addNeighbour(hotCells[row - 1][col + 1].element);
                        }
                        if (col - 1 >= 0) {
                            hotCell.element.addNeighbour(hotCells[row - 1][col - 1].element);
                        }
                    }
                }
            }
        }
    }

    public class HotCell extends JPanel {

        private final Element element;
        private Color backGround = Color.BLUE;

        public HotCell() {
            this.element = new Element(heatConstant);
            setBackground(backGround);

            addMouseMotionListener(new MouseMotionAdapter() {
                @Override
                public void mouseDragged(MouseEvent e) {
                    element.applyTempToElement(appliedTemp);
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            int red = (int) (255 * (element.getCurrentTemp() / 1000));
            int blue = 255 - red;

            backGround = new Color(red, 0, blue);
            setBackground(backGround);
            super.paintComponent(g);
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(20, 20);
        }
    }
}
