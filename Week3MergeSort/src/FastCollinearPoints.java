import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class FastCollinearPoints {
    private final LineSegment[] segments;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new IllegalArgumentException();
            }
        }
        int length = points.length;
        Point[] myPoints = points.clone();
        Point[] fixedOrder;
        int numSegments = 0;
        double testSlope;
        Point origin;
        LineSegment[] testSegments = new LineSegment[length*length];

        Arrays.sort(myPoints);
        fixedOrder = myPoints.clone();

        // check for duplicate points
        for(int i = 0; i < length - 1; i++) {
            if ((myPoints[i].compareTo(myPoints[i+1])) == 0) {
                throw new IllegalArgumentException();
            }
        }
        for (int i = 0; i < length; i++) {
            // get the origin
            origin = fixedOrder[i];
            Arrays.sort(myPoints);
            // Sort by slope using merge sort
            Arrays.sort(myPoints, origin.slopeOrder());
            int j = 0;
            int additionalPoints = 0;
            while (j < length - 2) {
                // Find all points with the same slope you are currently working with
                testSlope = origin.slopeTo(myPoints[j]);
                additionalPoints = 1;
                while (j + additionalPoints < length && testSlope == origin.slopeTo(myPoints[j+additionalPoints])) {
                    additionalPoints++;
                }
                additionalPoints -= 1;
                if (additionalPoints >= 2) {
                    if (origin.compareTo(myPoints[j]) < 0 &&
                        origin.compareTo(myPoints[j+additionalPoints]) <0) {
                        testSegments[numSegments] = new LineSegment(origin, myPoints[j+additionalPoints]);
                        numSegments++;
                    }
                    j = j + additionalPoints;
                }
                j++;
            }
        }
        segments = new LineSegment[numSegments];
        for (int i = 0; i < numSegments; i++) {
            segments[i] = testSegments[i];
        }
    }

    public int numberOfSegments() {
        return segments.length;
    }

    public LineSegment[] segments() {
        int length = numberOfSegments();
        LineSegment [] re = new LineSegment[length];
        for (int i = 0; i < length; i++) re[i] = segments[i];
        return re;
    }

    public static void main(String[] args) {
        In in = new In("input.txt");
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
