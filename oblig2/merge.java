import java.util.Arrays;

public class merge {
    int[] numbers;
    int comp = 0;
    int swaps = 0;
    long time;

    merge(int[] array) {
        this.numbers = array;
    }

    public int[] mergehelp(int[] a1, int[] a2, int[] a) {
        int i = 0;
        int j = 0;
        while (i < a1.length && j < a2.length) {
            comp++;
            if (a1[i] <= a2[j]) {
                a[i + j] = a1[i];
                i++;
                swaps++;
            }
            else {
                a[i + j] = a2[j];
                j++;
                swaps++;
            }
        }
        while (i < a1.length) {
            a[i + j] = a1[i];
            i++;
            swaps++;
        }
        while (j < a2.length) {
            a[i + j] = a2[j];
            j++;
            swaps++;
        }
        return a;
    }

    public int[] mergesort(int[] a) { //sorterer med mergesort
        if (a.length <= 1) {
            return a;
        }
        int i = a.length/2;
        int[] a1 = mergesort(Arrays.copyOfRange(a, 0, i));
        int[] a2 = mergesort(Arrays.copyOfRange(a, i, a.length));
        return mergehelp(a1, a2, a);
        
    }

    public void sort() {
        long t = System.nanoTime();
        this.numbers = mergesort(numbers);
        this.time = (System.nanoTime()-t)/1000;
    }

    public int[] hentSortert() {return numbers;}
    public int swaps() {return swaps;}
    public int comp() {return comp;}
    public long time() {return time;}
    public void nullstill() {comp = 0; swaps = 0; return;}

}
