import java.util.*;

public class FastCollinearPoints {
  private ArrayList<LineSegment> l = new ArrayList<>();
  private HashMap<Double, List<Point>> foundSlope = new HashMap<>();

  public FastCollinearPoints(Point[] points) {
    Point[] p = Arrays.copyOf(points, points.length);
    checkDuplicates(points);
    for (Point i : points) { 
      Arrays.sort(p, i.slopeOrder());

      int j = 1;
      while (j < p.length) {
        double slopeToJ = i.slopeTo(p[j]);
        ArrayList<Point> seg = new ArrayList<>();
        seg.add(i);
        seg.add(p[j]);
        for (int k = j + 1; k < p.length; ++k) {
          if (slopeToJ == i.slopeTo(p[k])) 
            seg.add(p[k]);
          else
            break;
        }

        if (seg.size() > 3) {
          addPointsToLineSeg(seg, slopeToJ);
        }
        j = j + seg.size() - 1;
      }
    }
  }

  private void addPointsToLineSeg(ArrayList<Point> p, double slope) {
    Collections.sort(p);
    Point start = p.get(0);
    Point end = p.get(p.size() - 1);
    if (!foundSlope.containsKey(slope)) {
      foundSlope.put(slope, new ArrayList<Point>());
    }

    List<Point> endP = foundSlope.get(slope);
    for (Point i : endP) {
      if (i.compareTo(end) == 0) 
        return;
    }

    endP.add(end);
    l.add(new LineSegment(start, end));
  }

  public int numberOfSegments() {
    return l.size();
  }

  public LineSegment[] segments() {
    return l.toArray(new LineSegment[l.size()]);
  }

  private void checkDuplicates(Point[] p) {
    for (int i = 0; i < p.length - 1; ++i) {
      if (p[i].compareTo(p[i + 1]) == 0) 
        throw new IllegalArgumentException();
    }
  }
}
