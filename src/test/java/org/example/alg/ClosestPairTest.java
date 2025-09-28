package org.example.alg;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ClosestPairTest {
    @Test
    void testSimple() {
        ClosestPair2D.Point[] pts = {
                new ClosestPair2D.Point(0,0),
                new ClosestPair2D.Point(3,4),
                new ClosestPair2D.Point(1,1)
        };
        double d = ClosestPair2D.closestPair(pts);
        assertEquals(Math.sqrt(2), d, 1e-9);
    }
}
