package datastructure.assignment2.exercise1;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.io.File;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class FileSorterUI extends JFrame {

    private final JLabel outputLabel = new JLabel("N/A");
    private final JLabel inputLabel = new JLabel("N/A");
    private final JLabel numberOfItemsDisplay = new JLabel("Number of items in the queue: 0", SwingConstants.LEFT);
    private final JProgressBar mergeProgressBar = new JProgressBar(SwingConstants.HORIZONTAL, 0, 100);
    private final JProgressBar splitProgressBar = new JProgressBar(SwingConstants.HORIZONTAL, 0, 100);
    private final JButton processTaskButton = new JButton("Process Task");
    private final JButton enQueueTaskButton = new JButton("Enqueue Task");

    private final int limit;
    private final Queue<FileSorter> tasks = new LinkedList<>();
    private FileSorter currentTask;

    private final Set<File> filesToBeProceed = new HashSet<>();

    public FileSorterUI(String title, int limit) {
        super(title);
        this.limit = limit;

        init();
        registerButtonActionListeners();
    }

    private void init() {
        JPanel northPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        northPanel.add(numberOfItemsDisplay);

        JPanel southPanel = new JPanel();
        southPanel.setLayout(new GridLayout(7, 1));

        JPanel maxStringPanel = new JPanel();
        maxStringPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Max Strings in memory:"),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));

        maxStringPanel.add(new JLabel(String.valueOf(limit)));
        maxStringPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        southPanel.add(maxStringPanel);

        JPanel inputPanel = new JPanel();
        inputPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Input File:"),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));

        inputPanel.add(inputLabel);
        inputPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        southPanel.add(inputPanel);

        JPanel outputPanel = new JPanel();
        outputPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Output File:"),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));

        outputPanel.add(outputLabel);
        outputPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        southPanel.add(outputPanel);

        JPanel mergeProgressPanel = new JPanel();
        mergeProgressPanel.add(new JLabel("Merge Progress:", SwingConstants.LEFT));

        mergeProgressBar.setPreferredSize(new Dimension(800, 30));
        mergeProgressBar.setStringPainted(true);
        mergeProgressPanel.add(mergeProgressBar);
        mergeProgressPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        southPanel.add(mergeProgressPanel);

        JPanel splitProgressPanel = new JPanel();
        splitProgressPanel.add(new JLabel("Split Progress:", SwingConstants.LEFT));

        splitProgressBar.setPreferredSize(new Dimension(800, 30));
        splitProgressBar.setStringPainted(true);
        splitProgressPanel.add(splitProgressBar);
        splitProgressPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        southPanel.add(splitProgressPanel);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(processTaskButton);
        processTaskButton.setEnabled(false);
        buttonPanel.add(enQueueTaskButton);
        southPanel.add(buttonPanel);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(northPanel);
        panel.add(southPanel);

        getContentPane().add(panel);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 400);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    private File selectInputFile() {
        JFileChooser jfc = new JFileChooser(".");
        jfc.setDialogTitle("Please choose the input file");
        jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int returnValue = jfc.showOpenDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jfc.getSelectedFile();
            if (filesToBeProceed.contains(selectedFile)) {
                JOptionPane.showMessageDialog(null,
                        String.format("File %s is in the queue already", selectedFile.getAbsolutePath()),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);

                return null;
            }

            return selectedFile;
        }

        return null;
    }

    private File selectOutputFile() {
        JFileChooser jfc = new JFileChooser(".");
        jfc.setDialogTitle("Please choose the output file");
        jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int returnValue = jfc.showOpenDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            return jfc.getSelectedFile();
        }

        return null;
    }

    private void registerButtonActionListeners() {
        enQueueTaskButton.addActionListener(e -> {
            File inputFile = selectInputFile();
            if (inputFile == null) {
                return;
            }

            File outputFile = selectOutputFile();

            if (outputFile == null) {
                JOptionPane.showMessageDialog(null,
                        "Output file is required!!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (inputFile.equals(outputFile)) {
                JOptionPane.showMessageDialog(null,
                        "Input and output files must be different from each other!!!!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            mergeProgressBar.setValue(0);
            splitProgressBar.setValue(0);

            filesToBeProceed.add(inputFile);
            tasks.add(new FileSorter(inputFile, outputFile, limit));
            updateItems();
            processTaskButton.setEnabled(true);

        });

        processTaskButton.addActionListener(e -> {
            if (tasks.isEmpty()) {
                return;
            }

            if (currentTask != null && !currentTask.isDone()) {
                JOptionPane.showMessageDialog(null,
                        "Current task is not done yet!!!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }

            currentTask = tasks.poll();
            updateItems();
            if (tasks.isEmpty()) {
                processTaskButton.setEnabled(false);
            }

            if (currentTask != null) {
                filesToBeProceed.remove(currentTask.getInputFile());
                inputLabel.setText(currentTask.getInputFile().getAbsolutePath());
                outputLabel.setText(currentTask.getOutputFile().getAbsolutePath());

                new Thread(currentTask).start();
                new Thread(new ProgressRunnable()).start();
            }
        });
    }

    class ProgressRunnable implements Runnable {
        @Override
        public void run() {
            if (currentTask != null) {
                while (!currentTask.isDone()) {
                    mergeProgressBar.setValue(currentTask.getMergeProgress());
                    splitProgressBar.setValue(currentTask.getSplitProgress());
                    processTaskButton.setEnabled(false);
                }

                if (!tasks.isEmpty()) {
                    processTaskButton.setEnabled(true);
                }
            }
        }
    }

    private void updateItems() {
        numberOfItemsDisplay.setText(String.format("Number of items in the queue: %s", tasks.size()));
    }

    public static void main(String[] args) {
        new FileSorterUI("FileSorter", 10);
    }
}
