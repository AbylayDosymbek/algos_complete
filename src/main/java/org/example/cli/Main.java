package org.example.cli;

import org.example.alg.*;
import org.example.util.*;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        String algo = "mergesort";
        int minN = 128, maxN = 1<<16, steps = 8, repeats = 3;
        String out = "data/results.csv";
        long seed = 123;

        for (int i=0;i<args.length;i++) {
            switch(args[i]) {
                case "--algo" -> algo = args[++i];
                case "--minN" -> minN = Integer.parseInt(args[++i]);
                case "--maxN" -> maxN = Integer.parseInt(args[++i]);
                case "--steps" -> steps = Integer.parseInt(args[++i]);
                case "--repeats" -> repeats = Integer.parseInt(args[++i]);
                case "--out" -> out = args[++i];
                case "--seed" -> seed = Long.parseLong(args[++i]);
            }
        }

        Random r = new Random(seed);
        new java.io.File("data").mkdirs();
        try (PrintWriter pw = new PrintWriter(new FileWriter(out))) {
            pw.println("algo,n,repeat,elapsed_ms,comparisons,swaps,max_rec_depth");
            for (int s=0;s<steps;s++) {
                int n = (int)(minN * Math.pow((double)maxN/minN, (double)s/(steps-1)));
                for (int rep=0; rep<repeats; rep++) {
                    int[] a = r.ints(n, -n*10, n*10).toArray();
                    Counters.reset(); DepthTracker.reset();
                    long t0 = System.nanoTime();
                    switch (algo.toLowerCase()) {
                        case "mergesort": MergeSort.sort(a); break;
                        case "quicksort": QuickSort.sort(a); break;
                        case "select": DeterministicSelect.select(a, n/2); break;
                        default: throw new IllegalArgumentException("Unknown algo " + algo);
                    }
                    long elapsed = (System.nanoTime() - t0)/1_000_000;
                    pw.printf(Locale.US, "%s,%d,%d,%d,%d,%d,%d%n", algo, n, rep, elapsed, Counters.getComparisons(), Counters.getSwaps(), DepthTracker.getMax());
                }
            }
        }
        System.out.println("Wrote " + out);
    }
}
