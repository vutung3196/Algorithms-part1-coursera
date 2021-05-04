package com.directedgraph;

import java.util.Stack;

public class DirectedCycleRepresentation {
    private boolean[] marked;
    private int[] edgeTo;
    private Stack<Integer> cycle;
    private boolean[] onStack;

    public DirectedCycleRepresentation(DirectedGraphRepresentation graph) {
        onStack = new boolean[graph.getVertices()];
        edgeTo = new int[graph.getVertices()];
        marked = new boolean[graph.getVertices()];
        for (int v = 0; v < graph.getVertices(); v++) {
            if (!marked[v])
                dfs(graph, v);
        }
    }

    private void dfs(DirectedGraphRepresentation graph, int v) {
        onStack[v] = true;
        marked[v] = true;
        for (int w : graph.adj(v))
            if (this.hasCycle()) return;
            else if (!marked[w]) {
                edgeTo[w] = v;
                dfs(graph, w);
            } else if (onStack[w]) {
                cycle = new Stack<Integer>();
                for (int x = v; x != w; x = edgeTo[x])
                    cycle.push(x);
                cycle.push(w);
                cycle.push(v);
            }
        onStack[v] = false;
    }

    public boolean hasCycle() {
        return cycle != null;
    }

    public Iterable<Integer> cycle() {
        return cycle;
    }

    public static void main(String[] args) {
    }
}
