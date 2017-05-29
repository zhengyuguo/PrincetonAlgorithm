import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
  private Node first, last;
  private int size = 0;

  private class Node {
    public Item item;
    public Node next;
    public Node prev;
  }

  public Deque() {
    first = null;
    last = null;
  }

  public boolean isEmpty() {
    return first == null && last == null;
  }

  public int size() {
    return size;
  }

  public void addFirst(Item item) {
    if (item == null)
      throw new NullPointerException();

    Node oldfirst = first;
    first = new Node();
    first.item = item;
    first.next = oldfirst;
    first.prev = null;
    if (last == null) 
      last = first;
    else
      oldfirst.prev = first;
    ++size;
  }

  public void addLast(Item item) {
    if (item == null)
      throw new NullPointerException();

    Node oldlast = last;
    last = new Node();
    last.item = item;
    last.next = null;
    last.prev = oldlast;
    if (first == null)
      first = last;
    else
      oldlast.next = last;
    ++size;
  }

  public Item removeFirst() {
    if (size == 0) 
      throw new NoSuchElementException();

    Item item = first.item;
    first = first.next;
    if (first != null) first.prev = null;

    if (first == null)
      last = null;
    --size;
    return item;
  }

  public Item removeLast() {
    if (size == 0) 
      throw new NoSuchElementException();

    Item item = last.item;
    last = last.prev;
    if (last != null) last.next = null;

    if (last == null)
      first = null;
    --size;
    return item;
  }


  @Override
  public Iterator<Item> iterator() { return new ListIterator(); }

  private class ListIterator implements Iterator<Item> {
    private Node current = first;

    @Override
    public boolean hasNext() { return current != null; }

    @Override
    public Item next() {
      if (!hasNext()) 
        throw new NoSuchElementException();

      Item item = current.item;
      current = current.next;
      return item;
    }

    @Override
    public void remove() {
      throw new UnsupportedOperationException();
    }
  }

  public static void main(String[] args) {
    Deque<Integer> deque = new Deque<>();
    deque.addFirst(0);
    System.out.println(deque.removeFirst());
    System.out.println(deque.isEmpty());
    deque.addFirst(3);
    System.out.println(deque.size());
    deque.addFirst(5);
    System.out.println(deque.removeFirst());
    deque.addLast(7);
    System.out.println(deque.removeLast());
    System.out.println(deque.removeLast());
    System.out.println(deque.isEmpty());
    System.out.println(deque.size());
    deque.addFirst(10);
    System.out.println(deque.removeLast());
    System.out.println(-1 % 10);

  }
}
