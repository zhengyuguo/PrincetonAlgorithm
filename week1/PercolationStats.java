import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
  private double[] percentage;
  private int num;

  public PercolationStats(int n, int trials) {    // perform trials independent experiments on an n-by-n grid
    if (n <= 0 || trials <= 0)
      throw new IllegalArgumentException();

    percentage = new double[trials];
    num = trials;

    for (int i = 0; i < percentage.length; ++i) {
      Percolation p = new Percolation(n);
      while (!p.percolates()) {
        int row = StdRandom.uniform(n) + 1;
        int col = StdRandom.uniform(n) + 1;
        p.open(row, col);
      }
      percentage[i] = (double) p.numberOfOpenSites() / (n*n);
    }
  }

  public double mean() {                          // sample mean of percolation threshold
    return StdStats.mean(percentage);
  }

  public double stddev() {                       // sample standard deviation of percolation threshold
    return StdStats.stddev(percentage);
  }

  public double confidenceLo() {                 // low  endpoint of 95% confidence interval
    return mean() - 1.96 * stddev() / Math.sqrt(num);
  }

  public double confidenceHi() {                 // high endpoint of 95% confidence interval
    return mean() + 1.96 * stddev() / Math.sqrt(num);
  }

  public static void main(String[] args) {       // test client (described below)
    PercolationStats p = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
    System.out.println(p.mean());
    System.out.println(p.stddev());
    System.out.println(p.confidenceLo());
    System.out.println(p.confidenceHi());
  }
}
