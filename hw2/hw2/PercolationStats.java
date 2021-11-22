package hw2;

import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdRandom;

public class PercolationStats {
    private int times;
    private double[] counts;

    public PercolationStats(int N, int T, PercolationFactory pf){
        if(N <= 0 || T <= 0){
            throw new java.lang.IllegalArgumentException();
        }
        times = T;
        counts = new double[T];
        for (int i = 0; i < T; i++){
            Percolation perco = pf.make(N);
            while (!perco.percolates()){
                int m = StdRandom.uniform(0, N);
                int n = StdRandom.uniform(0, N);
                perco.open(m,n);
            }
            counts[i] = perco.numberOfOpenSites();
        }
    }

    public double mean(){
        return StdStats.mean(counts);
    }

    public double stddev(){
        return StdStats.stddev(counts);

    }
    public double confidenceLow(){
        return mean()-1.96*stddev()/Math.sqrt(times);
    }
    public double confidenceHigh(){
        return mean()-1.96*stddev()/Math.sqrt(times);
    }
}
