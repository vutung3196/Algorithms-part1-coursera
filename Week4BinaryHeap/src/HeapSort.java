import edu.princeton.cs.algs4.StdOut;

import java.util.PriorityQueue;

public class HeapSort {
    private HeapSort() {

    }

    public static void sort(Comparable[] priorityQueue) {
        int n = priorityQueue.length;

        // heapify phase
        for (int k = n/2; k >= 1; k--) {
            sink(priorityQueue, k, n);
        }

        // sort down phase
        int k = n;
        while (k > 1) {
            // pass k and decrement k later
            exch(priorityQueue, 1, k);
            k-=1;
            sink(priorityQueue, 1, k);
        }
    }

    private static void sink(Comparable[] pq, int k, int n) {
        while (2*k <= n) {
            int j = 2*k;
            // 2k < 2k+1
            if (j < n && less(pq, j, j+1)) {
                j++;
            }
            if (!less(pq, k, j)) {
                break;
            }
            exch(pq, k, j);
            k = j;
        }
    }

    private static boolean less(Comparable[] pq, int i, int j) {
        return pq[i-1].compareTo(pq[j-1]) < 0;
    }

    private static void exch(Object[] pq, int k, int j) {
        Object swap = pq[k-1];
        pq[k-1] = pq[j-1];
        pq[j-1] = swap;
    }

    private static void show(Comparable[] a) {
        for (int i = 0; i < a.length; i++) {
            StdOut.println(a[i]);
        }
    }

    public static void main(String[] args) {
        String[] a = new String[] {"3", "1", "2"};
        HeapSort.sort(a);
        show(a);
    }
}
