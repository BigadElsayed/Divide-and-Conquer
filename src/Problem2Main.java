import Triangle.MinPerimeter;
import Triangle.Point;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Problem2Main {
    public static void main(String[] args) throws IOException {
        StreamTokenizer in = new StreamTokenizer(new BufferedReader(new InputStreamReader(System.in)));
        MinPerimeter solver = new MinPerimeter();
        StringBuilder output = new StringBuilder();

        while (in.nextToken() != StreamTokenizer.TT_EOF) {
            int n = (int) in.nval;

            List<Point> points = new ArrayList<>(n);
            for (int i = 0; i < n; i++) {
                in.nextToken();
                double x = in.nval;
                in.nextToken();
                double y = in.nval;
                points.add(new Point(x, y));
            }

            double result = solver.minPerimeter(points);
            output.append(String.format(Locale.US, "%.3f", result)).append('\n');
        }

        System.out.print(output);
    }
}