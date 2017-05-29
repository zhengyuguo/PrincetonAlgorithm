import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Queue;

public class KdTree {
  private TreeNode tree;
  private int nNode;


  private class TreeNode {
    private Point2D point;
    private RectHV rect;
    private TreeNode left;
    private TreeNode right;
    private boolean isVertical;

    public TreeNode(Point2D point, TreeNode left, TreeNode right, boolean isVertical, RectHV rect) {
      this.point = point;
      this.left = left;
      this.right = right;
      this.isVertical = isVertical;
      this.rect = rect;
    }

    public RectHV subRect(Point2D p) {
      if (isLeft(p) && isVertical) 
        return new RectHV(rect.xmin(), rect.ymin(), point.x(), rect.ymax());
      else if (!isLeft(p) && isVertical) 
        return new RectHV(point.x(), rect.ymin(), rect.xmax(), rect.ymax());
      else if (isLeft(p) && !isVertical) 
        return new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), point.y());
      else if (!isLeft(p) && !isVertical) 
        return new RectHV(rect.xmin(), point.y(), rect.xmax(), rect.ymax());
      else
        return null;
    }

    public boolean isLeft(Point2D that) {
      if (this.isVertical)
        return this.point.x() > that.x();
      else
        return this.point.y() > that.y();
    }
  }

  public KdTree() {
    tree = null;
    nNode = 0;
  }

  public boolean isEmpty() {
    return this.nNode == 0;
  }

  public int size() {
    return this.nNode;
  }

  public void insert(Point2D p) {
    if (this.isEmpty()) {
      ++this.nNode;
      tree =  new TreeNode(p, null, null, true, new RectHV(0, 0, 1, 1));
      return;
    } 

    TreeNode tmp = tree;
    TreeNode prev = null;
    boolean onLeft = true;
    while (tmp != null) {
      onLeft = tmp.isLeft(p);
      prev = tmp;
      if (onLeft)
        tmp = tmp.left;
      else if(!tmp.point.equals(p))
        tmp = tmp.right;
      else
        return;
    }

    if (onLeft) 
      prev.left = new TreeNode(p, null, null, !prev.isVertical, prev.subRect(p));
    else
      prev.right = new TreeNode(p, null, null, !prev.isVertical, prev.subRect(p));
    ++this.nNode;
  }

  public boolean contains(Point2D p) {
    TreeNode tmp = tree;
    while (tmp != null) {
      if (tmp.point.equals(p)) return true;
      if (tmp.isLeft(p)) 
        tmp = tmp.left;
      else
        tmp = tmp.right;
    }
    return false;
  }

  public void draw() {
  }

  public Iterable<Point2D> range(RectHV rect) {
    Queue<Point2D> res = new Queue<>();
    if (tree == null) return res;

    Queue<TreeNode> q = new Queue<>();
    q.enqueue(tree);
    TreeNode tmp;
    while (!q.isEmpty()) {
      tmp = q.dequeue();
      if (rect.contains(tmp.point))
        res.enqueue(tmp.point);
      if (tmp.left != null && rect.intersects(tmp.left.rect))
        q.enqueue(tmp.left);
      if (tmp.right != null && rect.intersects(tmp.right.rect))
        q.enqueue(tmp.right);
    }
    return res;
  }

  public Point2D nearest(Point2D p) {
    Point2D res = null;
    double dist = Double.POSITIVE_INFINITY;
    if (tree == null) return res;

    Queue<TreeNode> q = new Queue<>();
    q.enqueue(tree);
    TreeNode tmp;
    while (!q.isEmpty()) {
      tmp = q.dequeue();
      double d = p.distanceTo(tmp.point);
      if (d < dist) {
        res = tmp.point;
        dist = d;
      }

      if (tmp.left != null && tmp.left.rect.distanceTo(p) < dist)
        q.enqueue(tmp.left);
      if (tmp.right != null && tmp.right.rect.distanceTo(p) < dist)
        q.enqueue(tmp.right);
    }
    return res;
  }
}
