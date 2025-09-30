class insertion {
    int[] A;
    int comp = 0;
    int swaps = 0;
    long time;

    insertion(int[] A) {
        this.A = A;
    }

    public void sort() { //sorterer med insertionsort
        long t = System.nanoTime();
        for (int i = 1; i <= A.length - 1; i ++) {
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
        return A[a] > A[b];
    }

    void swap(int a, int b) {
        swaps++;
        int x = A[a];
        A[a] = A[b];
        A[b] = x;
    }

    public int[] hentSortert() {return A;}
    public int swaps() {return swaps;}
    public int comp() {return comp;}
    public long time() {return time;}
    public void nullstill() {comp = 0; swaps = 0; return;}


}
