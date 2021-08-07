import java.util.Arrays;

public class DistributionSorts {

    /* Destructively sorts ARR using counting sort. Assumes that ARR contains
       only 0, 1, ..., 9. */
    public static void countingSort(int[] arr) {
        int length = arr.length;
        int[] buckets = new int[10];  // count number of each value
        int[] sorted = new int[length];

        for (int val : arr) {
            buckets[val]++;
        }

        // put buckets into arr
        // Notice: one can also use cumulate-and-move methods, as described in spec
        // but personally I like the method below. Cumulate-and-move methods
        // can be found in below LSD radix sort.
        int index = 0;
        for(int i = 0; i < 10; ++i) {
            while(buckets[i]-- > 0) {
                arr[index++] = i;
            }
        }
    }

    /* Destructively sorts ARR using LSD radix sort. */
    public static void lsdRadixSort(int[] arr) {
        int maxDigit = mostDigitsIn(arr);
        for (int d = 0; d < maxDigit; d++) {
            countingSortOnDigit(arr, d);
        }
    }

    /* A helper method for radix sort. Modifies ARR to be sorted according to
       DIGIT-th digit. When DIGIT is equal to 0, sort the numbers by the
       rightmost digit of each number. */
    private static void countingSortOnDigit(int[] arr, int digit) {
        int length = arr.length;
        int[] count = new int[10];
        int[] sorted = new int[length];

        int divisor = (int) Math.pow(10, digit);
        for(int val : arr) {
            // get DIGIT-th digit
            int d = (val / divisor) % 10;
            count[d]++;
        }
        // compute cumulative times
        for(int i = 1; i < count.length; ++i) {
            count[i] += count[i - 1];
        }
        // move data according to count
        for(int i = length - 1; i >= 0; --i) {
            // arr[i] should be placed at position count[d(arr[i])] - 1
            // and remember to decrease count[] each time
            int val = arr[i];
            int d = (val / divisor) % 10;
            sorted[--count[d]] = arr[i];
        }
        // copy sorted array to original array
        for(int i = 0; i < length; ++i) {
            arr[i] = sorted[i];
        }
    }

    /* Destructively sorts ARR using LSD radix sort, but in binary. */
    public static void lsdRadixSortBinary(int[] arr) {
        final int BITS = 32;   // each int is at most 32 bits
        for (int d = 0; d < BITS; d++) {
            countingSortOnDigitBin(arr, d);
        }
    }

    /* A helper method for radix sort. But in binary */
    private static void countingSortOnDigitBin(int[] arr, int digit) {
        int length = arr.length;
        int[] count = new int[2];   // each bit should be either 0 or 1
        int[] sorted = new int[length];

        for(int val : arr) {
            int d = (val >> digit) & 1;
            count[d]++;
        }
        // cumulate
        count[1] += count[0];
        // move
        for(int i = length - 1; i >= 0; --i) {
            int val = arr[i];
            int d = (val >> digit) & 1;
            sorted[--count[d]] = val;
        }
        // copy sorted array to original array
        for(int i = 0; i < length; ++i) {
            arr[i] = sorted[i];
        }
    }

    /* Destructively sorts ARR using LSD radix sort, but in hexadecimal. */
    public static void lsdRadixSortHex(int[] arr) {
        final int HEX_BIT = 8;   // each int is at most 8 hex bits
        for (int d = 0; d < HEX_BIT; d++) {
            countingSortOnDigitHex(arr, d);
        }
    }

    /* A helper method for radix sort. But in hexadecimal */
    private static void countingSortOnDigitHex(int[] arr, int digit) {
        int length = arr.length;
        final int MAX_HEX = (1 << 4) - 1;   // 0xF
        final int BITS_PER_HEX = 4;         // remove 4 * digit bit each time
        int[] count = new int[MAX_HEX + 1];
        int[] sorted = new int[length];

        for(int val : arr) {
            int d = (val >> (digit * BITS_PER_HEX)) & MAX_HEX;
            count[d]++;
        }
        // cumulate
        for(int i = 1; i <= MAX_HEX; ++i) {
            count[i] += count[i - 1];
        }
        // move
        for(int i = length - 1; i >= 0; --i) {
            int val = arr[i];
            int d = (val >> (digit * BITS_PER_HEX)) & MAX_HEX;
            sorted[--count[d]] = val;
        }
        // copy sorted array to original array
        for(int i = 0; i < length; ++i) {
            arr[i] = sorted[i];
        }
    }

