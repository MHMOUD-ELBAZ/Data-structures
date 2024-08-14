import java.util.Arrays;

public class Sorting {
    public static void insertionSort(int[] arr){
        int current , inner, n = arr.length;
        for(int i=1;i<n;i++){
            current = arr[i];
            inner = i-1;

            while(inner>=0 && current < arr[inner]){
                arr[inner+1] = arr[inner];
                inner--;
            }
            arr[inner+1] = current;
        }
    }
    public static void bubbleSort(int[] arr) {
        int n = arr.length;
        boolean swapped = true;
        while (swapped) {
            swapped = false;
            for (int i = 1; i < n; i++) {
                if (arr[i - 1] > arr[i]) {
                    arr[i] += arr[i-1];
                    arr[i-1] = arr[i] - arr[i-1];
                    arr[i] -= arr[i-1];
                    swapped = true;
                }
            }
            n--; // Each pass puts the largest element in its final place so the unsorted portion decreased 1
        }

    }
    public static void mergeSort(int[] arr ){
        if(arr.length > 1){
            int[] leftHalf = Arrays.copyOfRange(arr,0,arr.length/2);
            mergeSort(leftHalf);

            int[] rightHalf = Arrays.copyOfRange(arr, arr.length/2, arr.length );
            mergeSort(rightHalf);

            merge(leftHalf,rightHalf,arr);
        }
    }
    public static void merge(int[] left,int[] right,int[] result){
        int leftIndex =0,rightIndex=0,resultIndex=0;
        int leftSize = left.length , rightSize = right.length;

        while(leftIndex < leftSize && rightIndex<rightSize){
            if(left[leftIndex]<right[rightIndex])
                result[resultIndex++] = left[leftIndex++];
            else
                result[resultIndex++] = right[rightIndex++];
        }

        while (leftIndex<leftSize)
            result[resultIndex++] = left[leftIndex++];

        while(rightIndex<rightSize)
            result[resultIndex++] = right[rightIndex++];

    }
    public static void quickSort(int[] arr,int left,int right){
        if(right > left){
            int pivotIndex = partition(arr,left,right);
            quickSort(arr,left,pivotIndex-1);
            quickSort(arr,pivotIndex+1,right);
        }

    }
    public static int partition(int[] arr,int left,int right){
        int pivot = arr[right];
        int finalPivotIndex = left-1;

        while(left<right){
            if(arr[left] <= pivot) {
                ++finalPivotIndex;
                int tmp = arr[left];
                arr[left] = arr[finalPivotIndex];
                arr[finalPivotIndex] =tmp;
            }
            left++;
        }
        finalPivotIndex++;
        int tmp = arr[right];
        arr[right] = arr[finalPivotIndex];
        arr[finalPivotIndex] =tmp;

        return finalPivotIndex;
    }
    public static void heapSort(int[] arr){
        int n = arr.length ;

        buildHeap(arr,n);
        int temp;
        for(int i = n-1 ;i> 0 ;i--){
            // Move current root to end
            temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;

            maxHeapify(arr, i, 0);
        }
    }
    public static void buildHeap(int[] arr,int n){
        int lastNonLeafNode = n/2 -1;

        while (lastNonLeafNode>=0)
            maxHeapify(arr,n,lastNonLeafNode--);
    }
    private static void maxHeapify(int[] arr,int n, int parent) {
        int largest = parent;
        int leftNode = 2*parent + 1 , rightNode = 2*parent+2;

        if(leftNode< n && arr[leftNode]>arr[largest])
            largest = leftNode;
        if(rightNode< n && arr[rightNode]>arr[largest])
            largest = rightNode;

        if(largest != parent) {
            arr[parent] += arr[largest];
            arr[largest] = arr[parent] - arr[largest];
            arr[parent] -= arr[largest];
            maxHeapify(arr,n, largest);
        }
    }
}
