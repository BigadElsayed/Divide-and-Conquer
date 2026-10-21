package Algorithms;

import java.util.Arrays;

public class MedianOfMedians extends Median {
    @Override
    public int getMedian(int[] arr) {
        if (arr.length == 0) {
            throw new IllegalArgumentException("Array is empty");
        }
        return medianOfMedians(arr, 0, arr.length - 1, arr.length / 2 + 1);
        // Here we pass (arr.length/2 + 1) not (arr.length/2) because we pass rank not its index -> 1-based
    }

    private int medianOfMedians(int[] arr, int start, int end, int median) {
        if (start == end) {
            return arr[start];
        }

        // the range looks like:  [ < pivot | == pivot | > pivot ]
        // bounds[0] = first index of the "equal" block
        // bounds[1] = last index of it
        int[] bounds = medianPartition(arr, start, end);
        int lt = bounds[0];
        int gt = bounds[1];

        int lessCount = lt - start;
        int lessOrEqualCount = gt - start + 1;

        if (median <= lessCount) {
            // the wanted rank is inside the "smaller" block
            return medianOfMedians(arr, start, lt - 1, median);
        }
        else if (median <= lessOrEqualCount) {
            // the wanted rank falls inside the "equal" block -> every element there is the answer
            return arr[lt];
        }
        else {
            // the wanted rank is inside the "greater" block
            return medianOfMedians(arr, gt + 1, end, median - lessOrEqualCount);
        }
    }

    private int[] medianPartition(int[] arr, int start, int end) {
        int pivotValue = getMedianOfMedians(Arrays.copyOfRange(arr, start, end + 1));
        return threeWayPartition(arr, start, end, pivotValue);
    }

    public int getMedianOfMedians(int[] arr) {
        if (arr.length <= 5) {
            return getMedianBySorting(arr);
        }

        int noOfArrays = (arr.length + 4) / 5;
        int[] medians = new int[noOfArrays];

        for (int i = 0; i < noOfArrays; i++) {
            int start = i * 5;
            int end = Math.min(start + 5, arr.length);
            medians[i] = getMedianBySorting(Arrays.copyOfRange(arr, start, end));
        }
        return getMedianOfMedians(medians);
    }

    private int getMedianBySorting(int[] arr) {
        Arrays.sort(arr);
        return arr[arr.length / 2];
    }
}
