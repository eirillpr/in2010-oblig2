/*
 * anvendelse i main:
 * heap h = new heap(A);
 * h.sort(); // tiden starter nå. utskrift av alle relevante parametre kommer etter algoritmen er terminer
*/

public class heap {
    int[] A;
    int n;
    int comp = 0;
    int swaps = 0;
    long måletid;

    // konstruktør
    heap(int[] A) {this.A = A; this.n = A.length;}

    // tiden starter
    public void sort() {
        long start = System.nanoTime();
        HeapSort(A);
        måletid = (System.nanoTime() - start) / 1000; 
        return;
    }

    // HeapSort
    public int[] HeapSort(int[] A) {
        BuildMaxHeap(n);
        for (int i = n-1; i > 0; i--) {bytt(0, i); BubbleDown(0, i);}
        return A;
    }

    // BubbleDown
    void BubbleDown(int i, int n) {
        int largest = i;
        int left = 2*i + 1;
        int right = 2*i + 2;

        if (left < n) {comp++; if (A[largest] < A[left]) {largest = left;}}
        if (right < n) {comp++; if (A[largest] < A[right]) {largest = right;}}
        if (i != largest) {bytt(i, largest); BubbleDown(largest, n);}}

    // BuildMaxHeap
    void BuildMaxHeap(int n) {
        for (int i = n/2-1; i >= 0; i--) {BubbleDown(i, n);}
    }

    // bytte plass på elementer
    void bytt(int i, int j) {
        if (i == j) return;
        int temp = A[i];
        A[i] = A[j];
        A[j] = temp;
        swaps++;
    }

    // til utskriving av fil
    public int[] hentSortert() {return A;}
    public int swaps() {return swaps;}
    public int comp() {return comp;}
    public long tid() {return måletid;}
    public void nullstill() {comp = 0; swaps = 0; return;}
}