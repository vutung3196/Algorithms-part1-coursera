import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class MinPriorityQueue<Key> implements Iterable<Key> {
    private Key[] priorityQueue;
    private int n;                         // number of items in the priority queue
    private Comparator<Key> comparator;   // optional comparator

    public MinPriorityQueue(int initCapacity) {
        priorityQueue = (Key[]) new Object[initCapacity+1];
        n = 0;
    }

    public MinPriorityQueue() {
        // initialize a max priority queue
        this(1);
    }

    public MinPriorityQueue(int initCapacity, Comparator<Key> comparator) {
        priorityQueue = (Key[]) new Object[initCapacity+1];
        this.comparator = comparator;
        n = 0;
    }

    public MinPriorityQueue(Key[] keys) {
        n = keys.length;
        priorityQueue = (Key[]) new Object[keys.length + 1];
        for (int i = 0; i < n; i++) {
            // starting from index 1;
            priorityQueue[i + 1] = keys[i];
        }
        // must use k = n/2 = 2 * k <= n;
        for (int k = n / 2; k >= 1; k--) {
            sink(k);
        }
        assert isMinHeap();
    }

    public MinPriorityQueue(Comparator<Key> comparator) {
        this(1, comparator);
    }

    public boolean isEmpty() {
        return n == 0;
    }

    public int size() {
        return n;
    }

    public Key min() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        } else {
            return priorityQueue[1];
        }
    }

    public void insert(Key x) {
        if (n == priorityQueue.length - 1) {
            resize(n*2);
        }
        priorityQueue[++n] = x;
        swim(n);
        assert isMinHeap();
    }

    public Key delMin() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Key min = priorityQueue[1];
        exchange(1, n--);
        sink(1);
        priorityQueue[n+1] = min;
        if (n >= 0 && n == (priorityQueue.length - 1)/4) {
            resize(priorityQueue.length / 2);
        }
        return min;
    }

    @Override
    public Iterator<Key> iterator() {
        return new HeapIterator();
    }

    private class HeapIterator implements Iterator<Key> {

        // create a new pq
        private MaxPriorityQueue<Key> copy;

        // add all items to copy of heap
        // takes linear time since already in heap order so no keys move
        public HeapIterator() {
            if (comparator == null) copy = new MaxPriorityQueue<Key>(size());
            else                    copy = new MaxPriorityQueue<Key>(size(), comparator);
            for (int i = 1; i <= n; i++)
                copy.insert(priorityQueue[i]);
        }

        public boolean hasNext()  { return !copy.isEmpty();                     }
        public void remove()      { throw new UnsupportedOperationException();  }

        public Key next() {
            if (!hasNext()) throw new NoSuchElementException();
            return copy.delMax();
        }
    }

    private void resize(int capacity) {
        Key[] temp = (Key[]) new Object[capacity];
        for (int i = 0; i < n; i++) {
            temp[i] = priorityQueue[i];
        }
        priorityQueue = temp;
    }

    // important functions: swim and sink
    private void swim(int k) {
        while (k > 1 && less(k, k/2)) {
            exchange(k, k/2);
            k = k / 2;
        }
    }

    private void sink(int k) {
        while (2 * k <= n) {
            // calculate the child node j =(2k or 2k + 1)
            // if arr[2k] < arr[2k+1] => increment k
            // if parent[k] < parent[j] => break;
            // exchange k with j
            // k = j
            int j = 2 * k;
            if (j < n && !less(j, j+1)) {
                j = j + 1;
            }
            if (less(k, j)) {
                break;
            }
            exchange(k, j);
            k = j;
        }
    }

    private boolean less(int i, int j) {
        if (comparator == null) {
            return ((Comparable<Key>) priorityQueue[i]).compareTo(priorityQueue[j]) < 0;
        } else {
            return comparator.compare(priorityQueue[i], priorityQueue[j]) < 0;
        }
    }

    private void exchange(int k, int i) {
        Key temp = priorityQueue[k];
        priorityQueue[k] = priorityQueue[i];
        priorityQueue[i] = temp;
    }

    private boolean isMinHeap() {
        if (priorityQueue[0] == null) {
            return false;
        }
        for (int i = 0; i <= n; i++) {
            if (priorityQueue[i] == null) {
                return false;
            }
        }
        for (int i = n + 1; i < priorityQueue.length; i++) {
            if (priorityQueue[i] != null) {
                return false;
            }
        }
        return isMinHeapOrdered(1);
    }

    private boolean isMinHeapOrdered(int k) {
        int left = 2 * k;
        int right = 2 * k + 1;
        // parent's value is greater than child's left value
        if (left <= n && !less(k, left)) {
            return false;
        }

        // parent's value is greater than child's left value
        if (right <= n && !less(k, right)) {
            return false;
        }
        return isMinHeapOrdered(left) && isMinHeapOrdered(right);
    }

    public static void main(String[] args) {
        Integer[] arr = new Integer[] {4, 5, 3, 6, 8, 9, 10};
        MinPriorityQueue<Integer> pq = new MinPriorityQueue<>(arr);
    }
}
