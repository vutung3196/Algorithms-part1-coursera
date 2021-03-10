public class QuickSort {
    public static void main(String[] args) {
        int[] arr = new int[] {1, 2, 3, -1, -2, -3, 9, 8};
        quickSort(arr, 0, arr.length - 1);
        for (int element: arr) {
            System.out.println(element);
        }
    }

    static void quickSort(int[] arr, int left, int right) {
        int index = partition(arr, left, right);
        // sort left half
        if (left < index - 1) {
            quickSort(arr, left, index - 1);
        }
        if (right > index) {
            quickSort(arr, index, right);
        }
    }

    static int partition(int[] arr, int left, int right) {
        int pivotElement = arr[(left + right) / 2]; // pick pivot point
        while (left <= right) {
            // Find element of the left that should be on the right
            while (arr[left] < pivotElement) {
                left++;
            }
            while (arr[right] > pivotElement) {
                right--;
            }
            if (left <= right) {
                int temp = arr[left];
                arr[left] = arr[right];
                arr[right] = temp;
                left++;
                right--;
            }
        }
        return left;
    }
}
