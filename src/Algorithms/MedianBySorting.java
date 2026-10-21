package Algorithms;

import java.util.Arrays;

public class MedianBySorting extends Median {
    public int getMedian(int[] arr) {
        if (arr.length == 0) {
            throw new IllegalArgumentException("Array is empty");
        }
        Arrays.sort(arr);
        return arr[arr.length / 2];
    }
}
