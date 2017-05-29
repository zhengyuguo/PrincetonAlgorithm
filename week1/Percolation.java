import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
  private int[][] sites;
  private WeightedQuickUnionUF uf;
  private int topNode;
  private int bottomNode;
  private int size;
  private int numberOpen = 0;

  public Percolation(int n) {                // create n-by-n grid, with all sites blocked
    if (n <= 0)
      throw new IllegalArgumentException();

    sites = new int[n][n];
    uf = new WeightedQuickUnionUF(n*n + 2);
    size = n;
    topNode = n*n;
    bottomNode = n*n + 1;

    for (int i = 0; i < n; ++i) {
      for (int j = 0; j < n; ++j) {
        sites[i][j] = 0;
      }
    }
  }

  private int xyToPos(int x, int y) {
    return size * (x - 1) + y - 1;
  }

  public void open(int row, int col) {    // open site (row, col) if it is not open already
    if (!isOpen(row, col)) {
      sites[row - 1][col - 1] = 1;
      int pos = xyToPos(row, col);

      if (row == 1) 
        uf.union(pos, topNode);
      else if (isOpen(row -1, col)) 
        uf.union(pos, xyToPos(row - 1, col));

      if (row == size)
        uf.union(pos, bottomNode);
      else if (isOpen(row + 1, col))
        uf.union(pos, xyToPos(row + 1, col));

      if (col > 1 && isOpen(row, col - 1))
        uf.union(pos, xyToPos(row, col - 1));

      if (col < size && isOpen(row, col + 1))
        uf.union(pos, xyToPos(row, col + 1));

      ++numberOpen;
    }
  }

  public boolean isOpen(int row, int col) {  // is site (row, col) open?
    return sites[row-1][col-1] == 1;
  }

  public boolean isFull(int row, int col) {  // is site (row, col) full?
    return isOpen(row, col) && uf.connected(xyToPos(row, col), topNode);
  }

  public int numberOfOpenSites() {       // number of open sites
    return numberOpen;
  }

  public boolean percolates() {              // does the system percolate?
    return uf.connected(topNode, bottomNode);
  }
}
