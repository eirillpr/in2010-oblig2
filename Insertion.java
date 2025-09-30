class Insertion {
    int[] numbers;
    int comp = 0;
    int swaps = 0;
    long time;

    Insertion(int[] array) {
        this.numbers = array;
        sort(numbers);
    }

    public void sort(int[] array) { //sorterer med insertionsort
        long t = System.nanoTime();
        for (int i = 1; i <= array.length - 1; i ++) {
            int j = i;
            while (j > 0 && less(j-1, j)) { //true hvis A[j-1] > A[j]
                swap(j, j-1);
                j--;
            } 
        }
        this.time = (System.nanoTime()-t)/1000;
    }

    boolean less(int a, int b) {
        comp++;
        return numbers[a] > numbers[b];
    }

    void swap(int a, int b) {
        swaps++;
        int x = numbers[a];
        numbers[a] = numbers[b];
        numbers[b] = x;
    }

    public int[] hentSortert() {return numbers;}
    public int swaps() {return swaps;}
    public int comp() {return comp;}
    public long tid() {return time;}
    public void nullstill() {comp = 0; swaps = 0; return;}


}