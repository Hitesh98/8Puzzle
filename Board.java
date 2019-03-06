import edu.princeton.cs.algs4.Stack;

import java.util.Arrays;

public class Board {

    private final char[][] board;
    private int manhattan;
    private int hamming;
    private int blankX;
    private int blankY;
    private final int len;

    /**
     * Initialisation of the original board
     * @param blocks the input for the intitial board
     */
    public Board(int[][] blocks) {
        this.len = blocks.length;
        this.board = new char[len*len][];
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                this.board[i*len + j] = Integer.toString(blocks[i][j]).toCharArray();
                if (blocks[i][j] != (len*i + j + 1)) this.hamming++;
                int temp = blocks[i][j];
                if (temp != 0) {
                    this.manhattan += Math.abs((temp - 1) / len - i);
                    this.manhattan += Math.abs((temp - 1) % len - j);
                } else {
                    this.blankX = i;
                    this.blankY = j;
                }
            }
        }
        // To account for the zero, we need to subtract 1.
        this.hamming--;
    }

    /**
     * @return the length of the board
     */
    public int dimension() {
        return this.len;
    }

    /**
     * Generic Method to calculate the hamming priority of the board.
     * @return The hamming priority
     */
    public int hamming() {
        return this.hamming;
    }

    /**
     * Generic Method to calculate the mahattan priority of the board.
     * @return The Manhattan priority.
     */
    public int manhattan() {
        return this.manhattan;
    }

    /**
     * Method to check if the current board is the goal or not.
     * @return <true> if the board is the goal.
     */
    public boolean isGoal() {
        return (this.hamming == 0);
    }

    /**
     * Method that returns a board that is obtained by exchanging any
     * pair of blocks on the current board. This is useful in checking
     * weather a board is solvable or not. Only one of the current Board
     * and its twin are solvable.
     * @return A new board that is obtained by exchanging any pair of blocks
     */
    public Board twin() {
        int x1 = -1, y1 = -1, x2 = -1, y2;
        int[][] newBoard = new int[len][len];
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                String s = new String(this.board[i*len + j]);
                newBoard[i][j] = Integer.parseInt(s);
            }
        }
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                if (newBoard[i][j] != 0) {
                    if (x1 < 0) {
                        x1 = i;
                        y1 = j;
                    } else {
                        x2 = i;
                        y2 = j;
                        int temp = newBoard[x1][y1];
                        newBoard[x1][y1] = newBoard[x2][y2];
                        newBoard[x2][y2] = temp;
                        break;
                    }
                }
            }
            if (x2 >= 0) break;
        }
        return new Board(newBoard);
    }

    /**
     * Compares the current Board to a guven Object y
     * @param y Given Object y
     * @return {@code true} if y is equal to the current Board
     */
    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board) y;
        if (this.dimension() != that.dimension()) return false;
        for (int i = 0; i < len*len; i++) {
            if (!Arrays.equals(this.board[i], that.board[i])) return false;
        }
        return true;
    }

    /**
     * Method to return all the neighouring boards
     * @return An Iterable list of Neihbouring Boards.
     */
    public Iterable<Board> neighbors() {
        Stack<Board> neighbours = new Stack<>();

        // Top Neighbour
        if (this.blankX - 1 >= 0) {
            int[][] temp = new int[len][len];
            for (int i = 0; i < len; i++) {
                for (int j = 0; j < len; j++) {
                    String s = new String(this.board[i*len + j]);
                    temp[i][j] = Integer.parseInt(s);
                }
            }
            int neighbourVal = temp[this.blankX - 1][this.blankY];
            temp[this.blankX - 1][this.blankY] = 0;
            temp[this.blankX][this.blankY] = neighbourVal;
            neighbours.push(new Board(temp));
        }

        // Bottom Neighbour
        if (this.blankX + 1 < len) {
            int[][] temp = new int[len][len];
            for (int i = 0; i < len; i++) {
                for (int j = 0; j < len; j++) {
                    String s = new String(this.board[i*len + j]);
                    temp[i][j] = Integer.parseInt(s);
                }
            }
            int neighbourVal = temp[this.blankX + 1][this.blankY];
            temp[this.blankX + 1][this.blankY] = 0;
            temp[this.blankX][this.blankY] = neighbourVal;
            neighbours.push(new Board(temp));
        }

        // Right Neighbour
        if (this.blankY + 1 < len) {
            int[][] temp = new int[len][len];
            for (int i = 0; i < len; i++) {
                for (int j = 0; j < len; j++) {
                    String s = new String(this.board[i*len + j]);
                    temp[i][j] = Integer.parseInt(s);
                }
            }
            int neighbourVal = temp[this.blankX][this.blankY + 1];
            temp[this.blankX][this.blankY + 1] = 0;
            temp[this.blankX][this.blankY] = neighbourVal;
            neighbours.push(new Board(temp));
        }

        // Left Neighbour
        if (this.blankY - 1 >= 0) {
            int[][] temp = new int[len][len];
            for (int i = 0; i < len; i++) {
                for (int j = 0; j < len; j++) {
                    String s = new String(this.board[i*len + j]);
                    temp[i][j] = Integer.parseInt(s);
                }
            }
            int neighbourVal = temp[this.blankX][this.blankY - 1];
            temp[this.blankX][this.blankY - 1] = 0;
            temp[this.blankX][this.blankY] = neighbourVal;
            neighbours.push(new Board(temp));
        }
        return neighbours;
    }

    /**
     * Generic Method to represent the Board as a String
     * @return String Representation of this Board
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(len + "\n");
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                s.append(new String(this.board[i*len + j]));
                s.append(" ");
            }
            s.append("\n");
        }
        return s.toString();
    }

    public static void main(String[] args) {
        int[][] array = {{0, 1, 2, 3}, {5, 6, 7, 4}, {9, 10, 11, 8}, {13, 14, 15, 12}};
        Board b = new Board(array);
        Iterable<Board> s = b.neighbors();
        System.out.println(b.twin());
        for (Board x : s) {
            System.out.println(x.toString());
        }
    }
}
