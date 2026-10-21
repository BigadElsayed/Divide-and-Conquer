import Algorithms.*;
import Array_Generator.ArrayGenerator;

import Benchmark.* ;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

public class Problem1Main {

    private static final int[] SIZES = {
            1_000, 5_000, 10_000, 50_000, 100_000, 500_000,
            1_000_000, 2_000_000, 5_000_000, 10_000_000
    };

    private static int runsFor(int n) {
        if (n <= 100000) return 50;
        if (n <= 1000000) return 20;
        return 5;
    }

    private static void verify(int[] arr) {
        int[] sorted = arr.clone();
        Arrays.sort(sorted);
        int expected = sorted[sorted.length / 2];

        int r = new RandomizedMedian().getMedian(arr.clone());
        int m = new MedianOfMedians().getMedian(arr.clone());
        int s = new MedianBySorting().getMedian(arr.clone());

        if (r != expected || m != expected || s != expected) {
            throw new IllegalStateException("Wrong median for n=" + arr.length + ": expected "
                    + expected + " but got randomized=" + r + ", MoM=" + m + ", sorting=" + s);
        }
    }

    private static void writeRow(PrintWriter out, int n, int runs, String name, BenchmarkResult b) {
        out.printf("%d,%d,%s,%.4f,%.4f,%.4f%n", n, runs, name,
                b.getMean() / 1e6, b.getMedian() / 1e6, b.getStdDev() / 1e6);
    }

    public static void main(String[] args) throws IOException {
        String csvPath = "results.csv";

        try (PrintWriter csv = new PrintWriter(new FileWriter(csvPath))) {
            csv.println("size,runs,algorithm,mean_ms,median_ms,stddev_ms");

            System.out.printf("%-12s %-5s | %-16s %-16s %-16s%n", "n", "runs", "Randomized (ms)", "MedianOfMed (ms)", "Sorting (ms)");
            System.out.println("-".repeat(70));

            for (int n : SIZES) {
                int runs = runsFor(n);
                int[] arr = ArrayGenerator.generateArray(n);   // same random input for all three

                verify(arr);                                   // correctness before timing

                BenchmarkResult rand = Benchmark.benchmarkRandomizedMedian(arr, runs);
                BenchmarkResult mom = Benchmark.benchmarkMedianByMOFM(arr, runs);
                BenchmarkResult sort = Benchmark.benchmarkMedianBySorting(arr, runs);

                System.out.printf("%-12d %-5d | %-16.3f %-16.3f %-16.3f%n", n, runs, rand.getMean() / 1e6, mom.getMean() / 1e6, sort.getMean() / 1e6);

                writeRow(csv, n, runs, "randomized", rand);
                writeRow(csv, n, runs, "median_of_medians", mom);
                writeRow(csv, n, runs, "sorting", sort);

                csv.flush();
            }
        }
        System.out.println("\nSaved full statistics " + csvPath);
    }
}