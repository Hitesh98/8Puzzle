import edu.princeton.cs.algs4.Stack;

public class Board {

    private final int[][] board;
    private int manhattan = 0;
    private int hamming = 0;
    private int blankX = 0;
    private int blankY = 0;
    private final int len;

    /**
     * Initialisation of the original board
     * @param blocks the input for the intitial board
     */
    public Board(int[][] blocks) {
        len = blocks.length;
        this.board = new int[len][len];
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                this.board[i][j] = blocks[i][j];
                if (this.board[i][j] != (len*i + j + 1)) this.hamming++;
                int temp = this.board[i][j];
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
        return len;
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
        int[][] newBoard = this.board;
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
            if (x2 > 0) break;
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
        int n = this.dimension();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (this.board[i][j] != that.board[i][j]) return false;
            }
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
                    temp[i][j] = this.board[i][j];
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
                    temp[i][j] = this.board[i][j];
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
                    temp[i][j] = this.board[i][j];
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
                    temp[i][j] = this.board[i][j];
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
                s.append(String.format("%2d ", this.board[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    public static void main(String[] args) {
        int[][] array = {{0, 3}, {2, 1}};
        Board b = new Board(array);
        System.out.println(b.toString());
        System.out.println(b.hamming() + " " + b.manhattan);
    }
}
