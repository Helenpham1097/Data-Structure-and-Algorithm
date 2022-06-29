package ex3_5;


import java.util.Random;

public class ArraySorter<E extends Comparable> {

    public static int COMPARE_COUNTER = 0;
    public static int SWAP_COUNTER = 0;

    public void selectionSort(E[] list) {
        COMPARE_COUNTER = 0;
        SWAP_COUNTER = 0;
        int indexMin;
        E temp;

        for (int i = 0; i < list.length - 1; i++) {
            indexMin = i;

            for (int j = i + 1; j < list.length; j++) {
                if (list[j].compareTo(list[indexMin]) < 0)
                    indexMin = j;
                COMPARE_COUNTER++;
            }

            temp = list[indexMin];
            list[indexMin] = list[i];
            list[i] = temp;
            SWAP_COUNTER++;
        }
    }

    public void insertionSort(E[] list) {
        E elementInsert;
        COMPARE_COUNTER = 0;
        SWAP_COUNTER = 0;

        for (int i = 1; i < list.length; i++) {
            elementInsert = list[i];
            int indexInsert = i;

            while (indexInsert > 0 &&
                    list[indexInsert - 1].compareTo(elementInsert) > 0) {
                list[indexInsert] = list[indexInsert - 1];
                indexInsert--;
                COMPARE_COUNTER++;
                SWAP_COUNTER++;
            }
            list[indexInsert] = elementInsert;
        }
    }

    public void bubbleSort(E[] list) {
        E temp;
        COMPARE_COUNTER = 0;
        SWAP_COUNTER = 0;

        for (int i = list.length - 1; i >= 0; i--) {
            boolean swapped = false;

            for (int j = 0; j < i; j++) {
                if (list[j].compareTo(list[j + 1]) > 0) {
                    COMPARE_COUNTER++;
                    temp = list[j + 1];
                    list[j + 1] = list[j];
                    list[j] = temp;
                    swapped = true;
                    SWAP_COUNTER++;
                }
            }

            if(swapped){
                return;
            }
        }
    }

    public void quickSort(E[] list) {
        COMPARE_COUNTER = 0;
        SWAP_COUNTER = 0;

        quickSortSegment(list, 0, list.length);
    }

    private void quickSortSegment(E[] list, int start, int end) {

        if (end - start > 1) {
            int indexPartition = partition(list, start, end);
            quickSortSegment(list, start, indexPartition);
            quickSortSegment(list, indexPartition + 1, end);
        }
    }

    private int partition(E[] list, int start, int end) {
        E temp;
        E partitionElement = list[start];
        int leftIndex = start;
        int rightIndex = end - 1;

        while (leftIndex < rightIndex) {

            while (list[leftIndex].compareTo(partitionElement) <= 0 && leftIndex < rightIndex) {
                leftIndex++;
                COMPARE_COUNTER++;
            }

            while (list[rightIndex].compareTo(partitionElement) > 0) {
                rightIndex--;
                COMPARE_COUNTER++;
            }
            if (leftIndex < rightIndex) {
                temp = list[leftIndex];
                list[leftIndex] = list[rightIndex];
                list[rightIndex] = temp;
                SWAP_COUNTER++;
            }
        }

        list[start] = list[rightIndex];
        list[rightIndex] = partitionElement;
        return rightIndex;
    }

    public void mergeSort(E[] list) {
        COMPARE_COUNTER = 0;
        SWAP_COUNTER = 0;

        mergeSortSegment(list, 0, list.length);
    }

    private void mergeSortSegment(E[] list, int start, int end) {
        int numElements = end - start;

        if (numElements > 1) {
            int middle = (start + end) / 2;
            mergeSortSegment(list, start, middle);
            mergeSortSegment(list, middle, end);

            E[] tempList = (E[]) (new Comparable[numElements]);
            for (int i = 0; i < numElements; i++)
                tempList[i] = list[start + i];

            int indexLeft = 0;
            int indexRight = middle - start;
            for (int i = 0; i < numElements; i++) {
                if (indexLeft < (middle - start)) {
                    if (indexRight < (end - start)) {
                        if (tempList[indexLeft].compareTo(tempList[indexRight]) < 0) {
                            COMPARE_COUNTER++;
                            list[start + i] = tempList[indexLeft++];
                            SWAP_COUNTER++;
                        } else
                            list[start + i] = tempList[indexRight++];
                        SWAP_COUNTER++;
                    } else
                        list[start + i] = tempList[indexLeft++];
                    SWAP_COUNTER++;
                } else
                    list[start + i] = tempList[indexRight++];
                SWAP_COUNTER++;
            }
        }
    }

    public static void main(String[] args) {
        ArraySorter<Integer> sorter = new ArraySorter<>();
        Random generator = new Random();
        Integer[] list = new Integer[10000];

        for(int i =0; i< list.length; i++){
            list[i] = generator.nextInt(10000);
        }
        Integer[] sortList = new Integer[list.length];

        System.out.println("=================================SELECTION SORT=================================");
        System.arraycopy(list, 0, sortList,0, list.length);
        long begin = System.currentTimeMillis();
        sorter.selectionSort(sortList);
        System.out.println("TIME TAKEN: " + (System.currentTimeMillis()-begin) + "ms");
        System.out.println("NUMBER OF SWAPS: " + ArraySorter.SWAP_COUNTER);
        System.out.println("NUMBER OF COMPARES: " + ArraySorter.COMPARE_COUNTER);

        System.out.println("=================================INSERTION SORT=================================");
        System.arraycopy(list, 0, sortList,0, list.length);
        begin = System.currentTimeMillis();
        sorter.insertionSort(sortList);
        System.out.println("TIME TAKEN: " + (System.currentTimeMillis()-begin) + "ms");
        System.out.println("NUMBER OF SWAPS: " + ArraySorter.SWAP_COUNTER);
        System.out.println("NUMBER OF COMPARES: " + ArraySorter.COMPARE_COUNTER);

        System.out.println("=================================BUBBLE SORT=================================");
        System.arraycopy(list, 0, sortList,0, list.length);
        begin = System.currentTimeMillis();
        sorter.bubbleSort(sortList);
        System.out.println("TIME TAKEN: " + (System.currentTimeMillis()-begin) + "ms");
        System.out.println("NUMBER OF SWAPS: " + ArraySorter.SWAP_COUNTER);
        System.out.println("NUMBER OF COMPARES: " + ArraySorter.COMPARE_COUNTER);

        System.out.println("=================================QUICK SORT=================================");
        System.arraycopy(list, 0, sortList,0, list.length);
        begin = System.currentTimeMillis();
        sorter.quickSort(sortList);
        System.out.println("TIME TAKEN: " + (System.currentTimeMillis()-begin) + "ms");
        System.out.println("NUMBER OF SWAPS: " + ArraySorter.SWAP_COUNTER);
        System.out.println("NUMBER OF COMPARES: " + ArraySorter.COMPARE_COUNTER);

        System.out.println("=================================MERGE SORT=================================");
        System.arraycopy(list, 0, sortList,0, list.length);
        begin = System.currentTimeMillis();
        sorter.mergeSort(sortList);
        System.out.println("TIME TAKEN: " + (System.currentTimeMillis()-begin) + "ms");
        System.out.println("NUMBER OF SWAPS: " + ArraySorter.SWAP_COUNTER);
        System.out.println("NUMBER OF COMPARES: " + ArraySorter.COMPARE_COUNTER);
    }
}
