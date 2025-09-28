package org.example.alg;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;

public class QuickSortTest {
    @Test
    void testCorrectness() {
        int[] a = new java.util.Random().ints(3000, -1000, 1000).toArray();
        int[] copy = Arrays.copyOf(a, a.length);
        QuickSort.sort(a);
        Arrays.sort(copy);
        assertArrayEquals(copy, a);
    }
}
