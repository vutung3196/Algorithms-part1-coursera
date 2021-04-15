package assignment;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.Stack;

public class KdTree {
    private Node root;
    private int size;

    private class Node {
        private final Point2D point;
        private Node left, right;
        private RectHV rectangle;

        private Node(Point2D p) {
            point = p;
        }

        private void makeRect(double xmin, double ymin, double xmax, double ymax) {
            rectangle = new RectHV(xmin, ymin, xmax, ymax);
        }

        private int compareX(Node that) {
            return Double.compare(this.point.x(), that.point.x());
        }

        private int compareY(Node that) {
            return Double.compare(this.point.y(), that.point.y());
        }
    }

    public KdTree() {
        size = 0;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public int size() {
        return size;
    }

    public void insert(Point2D point) {
        if (point == null) {
            throw new IllegalArgumentException();
        }
        if (!contains(point)) {
            root = insert(root, new Node(point), true, 0, 0, 1, 1);
            size++;
        }
    }

    private Node insert(Node current, Node toPut, boolean compX, double xmin, double ymin, double xmax, double ymax) {
        if (current == null) {
            toPut.makeRect(xmin, ymin, xmax, ymax);
            return toPut;
        }
        int cmp = compX ? toPut.compareX(current) : toPut.compareY(current);
        if (cmp < 0) {
            if (compX) {
                current.left = insert(current.left, toPut, !compX, xmin, ymin, current.point.x(), ymax);
            } else {
                current.left = insert(current.left, toPut, !compX, xmin, ymin, xmax, current.point.y());
            }
        } else {
            if (compX) {
                current.right = insert(current.right, toPut, !compX, current.point.x(), ymin, xmax, ymax);
            } else {
                current.right = insert(current.right, toPut, !compX, xmin, current.point.y(), xmax, ymax);
            }
        }
        return current;
    }

    public boolean contains(Point2D point) {
        if (point == null) {
            throw new IllegalArgumentException();
        }
        return contains(root, new Node(point), true);
    }

    private boolean contains(Node current, Node test, boolean comparedX) {
        if (current == null) {
            return false;
        }
        int cmpX = test.compareX(current);
        int cmpY = test.compareY(current);
        if (cmpY == 0 && cmpX == 0) {
            return true;
        }
        int cmp = comparedX ? cmpX : cmpY;
        if (cmp < 0) {
            return contains(current.left, test, !comparedX);
        } else {
            return contains(current.right, test, !comparedX);
        }
    }

    public Iterable<Point2D> range(RectHV rectangle) {
        if (rectangle == null) {
            throw new IllegalArgumentException();
        }
        Stack<Point2D> re = new Stack<>();
        Stack<Node> s = new Stack<>();
        Node current;
        s.push(root);
        while (!s.isEmpty()) {
            current = s.pop();
            if (current != null) {
                if (rectangle.contains(current.point)) {
                    re.push(current.point);
                }
                if (current.rectangle.intersects(rectangle)) {
                    if (current.left != null && current.left.rectangle.intersects(rectangle)) s.push(current.left);
                    if (current.right != null && current.right.rectangle.intersects(rectangle)) s.push(current.right);
                }
            }
        }
        return re;
    }

    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        if (size == 0) {
            return null;
        }
        Point2D re = null;
        Node current;
        Stack<Node> s = new Stack<>();
        double currentDistance;
        double smallestDistance = Integer.MAX_VALUE;
        double testDistanceA, testDistanceB;
        s.push(root);
        while (!s.isEmpty()) {
            current = s.pop();
            if (current != null) {
                currentDistance = p.distanceSquaredTo(current.point);
                if (currentDistance < smallestDistance) {
                    smallestDistance = currentDistance;
                    re = current.point;
                }
                if (current.rectangle.distanceSquaredTo(p) < smallestDistance) {
                    testDistanceA = (current.left == null) ? Integer.MAX_VALUE
                            : current.left.rectangle.distanceSquaredTo(p);
                    testDistanceB = (current.right == null) ? Integer.MAX_VALUE
                            : current.right.rectangle.distanceSquaredTo(p);
                    if (testDistanceA < testDistanceB) {
                        if (testDistanceB < smallestDistance) s.push(current.right);
                        if (testDistanceA < smallestDistance) s.push(current.left);
                    } else {
                        if (testDistanceA < smallestDistance) s.push(current.left);
                        if (testDistanceB < smallestDistance) s.push(current.right);
                    }
                }
            }
        }
        return re;
    }

    public void draw() {
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 1);
        StdDraw.setYscale(0, 1);
        draw(root, true);
        StdDraw.show();
    }

    private void draw(Node cur, boolean compX) {
        if (cur != null) {
            draw(cur.left, !compX);
            // Horizontal Line
            if (compX) {
                StdDraw.setPenColor(StdDraw.RED);
                StdDraw.setPenRadius();
                cur.rectangle.draw();
            } else {
                StdDraw.setPenColor(StdDraw.BLUE);
                StdDraw.setPenRadius();
                cur.rectangle.draw();
            }
            StdDraw.setPenRadius(0.01);
            cur.point.draw();
            draw(cur.right, !compX);
        }
    }


    public static void main(String[] args) {

    }
}
