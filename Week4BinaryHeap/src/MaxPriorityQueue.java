import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class MaxPriorityQueue<Key> implements Iterable<Key> {
    private Key[] priorityQueue;            // store items at indices
    private int n;                         // number of items in the priority queue
    private Comparator<Key> comparator;   // optional comparator

    public MaxPriorityQueue(int initCapacity) {
        // init a priority queue
        priorityQueue = (Key[]) new Object[initCapacity+1];
        n = 0;
    }

    public MaxPriorityQueue() {
        // initialize a max priority queue
        this(1);
    }

    public MaxPriorityQueue(int initCapacity, Comparator<Key> comparator) {
        this.comparator = comparator;
        priorityQueue = (Key[]) new Object[initCapacity+1];
        n = 0;
    }

    public MaxPriorityQueue(Comparator<Key> comparator) {
        this(1, comparator);
    }

    public boolean isEmpty() {
        return n == 0;
    }

    public int size() {
        return n;
    }

    public Key max() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        // start from index 1
        return priorityQueue[1];
    }

    public void insert(Key x) {
        if (n == priorityQueue.length - 1) {
            resize(2 * priorityQueue.length);
        }
        priorityQueue[++n] = x;
        swim(n);
        assert isMaxHeap();
    }

    public Key delMax() {
        if (isEmpty()) {
            throw new NoSuchElementException("Priority queue underflow");
        }
        Key max = priorityQueue[1];
        exchange(1, n--);
        sink(1);
        priorityQueue[n+1] = null;
        if ((n > 0) && (n == (priorityQueue.length - 1) / 4)) resize(priorityQueue.length / 2);
        assert isMaxHeap();
        return max;
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
        assert capacity > n;
        Key[] temp = (Key[]) new Object[capacity];
        for (int i = 1; i <= n; i++) {
            temp[i] = priorityQueue[i];
        }
        priorityQueue = temp;
    }

    // important functions: swim and sink
    private void swim(int k) {
        while (k > 1 && less(k/2, k)) {
            exchange(k, k/2);
            k = k/2;
        }
    }

    private void sink(int k) {
        while (2 * k <= n) {
            int j = 2 * k;
            if (j < n && less(j, j+1)) {
                j++;
            }
            if (!less(k, j)) {
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
        Key swap = priorityQueue[k];
        priorityQueue[k] = priorityQueue[i];
        priorityQueue[i] = swap;
    }

    private boolean isMaxHeap() {
        if (priorityQueue[0] == null) {
            return false;
        }
        for (int i = 1; i <= n; i++) {
            if (priorityQueue[i] == null) {
                return false;
            }
        }
        for (int i = n+1; i < priorityQueue.length; i++) {
            if (priorityQueue[i] != null) {
                return false;
            }
        }
        return isMaxHeapOrdered(1);
    }

    private boolean isMaxHeapOrdered(int k) {
        if (k > n) {
            return true;
        }
        int left = 2*k;
        int right = 2*k + 1;
        if (left <= n && less(k, left)) {
            return false;
        }
        if (right <= n && less(k, right)) {
            return false;
        }
        return isMaxHeapOrdered(left) && isMaxHeapOrdered(right);
    }
}
