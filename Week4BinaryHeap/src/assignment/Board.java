package assignment;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;

public class Board  {
    private final int[][] board;

    public Board(int[][] tiles) {
        board = new int[tiles.length][tiles.length];
        for (int row = 0; row < tiles.length; row++) {
            for (int column = 0; column < tiles.length; column++) {
                board[row][column] = tiles[row][column];
            }
        }
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        if (board == null) {
            throw new IllegalArgumentException();
        }
        // print the count
        stringBuilder.append(board[0].length);
        for (int i = 0; i < board.length; i++) {
            stringBuilder.append("\n");
            for (int j = 0; j < board[i].length; j++) {
                    stringBuilder.append(board[i][j]);
                    stringBuilder.append(" ");
                }
            }
        return stringBuilder.toString();
    }

    // number of tiles out of place
    public int hamming(){
        int count = 0;
        for (int row = 0; row < board.length; row++) {
            for (int column = 0; column < board.length; column++) {
                if (row == board.length - 1 && column == board.length - 1) {
                    break;
                }
                if (board[row][column] != board.length*row + 1 + column) {
                    count++;
                }
            }
        }
        return count;
    }

    public Iterable<Board> neighbors() {
        Stack<Board> neighbors = new Stack<Board>();
        // process here
        // construct a board from this.board
        int indexRowZero = 0, indexColumnZero = 0;
        for (int row = 0; row < board.length; row++) {
            for (int column = 0; column < board.length; column++) {
                if (board[row][column] == 0) {
                    indexRowZero = row;
                    indexColumnZero = column;
                    break;
                }
            }
        }

        // shift with left
        if (indexColumnZero > 0) {
            int nextColumn = indexColumnZero - 1;
            neighbors.push(new Board(getExchangedMatrix(board, indexRowZero, nextColumn, indexRowZero, indexColumnZero)));
        }

        // shift with right
        if (indexColumnZero < board.length - 1) {
            int nextColumn = indexColumnZero + 1;
            neighbors.push(new Board(getExchangedMatrix(board, indexRowZero, nextColumn, indexRowZero, indexColumnZero)));
        }

        // shift with up
        if (indexRowZero > 0) {
            int nextRow = indexRowZero - 1;
            neighbors.push(new Board(getExchangedMatrix(board, nextRow, indexColumnZero, indexRowZero, indexColumnZero)));
        }

        // shift with down
        if (indexRowZero < board.length - 1) {
            int nextRow = indexRowZero +1;
            neighbors.push(new Board(getExchangedMatrix(board, nextRow, indexColumnZero, indexRowZero, indexColumnZero)));
        }

        return neighbors;
    }

    private int[][] getExchangedMatrix(int[][] matrix, int row, int column, int updatedRow, int updatedColumn) {
        int[][] tempMatrix = new int[matrix.length][matrix.length];
        // copy array to avoid pass by reference
        for (int r = 0; r < matrix.length; r++) {
            for (int c = 0; c < matrix.length; c++) {
                tempMatrix[r][c] = matrix[r][c];
            }
        }
        int temp = tempMatrix[row][column];
        tempMatrix[row][column] = tempMatrix[updatedRow][updatedColumn];
        tempMatrix[updatedRow][updatedColumn] = temp;
        return tempMatrix;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int sum = 0;
        int N = board.length;
        for (int row = 0; row < N; row++) {
            for (int column = 0; column < N; column++) {
                if (board[row][column] == 0) {
                    continue;
                }
                if (board[row][column] != N*row + 1 + column) {
                    int goalIndexRow = (board[row][column] - 1)  / N;
                    int goalIndexColumn = (board[row][column] - 1) % N;
                    int distance = Math.abs(goalIndexRow - row) + Math.abs(goalIndexColumn - column);
                    sum += distance;
                }
            }
        }
        return sum;
    }

    // is this board the goal board?
    public boolean isGoal() {
        int N = board.length;
        for (int row = 0; row < N; row++) {
            for (int column = 0; column < N; column++) {
                if (row == board.length - 1 && column == N -1) break;
                if (board[row][column] != N*row + 1 + column) {
                    return false;
                }
            }
        }
        return true;
    }

    public int dimension() {
        return board.length;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        if (board[0][0] == 0) {
            var matrix = getExchangedMatrix(board, 1, 0, 1, 1);
            return new Board(matrix);
        } else if (board[0][1] == 0) {
            var matrix = getExchangedMatrix(board, 1, 0, 1, 1);
            return new Board(matrix);
        } else {
            var matrix = getExchangedMatrix(board, 0, 0, 0, 1);
            return new Board(matrix);
        }
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == null) {
            return false;
        }
        if (y.getClass() != this.getClass()) {
            return false;
        }
        if (((Board) y).board == null) {
            throw new IllegalArgumentException();
        }
        int[][] comparedBoard = ((Board) y).board;

        if (comparedBoard.length != this.board.length) {
            return false;
        }
        int N = this.board.length;
        for (int row = 0; row < N; row++) {
            for (int column = 0; column < N; column++) {
                if (this.board[row][column] != comparedBoard[row][column])
                    return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
//        int[][] arr = {
//                {8, 1, 3},
//                {4, 0, 2},
//                {7, 6, 5}
//        };
//        Board board = new Board(arr);
//        System.out.println(board.toString());
        In in = new In("D:\\VuTung\\Algorithms\\Algorithms-part1\\Week4BinaryHeap\\src\\assignment\\puzzle1.txt");
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board testBoard = new Board(tiles);
        for (Board element: testBoard.neighbors()) {
            System.out.println(element);
        }
//        System.out.println(board.hamming());
//        System.out.println("Sum: ");
//        System.out.println(board.manhattan());
//        System.out.println("=======");
//        for (Board element : board.neighbors()) {
//            System.out.println(element.toString());
//        }
    }
}
