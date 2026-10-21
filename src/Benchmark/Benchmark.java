package Benchmark;

import Algorithms.*;

import java.util.*;

public class Benchmark {
    private static final int warmUp = 50;
    private static final int WARM_UP_SIZE = 10_000;

    private static double[] computeStats(long[] times) {
        double[] stats = new double[3];
        Arrays.sort(times);
        int noOfTimes = times.length;

        // median
        if (noOfTimes % 2 != 0) {
            stats[0] = times[noOfTimes / 2];
        } else {
            stats[0] = (times[noOfTimes / 2 - 1] + times[noOfTimes / 2]) / 2.0;
        }

        // mean
        stats[1] = 0;
        for (long time : times) {
            stats[1] += time;
        }
        stats[1] = stats[1] / noOfTimes;

        // standard deviation
        double variance = 0;
        for (long time : times) {
            double diff = time - stats[1];
            variance += diff * diff;
        }
        variance = variance / noOfTimes;
        stats[2] = Math.sqrt(variance);

        return stats;
    }

    private static BenchmarkResult benchmark(Median algorithm, int[] arr, int runs) {
        if (runs <= 0) {
            throw new IllegalArgumentException("runs must be > 0");
        }

        // Warm-up on a small prefix of arr (still random data) so the JIT compiles the hot methods
        int[] warmArr = Arrays.copyOf(arr, Math.min(arr.length, WARM_UP_SIZE));
        for (int i = 0; i < warmUp; i++) {
            algorithm.getMedian(warmArr.clone());
        }

        long[] times = new long[runs];
        for (int i = 0; i < runs; i++) {
            int[] copy = arr.clone();
            long start = System.nanoTime();
            algorithm.getMedian(copy);
            long end = System.nanoTime();
            times[i] = end - start;
        }

        double[] stats = computeStats(times);
        return new BenchmarkResult(stats[0], stats[1], stats[2]);
    }

    public static BenchmarkResult benchmarkRandomizedMedian(int[] arr, int runs) {
        return benchmark(new RandomizedMedian(), arr, runs);
    }

    public static BenchmarkResult benchmarkMedianByMOFM(int[] arr, int runs) {
        return benchmark(new MedianOfMedians(), arr, runs);
    }

    public static BenchmarkResult benchmarkMedianBySorting(int[] arr, int runs) {
        return benchmark(new MedianBySorting(), arr, runs);
    }

}