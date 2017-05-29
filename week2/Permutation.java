import edu.princeton.cs.algs4.StdIn;

public class Permutation {
   public static void main(String[] args) {
    RandomizedQueue<String> q = new RandomizedQueue<>(); 
    int num = Integer.parseInt(args[0]);
    while (!StdIn.isEmpty()) 
     q.enqueue(StdIn.readString());

    for (int i = 0; i < num; ++i) {
      System.out.println(q.dequeue());
    }
   }
}
