import java.util.Comparator;
import java.util.LinkedList;
import java.util.Random;

/**
 * Your implementation of various sorting algorithms.
 *
 * @author Alan Chiang
 * @version 1.0
 */
public class Sorting {

    /**
     * Implement cocktail sort.
     *
     * It should be:
     *  in-place
     *  stable
     *
     * Have a worst case running time of:
     *  O(n^2)
     *
     * And a best case running time of:
     *  O(n)
     *
     * Any duplicates in the array should be in the same relative position after
     * sorting as they were before sorting. (stable).
     *
     * See the PDF for more info on this sort.
     *
     * @throws IllegalArgumentException if the array or comparator is null
     * @param <T> data type to sort
     * @param arr the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     */
    public static <T> void cocktailSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException(
                    "Cannot sort with null data or null comparator");
        }
        int start = 0;
        int end = arr.length - 1;
        boolean done = true;
        while (done) {
            done = false;
            for (int i = start; i < end; i++) {
                if (comparator.compare(arr[i], arr[i + 1]) > 0) {
                    T temp = arr[i];
                    arr[i] = arr[i + 1];
                    arr[i + 1] = temp;
                    done = true;
                }
            }
            end--;
            //break if sorted
            for (int j = end; j > start; j--) {
                if (comparator.compare(arr[j], arr[j - 1]) < 0) {
                    T temp = arr[j];
                    arr[j] = arr[j - 1];
                    arr[j - 1] = temp;
                    done = true;
                }
            }
            start++;
        }
    }

    /**
     * Implement insertion sort.
     *
     * It should be:
     *  in-place
     *  stable
     *
     * Have a worst case running time of:
     *  O(n^2)
     *
     * And a best case running time of:
     *  O(n)
     *
     * Any duplicates in the array should be in the same relative position after
     * sorting as they were before sorting. (stable).
     *
     * See the PDF for more info on this sort.
     *
     * @throws IllegalArgumentException if the array or comparator is null
     * @param <T> data type to sort
     * @param arr the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     */
    public static <T> void insertionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException(
                    "Cannot sort with null data or null comparator");
        }
        for (int i = 1; i < arr.length; i++) {
            int j = i - 1;
            for (; j > -1 && comparator.compare(arr[j + 1], arr[j]) < 0; j--) {
                T temp = arr[j + 1];
                arr[j + 1] = arr[j];
                arr[j] = temp;
            }
        }
    }

    /**
     * Implement selection sort.
     *
     * It should be:
     *  in-place
     *
     * Have a worst case running time of:
     *  O(n^2)
     *
     * And a best case running time of:
     *  O(n^2)
     *
     * Note that there may be duplicates in the array, but they may not
     * necessarily stay in the same relative order.
     *
     * @throws IllegalArgumentException if the array or comparator is null
     * @param <T> data type to sort
     * @param arr the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     */
    public static <T> void selectionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException(
                    "Cannot sort with null data or null comparator");
        }
        for (int i = 0; i < arr.length - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (comparator.compare(arr[j], arr[minIndex]) < 0) {
                    minIndex = j;
                }
            }
            T temp = arr[i];
            arr[i] = arr[minIndex];
            arr[minIndex] = temp;
        }
    }

    /**
     * Implement quick sort.
     *
     * Use the provided random object to select your pivots.
     * For example if you need a pivot between a (inclusive)
     * and b (exclusive) where b > a, use the following code:
     *
     * int pivotIndex = r.nextInt(b - a) + a;
     *
     * It should be:
     *  in-place
     *
     * Have a worst case running time of:
     *  O(n^2)
     *
     * And a best case running time of:
     *  O(n log n)
     *
     * Note that there may be duplicates in the array.
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not get full
     * credit if you do not use the one we have taught you!
     *
     * @throws IllegalArgumentException if the array or comparator or rand is
     * null
     * @param <T> data type to sort
     * @param arr the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @param rand the Random object used to select pivots
     */
    public static <T> void quickSort(T[] arr, Comparator<T> comparator,
                                     Random rand) {
        if (arr == null || comparator == null || rand == null) {
            throw new IllegalArgumentException(
                    "Cannot sort with null data, random, or comparator");
        }
        quickHelp(arr, comparator, rand, 0, arr.length);
    }

    /**
     * Private helper method for quickSort()
     * @param <T> data type to sort
     * @param arr the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @param rand the Random object used to select pivots
     * @param start the start index of the subarray
     * @param end the end index of the subarray
     */
    private static <T> void quickHelp(T[] arr, Comparator<T> comparator,
                                      Random rand, int start, int end) {
        if (end - start <= 1) {
            return;
        }
        int pivot = rand.nextInt(end - start) + start;
        int j = start + 1;
        int k = end - 1;
        boolean swap = true;
        T temp = arr[start];
        arr[start] = arr[pivot];
        arr[pivot] = temp;
        while (swap) {
            while (j < end && comparator.compare(arr[start], arr[j]) >= 0) { //should make j <= k?
                j++;
            }
            while (k > start && comparator.compare(arr[start], arr[k]) <= 0) { //should alter k >= j?
                k--;
            }
            if (j > k) {
                temp = arr[start];
                arr[start] = arr[k];
                arr[k] = temp;
                swap = false;
            } else {
                temp = arr[j];
                arr[j] = arr[k];
                arr[k] = temp;
                swap = true;
            }
        }
        quickHelp(arr, comparator, rand, start, k);
        quickHelp(arr, comparator, rand, k + 1, end);
    }

    /**
     * Implement merge sort.
     *
     * It should be:
     *  stable
     *
     * Have a worst case running time of:
     *  O(n log n)
     *
     * And a best case running time of:
     *  O(n log n)
     *
     * You can create more arrays to run mergesort, but at the end,
     * everything should be merged back into the original T[]
     * which was passed in.
     *
     * Any duplicates in the array should be in the same relative position after
     * sorting as they were before sorting.
     *
     * @throws IllegalArgumentException if the array or comparator is null
     * @param <T> data type to sort
     * @param arr the array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     */
    public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException(
                    "Cannot sort with null data or null comparator");
        }
        if (arr.length == 1) { //also if arr.length == 0
            return;
        }
        T[] leftHalf = (T[]) new Object[arr.length / 2];
        T[] rightHalf = (T[]) new Object[arr.length - arr.length / 2];
        for (int i = 0; i < arr.length; i++) {
            if (i < arr.length / 2) {
                leftHalf[i] = arr[i];
            } else {
                rightHalf[i - leftHalf.length] = arr[i];
            }
        }
        mergeSort(leftHalf, comparator);
        mergeSort(rightHalf, comparator);
        merge(leftHalf, rightHalf, comparator, arr);
    }

    /**
     * Private helper method to merge parts back together
     * @param firstHalf the left half of the to-be-merged array
     * @param secondHalf the right half of the to-be-merged array
     * @param comparator the comparator to sort objects
     * @param result the merged array
     * @param <T> the datatype to sort
     */
    private static <T> void merge(
        T[] firstHalf, T[] secondHalf, Comparator<T> comparator, T[] result) {
        int i = 0;
        int j = 0;
        int k = 0;
        while (i < firstHalf.length && j < secondHalf.length) {
            if (comparator.compare(firstHalf[i], secondHalf[j]) < 0) { //should be combined with last block to <=
                result[k] = firstHalf[i];
                i++;
                k++;
            } else if (comparator.compare(secondHalf[j], firstHalf[i]) < 0) {
                result[k] = secondHalf[j];
                j++;
                k++;
            } else if (comparator.compare(secondHalf[j], firstHalf[i]) == 0) { //turn these three into if-else
                result[k] = firstHalf[i];
                i++;
                k++;
            }
            if (i >= firstHalf.length) {
                for (; k < result.length; k++) {
                    result[k] = secondHalf[j];
                    j++;
                }
            }
            if (j >= secondHalf.length) {
                for (; k < result.length; k++) {
                    result[k] = firstHalf[i];
                    i++;
                }
            }
        }
    }

    /**
     * Implement LSD (least significant digit) radix sort.
     *
     * Remember you CANNOT convert the ints to strings at any point in your
     * code!
     *
     * It should be:
     *  stable
     *
     * Have a worst case running time of:
     *  O(kn)
     *
     * And a best case running time of:
     *  O(kn)
     *
     * Any duplicates in the array should be in the same relative position after
     * sorting as they were before sorting. (stable)
     *
     * Do NOT use {@code Math.pow()} in your sort. Instead, if you need to, use
     * the provided {@code pow()} method below.
     *
     * You may use {@code java.util.ArrayList} or {@code java.util.LinkedList}
     * if you wish, but it may only be used inside radix sort and any radix sort
     * helpers. Do NOT use these classes with other sorts.
     *
     * @throws IllegalArgumentException if the array is null
     * @param arr the array to be sorted
     * @return the sorted array
     */
    public static int[] lsdRadixSort(int[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException(
                    "Cannot sort with null data or null comparator");
        }
        int place = 1;
        boolean more = true;
        LinkedList<Integer>[] buckets = new LinkedList[19];
        for (int i = 0; i < 19; i++) {
            buckets[i] = new LinkedList<>();
        }
        while (more) {
            more = false;
            for (int current : arr) {
                int test = current / place;
                int bucket = ((current / place) % 10);
                buckets[bucket + 9].add(current);
                if (test / 10 != 0) {
                    more = true;
                }
            }
            place *= 10;
            int i = 0;
            for (LinkedList<Integer> bucket : buckets) {
                while (!(bucket.isEmpty())) {
                    arr[i] = bucket.remove();
                    i++;
                }
            }
        }
        return arr;
    }

    /**
     * Calculate the result of a number raised to a power. Use this method in
     * your radix sorts instead of {@code Math.pow()}.
     *
     * DO NOT MODIFY THIS METHOD.
     *
     * @throws IllegalArgumentException if both {@code base} and {@code exp} are
     * 0
     * @throws IllegalArgumentException if {@code exp} is negative
     * @param base base of the number
     * @param exp power to raise the base to. Must be 0 or greater.
     * @return result of the base raised to that power
     */
    private static int pow(int base, int exp) {
        if (exp < 0) {
            throw new IllegalArgumentException("Exponent cannot be negative.");
        } else if (base == 0 && exp == 0) {
            throw new IllegalArgumentException(
                    "Both base and exponent cannot be 0.");
        } else if (exp == 0) {
            return 1;
        } else if (exp == 1) {
            return base;
        }
        int halfPow = pow(base, exp / 2);
        if (exp % 2 == 0) {
            return halfPow * halfPow;
        } else {
            return halfPow * pow(base, (exp / 2) + 1);
        }
    }
}
