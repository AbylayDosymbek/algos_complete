package org.example.alg;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;

public class MergeSortTest {
    @Test
    void testSmallArray() {
        int[] a = {5,2,3,1,4,9,0};
        MergeSort.sort(a);
        assertArrayEquals(new int[]{0,1,2,3,4,5,9}, a);
    }

    @Test
    void testRandomLarge() {
        int[] a = new java.util.Random().ints(1000, -10000, 10000).toArray();
        int[] copy = Arrays.copyOf(a, a.length);
        MergeSort.sort(a);
        Arrays.sort(copy);
        assertArrayEquals(copy, a);
    }
}
