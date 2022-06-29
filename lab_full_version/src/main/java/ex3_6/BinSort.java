package ex3_6;

import java.util.Arrays;
import java.util.Random;

public class BinSort {

    public static void binSort(int[] list, int MAX_VALUE){
        int[] bin = new int[MAX_VALUE + 1];

        for (int i = 0; i < list.length; i++) {
            if(bin[list[i]]==0){
                bin[list[i]] = 1;
            }else {
                bin[list[i]]++;
            }
        }

        int index = 0;
        for(int i =0; i< bin.length; i++){
            while(bin[i]>0 && index < list.length){
                list[index] = i;
                bin[i]--;
                index++;
            }
        }
    }

    public static void main(String[] args) {
        Random generator = new Random();
        final int MAX_VALUE = 1000;
        int[]list = new int[500];

        for(int i = 0; i< list.length; i++){
            list[i] = generator.nextInt(MAX_VALUE);
        }

        System.out.println("THE ARRAY BEFORE SORTED ");
        System.out.println(Arrays.toString(list));

        long begin = System.currentTimeMillis();
        binSort(list, MAX_VALUE);

        System.out.println("THE ARRAY AFTER SORTED BY BIN SORT");
        System.out.println(Arrays.toString(list));

        System.out.println("TIME TO SORT: "+ (System.currentTimeMillis()-begin) + " ms");
    }
}
