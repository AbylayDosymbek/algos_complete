package org.example.alg;

import org.example.util.DepthTracker;
import org.example.util.Counters;
import java.util.Arrays;

public final class DeterministicSelect {

    public static int select(int[] a, int k) {
        if (k < 0 || k >= a.length) throw new IllegalArgumentException("k out of bounds");
        DepthTracker.reset(); Counters.reset();
        return selectRec(a, 0, a.length - 1, k);
    }

    private static int selectRec(int[] a, int lo, int hi, int k) {
        DepthTracker.enter();
        try {
            while (lo <= hi) {
                int n = hi - lo + 1;
                if (n <= 10) {
                    Arrays.sort(a, lo, hi+1);
                    return a[lo + k];
                }
                int numMeds = 0;
                for (int i = lo; i <= hi; i += 5) {
                    int subHi = Math.min(i+4, hi);
                    insertionSort(a, i, subHi);
                    int medianIdx = i + (subHi - i)/2;
                    swap(a, lo + numMeds, medianIdx);
                    numMeds++;
                }
                int medOfMed = selectRec(a, lo, lo + numMeds - 1, numMeds/2);
                int pivotIndex = partitionAroundValue(a, lo, hi, medOfMed);
                int rank = pivotIndex - lo;
                if (k == rank) return a[pivotIndex];
                else if (k < rank) hi = pivotIndex - 1;
                else { k = k - (rank + 1); lo = pivotIndex + 1; }
            }
            throw new IllegalStateException();
        } finally { DepthTracker.exit(); }
    }

    private static int partitionAroundValue(int[] a, int lo, int hi, int value) {
        for (int j = lo; j <= hi; j++) if (a[j] == value) { swap(a, j, hi); break; }
        int pivot = a[hi];
        int idx = lo - 1;
        for (int j = lo; j < hi; j++) {
            Counters.incComparisons(1);
            if (a[j] <= pivot) { idx++; swap(a, idx, j); }
        }
        swap(a, idx+1, hi);
        return idx+1;
    }

    private static void swap(int[] a, int i, int j) {
        if (i == j) return;
        int t = a[i]; a[i] = a[j]; a[j] = t;
        Counters.incSwaps(1);
    }

    private static void insertionSort(int[] a, int lo, int hi) {
        for (int i = lo+1; i <= hi; i++) {
            int key = a[i];
            int j = i-1;
            while (j >= lo) {
                Counters.incComparisons(1);
                if (a[j] <= key) break;
                a[j+1] = a[j];
                Counters.incSwaps(1);
                j--;
            }
            a[j+1] = key;
        }
    }
}
