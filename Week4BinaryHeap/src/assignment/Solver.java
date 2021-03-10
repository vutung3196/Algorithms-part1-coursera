package assignment;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Stack;

public class Solver {
//    private final MinPQ<SolverStepNode> prioritizedSteps;
    private boolean isSolvable;
    private Stack<Board> solutionBoards = new Stack<>();
    private SolverStepNode finalNode;

    private class SolverStepNode {
        private int moves;
        private Board board;
        private SolverStepNode previousNode;

        public SolverStepNode(Board board, int moves, SolverStepNode previousNode) {
            this.moves = moves;
            this.board = board;
            this.previousNode = previousNode;
        }

        public int getMoves() {
            return moves;
        }

        public Board getBoard() {
            return board;
        }

        public SolverStepNode getPreviousNode() {
            return previousNode;
        }

        public int getPriority() {
            return board.manhattan() + moves;
        }
    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        MinPQ<SolverStepNode> priorizedSteps = new MinPQ<>(new SolverStepComparator());
        priorizedSteps.insert(new SolverStepNode(initial, 0, null));

        MinPQ<SolverStepNode> priorizedStepsTwin = new MinPQ<>(new SolverStepComparator());
        priorizedStepsTwin.insert(new SolverStepNode(initial.twin(), 0, null));

        SolverStepNode step;
        while (!priorizedSteps.min().getBoard().isGoal() && !priorizedStepsTwin.min().getBoard().isGoal()) {
            step = priorizedSteps.delMin();
            for (Board neighbor : step.getBoard().neighbors()) {
                if (!isAlreadyInSolutionPath(step, neighbor)) {
                    priorizedSteps.insert(new SolverStepNode(neighbor, step.getMoves() + 1, step));
                }
            }

            SolverStepNode stepTwin = priorizedStepsTwin.delMin();
            for (Board neighbor : stepTwin.getBoard().neighbors()) {
                if (!isAlreadyInSolutionPath(stepTwin, neighbor)) {
                    priorizedStepsTwin.insert(new SolverStepNode(neighbor, stepTwin.getMoves() + 1, stepTwin));
                }
            }
        }
        step = priorizedSteps.delMin();
        isSolvable = step.getBoard().isGoal();

        solutionBoards.add(step.getBoard());
        while ((step = step.getPreviousNode()) != null) {
            solutionBoards.add(0, step.getBoard());
        }
    }

    private boolean isAlreadyInSolutionPath(SolverStepNode lastStep, Board board) {
        SolverStepNode previousStep = lastStep;
        while ((previousStep = previousStep.getPreviousNode()) != null) {
            if (previousStep.getBoard().equals(board)) {
                return true;
            }
        }
        return false;
    }

    // is the initial board solvable
    public boolean isSolvable() {
        return isSolvable;
    }

    // min number of moves
    public int moves() {
        if (!isSolvable) return -1;
        else {
            return solutionBoards.size() - 1;
        }
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        Iterable<Board> iterable;
        if (isSolvable()) {
            iterable = new Iterable<Board>() {
                @Override
                public Iterator<Board> iterator() {
                    return new SolutionIterator();
                }
            };
        } else {
            iterable = null;
        }
        return iterable;
    }

    private static int numMoves(SolverStepNode node) {
        int moves = 0;
        SolverStepNode current = node;

        while (current.getPreviousNode() != null) {
            moves++;
            current = current.previousNode;
        }
        return moves;
    }

    private static class SolverStepComparator implements Comparator<SolverStepNode> {
        @Override
        public int compare(SolverStepNode step1, SolverStepNode step2) {
            return step1.getPriority() - step2.getPriority();
        }
    }

    private class SolutionIterator implements Iterator<Board> {
        private int index = 0;

        @Override
        public boolean hasNext() {
            return index < solutionBoards.size();
        }

        @Override
        public Board next() {
            return solutionBoards.get(index++);
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("It is not supported to remove a board from the solution.");
        }
    }

    public static void main(String[] args) {
        In in = new In("");
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);
        var solver = new Solver(initial);
        var solutions = solver.solution();
        System.out.println("Minimum number of moves: " + solver.moves());
        for (Board solution: solutions) {
            System.out.println("=======");
            StdOut.println(solution);
        }
    }
}
