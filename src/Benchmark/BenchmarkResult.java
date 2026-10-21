package Benchmark;

public class BenchmarkResult {
    double median;
    double mean;
    double standardDeviation;

    public BenchmarkResult() {
    }

    public BenchmarkResult(double median, double mean, double standardDeviation) {
        this.median = median;
        this.mean = mean;
        this.standardDeviation = standardDeviation;
    }


    public double getMedian() {
        return median;
    }

    public void setMedian(double median) {
        this.median = median;
    }

    public double getMean() {
        return mean;
    }

    public void setMean(double mean) {
        this.mean = mean;
    }

    public double getStdDev() {
        return standardDeviation;
    }

    public void setStdDev(double standardDeviation) {
        this.standardDeviation = standardDeviation;
    }

}