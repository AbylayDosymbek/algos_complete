package org.example.alg;

import org.example.util.DepthTracker;
import java.util.*;

public final class ClosestPair2D {
    public record Point(double x, double y) {}

    public static double closestPair(Point[] pts) {
        if (pts.length < 2) return Double.POSITIVE_INFINITY;
        Point[] byX = pts.clone();
        Arrays.sort(byX, Comparator.comparingDouble(p -> p.x));
        Point[] byY = pts.clone();
        Arrays.sort(byY, Comparator.comparingDouble(p -> p.y));
        DepthTracker.reset();
        return closestRec(byX, byY);
    }

    private static double closestRec(Point[] byX, Point[] byY) {
        int n = byX.length;
        if (n <= 3) {
            return brute(byX);
        }
        int mid = n/2;
        double midX = byX[mid].x;
        Point[] leftX = Arrays.copyOfRange(byX, 0, mid);
        Point[] rightX = Arrays.copyOfRange(byX, mid, n);

        List<Point> leftYList = new ArrayList<>();
        List<Point> rightYList = new ArrayList<>();
        for (Point p : byY) {
            if (p.x <= midX) leftYList.add(p); else rightYList.add(p);
        }
        Point[] leftY = leftYList.toArray(new Point[0]);
        Point[] rightY = rightYList.toArray(new Point[0]);

        double dl = closestRec(leftX, leftY);
        double dr = closestRec(rightX, rightY);
        double d = Math.min(dl, dr);

        List<Point> strip = new ArrayList<>();
        for (Point p : byY) if (Math.abs(p.x - midX) < d) strip.add(p);

        for (int i = 0; i < strip.size(); i++) {
            for (int j = i+1; j < strip.size() && (strip.get(j).y - strip.get(i).y) < d; j++) {
                d = Math.min(d, dist(strip.get(i), strip.get(j)));
            }
        }
        return d;
    }

    private static double brute(Point[] pts) {
        double best = Double.POSITIVE_INFINITY;
        for (int i=0;i<pts.length;i++) for (int j=i+1;j<pts.length;j++) best = Math.min(best, dist(pts[i], pts[j]));
        return best;
    }

    private static double dist(Point a, Point b) {
        double dx = a.x - b.x, dy = a.y - b.y;
        return Math.hypot(dx, dy);
    }
}
