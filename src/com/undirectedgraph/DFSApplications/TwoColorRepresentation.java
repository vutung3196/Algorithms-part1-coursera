package com.undirectedgraph.DFSApplications;

import com.undirectedgraph.GraphRepresentation;

/**
 * Two-colorability. Support this query: Can the vertices of a given graph be assigned
 * one of two colors in such a way that no edge connects vertices of the same color ?
 * which is equivalent to this question: Is the graph bipartite ?
 */
public class TwoColorRepresentation {
    private boolean[] marked;
    private boolean[] color;
    private boolean isTwoColorable = true;

    public TwoColorRepresentation(GraphRepresentation graph) {
        marked = new boolean[graph.getVertices()];
        color = new boolean[graph.getVertices()];
        for (int sourceVertex = 0; sourceVertex < graph.getVertices(); sourceVertex++) {
            if (!marked[sourceVertex]) {
                dfs(graph, sourceVertex);
            }
        }
    }

    // dfs is here
    private void dfs(GraphRepresentation graph, int sourceVertex) {
        marked[sourceVertex] = true;
        for (int w : graph.adjacent(sourceVertex)) {
            if (!marked[w]) {
                color[w] = !color[sourceVertex];
                dfs(graph, w);
            } else if (color[w] == color[sourceVertex]) isTwoColorable = false;
        }
    }

    public boolean isBipartite() {
        return isTwoColorable;
    }
}
