package com.undirectedgraph.DFSApplications;

import com.undirectedgraph.GraphRepresentation;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.Stack;

/******************************************************************************
 *  Compilation:  javac Cycle.java
 *  Execution:    java  Cycle filename.txt
 *  Dependencies: Graph.java Stack.java In.java StdOut.java
 *  Data files:   https://algs4.cs.princeton.edu/41graph/tinyG.txt
 *                https://algs4.cs.princeton.edu/41graph/mediumG.txt
 *                https://algs4.cs.princeton.edu/41graph/largeG.txt
 *
 *  Identifies a cycle.
 *  Runs in O(E + V) time.
 *
 *  % java Cycle tinyG.txt
 *  3 4 5 3
 *
 *  % java Cycle mediumG.txt
 *  15 0 225 15
 *
 *  % java Cycle largeG.txt
 *  996673 762 840164 4619 785187 194717 996673
 *
 ******************************************************************************/
// time complexity: O(E+V)
public class Cycle {
    // cycle detection
    // is a given graph acyclic
    private boolean[] marked;
    private int[] edgeTo;
    private Stack<Integer> cycle;

    /**
     * Determines whether the undirected graph {@code G} has a cycle and,
     * if so, finds such a cycle.
     *
     * @param G the undirected graph
     */
    public Cycle(GraphRepresentation G) {
        if (hasParallelEdges(G)) {
            return;
        }
        // if (hasSelfLoop(G)) return;
        marked = new boolean[G.getVertices()];
        edgeTo = new int[G.getVertices()];
        for (int v = 0; v < G.getVertices(); v++) {
            if (!marked[v]) {
                dfs(G, -1, v);
            }
        }
    }

    // ???
    // u: parent vertex
    // v: child vertex
    private void dfs(GraphRepresentation G, int parentOfv, int v) {
        marked[v] = true;
        for (int adjacentOfv : G.adjacent(v)) {
            // short circuit if cycle is already found
            if (cycle != null) return;

            if (!marked[adjacentOfv]) {
                edgeTo[adjacentOfv] = v;
                dfs(G, v, adjacentOfv);
            }

            // if adjacent of v is not the parent of v and
            // it has been already visited => there is a cycle
            else if (adjacentOfv != parentOfv) {
                cycle = new Stack<>();
                for (int x = v; x != adjacentOfv; x = edgeTo[x]) {
                    cycle.push(x);
                }
                cycle.push(adjacentOfv);
                cycle.push(v);
            }
        }
    }

    /**
     * Returns true if the graph {@code G} has a cycle.
     *
     * @return {@code true} if the graph has a cycle; {@code false} otherwise
     */
    public boolean hasCycle() {
        return cycle != null;
    }

    /**
     * Returns a cycle in the graph {@code G}.
     * @return a cycle if the graph {@code G} has a cycle,
     *         and {@code null} otherwise
     */
    public Iterable<Integer> cycle() {
        return cycle;
    }

    // does this graph have a self loop?
    // side effect: initialize cycle to be self loop
    private boolean hasSelfLoop(GraphRepresentation G) {
        for (int v = 0; v < G.getVertices(); v++) {
            for (int w : G.adjacent(v)) {
                if (v == w) {
                    cycle = new Stack<>();
                    cycle.push(v);
                    cycle.push(v);
                    return true;
                }
            }
        }
        return false;
    }

    // does this graph have two parallel edges?
    // side effect: initialize cycle to be two parallel edges
    private boolean hasParallelEdges(GraphRepresentation G) {
        marked = new boolean[G.getVertices()];

        for (int v = 0; v < G.getVertices(); v++) {
            // check for parallel edges incident to v
            for (int w : G.adjacent(v)) {
                if (marked[w]) {
                    cycle = new Stack<>();
                    cycle.push(v);
                    cycle.push(w);
                    cycle.push(v);
                    return true;
                }
                marked[w] = true;
            }

            // reset so marked[v] = false for all v
            for (int w : G.adjacent(v)) {
                marked[w] = false;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        In in = new In("src/com/undirectedgraph/cycleG.txt");
        GraphRepresentation G = new GraphRepresentation(in);
        Cycle finder = new Cycle(G);
        if (finder.hasCycle()) {
            for (int v : finder.cycle()) {
                StdOut.print(v + " ");
            }
            StdOut.println();
        }
        else {
            StdOut.println("Graph is acyclic");
        }
    }
}
