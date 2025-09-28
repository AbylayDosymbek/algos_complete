package org.example.alg;

import org.example.util.DepthTracker;
import org.example.util.Counters;

public final class MergeSort {
    public static final int CUTOFF = 32;

    public static void sort(int[] a) {
        if (a == null || a.length < 2) return;
        int[] buf = new int[a.length];
        DepthTracker.reset(); Counters.reset();
        mergeSort(a, buf, 0, a.length);
    }

    private static void mergeSort(int[] a, int[] buf, int lo, int hi) {
        DepthTracker.enter();
        try {
            int n = hi - lo;
            if (n <= CUTOFF) { insertionSort(a, lo, hi); return; }
            int mid = lo + (n >>> 1);
            mergeSort(a, buf, lo, mid);
            mergeSort(a, buf, mid, hi);
            merge(a, buf, lo, mid, hi);
        } finally { DepthTracker.exit(); }
    }

    private static void merge(int[] a, int[] buf, int lo, int mid, int hi) {
        int i = lo, j = mid, k = lo;
        while (i < mid && j < hi) {
            Counters.incComparisons(1);
            if (a[i] <= a[j]) buf[k++] = a[i++]; else buf[k++] = a[j++];
        }
        while (i < mid) buf[k++] = a[i++];
        while (j < hi) buf[k++] = a[j++];
        System.arraycopy(buf, lo, a, lo, hi - lo);
    }

    private static void insertionSort(int[] a, int lo, int hi) {
        for (int i = lo + 1; i < hi; i++) {
            int key = a[i];
            int j = i - 1;
            while (j >= lo) {
                Counters.incComparisons(1);
                if (a[j] <= key) break;
                a[j+1] = a[j]; Counters.incSwaps(1); j--;
            }
            a[j+1] = key;
        }
    }
}
