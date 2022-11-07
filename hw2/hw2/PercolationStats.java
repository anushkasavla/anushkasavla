package hw2;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;


public class PercolationStats {
    private int trials;
    private double[] stats;
    private int openSites;
    private int n;
    // perform T independent experiments on an N-by-N grid
    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new java.lang.IllegalArgumentException();
        }
        trials = T;
        n = N;
        stats = new double [T];
        for (int i = 0; i < T; i += 1){
            Percolation trial = pf.make(n);
            openSites = 0;
            while (trial.percolates() == false){
                int row = StdRandom.uniform(n);
                int col = StdRandom.uniform(n);
                if (trial.isOpen(row,col) == false) {
                    trial.open(row, col);
                    openSites += 1;
                }
            }
            stats[i] = (double) (openSites)/ (n*n);
        }
    }
    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(stats);
    }
    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(stats);
    }
    // low endpoint of 95% confidence interval
    public double confidenceLow() {
        return mean() - (1.96 * stddev()) / Math.sqrt(trials);
    }
    // high endpoint of 95% confidence interval
    public double confidenceHigh(){
        return mean() + (1.96 * stddev()) / Math.sqrt(trials);
    }
}
