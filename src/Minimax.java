public class Minimax {
    // Either return 0 or infinity for terminal node utility
    public static int utility(Board board, int color) {
        int winner = board.getWinner();
        if (winner == color) {
            return 0;
        } else {
            return Integer.MAX_VALUE;
        }
    }

    public static int[] minimax(Board board, int color) {
        int[] bestMove = new int[2];
        int bestScore = Integer.MIN_VALUE;

        // Find all legal moves and recurse on each one
        for (int row = 0; row < board.getSize(); row++) {
            for (int col = 0; col < board.getSize(); col++) {
                if (board.legalMove(row, col, color)) {
                    Board newBoard = new Board(board);
                    newBoard.makeMove(row, col, color);

                    // Use the utility function to pick the best next move
                    int score = recurse(newBoard, 3 - color);
                    if (score > bestScore) {
                        bestScore = score;
                        bestMove[0] = row;
                        bestMove[1] = col;
                    }
                }
            }
        }
        return bestMove;
    }

    public static int recurse(Board board, int color) {
        // If there's no legal move, then it must be a terminal node, so return the utility
        if (!board.hasLegalMoves(color)) {
            return utility(board, color);
        }

        // Recurse on all legal moves and return the best score
        int bestScore = Integer.MIN_VALUE;
        for (int row = 0; row < board.getSize(); row++) {
            for (int col = 0; col < board.getSize(); col++) {
                if (board.legalMove(row, col, color)) {
                    Board newBoard = new Board(board);
                    newBoard.makeMove(row, col, color);
                    int score = recurse(newBoard, 3 - color);
                    if (score > bestScore) {
                        bestScore = score;
                    }
                }
            }
        }
        return bestScore;
    }
}
