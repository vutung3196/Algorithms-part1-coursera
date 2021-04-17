package com.undirectedgraph.DFSApplications;

import com.undirectedgraph.GraphRepresentation;
import edu.princeton.cs.algs4.In;

public class ConnectedComponents {
    private boolean[] marked;
    private int[] id;           // id[v] = id of component containing vertex v
    private int count;

    public ConnectedComponents(GraphRepresentation graph) {
        marked = new boolean[graph.getVertices()];
        id = new int[graph.getVertices()];
        for (int vertex = 0; vertex < graph.getVertices(); vertex++) {
            if (!marked[vertex]) {
                dfs(graph, vertex);
                count++;
            }
        }
    }

    private void dfs(GraphRepresentation graph, int vertex) {
        marked[vertex] = true;
        id[vertex] = count;
        for (int w : graph.adjacent(vertex)) {
            if (!marked[w])
                dfs(graph, w);
        }
    }

    boolean connected(int v, int w) {
        return id[v] == id[w];
    }

    int count() {
        return count;
    }

    public int id(int v) {
        return id[v];
    }

    public static void main(String[] args) {
        In in = new In("src/com/undirectedgraph/connectedComponent.txt");
        GraphRepresentation graph = new GraphRepresentation(in);
        var cc = new ConnectedComponents(graph);
        System.out.println(cc.count);
        System.out.println(cc.connected(4, 5));
    }
}
