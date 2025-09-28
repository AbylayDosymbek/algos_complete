package org.example.alg;

import org.example.util.DepthTracker;
import org.example.util.Counters;
import java.util.Random;

public final class QuickSort {
    private static final Random RNG = new Random();
    private static final int CUTOFF = 16;

    public static void sort(int[] a) {
        if (a == null || a.length < 2) return;
        DepthTracker.reset(); Counters.reset();
        quicksort(a, 0, a.length - 1);
    }

    private static void quicksort(int[] a, int lo, int hi) {
        while (lo < hi) {
            if (hi - lo + 1 <= CUTOFF) { insertionSort(a, lo, hi); return; }
            DepthTracker.enter();
            try {
                int pivotIdx = lo + RNG.nextInt(hi - lo + 1);
                swap(a, pivotIdx, hi);
                int pivot = a[hi];
                int i = lo - 1;
                for (int j = lo; j < hi; j++) {
                    Counters.incComparisons(1);
                    if (a[j] <= pivot) { i++; swap(a, i, j); }
                }
                swap(a, i+1, hi);
                int p = i+1;
                if (p - lo < hi - p) {
                    quicksort(a, lo, p-1);
                    lo = p + 1;
                } else {
                    quicksort(a, p+1, hi);
                    hi = p - 1;
                }
            } finally { DepthTracker.exit(); }
        }
    }

    private static void insertionSort(int[] a, int lo, int hi) {
        for (int i = lo + 1; i <= hi; i++) {
            int key = a[i]; int j = i - 1;
            while (j >= lo) {
                Counters.incComparisons(1);
                if (a[j] <= key) break;
                a[j+1] = a[j]; Counters.incSwaps(1); j--;
            }
            a[j+1] = key;
        }
    }

    private static void swap(int[] a, int i, int j) {
        if (i == j) return;
        int t = a[i]; a[i] = a[j]; a[j] = t;
        Counters.incSwaps(1);
    }
}
