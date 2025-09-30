import java.nio.file.*;
import java.io.*;
import java.util.*;

class Main {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Mangler fil.");
            System.exit(1);
        }

        Path in = Path.of(args[0]);
        String inputfil = stripExt(in.getFileName().toString());
        Path quickOut = Path.of(inputfil + "_quick.out");
        Path heapOut  = Path.of(inputfil + "_heap.out");
        Path insertionOut  = Path.of(inputfil + "_insertion.out");
        Path mergeOut = Path.of(inputfil + "_merge.out");
        Path results  = Path.of(inputfil + "_results.csv");

        try {
            List<String> linjer = Files.readAllLines(in);
            List<Integer> values = new ArrayList<>();
            for (String s : linjer) {
                s = s.trim();
                if (!s.isEmpty()) values.add(Integer.parseInt(s));
            }
            int n = values.size();
            int[] A = new int[n];
            for (int i = 0; i < n; i++) A[i] = values.get(i);
            
            Files.writeString(
                results,
                "n, quick_cmp, quick_swaps, quick_time, heap_cmp, heap_swaps, heap_time, insertion_cmp, insertion_swaps, insertion_time, merge_cmp, merge_swaps, merge_time\n",
                StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING
            );
            for (int size = 0; size <= n; size++) {
                int[] A_q = java.util.Arrays.copyOf(A, size);
                int[] A_h = A_q.clone();
                int[] A_i = A_q.clone();
                int[] A_m = A_q.clone();

                // quick
                quick q = new quick(A_q, 0, size - 1);
                q.sort();

                // heap
                heap h = new heap(A_h);
                h.sort();

                // insertion
                insertion ins = new insertion(A_i);
                ins.sort();

                // merge
                merge m = new merge(A_m);
                m.sort();

                // formattering
                final int W_N   = "n".length();
                final int W_QC  = "quick_cmp".length();
                final int W_QS  = "quick_swaps".length();
                final int W_QT  = "quick_time".length();
                final int W_HC  = "heap_cmp".length();
                final int W_HS  = "heap_swaps".length();
                final int W_HT  = "heap_time".length();
                final int W_IC  = "insertion_cmp".length();
                final int W_IS  = "insertion_swaps".length();
                final int W_IT  = "insertion_time".length();
                final int W_MC  = "merge_cmp".length();
                final int W_MS  = "merge_swaps".length(); 
                final int W_MT  = "merge_time".length();

                final String FMT =
                    "%" + W_N  + "d," +
                    "%" + W_QC + "d," +
                    "%" + W_QS + "d," +
                    "%" + W_QT + "d," +
                    "%" + W_HC + "d," +
                    "%" + W_HS + "d," +
                    "%" + W_HT + "d," +
                    "%" + W_IC + "d," +
                    "%" + W_IS + "d," +
                    "%" + W_IT + "d" +
                    "%" + W_MC + "d," +
                    "%" + W_MS + "d," +
                    "%" + W_MT + "d%n";

                Files.writeString(
                    results,
                    String.format(FMT,
                        size,
                        q.comp(), q.swaps(), q.time(),
                        h.comp(), h.swaps(), h.time(),
                        ins.comp(), ins.swaps(), ins.time(),
                        m.comp(), m.swaps(), m.time()
                    ),
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND
                );


                // skriver ut sorterte arrayer til .out-filene
                if (size == n) {
                    writeLinesOnlyNumbers(quickOut, A_q); 
                    writeLinesOnlyNumbers(heapOut,  A_h);
                    writeLinesOnlyNumbers(insertionOut,  A_i);
                    writeLinesOnlyNumbers(mergeOut,  A_m);
                }
            }
        } catch (NoSuchFileException e) {
            System.err.println("Filnavn er ikke korrekt.");
        } catch (NumberFormatException e) {
            System.err.println("Ugyldig tall i inputfil.");
        } catch (IOException e) {
            System.err.println("En feil oppstod.");
        }
    }

    // hjelpemetoder
    private static String stripExt(String name) {
        int i = name.lastIndexOf('.');
        return (i > 0) ? name.substring(0, i) : name;
    }

    private static void writeLinesOnlyNumbers(Path path, int[] arr) throws IOException {
        StringBuilder sb = new StringBuilder();
        for (int v : arr) {
            sb.append(v).append(System.lineSeparator());
        }
        Files.writeString(
            path,
            sb.toString(),
            StandardOpenOption.CREATE,
            StandardOpenOption.TRUNCATE_EXISTING,
            StandardOpenOption.WRITE
        );
    }
}
