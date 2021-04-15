package com.undirectedgraph;

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.NoSuchElementException;
import java.util.Stack;

public class GraphRepresentation {
    private static final String NEWLINE = System.getProperty("line.separator");

    private int Vertices;
    private int Edges;
    // array of a bag or a linked list
    private Bag<Integer>[] adjacents;

    /**
     * Initializes an empty graph with {@code V} vertices and 0 edges.
     * param V the number of vertices
     *
     * @param vertices: number of vertices
     * @throws IllegalArgumentException if {@code V < 0}
     */
    public GraphRepresentation(int vertices) {
        if (vertices < 0) throw new IllegalArgumentException("Number of vertices should be greater than 0");
        this.Vertices = vertices;
        this.Edges = 0;
        adjacents = (Bag<Integer>[]) new Bag[Vertices];
        for (int v = 0; v < vertices; v++) {
            adjacents[v] = new Bag<>();
        }
    }

    /**
     * Initializes a graph from the specified input stream.
     * The format is the number of vertices <em>V</em>,
     * followed by the number of edges <em>E</em>,
     * followed by <em>E</em> pairs of vertices, with each entry separated by whitespace.
     *
     * @param input
     * @throws IllegalArgumentException if {@code in} is {@code null}
     * @throws IllegalArgumentException if the endpoints of any edge are not in prescribed range
     * @throws IllegalArgumentException if the number of vertices or edges is negative
     * @throws IllegalArgumentException if the input stream is in the wrong format
     */
    public GraphRepresentation(In input) {
        if (input == null) throw new IllegalArgumentException("argument is null");
        try {
            this.Vertices = input.readInt();
            if (Vertices < 0) throw new IllegalArgumentException("Number of vertices should be greater than 0");
            adjacents = (Bag<Integer>[]) new Bag[Vertices];
            for (int v = 0; v < Vertices; v++) {
                adjacents[v] = new Bag<>();
            }
            int edges = input.readInt();
            if (edges < 0) throw new IllegalArgumentException("Number of edges should be greater than 0");
            for (int i = 0; i < edges; i++) {
                int v = input.readInt();
                int w = input.readInt();
                validateVertex(v);
                validateVertex(w);
                addEdge(v, w);
            }
        } catch (NoSuchElementException exception) {

        }
    }


    /**
     * Initializes a new graph that is a deep copy of {@code Graph}.
     *
     * @param graph the graph to copy
     * @throws IllegalArgumentException if {@code G} is {@code null}
     */
    public GraphRepresentation(GraphRepresentation graph) {
        this.Vertices = graph.getVertices();
        this.Edges = graph.getEdges();
        if (Vertices < 0) throw new IllegalArgumentException("Number of vertices should be non-negative");

        // update adjacency list
        adjacents = (Bag<Integer>[]) new Bag[Vertices];
        for (int vertice = 0; vertice < Vertices; vertice++) {
            adjacents[vertice] = new Bag<>();
        }
        for (int v = 0; v < graph.getVertices(); v++) {
            Stack<Integer> reverse = new Stack<>();
            for (int w : graph.adjacents[v]) {
                reverse.push(w);
            }
            for (int w : reverse) {
                adjacents[v].add(w);
            }
        }
    }

    private void validateVertex(int v) {
        if (v < 0 || v >= Vertices)
            throw new IllegalArgumentException("vertext " + v + " is not between 0 and " + (Vertices-1));
    }

    private void addEdge(int v, int w) {
        validateVertex(v);
        validateVertex(w);
        Edges++;
        adjacents[v].add(w);
        adjacents[w].add(v);
    }

    public int getVertices() {
        return Vertices;
    }

    public int getEdges() {
        return Edges;
    }

    /**
     * Returns the vertices adjacent to vertex {@code v}.
     *
     * @param  v the vertex
     * @return the vertices adjacent to vertex {@code v}, as an iterable
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public Iterable<Integer> adjacent(int v) {
        validateVertex(v);
        return adjacents[v];
    }

    /**
     * Returns the degree of vertex {@code v}.
     *
     * @param  v the vertex
     * @return the degree of vertex {@code v}
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public int degree(int v) {
        validateVertex(v);
        return adjacents[v].size();
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(Vertices + " vertices, " + Edges + " edges " + NEWLINE);
        for (int v = 0; v < Vertices; v++) {
            s.append(v + ": ");
            for (int w : adjacents[v]) {
                s.append(w + " ");
            }
            s.append(NEWLINE);
        }
        return s.toString();
    }

    public static void main(String[] args) {
        In in = new In("D:\\VuTung\\Algorithms\\Algorithms-part1\\src\\com\\undirectedgraph\\superTinyG");
        GraphRepresentation G = new GraphRepresentation(in);
        StdOut.println(G);
    }
}
