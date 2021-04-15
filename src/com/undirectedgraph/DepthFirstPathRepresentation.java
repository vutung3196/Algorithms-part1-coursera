package com.undirectedgraph;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.Stack;

public class DepthFirstPathRepresentation {
    private boolean[] marked;
    private int[] edgeTo;
    private final int s;

    public DepthFirstPathRepresentation(GraphRepresentation graph, int s) {
        this.s = s;
        edgeTo = new int[graph.getVertices()];
        marked = new boolean[graph.getVertices()];
        validateVertex(s);
        dfs(graph, s);
    }

    private void dfs(GraphRepresentation graph, int v) {
        marked[v] = true;
        for (int w: graph.adjacent(v)) {
            if (!marked[w]) {
                edgeTo[w] = v;
                dfs(graph, w);
            }
        }
    }

    public boolean hasPathTo(int v) {
        validateVertex(v);
        return marked[v];
    }

    public Iterable<Integer> pathTo(int v) {
        validateVertex(v);
        if (!hasPathTo(v)) return null;
        Stack<Integer> path = new Stack<>();
        for (int x = v; x != s; x = edgeTo[x]) {
            path.push(x);
        }
        path.push(s);
        return path;
    }

    private void validateVertex(int v) {
        int V = marked.length;
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
    }

    public static void main(String[] args) {
        In in = new In("D:\\VuTung\\Algorithms\\Algorithms-part1\\src\\com\\undirectedgraph\\tinyG.txt");
        GraphRepresentation G = new GraphRepresentation(in);
        int s = Integer.parseInt("0");
        DepthFirstPathRepresentation dfs = new DepthFirstPathRepresentation(G, s);

        for (int v = 0; v < G.getVertices(); v++) {
            if (dfs.hasPathTo(v)) {
                StdOut.printf("%d to %d:  ", s, v);
                for (int x : dfs.pathTo(v)) {
                    if (x == s) StdOut.print(x);
                    else {
                        StdOut.print("-" + x);
                    }
                }
                StdOut.println();
            }

            else {
                StdOut.printf("%d to %d:  not connected\n", s, v);
            }

        }
    }
}
