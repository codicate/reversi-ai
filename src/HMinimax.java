public class HMinimax {
    public static int heuristic(Board board, int color) {
        int score = 0;
        for (int row = 0; row < board.getSize(); row++) {
            for (int col = 0; col < board.getSize(); col++) {
                if (board.getCell(row, col) == color) {
                    score++;
                }
            }
        }
        return score;
    }

    public static int[] minimax(Board board, int color, int depth) {
        int[] bestMove = new int[2];
        int bestScore = Integer.MIN_VALUE;

        // Find all legal moves and recurse on each one
        for (int row = 0; row < board.getSize(); row++) {
            for (int col = 0; col < board.getSize(); col++) {
                if (board.legalMove(row, col, color)) {
                    Board newBoard = new Board(board);
                    newBoard.makeMove(row, col, color);

                    // Use the heuristic function to pick the best next move
                    int score = recurse(newBoard, 3 - color, depth, Integer.MIN_VALUE, Integer.MAX_VALUE);
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

    // recursion with fixed depth cutoff and alpha beta pruning
    public static int recurse(Board board, int color, int depth, int alpha, int beta) {
        // if depth is 0, meaning depth is reached, then return the heuristic
        if (depth == 0) {
            return heuristic(board, color);
        }

        // if there's no legal move, then it must be a terminal node, so return the infinity because a winning move should always be chosen
        if (!board.hasLegalMoves(color)) {
            int winner = board.getWinner();
            if (winner == color) {
                return 0;
            } else {
                return Integer.MAX_VALUE;
            }
        }

        // Recurse on all legal moves and return the best score
        int bestScore = Integer.MIN_VALUE;
        for (int row = 0; row < board.getSize(); row++) {
            for (int col = 0; col < board.getSize(); col++) {
                if (board.legalMove(row, col, color)) {
                    Board newBoard = new Board(board);
                    newBoard.makeMove(row, col, color);
                    int score = recurse(newBoard, 3 - color, depth - 1, alpha, beta);

                    // if score is greater than bestScore, then update bestScore
                    if (score > bestScore) {
                        bestScore = score;
                    }

                    // if bestScore is greater than alpha, then update alpha
                    if (bestScore > alpha) {
                        alpha = bestScore;
                    }

                    // if alpha is greater than beta, then prune the rest of the tree
                    if (alpha >= beta) {
                        return bestScore;
                    }
                }
            }
        }
        return bestScore;
    }
}
