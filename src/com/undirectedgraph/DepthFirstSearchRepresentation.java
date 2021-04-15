package com.undirectedgraph;

import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class DepthFirstSearchRepresentation {
    private boolean[] marked; // is there an s-v path
    private int count;

    /**
     * Computes the vertices in graph {@code G} that are
     * connected to the source vertex {@code s}.
     * @param graph the graph
     * @param s the source vertex
     * @throws IllegalArgumentException unless {@code 0 <= s < V}
     */
    public DepthFirstSearchRepresentation(GraphRepresentation graph, int s) {
        marked = new boolean[graph.getVertices()];
        validateVertex(s);
        dfs(graph, s);
    }

    // depth first search from vertex v
    private void dfs(GraphRepresentation graph, int v) {
        count++;
        marked[v] = true;
        for(int w : graph.adjacent(v)) {
            if (!marked[w]) {
                dfs(graph, w);
            }
        }
    }

    private void validateVertex(int v) {
        int V = marked.length;
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
    }

    public boolean marked(int v) {
        validateVertex(v);
        return marked[v];
    }

    /**
     * Returns the number of vertices connected to the source vertex {@code s}.
     * @return the number of vertices connected to the source vertex {@code s}
     */
    public int count() {
        return count;
    }

    public static void main(String[] args) {
        In in = new In("D:\\VuTung\\Algorithms\\Algorithms-part1\\src\\com\\undirectedgraph\\tinyG.txt");
        GraphRepresentation G = new GraphRepresentation(in);
        int s = 5;
        DepthFirstSearchRepresentation search = new DepthFirstSearchRepresentation(G, s);
        for (int v = 0; v < G.getVertices(); v++) {
            if (search.marked(v))
                StdOut.print(v + " ");
        }
        System.out.println();
        System.out.println(search.marked(3));

        StdOut.println();
        if (search.count() != G.getVertices()) StdOut.println("NOT connected");
        else                         StdOut.println("connected");
    }
}
