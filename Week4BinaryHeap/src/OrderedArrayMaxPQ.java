//  Priority queue implementation with an ordered array.

import edu.princeton.cs.algs4.StdOut;

public class OrderedArrayMaxPQ<Key extends Comparable<Key>> {
    // elements
    private Key[] priorityQueue;
    // number of elements
    private int n;

    public OrderedArrayMaxPQ(int capacity) {
        priorityQueue = (Key[]) (new Comparable[capacity]);
        n = 0;
    }

    public void insert(Key key) {
        int i = n - 1;
        while (i >= 0 && less(key, priorityQueue[i])) {
            priorityQueue[i+1] = priorityQueue[i];
            i--;
        }
        priorityQueue[i+1] = key;
        n++;
    }

    public Key deleteMax() {
        // return priorityQueue[n-1] first and decrement n to n - 1
        return priorityQueue[--n];
    }

    private boolean less(Key i, Key j) {
        return i.compareTo(j) < 0;
    }

    private boolean isEmpty() {
        return n == 0;
    }

    public static void main(String[] args) {
        OrderedArrayMaxPQ<String> pq = new OrderedArrayMaxPQ<>(10);
        pq.insert("this");
        pq.insert("is");
        pq.insert("a");
        pq.insert("test");
        while(!pq.isEmpty()) {
            StdOut.println(pq.deleteMax());
        }
    }
}
