package Algorithms;

public abstract class Median {


    public abstract int getMedian(int[] arr) ;
    protected int[] threeWayPartition(int[] arr, int start, int end, int pivotValue) {
        int lt = start;
        int i = start;
        int gt = end;

        while (i <= gt) {
            if (arr[i] < pivotValue) {
                swap(arr, lt, i);
                lt++;
                i++;
            } else if (arr[i] > pivotValue) {
                swap(arr, i, gt);
                gt--;
            } else {
                i++;
            }
        }

        return new int[]{lt, gt};
    }

    protected void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
