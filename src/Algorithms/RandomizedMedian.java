package Algorithms;

public class RandomizedMedian extends Median {
    @Override
    public int getMedian(int[] arr) {
        if (arr.length == 0) {
            throw new IllegalArgumentException("Array is empty");
        }
        return randomizedMedian(arr, 0, arr.length - 1, arr.length / 2 + 1);
        // Here we pass (arr.length/2 + 1) not (arr.length/2) because we pass rank not its index -> 1-based
    }

    private int randomizedMedian(int[] arr, int start, int end, int median) {
        if (start == end) {
            return arr[start];
        }

        // the range looks like:  [ < pivot | == pivot | > pivot ]
        // bounds[0] = first index of the "equal" block
        // bounds[1] = last index of it
        int[] bounds = randomizedPartition(arr, start, end);
        int lt = bounds[0];
        int gt = bounds[1];

        int lessCount = lt - start;
        int lessOrEqualCount = gt - start + 1;

        if (median <= lessCount) {
            // the wanted rank is inside the "smaller" block
            return randomizedMedian(arr, start, lt - 1, median);
        }
        else if (median <= lessOrEqualCount) {
            // the wanted rank falls inside the "equal" block -> every element there is the answer
            return arr[lt];
        }
        else {
            // the wanted rank is inside the "greater" block
            return randomizedMedian(arr, gt + 1, end, median - lessOrEqualCount);
        }
    }

    private int[] randomizedPartition(int[] arr, int start, int end) {
        int randomIdx = start + (int) (Math.random() * (end - start + 1));
        return threeWayPartition(arr, start, end , arr[randomIdx]);
    }

}
