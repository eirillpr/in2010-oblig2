import java.nio.file.*;
import java.io.*;
import java.util.*;

// resultater fra hver algoritme skrives til separate inputfil_alg.out-filer
// resultatene fra alle algoritmene skrives ut felles til en inputfil_results.csv-fil
class tidstest {

    // små, men nyttige tillegg
    private static final int WARMUP_RUNS = 8;
    private static final int RUNS =  nine(); // 9 repetisjoner for en stabil median

    // mikroskopisk "triks" for å slippe magic number rett i koden
    private static int nine() { return 9; }

    private static long median(long[] xs) {
        Arrays.sort(xs);
        return xs[xs.length / 2];
    }

    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Mangler fil.");
            System.exit(1);
        }
        Path in = Path.of(args[0]);
        String inputfil = stripExt(in.getFileName().toString());
        Path quickOut = Path.of(inputfil + "_quick.out");
        Path heapOut  = Path.of(inputfil + "_heap.out");
        Path results  = Path.of(inputfil + "_results.csv");

        try {
            // les hele input én gang og parse til base-array (prefiksene brukes videre)
            List<String> linjer = Files.readAllLines(in);
            int n = linjer.size();
            int[] base = new int[n];
            for (int i = 0; i < n; i++) {
                String s = linjer.get(i).trim();
                if (!s.isEmpty()) base[i] = Integer.parseInt(s);
            }

            // enkel oppvarming på representativt datasett (midlere størrelse)
            int warmSize = Math.min(n, 10_000);
            int[] warm = Arrays.copyOf(base, warmSize);
            for (int r = 0; r < WARMUP_RUNS; r++) {
                int[] a1 = Arrays.copyOf(warm, warm.length);
                new quick(a1, 0, Math.max(0, a1.length - 1)).sort();
                int[] a2 = Arrays.copyOf(warm, warm.length);
                new heap(a2).sort();
            }

            // overskrifter
            Files.writeString(quickOut, "n,cmp,swaps,time\n",
                    StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            Files.writeString(heapOut,  "n,cmp,swaps,time\n",
                    StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            Files.writeString(results,  "n,quick_cmp,quick_swaps,quick_time,heap_cmp,heap_swaps,heap_time\n",
                    StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

            // måling: 0..n (inkl tom array)
            for (int size = 0; size <= n; size++) {
                // --- QUICK ---
                long[] tQuick = new long[RUNS];
                quick qFirst = null;
                for (int r = 0; r < RUNS; r++) {
                    int[] A_q = Arrays.copyOf(base, size);
                    quick q = new quick(A_q, 0, size - 1);
                    q.sort();
                    tQuick[r] = q.tid();           // bruker tid() fra din klasse
                    if (r == 0) qFirst = q;        // samme input → samme cmp/swaps
                }
                long medQuick = median(tQuick);

                // --- HEAP ---
                long[] tHeap = new long[RUNS];
                heap hFirst = null;
                for (int r = 0; r < RUNS; r++) {
                    int[] A_h = Arrays.copyOf(base, size);
                    heap h = new heap(A_h);
                    h.sort();
                    tHeap[r] = h.tid();
                    if (r == 0) hFirst = h;
                }
                long medHeap = median(tHeap);

                // skriv ut (bruker cmp/swaps fra første run, men MEDIAN tid)
                Files.writeString(
                    quickOut,
                    String.format("%d,%d,%d,%d%n", size, qFirst.comp(), qFirst.swaps(), medQuick),
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND
                );
                Files.writeString(
                    heapOut,
                    String.format("%d,%d,%d,%d%n", size, hFirst.comp(), hFirst.swaps(), medHeap),
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND
                );
                Files.writeString(
                    results,
                    String.format("%d,%d,%d,%d,%d,%d,%d%n",
                        size,
                        qFirst.comp(), qFirst.swaps(), medQuick,
                        hFirst.comp(), hFirst.swaps(), medHeap
                    ),
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND
                );
            }
        } catch (NoSuchFileException e) {
            System.err.println("Filnavn er ikke korrekt.");
        } catch (IOException e) {
            System.err.println("En feil oppstod.");
        }
    }

    // hjelpemetode
    private static String stripExt(String name) {
        int i = name.lastIndexOf('.');
        return (i > 0) ? name.substring(0, i) : name;
    }
}
