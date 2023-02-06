public class HMinimax {
    public static int utility(Board board, int color) {
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
                    int score = recurse(newBoard, 3 - color, depth);
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

    public static int recurse(Board board, int color, int depth) {
        if (depth == 0) {
            return utility(board, color);
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
