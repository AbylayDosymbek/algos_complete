package org.example.alg;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;

public class DeterministicSelectTest {
    @Test
    void testMedian() {
        int[] a = {7,1,5,3,9,2};
        int median = DeterministicSelect.select(a, a.length/2);
        Arrays.sort(a);
        assertEquals(a[a.length/2], median);
    }

    @Test
    void testRandom() {
        for (int t = 0; t < 50; t++) {
            int[] a = new java.util.Random().ints(200, -1000, 1000).toArray();
            int k = new java.util.Random().nextInt(a.length);
            int sel = DeterministicSelect.select(Arrays.copyOf(a, a.length), k);
            Arrays.sort(a);
            assertEquals(a[k], sel);
        }
    }
}
