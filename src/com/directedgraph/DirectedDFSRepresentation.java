package com.directedgraph;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class DirectedDFSRepresentation {
    private boolean[] marked;

    public DirectedDFSRepresentation(DirectedGraphRepresentation graph, int s) {
        marked = new boolean[graph.getVertices()];
        dfs(graph, s);
    }

    private void dfs(DirectedGraphRepresentation graph, int v) {
        marked[v] = true;
        for (int w : graph.adj(v)) {
            if (!marked[w])
                dfs(graph, w);
        }
    }

    public boolean marked(int v) {
        return marked[v];
    }

    public DirectedDFSRepresentation(DirectedGraphRepresentation G, Iterable<Integer> sources) {
        marked = new boolean[G.getVertices()];
        for (int s : sources)
            if (!marked[s]) dfs(G, s);
    }

    public static void main(String[] args) {
        DirectedGraphRepresentation graph = new DirectedGraphRepresentation(new In("src/com/directedgraph/tinyDG.txt"));
        int source = 2;
        DirectedDFSRepresentation reachable = new DirectedDFSRepresentation(graph, source);
        for (int v = 0; v < graph.getVertices(); v++) {
            if (reachable.marked(v))
                StdOut.println(v + " ");
            StdOut.println();
        }
    }
}
