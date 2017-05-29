import java.util.Comparator;
import edu.princeton.cs.algs4.StdDraw;

public class Point implements Comparable<Point> {

  private final int x;     // x-coordinate of this point
  private final int y;     // y-coordinate of this point

  public Point(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public void draw() {
    StdDraw.point(x, y);
  }

  public void drawTo(Point that) {
    StdDraw.line(this.x, this.y, that.x, that.y);
  }

  public double slopeTo(Point that) {
    if (that == null)
      throw new NullPointerException();
    if (this.compareTo(that) == 0) return Double.NEGATIVE_INFINITY;
    if (this.x == that.x) return Double.POSITIVE_INFINITY;
    if (this.y == that.y) return 0.0;
    return (double) (that.y - this.y) / (that.x - this.x);
  }

  public int compareTo(Point that) {
    if (that == null)
      throw new NullPointerException();
    if (this.y < that.y) return -1;
    if (this.y > that.y) return 1;
    if (this.x < that.x) return -1;
    if (this.x > that.x) return 1;
    return 0;
  }

  public Comparator<Point> slopeOrder() {
    return new Comparator<Point>() {
      @Override
      public int compare(Point a, Point b) {
        return slopeTo(a) < slopeTo(b) ? -1 : (slopeTo(a) == slopeTo(b) ? 0 : 1);
      }
    };
  }

  public String toString() {
    return "(" + x + ", " + y + ")";
  }

  public static void main(String[] args) {
  }
}
