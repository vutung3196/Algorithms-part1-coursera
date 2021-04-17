package com.undirectedgraph;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;

public class DepthAndBreathFirstSearchExample {
    private enum SearchType {
        DFS,
        BFS
    }

    public boolean[] marked;
    private int count;

    public DepthAndBreathFirstSearchExample(GraphRepresentation graph, int sourceVertex, SearchType type) {
        marked = new boolean[graph.getVertices()];
        switch (type) {
            case DFS -> depthFirstSearch(graph, sourceVertex);
            case BFS -> breathFirstSearch(graph, sourceVertex);
        }
    }

    private void breathFirstSearch(GraphRepresentation graph, int sourceVertex) {
        marked[sourceVertex] = true;
        System.out.println("Found " + sourceVertex);
        Queue<Integer> queue = new Queue<>();
        queue.enqueue(sourceVertex);
        while (!queue.isEmpty()) {
            var root = queue.dequeue();
            for (int item : graph.adjacent(root)) {
                if (!marked[item]) {
                    System.out.println("Found " + item);
                    marked[item] = true;
                    queue.enqueue(item);
                }
            }
        }
    }

    private void depthFirstSearch(GraphRepresentation graph, int sourceVertex) {
        marked[sourceVertex] = true;
        System.out.println("Found " + sourceVertex);
        count++;
        for (int i : graph.adjacent(sourceVertex)) {
            if (!marked[i]) {
                depthFirstSearch(graph, i);
            }
        }
    }

    public static void main(String[] args) {
        In input = new In("src/com/undirectedgraph/cycleG.txt");
        var graph = new GraphRepresentation(input);
        var dfs = new DepthAndBreathFirstSearchExample(graph, 0, SearchType.DFS);
        System.out.println("Done");
        var bfs = new DepthAndBreathFirstSearchExample(graph, 0, SearchType.BFS);
    }
}
