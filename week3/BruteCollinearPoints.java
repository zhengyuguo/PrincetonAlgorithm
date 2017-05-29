import java.util.Arrays;
import java.util.ArrayList;
public class BruteCollinearPoints {
  private LineSegment[] l;
  public BruteCollinearPoints(Point[] points) {
    ArrayList<LineSegment> tempL = new ArrayList<>();

    Point[] p = Arrays.copyOf(points, points.length);
    Arrays.sort(p);
    checkDuplicates(p);

    for (int u = 0; u < p.length - 3; ++u) {
      for (int v = u + 1; v < p.length - 2; ++v) {
        for (int w = v + 1; w < p.length - 1; ++w) {
          for (int x = w + 1; x < p.length; ++x) {
            if (p[u].slopeTo(p[v]) == p[v].slopeTo(p[w]) && p[v].slopeTo(p[w]) == p[w].slopeTo(p[x]))
              tempL.add(new LineSegment(p[u], p[x]));
          }
        }
      }
    }
    l = tempL.toArray(new LineSegment[tempL.size()]);
  }

  public int numberOfSegments() {
    return l.length;
  }

  public LineSegment[] segments() {
    return Arrays.copyOf(l, this.numberOfSegments());
  }

  private void checkDuplicates(Point[] p) {
    for (int i = 0; i < p.length - 1; ++i) {
      if (p[i].compareTo(p[i + 1]) == 0) 
        throw new IllegalArgumentException();
    }
  }
}
