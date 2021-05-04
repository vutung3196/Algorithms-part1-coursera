package com.undirectedgraph;

import java.util.Iterator;
import java.util.Stack;

public class NonRecursiveDFS {
    private boolean[] marked;

    public NonRecursiveDFS(GraphRepresentation graph, int s) {
        marked = new boolean[graph.getVertices()];
        Iterator<Integer>[] adj = (Iterator<Integer>[]) new Iterator[graph.getVertices()];
        for (int v = 0; v < graph.getVertices(); v++) {
            adj[v] = graph.adjacent(v).iterator();
        }

        // key
        Stack<Integer> stack = new Stack<>();
        marked[s] = true;
        stack.push(s);
        while (!stack.isEmpty()) {
            int v = stack.peek();
            if (adj[v].hasNext()) {
                int w = adj[v].next();
                if (!marked[w]) {
                    marked[w] = true;
                    stack.push(w);
                }
            } else {
                stack.pop();
            }
        }
    }
}