    /* Returns the largest number of digits that any integer in ARR has. */
    private static int mostDigitsIn(int[] arr) {
        int maxDigitsSoFar = 0;
        for (int num : arr) {
            int numDigits = (int) (Math.log10(num) + 1);
            if (numDigits > maxDigitsSoFar) {
                maxDigitsSoFar = numDigits;
            }
        }
        return maxDigitsSoFar;
    }

    /* Returns a random integer between 0 and 9999. */
    private static int randomInt() {
        return (int) (10000 * Math.random());
    }

    /* Returns a random integer between 0 and 9. */
    private static int randomDigit() {
        return (int) (10 * Math.random());
    }

    private static void runCountingSort(int len) {
        int[] arr1 = new int[len];
        for (int i = 0; i < arr1.length; i++) {
            arr1[i] = randomDigit();
        }
        System.out.println("\n===== Counting Sort =====");
        System.out.println("Original array: " + Arrays.toString(arr1));
        countingSort(arr1);
        if (arr1 != null) {
            System.out.println("Should be sorted: " + Arrays.toString(arr1));
        }
    }

    private static void runLSDRadixSort(int len) {
        int[] arr2 = new int[len];
        for (int i = 0; i < arr2.length; i++) {
            arr2[i] = randomInt();
        }
        System.out.println("\n===== LSD Radix Sort =====");
        System.out.println("Original array: " + Arrays.toString(arr2));
        lsdRadixSort(arr2);
        System.out.println("Should be sorted: " + Arrays.toString(arr2));

    }

    private static void runLSDRadixSortBinary(int len) {
        int[] arr2 = new int[len];
        for (int i = 0; i < arr2.length; i++) {
            arr2[i] = randomInt();
        }
        System.out.println("\n===== LSD Radix Sort (Binary) =====");
        System.out.println("Original array: " + Arrays.toString(arr2));
        lsdRadixSortBinary(arr2);
        System.out.println("Should be sorted: " + Arrays.toString(arr2));

    }

    private static void runLSDRadixSortHex(int len) {
        int[] arr2 = new int[len];
        for (int i = 0; i < arr2.length; i++) {
            arr2[i] = randomInt();
        }
        System.out.println("\n===== LSD Radix Sort (Hexadecimal) =====");
        System.out.println("Original array: " + Arrays.toString(arr2));
        lsdRadixSortHex(arr2);
        System.out.println("Should be sorted: " + Arrays.toString(arr2));

    }

    private static void pressureTest() {
        System.out.println("\n===== Pressure Test =====");
        int length = 5000000;
        int[] arr = new int[length];

        System.out.println("\nUse LSD Radix Sort (Decimal) to sort 5 million numbers");
        for(int i = 0; i < arr.length; ++i) arr[i] = randomInt();
        // get time diff
        long start = System.currentTimeMillis();
        lsdRadixSort(arr);
        long end = System.currentTimeMillis();
        System.out.println("Time: " + (end - start) + " ms");

        System.out.println("\nUse LSD Radix Sort (Binary) to sort 5 million numbers");
        for(int i = 0; i < arr.length; ++i) arr[i] = randomInt();
        // get time diff
        start = System.currentTimeMillis();
        lsdRadixSortBinary(arr);
        end = System.currentTimeMillis();
        System.out.println("Time: " + (end - start) + " ms");

        System.out.println("\nUse LSD Radix Sort (Hexadecimal) to sort 5 million numbers");
        for(int i = 0; i < arr.length; ++i) arr[i] = randomInt();
        // get time diff
        start = System.currentTimeMillis();
        lsdRadixSortHex(arr);
        end = System.currentTimeMillis();
        System.out.println("Time: " + (end - start) + " ms");

        System.out.println("\nOh! Hexadecimal is much faster!!");
    }

    public static void main(String[] args) {
        runCountingSort(20);
        runLSDRadixSort(3);
        runLSDRadixSort(30);
        runLSDRadixSortBinary(30);
        runLSDRadixSortHex(30);
        pressureTest();
    }
}