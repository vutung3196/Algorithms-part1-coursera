public class ReviewMergeSort {
    public static void main(String[] args) {
        int[] arr = new int[] {1, 2, 3, 4, 5, 6, 7, 8, 8, 9, 10 , 11, 12, -1, -3};
        sort(arr);
        for (int element: arr) {
            System.out.println(element);
        }
    }

    static void sort(int[] initialArr, int[] helperArr, int low, int high) {
        if (low < high) {
            // divide into two parts
            int middle = (low + high) / 2;

            // sort first part
            sort(initialArr, helperArr, low, middle);
            // sort second part
            sort(initialArr, helperArr, middle + 1, high);

            // merge two arrays
            mergeSort(initialArr, helperArr, low, middle, high);
        }
    }

    private static void mergeSort(int[] initialArr, int[] helperArr, int low, int middle, int high) {
        for (int i = low; i <= high; i++) {
            helperArr[i] = initialArr[i];
        }
        int helperLeft = low;
        int helperRight = middle + 1;
        int current = low;
        while (helperLeft <= middle && helperRight <= high) {
            if (helperArr[helperLeft] >= helperArr[helperRight]) {
                initialArr[current] = helperArr[helperLeft];
                helperLeft++;
            } else {
                initialArr[current] = helperArr[helperRight];
                helperRight++;
            }
            current++;
        }

        // copy the rest of the left side of the array into the target one
        int remaining = middle - helperLeft;
        for (int i = 0; i <= remaining; i++) {
            initialArr[current + i] = helperArr[helperLeft + i];
        }
    }

    static void sort(int[] arr) {
        int[] helperArr = new int[arr.length];
        sort(arr, helperArr, 0, arr.length - 1);
    }
}
