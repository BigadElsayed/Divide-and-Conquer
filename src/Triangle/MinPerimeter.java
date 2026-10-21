package Triangle;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MinPerimeter {
    public double minPerimeter(List<Point> points) {
        List<Point> X = new ArrayList<>(points);
        List<Point> Y = new ArrayList<>(points);

        X.sort(new Comparator<Point>() {
            public int compare(Point o1, Point o2) {
                if (o1.getX() == o2.getX()) {
                    return Double.compare(o1.getY(), o2.getY());
                }
                return Double.compare(o1.getX(), o2.getX());
            }
        });

        Y.sort(new Comparator<Point>() {
            public int compare(Point o1, Point o2) {
                return Double.compare(o1.getY(), o2.getY());
            }
        });

        return recurseMinPerimeter(X, Y, 0, X.size() - 1);
    }

    private int compareXThenY(Point o1, Point o2) {
        if (o1.getX() == o2.getX()) {
            return Double.compare(o1.getY(), o2.getY());
        }
        return Double.compare(o1.getX(), o2.getX());
    }

    private double recurseMinPerimeter(List<Point> X, List<Point> Y, int xLow, int xHigh) {
        if (xHigh - xLow <= 3) {
            return bruteForceComparing(X, xLow, xHigh);
        }

        int mid = (xHigh + xLow) / 2;
        Point midPoint = X.get(mid);

        List<Point> YL = new ArrayList<>(mid - xLow + 1);
        List<Point> YR = new ArrayList<>(xHigh - mid);

        for (Point p : Y) {
            if (compareXThenY(p, midPoint) <= 0) {
                YL.add(p);
            } else {
                YR.add(p);
            }
        }

        double pLeft = recurseMinPerimeter(X, YL, xLow, mid);
        double pRight = recurseMinPerimeter(X, YR, mid + 1, xHigh);
        double best = Math.min(pLeft, pRight);

        List<Point> YStrip = new ArrayList<>(xHigh - xLow + 1);

        double midStrip = midPoint.getX();
        double leftStrip = midStrip - best / 2;
        double rightStrip = midStrip + best / 2;

        for (Point p : Y) {
            if (p.getX() <= rightStrip && p.getX() >= leftStrip) {
                YStrip.add(p);
            }
        }

        for (int i = 0; i < YStrip.size(); i++) {
            for (int j = i + 1; j <= Math.min(i + 15, YStrip.size() - 1); j++) {
                if (YStrip.get(j).getY() - YStrip.get(i).getY() >= best / 2) break;

                for (int k = j + 1; k <= Math.min(i + 15, YStrip.size() - 1); k++) {
                    best = Math.min(best, computePerimeter(YStrip.get(i), YStrip.get(j), YStrip.get(k)));
                }

            }
        }

        return best;
    }

    private double bruteForceComparing(List<Point> X, int xLow, int xHigh) {
        double p = Double.MAX_VALUE;

        for (int i = xLow; i <= xHigh; i++) {
            for (int j = i + 1; j <= xHigh; j++) {
                for (int k = j + 1; k <= xHigh; k++) {
                    p = Math.min(p, computePerimeter(X.get(i), X.get(j), X.get(k)));
                }
            }
        }

        return p;
    }

    private double computePerimeter(Point p1, Point p2, Point p3) {
        double p = Math.hypot(p1.getX() - p2.getX(), p1.getY() - p2.getY());
        p += Math.hypot(p1.getX() - p3.getX(), p1.getY() - p3.getY());
        p += Math.hypot(p3.getX() - p2.getX(), p3.getY() - p2.getY());

        return p;
    }


}

