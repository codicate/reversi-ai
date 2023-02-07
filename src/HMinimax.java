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
        for (int row = 0; row < board.getSize(); row++) {
            for (int col = 0; col < board.getSize(); col++) {
                if (board.legalMove(row, col, color)) {
                    Board newBoard = new Board(board);
                    newBoard.makeMove(row, col, color);
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
        if (depth == 0) {
            return heuristic(board, color);
        }
        if (!board.hasLegalMoves(color)) {
            int winner = board.getWinner();
            if (winner == color) {
                return 0;
            } else {
                return Integer.MAX_VALUE;
            }
        }
        int bestScore = Integer.MIN_VALUE;
        for (int row = 0; row < board.getSize(); row++) {
            for (int col = 0; col < board.getSize(); col++) {
                if (board.legalMove(row, col, color)) {
                    Board newBoard = new Board(board);
                    newBoard.makeMove(row, col, color);
                    int score = recurse(newBoard, 3 - color, depth - 1, alpha, beta);
                    if (score > bestScore) {
                        bestScore = score;
                    }
                    if (bestScore > alpha) {
                        alpha = bestScore;
                    }
                    if (alpha >= beta) {
                        return bestScore;
                    }
                }
            }
        }
        return bestScore;
    }

    // recursion with fixed depth cutoff without alpha beta pruning
    public static int recurse(Board board, int color, int depth) {
        if (depth == 0) {
            return heuristic(board, color);
        }
        if (!board.hasLegalMoves(color)) {
            int winner = board.getWinner();
            if (winner == color) {
                return 0;
            } else {
                return Integer.MAX_VALUE;
            }
        }
        int bestScore = Integer.MIN_VALUE;
        for (int row = 0; row < board.getSize(); row++) {
            for (int col = 0; col < board.getSize(); col++) {
                if (board.legalMove(row, col, color)) {
                    Board newBoard = new Board(board);
                    newBoard.makeMove(row, col, color);
                    int score = recurse(newBoard, 3 - color, depth - 1);
                    if (score > bestScore) {
                        bestScore = score;
                    }
                }
            }
        }
        return bestScore;
    }
}
