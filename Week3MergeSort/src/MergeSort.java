public class MergeSort {
    public static void main(String[] args) {
        int[] a = new int[] {3, 2, 1, 4, 4, 5, 6, 7, 8};
        int i = 0;
        sort(a);
        for (int x: a) {
            System.out.println(x);
        }
    }


    // first implementation
    private static void mergeFirstImplement(int[] a, int[] aux, int low, int mid, int high) {
        // insertion sort for small sub arrays
        // copy array to aux array and then merge in array a
        for(int k = low; k <= high; k++) {
            aux[k] = a[k];
        }
        int i = low;
        int j = mid + 1;
        for(int k = low; k <= high; k++) {
            if (i > mid) {
                if (i > mid) {
                    a[k] = aux[j];
                    j++;
                } else if (j > high) {
                    a[k] = aux[i];
                    i++;
                } else if (aux[j] < aux[i]) {
                    a[k] = aux[j];
                    j++;
                } else {
                    a[k] = aux[i];
                    i++;
                }
            }
        }
    }

    private static void mergeSecondImplementation(int[] array, int[] helper, int low, int middle, int high) {
        for (int i = low; i <= high; i++) {
            helper[i] = array[i];
        }
        int helperLeft = low;
        int helperRight = middle + 1;
        int current = low;
        while (helperLeft <= middle && helperRight <= high) {
            if (helper[helperLeft] >= helper[helperRight]) {
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

    private static void sort(int a[], int[] aux, int low, int high) {
        if (low < high) {
            int mid = (low + high) / 2;
            sort(a, aux, low, mid);
            sort(a, aux, mid + 1, high);
            mergeSecondImplementation(a, aux, low, mid, high);
        }
    }

    private static void sort(int a[]) {
        int[] aux = new int[a.length];
        sort(a, aux, 0, a.length - 1);
    }
}

