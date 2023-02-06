//Agent to play the game of Othello using the minimax algorithm (full adversarial search, no fixed depth)

import java.util.ArrayList;

public class Minimax implements NextMove{
    private int humanColor;
    public Board board;

    public Minimax(int color) {
        this.humanColor = color;
    }


    public static String getNextMove(Board board, int color) {
        board = board;
        int[] move = minimax(board, color);
        // return the move as a string in the accepted format
        return String.valueOf((char) (move[1] + 65)) + (move[2] + 1);
    }


    public static int utility(Board board, int color) {
        // get the winner of the board and return the utility value
        int winner = board.getWinner();
        if (winner == color){
            return 1;
        } else if (winner == 0){
            return 0;
        } else {
            return -1;
        }
    }

    public static int[] minimax(Board board, int color) {
        // if the game is over, return the utility value
        if (board.getWinner() != 0){
            return new int[]{utility(board, color), -1, -1};
        }
        // if the game is not over, then get the next possible moves
        ArrayList<int[]> nextMoves = board.getNextMoves(color);
        // if there are no possible moves, then return the utility value
        if (nextMoves.size() == 0){
            return new int[]{utility(board, color), -1, -1};
        }
        // if there are possible moves, then get the best move
        int bestMove = -1;
        int bestValue = -2;
        for (int i = 0; i < nextMoves.size(); i++){
            // for each possible move, create a new board and make the move
            Board newBoard = new Board(board.getSize());
            newBoard.makeMove(nextMoves.get(i)[0], nextMoves.get(i)[1], color);
            // get the value of the move
            int value = minimax(newBoard, 3 - color)[0];
            // if the value is better than the best value, then update the best value and best move
            if (value > bestValue){
                bestValue = value;
                bestMove = i;
            }
        }
        // return the best move
        return new int[]{bestValue, nextMoves.get(bestMove)[0], nextMoves.get(bestMove)[1]};


    }

}
