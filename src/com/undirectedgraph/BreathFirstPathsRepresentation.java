package com.undirectedgraph;


import edu.princeton.cs.algs4.Queue;

public class BreathFirstPathsRepresentation {
    private static final int INFINITY = Integer.MAX_VALUE;
    // tô màu
    private boolean[] marked; // marked: is there an s-v path
    private int[] edgeTo;     // previous edge on shortest s-v path
    private int[] distTo;     // number of edges on shortest s-v path

    public BreathFirstPathsRepresentation(GraphRepresentation G, int s) {
        marked = new boolean[G.getVertices()];
        distTo = new int[G.getVertices()];
        edgeTo = new int[G.getVertices()];
        validateVertex(s);
        bfs(G, s);
    }

    // breath-first-search from single source
    private void bfs(GraphRepresentation graph, int s) {
        Queue<Integer> q = new Queue<>();
        for (int v = 0; v < graph.getVertices(); v++) {
            distTo[v] = INFINITY;
        }
        distTo[s] = 0;
        marked[s] = true;
        q.enqueue(s);

        while (!q.isEmpty()) {
            int v = q.dequeue();
            // getting adjacent
            for (int w: graph.adjacent(v)) {
                if (!marked[w]) {
                    edgeTo[w] = v;
                    distTo[w] = distTo[v] + 1;
                    marked[w] = true;
                    q.enqueue(w);
                }
            }
        }
    }

    /**
     * Is there a path between the source vertex {@code s} (or sources) and vertex {@code v}?
     * @param v the vertex
     * @return {@code true} if there is a path, and {@code false} otherwise
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public boolean hasPathTo(int v) {
        validateVertex(v);
        // tô màu
        return marked[v];
    }

    // throw an IllegalArgumentException unless {@code 0 <= v < V}
    private void validateVertex(int v) {
        int V = marked.length;
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
    }

    /**
     * Returns the number of edges in a shortest path between the source vertex {@code s}
     * (or sources) and vertex {@code v}?
     * @param v the vertex
     * @return the number of edges in such a shortest path
     *         (or {@code Integer.MAX_VALUE} if there is no such path)
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public int distTo(int v) {
        validateVertex(v);
        return distTo[v];
    }
}
