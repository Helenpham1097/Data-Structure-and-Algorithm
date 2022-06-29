package datastructure.assignment2.exercise1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class FileSorter implements Runnable {

    private final Queue<Path> tempFiles = new LinkedList<>();

    private final File inputFile;
    private final File outputFile;

    private final int limit;

    private int splitProgress = 0;
    private int splitCount = 0;
    private int mergeProgress = 0;

    private long lineCount;

    private volatile boolean isDone = false;

    public FileSorter(File inputFile, File outputFile, int limit) {
        this.inputFile = inputFile;
        this.outputFile = outputFile;
        this.limit = limit;

        try {
            lineCount = Files.lines(inputFile.toPath()).count();
            System.out.println("Total Lines to process... " + lineCount);
        } catch (IOException ex) {
            System.out.println("Error in input file " + ex);
        }
    }

    public File getInputFile() {
        return inputFile;
    }

    public File getOutputFile() {
        return outputFile;
    }

    public void sort() throws IOException {

        if (!inputFile.exists()) {
            isDone = true;
            throw new IllegalArgumentException("File input does not exist");
        }

        if (!inputFile.isFile() || !outputFile.isFile()) {
            isDone = true;
            throw new IllegalArgumentException("Input and output must be files");
        }

        Path tempDir = split();
        Path mergeDir = merge();

        Files.delete(tempDir);
        Files.delete(mergeDir);

        isDone = true;
    }

    private Path split() throws IOException {

        Path path = Paths.get(inputFile.getAbsolutePath());
        Path parent = Paths.get(inputFile.getAbsolutePath()).getParent();
        Path tempDir = Files.createDirectory(Paths.get(String.format("%s/temp", parent.toString())));

        try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            String line = reader.readLine();
            while (line != null) {
                List<String> lines = new ArrayList<>();
                int index = 0;
                while (index < limit && line != null) {
                    lines.add(line);
                    index++;
                    line = reader.readLine();
                }
                sortAndStoreTempFile(lines.toArray(new String[0]), tempDir);
                splitCount++;
                splitProgress = splitCount * limit * 100 / (int)lineCount;
            }
        }

        splitProgress = 100;
        return tempDir;
    }

    private void sortAndStoreTempFile(String[] lines, Path tempDir) throws IOException {
        QuickSort.sort(lines, 0, lines.length - 1);
        Path tempFile = Paths.get(tempDir.toString(), "" + (tempFiles.size() + 1));
        Files.createFile(tempFile);

        try (BufferedWriter writer = Files.newBufferedWriter(tempFile, StandardCharsets.UTF_8)) {
            for (String line : lines) {
                writer.write(line);
                writer.write("\n");
            }
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }

        tempFiles.add(tempFile);
    }

    private Path merge() throws IOException {

        Path parent = inputFile.toPath().getParent();
        Path mergeDir = Files.createDirectory(Paths.get(String.format("%s/merge", parent.toString())));

        int maxTempFiles = tempFiles.size() * 2;
        int trackTempFiles = 0;

        int index = 0;
        while (!tempFiles.isEmpty()) {
            Path file1 = tempFiles.poll();
            if (tempFiles.isEmpty()) {
                merge(file1, outputFile.toPath());
                Files.delete(file1);
                mergeProgress = 100;
                return mergeDir;
            }

            Path file2 = tempFiles.poll();

            if (tempFiles.isEmpty()) {
                merge(file1, file2, outputFile.toPath());
                mergeProgress = 100;
            } else {
                Path mergeFile = merge(file1, file2, mergeDir, String.valueOf(index++));
                tempFiles.add(mergeFile);
                trackTempFiles++;
                mergeProgress = 2 * trackTempFiles * 100 / maxTempFiles;
            }

            Files.delete(file1);
            Files.delete(file2);
        }

        return mergeDir;
    }

    private Path merge(Path file1, Path file2, Path dir, String fileName) throws IOException {
        Path file = Files.createFile(Paths.get(String.format("%s/%s", dir.toString(), fileName)));
        merge(file1, file2, file);

        return file;
    }

    private void merge(Path file, Path targetFile) throws IOException {
        try (BufferedReader reader = Files.newBufferedReader(file, StandardCharsets.UTF_8);
             BufferedWriter writer = Files.newBufferedWriter(targetFile, StandardCharsets.UTF_8)) {

            String line = reader.readLine();
            while (line != null) {
                writer.write(line);
                writer.write("\n");
                line = reader.readLine();
            }

        } catch (IOException e) {
            throw e;
        }
    }

    private void merge(Path file1, Path file2, Path targetFile) throws IOException {

        try (BufferedReader reader1 = Files.newBufferedReader(file1, StandardCharsets.UTF_8);
             BufferedReader reader2 = Files.newBufferedReader(file2, StandardCharsets.UTF_8);
             BufferedWriter writer = Files.newBufferedWriter(targetFile, StandardCharsets.UTF_8)) {

            String line1 = reader1.readLine();
            String line2 = reader2.readLine();

            while (line1 != null && line2 != null) {
                if (line1.compareTo(line2) <= 0) {
                    writer.write(line1);
                    writer.write("\n");
                    line1 = reader1.readLine();
                } else {
                    writer.write(line2);
                    writer.write("\n");
                    line2 = reader2.readLine();
                }
            }

            while (line1 != null) {
                writer.write(line1);
                writer.write("\n");
                line1 = reader1.readLine();
            }

            while (line2 != null) {
                writer.write(line2);
                writer.write("\n");
                line2 = reader2.readLine();
            }

        } catch (IOException e) {
            throw e;
        }
    }

    public int getSplitProgress() {
        return splitProgress;
    }

    public int getMergeProgress() {
        return mergeProgress;
    }

    public boolean isDone() {
        return isDone;
    }

    @Override
    public void run() {
        try {
            sort();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
