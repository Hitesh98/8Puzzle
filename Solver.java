import edu.princeton.cs.algs4.MinPQ;

import java.util.Comparator;

public class Solver {

    // The Node to store the Solution to the initial Board
    private Node solution = null;
    // The Initial Board Varaibale
    private final Board initial;
    // Variable to store the minimum number of moves required to solve the board
    private final int minimumNumberOfMoves;


    /**
     * Constructor of the Solver class which itialises the solution by
     * calling the isSolvable method, the minimum m=nuber of moves by
     * de-referencing the moves taken by the solution or -1 if no solution
     * exists. The only input parameter is the initial Board.
     * @param initial - The initial Board to Solve.
     */
    public Solver(Board initial) {
        this.initial = initial;
        if (this.isSolvable())
            minimumNumberOfMoves = this.solution.movesToBoard;
        else
            minimumNumberOfMoves = -1;
    }


    /**
     * Private class that returns the Comparator for the Priority Queue
     * @return A Comparator of Nodes.
     */
    private Comparator<Node> getComparator() {
        class QueueCompare implements Comparator<Node> {
            @Override
            public int compare(Node o1, Node o2) {
                if (o1.manPrior > o2.manPrior) return 1;
                else if (o1.manPrior < o2.manPrior) return -1;
                else if (o1.hamPrior > o2.hamPrior) return 1;
                else if (o1.hamPrior < o2.hamPrior) return -1;
                else if (o1.manPrior - o1.movesToBoard > o2.manPrior - o2.movesToBoard) return 1;
                else if (o1.manPrior - o1.movesToBoard < o2.manPrior - o2.movesToBoard) return -1;
                else if (o1.hamPrior - o1.movesToBoard > o2.hamPrior - o2.movesToBoard) return 1;
                else if (o1.hamPrior - o1.movesToBoard < o2.hamPrior - o2.movesToBoard) return -1;
                return 1;
            }
        }
        return new QueueCompare();
    }

    /**
     * Method to determine wheather the given initial Board is solvable
     * or not by solving it and its twin simulatenously using the A*
     * algorithm.
     * @return True if solution if reached by the Main Queue i.e. the
     * initial Board and False if it is reached by the twin. The method
     * also sets the global Varaible Solution accordingly.
     */
    public boolean isSolvable() {
        Board twin = initial.twin();
        MinPQ<Node> mainQueue = new MinPQ<>(getComparator());
        MinPQ<Node> twinQueue = new MinPQ<>(getComparator());
        mainQueue.insert(new Node(initial, 0, null));
        twinQueue.insert(new Node(twin, 0, null));
        while (this.solution == null) {
            Node temp = mainQueue.delMin();
            if (temp.board.isGoal()) {
                this.solution = temp;
                return true;
            }
            Iterable<Board> nebrs = temp.board.neighbors();
            for (Board x : nebrs) {
                if (!x.equals(temp.previousNode.board)) {
                    mainQueue.insert(new Node(x, temp.movesToBoard + 1, temp));
                }
            }
            Node temp2 = twinQueue.delMin();
            if (temp2.board.isGoal()) {
                this.solution = null;
                return false;
            }
            Iterable<Board> nebrs2 = temp2.board.neighbors();
            for (Board x : nebrs2) {
                if (!x.equals(temp2.previousNode.board)) {
                    twinQueue.insert(new Node(x, temp2.movesToBoard + 1, temp2));
                }
            }
        }
        return false;
    }


    public int moves() {
        return this.minimumNumberOfMoves;
    }

    public Iterable<Board> solution() {
        return null;
    }

    public static void main(String[] args) {

    }

    private class Node {
        private Board board;
        private int movesToBoard;
        private Node previousNode;
        private int manPrior;
        private int hamPrior;

        public Node(final Board board, final int movesToBoard, final Node previousNode) {

            this.board = board;
            this.movesToBoard = movesToBoard;
            this.previousNode = previousNode;
            this.manPrior = board.manhattan() + movesToBoard;
            this.hamPrior = board.hamming() + movesToBoard;
        }
    }
}
