import org.junit.jupiter.api.Test;

import Triangle.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MinPerimeterTest {
    private final MinPerimeter solver = new MinPerimeter();

    // minPerimeter(0,0, 3,0, 0,4) -> (0,0) (3,0) (0,4)
    private double solve(int... coords) {
        List<Point> points = new ArrayList<>();
        for (int i = 0; i < coords.length; i += 2) {
            points.add(new Point(coords[i], coords[i + 1]));
        }
        return solver.minPerimeter(points);
    }

    @Test
    void exactlyThreePoints() {
        assertEquals(12.0, solve(0, 0, 3, 0, 0, 4), 1e-9);   // 3-4-5 triangle
    }

    @Test
    void threeCollinearPoints() {
        assertEquals(6.0, solve(0, 0, 1, 0, 3, 0), 1e-9);    // 2 * 3
    }

    @Test
    void sampleCase1() {
        assertEquals(4.0, solve(0, 0, 1, 0, 2, 0, 1, 3), 1e-9);
    }

    @Test
    void sampleCase2() {
        assertEquals(3.414, solve(0, 0, 3, 0, 0, 4, 10, 10, 11, 10, 10, 11), 1e-3);
    }

    @Test
    void sampleCase4() {
        assertEquals(24.0, solve(0, 0, 6, 0, 0, 8), 1e-9);
    }

    @Test
    void allPointsOnOneVerticalLine() {
        assertEquals(4.0, solve(0, 0, 0, 1, 0, 2, 0, 3, 0, 10), 1e-9);
    }

    @Test
    void gridWithManySameX() {
        assertEquals(2 + Math.sqrt(2), solve(0, 0, 0, 1, 0, 2, 1, 0, 1, 1, 1, 2, 2, 0, 2, 1, 2, 2), 1e-9);
    }

    @Test
    void bestTriangleCrossesTheMiddle() {
        assertEquals(2 + 2 * Math.sqrt(2), solve(-3000, 0, -2000, 0, -1, 0, 0, 1, 1, 0, 2000, 0, 3000, 0), 1e-9);
    }

    @Test
    void inputOrderDoesNotMatter() {
        List<Point> points = new ArrayList<>(Arrays.asList(
                new Point(0, 0), new Point(5, 5), new Point(1, 0), new Point(0, 1),
                new Point(9, 2), new Point(7, 8), new Point(3, 6)));
        double expected = solver.minPerimeter(points);

        Collections.shuffle(points, new Random(1));
        assertEquals(expected, solver.minPerimeter(points), 1e-9);
    }

    @Test
    void matchesBruteForceOnRandomInputs() {
        Random random = new Random(42);
        for (int test = 0; test < 200; test++) {
            List<Point> points = randomPoints(random, 3 + random.nextInt(30), 20);
            assertEquals(bruteForce(points), solver.minPerimeter(points), 1e-9);
        }
    }

    @Test
    void twoFarClustersPicksTheSmallerTriangle() {
        assertEquals(2 + Math.sqrt(2),solve(0, 0, 1, 0, 0, 1, 1000, 1000, 1003, 1000, 1000, 1004), 1e-9);
    }

    @Test
    void negativeCoordinates() {
        assertEquals(2 + Math.sqrt(2),solve(-5, -5, -5, -4, -4, -5, 100, 100, -100, 100, 50, -70), 1e-9);
    }

    @Test
    void largestCoordinates() {
        // corners of the allowed range: sides 2e6, 2e6 and 2e6 * sqrt(2)
        assertEquals(2_000_000 * (2 + Math.sqrt(2)),solve(-1_000_000, -1_000_000, 1_000_000, 1_000_000, 1_000_000, -1_000_000), 1e-3);
    }

    @Test
    void largerRandomInputMatchesBruteForce() {
        List<Point> points = randomPoints(new Random(7), 150, 1000);
        assertEquals(bruteForce(points), solver.minPerimeter(points), 1e-9);
    }

    // ---------- helpers ----------

    private List<Point> randomPoints(Random random, int count, int range) {
        Set<String> used = new HashSet<>();
        List<Point> points = new ArrayList<>();
        while (points.size() < count) {
            int x = random.nextInt(2 * range + 1) - range;
            int y = random.nextInt(2 * range + 1) - range;
            if (used.add(x + "," + y)) {          // keep all points distinct
                points.add(new Point(x, y));
            }
        }
        return points;
    }

    private double bruteForce(List<Point> p) {
        double best = Double.MAX_VALUE;
        for (int i = 0; i < p.size(); i++) {
            for (int j = i + 1; j < p.size(); j++) {
                for (int k = j + 1; k < p.size(); k++) {
                    best = Math.min(best, dist(p.get(i), p.get(j)) + dist(p.get(i), p.get(k)) + dist(p.get(j), p.get(k)));
                }
            }
        }
        return best;
    }

    private double dist(Point a, Point b) {
        return Math.hypot(a.getX() - b.getX(), a.getY() - b.getY());
    }
}