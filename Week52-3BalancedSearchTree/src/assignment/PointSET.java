package assignment;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.Stack;
import java.util.TreeSet;

public class PointSET {
    private final TreeSet<Point2D> points;

    // construct an empty set of points
    public PointSET() {
        points = new TreeSet<>();
    }

    // is the set empty?
    public boolean isEmpty() {
        return points.isEmpty();
    }

    // number of points in the set
    public int size() {
        return points.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        points.add(p);
    }

    // does the set contain point p?
    public boolean contains(Point2D p)    {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        return points.contains(p);
    }

    // draw all points to standard draw
    public void draw() {
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 100);
        StdDraw.setYscale(0, 100);
        for(Point2D p : points) {
            p.draw();
        }
        StdDraw.show();
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rectangle)   {
        if (rectangle == null) {
            throw new IllegalArgumentException();
        }
        Stack<Point2D> re = new Stack<>();
        for(Point2D p : points) {
            if (rectangle.contains(p)) {
                re.push(p);
            }
        }
        return re;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p)  {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        if (points.isEmpty()) return null;
        Point2D re = null;
        double testDistance;
        double minDistance = Integer.MAX_VALUE;
        for(Point2D point : points) {
            testDistance = p.distanceSquaredTo(point);
            if (testDistance < minDistance) {
                re = point;
                minDistance = testDistance;
            }
        }
        return re;
    }

    public static void main(String[] args) {
        var set = new PointSET();
//        var point = new Point2D(1, 2);
//        var point = new Point2D(1, 2);
//        var point = new Point2D(1, 2);
//        var point = new Point2D(1, 2);
//        var point = new Point2D(1, 2);
        for (int i = 0; i < 100; i++) {
            var point = new Point2D(i, i+1);
            set.insert(point);
        }
        set.draw();
        var a = set.nearest(new Point2D(100, 101));
        a.draw();
    }
}
