class InsertionSort {
    int[] numbers;
    int comparisons = 0;
    int swaps = 0;
    long time;

    InsertionSort(int[] array) {
        this.numbers = array;
        sort(numbers);
    }

    public void sort(int[] array) { //sorterer med insertionsort
        long t = System.nanoTime();
        for (int i = 1; i <= array.length - 1; i ++) {
            int j = i;
            while (j > 0 && less(j-1, j)) {
                swap(j, j-1);
                j--;
            } 
        }
        this.time = (System.nanoTime()-t)/1000;
    }

    boolean less(int a, int b) {
        comparisons++;
        return numbers[a] < numbers[b];
    }

    void swap(int a, int b) {
        swaps++;
        int x = numbers[a];
        numbers[a] = numbers[b];
        numbers[b] = x;
    }


}