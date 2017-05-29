import edu.princeton.cs.algs4.Queue;

public class Board {
  private int[][] b;
  private int blankRow;
  private int blankCol;

  public Board(int[][] blocks) {
    b = new int[blocks.length][blocks.length];
    for (int i = 0; i < blocks.length; ++i)
      for (int j = 0; j < blocks.length; ++j) {
        b[i][j] = blocks[i][j];
        if (b[i][j] == 0) {
          blankRow = i;
          blankCol = j;
        }
      }
  }

  public int dimension() { 
    return b.length;
  }

  public int hamming() {
    int dist = 0;
    for (int i = 0; i < this.dimension(); ++i)
      for (int j = 0; j < this.dimension(); ++j)
        if (b[i][j] != 0 && b[i][j] != this.dimension() * i + j + 1)
          ++dist;
    return dist;
  }

  public int manhattan() {
    int dist = 0;
    for (int i = 0; i < this.dimension(); ++i)
      for (int j = 0; j < this.dimension(); ++j) {
        if (b[i][j] != 0) {
          int trueRow = (b[i][j] - 1) / this.dimension();
          int trueCol = (b[i][j] - 1) % this.dimension();
          dist += ((i > trueRow) ? (i - trueRow) : (trueRow - i)) + ((j > trueCol) ? (j - trueCol) : (trueCol - j));
        }
      }
    return dist;
  }

  public boolean isGoal() {
    if (b[this.dimension() - 1][this.dimension() - 1] != 0) return false;
    for (int i = 0; i < this.dimension() * this.dimension() - 1; ++i) {
      int row = i / this.dimension();
      int col = i % this.dimension();
      if (b[row][col] != i + 1)
        return false;
    }
    return true;
  }

  private static void swap(int[][] a, int fromR, int fromC, int toR, int toC) {
    int temp = a[fromR][fromC];
    a[fromR][fromC] = a[toR][toC]; 
    a[toR][toC] = temp;
  }

  private static int[][] copySquare2D(int[][] a, int dim) {
    int[][] t = new int[dim][dim];
    for (int i = 0; i < dim; ++i)
      for (int j = 0; j < dim; ++j) {
        t[i][j] = a[i][j];
      }
    return t;
  }

  public Board twin() {
    int[][] t = copySquare2D(b, this.dimension());
    swap(t, (blankRow + 1) % this.dimension(), blankCol, blankRow, (blankCol + 1) % this.dimension());
    return new Board(t);
  }

  public boolean equals(Object y) {
    if (!(y instanceof Board)) return false;
    Board that = (Board) y;
    if (that.dimension() != this.dimension()) return false;
    for (int i = 0; i < this.dimension(); ++i)
      for (int j = 0; j < this.dimension(); ++j)
        if (b[i][j] != that.b[i][j])
          return false;
    return true;
  }

  public Iterable<Board> neighbors() {
    Queue<Board> q = new Queue<>();
    if (blankRow == 0) {
      int[][] tmp = copySquare2D(b, this.dimension());
      swap(tmp, blankRow, blankCol, blankRow + 1, blankCol);
      q.enqueue(new Board(tmp));
    } else if (blankRow == this.dimension() - 1) {
      int[][] tmp = copySquare2D(b, this.dimension());
      swap(tmp, blankRow, blankCol, blankRow - 1, blankCol);
      q.enqueue(new Board(tmp));
    } else {
      int[][] tmp1 = copySquare2D(b, this.dimension());
      swap(tmp1, blankRow, blankCol, blankRow - 1, blankCol);
      q.enqueue(new Board(tmp1));
      int[][] tmp2 = copySquare2D(b, this.dimension());
      swap(tmp2, blankRow, blankCol, blankRow + 1, blankCol);
      q.enqueue(new Board(tmp2));
    }

    if (blankCol == 0) {
      int[][] tmp = copySquare2D(b, this.dimension());
      swap(tmp, blankRow, blankCol, blankRow, blankCol + 1);
      q.enqueue(new Board(tmp));
    } else if (blankCol == this.dimension() - 1) {
      int[][] tmp = copySquare2D(b, this.dimension());
      swap(tmp, blankRow, blankCol, blankRow, blankCol - 1);
      q.enqueue(new Board(tmp));
    } else {
      int[][] tmp1 = copySquare2D(b, this.dimension());
      swap(tmp1, blankRow, blankCol, blankRow, blankCol - 1);
      q.enqueue(new Board(tmp1));                              
      int[][] tmp2 = copySquare2D(b, this.dimension());                      
      swap(tmp2, blankRow, blankCol, blankRow, blankCol + 1);
      q.enqueue(new Board(tmp2));
    }
    return q;
  }

  public String toString() {
    StringBuilder s = new StringBuilder();
    s.append(this.dimension() + "\n");
    for (int i = 0; i < this.dimension(); i++) {
      for (int j = 0; j < this.dimension(); j++) {
        s.append(String.format("%2d ", b[i][j]));
      }
      s.append("\n");
    }
    return s.toString();
  }
}
