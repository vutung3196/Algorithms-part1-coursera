package com.directedgraph;

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.NoSuchElementException;

public class DirectedGraphRepresentation {
    private final int vertices;
    private int edges;
    private Bag<Integer>[] adj;
    private int[] indegree;
    private static final String NEWLINE = System.getProperty("line.separator");

    public DirectedGraphRepresentation(int vertices) {
        if (vertices < 0) throw new IllegalArgumentException("Number of vertices should be non-negative");
        this.vertices = vertices;
        this.edges = 0;
        indegree = new int[vertices];
        adj = (Bag<Integer>[]) new Bag[vertices];
        for (int v = 0; v < vertices; v++) {
            adj[v] = new Bag<>();
        }
    }

    public int getVertices() {
        return vertices;
    }

    public int getEdges() {
        return edges;
    }

    public Bag<Integer> adj(int v) {
        return adj[v];
    }

    public void addEdge(int v, int w) {
        adj[v].add(w);
        indegree[w]++;
        edges++;
    }

    public DirectedGraphRepresentation reverse() {
        var reversedGraph = new DirectedGraphRepresentation(vertices);
        for (int v = 0; v < vertices; v++) {
            for (int w : adj[v]) {
                reversedGraph.addEdge(w, v);
            }
        }
        return reversedGraph;
    }

    public DirectedGraphRepresentation(In in) {
        if (in == null)
            throw new IllegalArgumentException("argument is null");
        try {
            this.vertices = in.readInt();
            if (vertices < 0)
                throw new IllegalArgumentException("The number of vertices should be greater than 0");
            adj = (Bag<Integer>[]) new Bag[vertices];
            for (int v = 0; v < vertices; v++) {
                adj[v] = new Bag<>();
            }
            int edges = in.readInt();
            if (edges < 0)
                throw new IllegalArgumentException("The number of edges should be greater than 0");
            indegree = new int[vertices];
            for (int i = 0; i < edges; i++) {
                int v = in.readInt();
                int w = in.readInt();
                addEdge(v, w);
            }
        } catch (NoSuchElementException exception) {
            throw new IllegalArgumentException("Invalid input format in directed graph constructor.");
        }
    }

    /**
     * Returns the number of directed edges incident from vertex {@code v}.
     * This is known as the <em>outdegree</em> of vertex {@code v}.
     *
     * @param v the vertex
     * @return the outdegree of vertex {@code v}
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public int outdegree(int v) {
        return adj[v].size();
    }

    /**
     * Returns the number of directed edges incident to vertex {@code v}.
     * This is known as the <em>indegree</em> of vertex {@code v}.
     *
     * @param v the vertex
     * @return the indegree of vertex {@code v}
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public int indegree(int v) {
        return indegree[v];
    }

    /**
     * Returns a string representation of the graph.
     *
     * @return the number of vertices <em>V</em>, followed by the number of edges <em>E</em>,
     * followed by the <em>V</em> adjacency lists
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(vertices + " vertices, " + edges + " edges " + NEWLINE);
        for (int v = 0; v < vertices; v++) {
            s.append(String.format("%d: ", v));
            for (int w : adj[v]) {
                s.append(String.format("%d ", w));
            }
            s.append(NEWLINE);
        }
        return s.toString();
    }

    public static void main(String[] args) {
        In in = new In("src/com/directedgraph/tinyDG.txt");
        DirectedGraphRepresentation G = new DirectedGraphRepresentation(in);
        StdOut.println(G);
    }
}
