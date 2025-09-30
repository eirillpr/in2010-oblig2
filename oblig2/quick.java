/* anvendelse i main:
 * quick q = new quick(A, low, high);
 * q.sort(); // tiden starter nå. utskrift av alle relevante parametre kommer etter algoritmen er terminert
*/

public class quick {
    int[] A;
    int low;
    int high;
    int comp = 0;
    int swaps = 0;
    long måletid;

    // konstruktør
    quick(int[] A, int low, int high) {this.A = A; this.low = low; this.high = high;}

    // tiden starter
    public void sort() {
        long start = System.nanoTime();
        QuickSort(A, low, high);
        måletid = (System.nanoTime() - start) / 1000; 
        return;
    }

    // QuickSort
    public int[] QuickSort(int[] A, int low, int high) {
        if (low >= high) return A;
        int p = partition(A, low, high);
        QuickSort(A, low, p-1);
        QuickSort(A, p+1, high);
        return A;
    }
    
    // sorterer og returnerer en indeks
    public int partition(int[] A, int low, int high) {
        int pivot_indeks = low + (high-low) / 2; // midterste indeks
        // legger pivot bakerst
        bytt(pivot_indeks, high);
        
        // siden A[high] nå er i midten
        int pivot = A[high];
        int left = low;
        int right = high - 1;

        while (left <= right) {
            left = venstre(left, right, pivot);
            right = hoyre(left, right, pivot);
            if (left < right) bytt(left, right);
        }
        bytt(left, high);
        return left;
    }


    // bytter plass på to elementer
    public void bytt(int i, int j) {
        if (i == j) return;
        int temp = A[i];
        A[i] = A[j];
        A[j] = temp;
        swaps++;
    }

    // sammenligner to elementer
    public int venstre(int left, int right, int pivot) {
        while (left <= right) {comp++; if (A[left] <= pivot) left++; else break;}
        return left;
    }

    public int hoyre(int left, int right, int pivot) {
        while (right >= left) {comp++; if (A[right] >= pivot) right--; else break;}
        return right;
    }

    // til utskriving av fil og nullstilling
    public int[] hentSortert() {return A;}
    public int swaps() {return swaps;}
    public int comp() {return comp;}
    public long tid() {return måletid;}
    public void nullstill() {comp = 0; swaps = 0; return;}
}