import javax.sound.sampled.Line;
import java.util.Arrays;

public class BruteCollinearPoints {
    private final LineSegment[] segments;

    public BruteCollinearPoints(Point[] points) {
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
        LineSegment[] tempSegments = new LineSegment[length*length];
        int count = 0;
        Point pa, pb, pc, pd;
        Arrays.sort(myPoints);

        for (int i = 0; i < points.length - 1; i++) {
            if (myPoints[i].compareTo(myPoints[i+1]) == 0) {
                throw new IllegalArgumentException();
            }
        }
        for (int a = 0; a < length; a++) {
            for (int b = a+1; b < length; b++) {
                for (int c = b+1; c < length; c++) {
                    for (int d = c+1; d < length; d++) {
                        pa = myPoints[a];
                        pb = myPoints[b];
                        pc = myPoints[c];
                        pd = myPoints[d];
                        if (pa.slopeTo(pb) == pa.slopeTo(pc) && pa.slopeTo(pc) == pa.slopeTo(pd)) {
                            tempSegments[count++] = new LineSegment(pa, pd);
                        }
                    }
                }
            }
        }
        segments = new LineSegment[count];
        for (int i = 0; i < count; i++) {
            segments[i] = tempSegments[i];
        }
    }

    // finds all line segments containing 4 points
    // the number of line segments
    public int numberOfSegments() {
        return segments.length;
    }

    // the line segments
    public LineSegment[] segments() {
        int length = numberOfSegments();
        LineSegment[] re = new LineSegment[length];
        for (int i = 0; i < length; i++) {
            re[i] = segments[i];
        }
        return re;
    }
}
