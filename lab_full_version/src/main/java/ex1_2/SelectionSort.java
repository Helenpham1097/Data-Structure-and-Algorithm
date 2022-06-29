package ex1_2;

import java.util.Arrays;
import java.util.Scanner;

public class SelectionSort {

    public static void main(String[]args){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the array");
        String array = scanner.next();
        int [] input = Arrays.stream(array.split(","))
                .mapToInt(Integer::parseInt)
                .toArray();
        SelectionSort selectionSort = new SelectionSort();
        System.out.println(Arrays.toString(selectionSort.selectionSort(input)));
        /**
         * Growth function of selection sort is n(n-1)/1 = (n^2-n)/1
         * Time complexity is O(n^2)
         */
    }

    public int[] selectionSort(int[] array){
        for(int i =0; i< array.length;i++){
            int minElementId = i;
            for(int j = i+1; j< array.length; j++){
                if(array[minElementId] > array[j]){
                    minElementId = j;
                }
            }
            if(minElementId !=i){
                int temporary = array[i];
                array[i] =array[minElementId];
                array[minElementId] = temporary;
            }
        }
        return array;
    }
}
