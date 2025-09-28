package org.example.util;

public final class Counters {
    private static final ThreadLocal<Long> comparisons = ThreadLocal.withInitial(() -> 0L);
    private static final ThreadLocal<Long> swaps = ThreadLocal.withInitial(() -> 0L);

    private Counters() {}

    public static void incComparisons(long v) { comparisons.set(comparisons.get() + v); }
    public static void incSwaps(long v) { swaps.set(swaps.get() + v); }
    public static long getComparisons() { return comparisons.get(); }
    public static long getSwaps() { return swaps.get(); }
    public static void reset() { comparisons.set(0L); swaps.set(0L); }
}
