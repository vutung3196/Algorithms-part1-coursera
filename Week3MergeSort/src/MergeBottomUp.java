import edu.princeton.cs.algs4.StdOut;

public class MergeBottomUp {
    public static void main(String[] args) {
        Comparable[] arr = new Comparable[] {1, 2, 3, 4, -1, -2, -3, -4, -5};
        sort(arr);
        show(arr);
    }

    public static void sort(Comparable[] arr) {
        int n = arr.length;
        Comparable[] helperArray = new Comparable[n];
        for (int len = 1; len < n; len*=2) {
            for (int low = 0; low < n - len; low += len + len) {
                int mid = low + len - 1;
                int high = Math.min(low + len + len - 1, n - 1);
                fasterMerge(arr, helperArray, low, mid, high);
            }
        }

        assert isSorted(arr);
    }

    private static void mergeSecondImplementation(Comparable[] array, Comparable[] helper, int low, int middle, int high) {
        for (int i = low; i <= high; i++) {
            helper[i] = array[i];
        }
        int helperLeft = low;
        int helperRight = middle + 1;
        int current = low;
        while (helperLeft <= middle && helperRight <= high) {
            if (less(helper[helperLeft], helper[helperRight])) {
                array[current] = helper[helperLeft];
                helperLeft++;
            } else {
                array[current] = helper[helperRight];
                helperRight++;
            }
            current++;
        }

        // copy the rest of the left side of the array into the target one
        int remaining = middle - helperLeft;
        for (int i = 0; i <= remaining; i++) {
            array[current + i] = helper[helperLeft + i];
        }
    }

    private static void fasterMerge(Comparable[] arr, Comparable[] helperArr, int low, int mid, int high) {
        for (int i = low; i <= mid; i++) {
            helperArr[i] = arr[i];
        }
        for (int j = mid+1; j <= high; j++) {
            helperArr[j] = arr[j];
        }
        int i = low;
        int j = high;
        for (int k = low; k <= high; k++) {
            if (less(helperArr[j], helperArr[i])) {
                arr[k] = helperArr[j--];
            } else {
                arr[k] = helperArr[i++];
            }
        }
    }

    private static void merge(Comparable[] arr, Comparable[] helperArray, int low, int mid, int high) {
        for (int k = low; k <= high; k++) {
            helperArray[k] = arr[k];
        }
        int i = low, j = mid + 1;
        for (int k = 0; k <= high; k++) {
            if (i > mid) {
                // copying
                arr[k] = helperArray[j];
                j++;
            } else if (j > high) {
                arr[k] = helperArray[i];
                i++;
            } else if (less(helperArray[j], helperArray[i])) {
                arr[k] = helperArray[j++];
            } else {
                arr[k] = helperArray[i++];
            }
        }
    }

    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    private static boolean isSorted(Comparable[] arr) {
        for (int i = 0; i < arr.length; i++) {
            if (!less(arr[i], arr[i+1])) return false;
        }
        return true;
    }

    private static void show(Comparable[] arr) {
        for (int i = 0; i < arr.length; i++) {
            StdOut.println(arr[i]);
        }
    }
}
