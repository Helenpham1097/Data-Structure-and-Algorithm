package datastructure.assignment2.exercise1;

public class QuickSort {

    public static int partition(String[] arr, int low, int high) {
        String pivot = arr[high];
        int i = (low - 1);
        for (int j = low; j < high; j++) {
            if (arr[j].compareTo(pivot) <=0) {
                i++;
                String temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }

        String temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;

        return i + 1;
    }

    public static void sort(String[] arr, int low, int high) {
        if (low < high) {
            int pivot = partition(arr, low, high);
            sort(arr, low, pivot - 1);
            sort(arr, pivot + 1, high);
        }
    }
}
