public class ArrayOperations {
    /**
     * Delete the value at the given position in the argument array, shifting
     * all the subsequent elements down, and storing a 0 as the last element of
     * the array.
     */
    public static void delete(int[] values, int pos) {
        if (pos < 0 || pos >= values.length) {
            return;
        }
        for(int i = pos; i < values.length - 1; ++i){
            values[i] = values[i + 1];
        }
        values[values.length - 1] = 0;
    }

    /**
     * Insert newInt at the given position in the argument array, shifting all
     * the subsequent elements up to make room for it. The last element in the
     * argument array is lost.
     */
    public static void insert(int[] values, int pos, int newInt) {
        if (pos < 0 || pos >= values.length) {
            return;
        }
        for(int i = values.length - 1; i > pos; --i){
            values[i] = values[i - 1];
        }
        values[pos] = newInt;
    }

    /** 
     * Returns a new array consisting of the elements of A followed by the
     *  the elements of B. 
     */
    public static int[] catenate(int[] A, int[] B) {
        int[] result = new int[A.length + B.length];
        for(int i = 0 ; i < A.length; ++i){
            result[i] = A[i];
        }
        for(int i = 0; i < B.length; ++i){
            result[A.length + i] = B[i];
        }
        return result;
    }

    /** 
     * Returns the array of arrays formed by breaking up A into
     *  maximal ascending lists, without reordering.
     */
    public static int[][] naturalRuns(int[] A) {
        int[][] result = new int[A.length][];
        int count = 0;          // count how many groups in total
        int endIndex = -1;      // end index of previous group
        for(int i = 1; i < A.length; ++i){
            if(A[i] <= A[i - 1]){
                int[] group = new int[i - 1 - endIndex];
                int countGroup = 0;     // count how many elements in this group
                for(int j = endIndex + 1; j < i; ++j){
                    group[countGroup++] = A[j];
                }
                result[count++] = group;    // add new group to result array
                endIndex = i - 1;           // update endIndex
            }
        }
        // Make last few elements into a group
        int[] group = new int[A.length - 1 - endIndex];
        int countGroup = 0;     // count how many elements in this group
        for(int j = endIndex + 1; j < A.length; ++j){
            group[countGroup++] = A[j];
        }
        result[count++] = group;    // add new group to result array

        // Clear null elements in result array
        int[][] resultWithoutNull = new int[count][];
        for(int i = 0; i < count; ++i){
            resultWithoutNull[i] = result[i];
        }
        return resultWithoutNull;
    }

    /*
    * Returns the subarray of A consisting of the LEN items starting
    * at index K.
    */
    public static int[] subarray(int[] A, int k, int len) {
        int[] result = new int[len];
        System.arraycopy(A, k, result, 0, len);
        return result;
    }

}