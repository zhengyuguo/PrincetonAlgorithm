import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

public class Solver {
  private Stack<Board> boards;
  private boolean solvable;
  private int moves;

  private class SearchNode implements Comparable<SearchNode> {
    private Board board;
    private int moves;
    private SearchNode previous;
    private int pri = -1;
    public SearchNode(Board board, int moves, SearchNode previous) {
      this.board = board;
      this.moves = moves;
      this.previous = previous;
    }

    private int priority() {
      if (pri == -1) {
        pri = board.manhattan() + moves;
      }
      return pri;
    }

    @Override
    public int compareTo(SearchNode that) {
      if (this.priority() < that.priority()) return -1;
      if (this.priority() > that.priority()) return 1;
      return 0;
    }
  }

  public Solver(Board initial) {
    this.boards = new Stack<>();
    MinPQ<SearchNode> queue = new MinPQ<>();
    MinPQ<SearchNode> queueTwin = new MinPQ<>();
    Board b = initial;
    Board bTwin = initial.twin();
    SearchNode node = new SearchNode(b, 0, null);
    SearchNode nodeTwin = new SearchNode(bTwin, 0, null);
    queue.insert(node);
    queueTwin.insert(nodeTwin);

    while (true) {
      node = queue.delMin();
      nodeTwin = queueTwin.delMin();
      b = node.board;
      bTwin = nodeTwin.board;

      if (b.isGoal()) {
        this.solvable = true;
        this.moves = node.moves;
        while (node != null) {
          this.boards.push(node.board);
          node = node.previous;
        }
        return;
      }

      if (bTwin.isGoal()) {
        this.solvable = false;
        this.moves = -1;
        return;
      }

      for (Board i : b.neighbors()) {
        if (node.previous != null && i.equals(node.previous.board)) {
          continue;
        }

        SearchNode tmp = new SearchNode(i, node.moves + 1, node);
        queue.insert(tmp);
      }

      for (Board i : bTwin.neighbors()) {
        if (nodeTwin.previous != null && i.equals(nodeTwin.previous.board))
          continue;
        SearchNode tmp = new SearchNode(i, nodeTwin.moves + 1, nodeTwin);
        queueTwin.insert(tmp);
      }
    }
  }

  public boolean isSolvable() {
    return this.solvable;
  }

  public int moves() {
    return this.moves;
  }

  public Iterable<Board> solution() { 
    if (this.isSolvable())
      return boards;
    else
      return null;
  }
}
