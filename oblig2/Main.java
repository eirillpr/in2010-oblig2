import java.nio.file.*;
import java.io.*;
import java.util.*;

// resultater fra hver algoritme skrives til separate inputfil_alg.out-filer
// resultatene fra alle algoritmene skrives ut felles til en inputfil_results.csv-fil
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
        Path results  = Path.of(inputfil + "_results.csv");

        try {
            // leser fra inputfil og lagrer linjene i en liste
            List<String> linjer = Files.readAllLines(in);
            
            // overskriftene til nye filer
            Files.writeString(quickOut, "n,cmp,swaps,time\n",
                    StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            Files.writeString(heapOut,  "n,cmp,swaps,time\n",
                    StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            Files.writeString(results,  "n,quick_cmp,quick_swaps,quick_time,heap_cmp,heap_swaps,heap_time\n",
                    StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

            // argumenter til algoritmene
            int n = linjer.size();
            int[] A = new int[n];

            for (int i = 0; i <= n; i++) { // n+1 iterasjoner (for å inkl tom array)
                int size = i;
                // heap og quick sorterer samme arrayer
                int[] A_q = java.util.Arrays.copyOf(A, size);
                int[] A_h = A_q.clone();

                // kaller sort() for hver iterasjon og skriver resultatet til .out-filene
                quick q = new quick(A_q, 0, size - 1);
                q.sort(); 
                Files.writeString(
                    quickOut,
                    String.format("%d,%d,%d,%d%n", i, q.comp(), q.swaps(), q.tid()),
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND                    
                );
                heap h = new heap(A_h);
                h.sort(); 
                Files.writeString(
                    heapOut,
                    String.format("%d,%d,%d,%d%n", i, h.comp(), h.swaps(), h.tid()),
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND                   
                );

                // samlede resultater til .csv-fil
                Files.writeString(
                    results,
                    String.format("%d,%d,%d,%d,%d,%d,%d%n",
                        size, q.comp(), q.swaps(), q.tid(), h.comp(), h.swaps(), h.tid()),
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND
                );
                
                // nullstiller swaps og comp før neste opptelling
                q.nullstill(); h.nullstill();

                // legger inn tallet for NESTE iterasjon (ikke når i >= n)
                if (i < n) {
                    String s = linjer.get(i).trim();
                    if (!s.isEmpty()) A[i] = Integer.parseInt(s);
                }
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
    
