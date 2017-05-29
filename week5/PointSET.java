import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Queue;
import java.util.TreeSet;

public class PointSET {
  private TreeSet<Point2D> points;

  public PointSET() {
    points = new TreeSet<>();
  }

  public boolean isEmpty() {
    return points.isEmpty();
  }

  public int size() {
    return points.size();
  }

  public void insert(Point2D p) {
    points.add(p);
  }

  public boolean contains(Point2D p) {
    return points.contains(p);
  }

  public void draw() {
    for (Point2D point : points) {
      point.draw();
    }
  }

  public Iterable<Point2D> range(RectHV rect) {
    Queue<Point2D> q = new Queue<>();
    for (Point2D p : points) {
      if (rect.contains(p)) {
        q.enqueue(p);
      }
    }
    return q;
  }

  public Point2D nearest(Point2D p) {
    double minD = Double.POSITIVE_INFINITY;
    Point2D minPoint = null;
    for (Point2D point : points) {
      double tmpD = point.distanceTo(p);
      if (tmpD < minD) {
        minPoint = point;
        minD = tmpD;
      }
    }
    return minPoint;
  }
}
