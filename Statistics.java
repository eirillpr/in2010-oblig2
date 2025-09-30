public class Statistics {
    long comparisons;
    long swaps;

    boolean less(int a, int b) {
        comparisons++;
        return a < b;
    }

    void swap(int[] array, int i, int j) {
        swaps++;
        int x = array[i];
        array[i] = array[j];
        array[j] = x;
    }
    
}
