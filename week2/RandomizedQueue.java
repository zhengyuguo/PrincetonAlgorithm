import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
  private Item[] items;
  private int size;
  private int head, tail;
  private int capacity;

  public RandomizedQueue() {
    size = 0;
    head = 0;
    tail = 0;
    capacity = 1;
    items = (Item[]) new Object[capacity];
  }

  public boolean isEmpty() { return size == 0; }

  public int size() { return size; }

  private int toIndex(int i) { 
    return (i < 0) ? (capacity - ((-i) % capacity)) % capacity : (i % capacity); 
  }

  private int nextIndex(int i) { return toIndex(i + 1); }

  private int prevIndex(int i) { return toIndex(i - 1); }

  private void resize(int cap) {
    Item[] copy = (Item[]) new Object[cap];
    int limit = capacity > cap ? cap : capacity;
    for (int i = 0; i < limit; ++i) 
      copy[i] = items[i];
    items = copy;
    capacity = cap;
  }

  public void enqueue(Item item) {
    if (item == null)
      throw new NullPointerException();
    items[tail] = item;
    ++size;
    if (size == capacity) 
      resize(capacity * 2);
    tail = nextIndex(tail);
  }

  public Item dequeue() {
    if (isEmpty())
      throw new NoSuchElementException();

    int index = toIndex(StdRandom.uniform(size) + head);

    Item ret = items[index];
    if (tail != head) tail = prevIndex(tail);
    items[index] = items[tail];
    items[tail] = null;
    --size;
    if (size > 0 && size <= capacity/4) 
      resize(capacity/2);
    return ret;
  }

  public Item sample() {
    if (isEmpty())
      throw new NoSuchElementException();

    int index = toIndex(StdRandom.uniform(size) + head);
    return items[index];
  }

  @Override
  public Iterator<Item> iterator() { return new ArrayIterator(); }

  private class ArrayIterator implements Iterator<Item> {
    private int[] index;
    private int numOfRest;

    public ArrayIterator() {
      index = new int[size];
      numOfRest = size;
      for (int i = 0; i < index.length; ++i)
        index[i] = i;
    }

    @Override
    public boolean hasNext() { return numOfRest > 0; }

    @Override
    public Item next() {
      if (!hasNext()) 
        throw new NoSuchElementException();

      int i = StdRandom.uniform(numOfRest);
      int itemIndex = index[i];
      index[i] = index[numOfRest - 1];
      --numOfRest;
      return items[toIndex(head + itemIndex)];
    }

    @Override
    public void remove() {
      throw new UnsupportedOperationException();
    }
  }
}
