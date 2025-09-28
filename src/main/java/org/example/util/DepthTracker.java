package org.example.util;

public final class DepthTracker {
    private static final ThreadLocal<Integer> current = ThreadLocal.withInitial(() -> 0);
    private static final ThreadLocal<Integer> max = ThreadLocal.withInitial(() -> 0);

    private DepthTracker() {}

    public static void enter() {
        int c = current.get() + 1;
        current.set(c);
        if (c > max.get()) max.set(c);
    }

    public static void exit() {
        current.set(current.get() - 1);
    }

    public static int getMax() { return max.get(); }
    public static void reset() { current.set(0); max.set(0); }
}
