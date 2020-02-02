/* *****************************************************************************
 *  Name:              Jake Perez
 *  Coursera User ID:  123456
 *  Last modified:     1/26/2020
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private static final double CONFIDENCE_95 = 1.96;
    private final double[] meanFractions;

    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("n and t must be greater than 0");
        }

        meanFractions = new double[trials];
        for (int i = 0; i < trials; i++) {
            Percolation percolationSystem = new Percolation(n);
            while (!percolationSystem.percolates()) {
                int row = StdRandom.uniform(n) + 1;
                int col = StdRandom.uniform(n) + 1;

                if (!percolationSystem.isOpen(row, col)) {
                    percolationSystem.open(row, col);
                }
            }
            meanFractions[i] = (double) percolationSystem.numberOfOpenSites() / (n * n);
        }

    }

    public double mean() {
        return StdStats.mean(meanFractions);
    }

    public double stddev() {
        return StdStats.stddev(meanFractions);
    }

    public double confidenceLo() {
        double rootT = Math.sqrt(meanFractions.length);
        return mean() - ((CONFIDENCE_95 * stddev()) / rootT);
    }

    public double confidenceHi() {
        double rootT = Math.sqrt(meanFractions.length);
        return mean() + ((CONFIDENCE_95 * stddev()) / rootT);
    }

    public static void main(String[] args) {
        PercolationStats percStats = new PercolationStats(Integer.parseInt(args[0]),
                                                          Integer.parseInt(args[1]));
        System.out.println("mean                     = " + percStats.mean());
        System.out.println("standard deviation       = " + percStats.stddev());
        System.out.println(
                "95% confidence interval  = " + "[" + percStats.confidenceLo() + ",  " + percStats
                        .confidenceHi() + "]");

    }
}
