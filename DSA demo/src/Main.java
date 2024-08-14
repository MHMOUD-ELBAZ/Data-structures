import java.lang.reflect.Array;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        int[] arr = {-5,10,2,6,8,6,-50,0,60,32,21,11,4};

        int[] bResult =Arrays.copyOf(arr,arr.length);
        Sorting.bubbleSort(bResult);
        for(int n : bResult) System.out.print(n+" ");
        System.out.println();

        int[] iResult =Arrays.copyOf(arr,arr.length);
        Sorting.insertionSort(iResult);
        for(int n : iResult) System.out.print(n+" ");
        System.out.println();

        int[] mResult =Arrays.copyOf(arr,arr.length);
        Sorting.mergeSort(mResult);
        for(int n : mResult) System.out.print(n+" ");
        System.out.println();

        int[] qResult =Arrays.copyOf(arr,arr.length);
        Sorting.quickSort(qResult,0,qResult.length-1);
        for(int n : qResult) System.out.print(n+" ");
        System.out.println();

        int[] hResult =Arrays.copyOf(arr,arr.length);
        Sorting.heapSort(hResult);
        for(int n : hResult) System.out.print(n+" ");
        System.out.println();
    }

}