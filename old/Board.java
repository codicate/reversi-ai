// The board for Othello

import java.util.ArrayList;

public class Board {
    private Piece[][] board;
    private int size;

    public Board(int size) {
        this.size = size;
        board = new Piece[size][size];
    }

    public int getSize() {
        return size;
    }

    public int getCell(int row, int col) {
        if (row < 0 || row >= size || col < 0 || col >= size) {
            return -1;
        }
        // check if piece is populated by piece and return color, if not then return 0
        if (board[row][col] != null) {
            return board[row][col].getColor();
        } else {
            return 0;
        }
    }

    public void switchColor(int row, int col) {
        if (board[row][col].getColor() == 1) {
            board[row][col].setColor(2);
        } else {
            board[row][col].setColor(1);
        }
    }

    public void setCell(int row, int col, int color) {
        board[row][col] = new Piece(row, col, color);
    }

    public void clearBoard() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j] = null;
            }
        }
    }

    // function to get next moves for a given player based on the current board
    public ArrayList<int[]> getNextMoves(int color) {
        ArrayList<int[]> nextMoves = new ArrayList<>();
        // Go through all the empty squares and check if opposite color pieces are in the 8 directions
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j] == null) {
                    // when found an empty square, check the 8 directions
                    for (int k = -1; k <= 1; k++) {
                        for (int l = -1; l <= 1; l++) {
                            if (k != 0 || l != 0) {
                                // check legality by going through squares in the direction
                                int row = i + k;
                                int col = j + l;
                                while (row >= 0 && row < size && col >= 0 && col < size) {
                                    if (board[row][col] != null) {
                                        if (board[row][col].getColor() != board[i][j].getColor()) {
                                            nextMoves.add(new int[]{i, j});
                                            break;
                                        } else {
                                            break;
                                        }
                                    }
                                    row += k;
                                    col += l;
                                }
                            }
                        }
                    }
                }
            }
        }
        return nextMoves;
    }

    // function to make a move on the board (this is used for the minimax algorithm)
    public void makeMove(int row, int col, int color) {
        // set the cell to the color
        board[row][col] = new Piece(row, col, color);
        // Go through all the empty squares and check if opposite color pieces are in the 8 directions
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i != 0 || j != 0) {
                    // check if opposite color pieces are in the 8 directions
                    int r = row + i;
                    int c = col + j;
                    while (r >= 0 && r < size && c >= 0 && c < size) {
                        if (board[r][c] != null) {
                            if (board[r][c].getColor() != board[row][col].getColor()) {
                                r += i;
                                c += j;
                            } else {
                                // if the color is the same, then flip the pieces
                                while (r != row || c != col) {
                                    r -= i;
                                    c -= j;
                                    switchColor(r, c);
                                }
                                break;
                            }
                        } else {
                            break;
                        }
                    }
                }
            }
        }
    }


    // function to check if a legal move exists for both players
    public boolean legalMoveExists(){
        // Go through all the empty squares and check if opposite color pieces are in the 8 directions
        for (int i = 0; i < size; i++){
            for (int j = 0; j < size; j++){
                if (board[i][j] == null){
                    // Check if opposite color pieces are in the 8 directions
                    for (int k = -1; k <= 1; k++){
                        for (int l = -1; l <= 1; l++){
                            if (k != 0 || l != 0){
                                // check if opposite color pieces are in the 8 directions
                                int row = i + k;
                                int col = j + l;
                                while (row >= 0 && row < size && col >= 0 && col < size){
                                    if (board[row][col] != null){
                                        if (board[row][col].getColor() != board[i][j].getColor()){
                                            return true;
                                        } else {
                                            break;
                                        }
                                    }
                                    row += k;
                                    col += l;
                                }
                            }
                        }

                    }
                }
            }
        }
        // if both are false, then return false
        return false;
    }

    // utility function to check the winner of the game
    public int getWinner() {
        // 1 = white, 2 = black, 0 = tie
        // check if there are any empty squares
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j] == null) {
                    // if there are empty squares, check if there are any legal moves for both players
                    if (legalMoveExists()) {
                        // Since there are legal moves, the game is not over so return 0
                        return 0;
                    }
                }
            }
        }
        // if there are no empty squares, then the game is over, so check the winner
        int whiteCount = 0;
        int blackCount = 0;
        // count the number of white and black pieces
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j] != null) {
                    if (board[i][j].getColor() == 1) {
                        whiteCount++;
                    } else {
                        blackCount++;
                    }
                }
            }
        }
        // return the winner
        if (whiteCount > blackCount) {
            return 1;
        } else if (blackCount > whiteCount) {
            return 2;
        } else {
            return 0;
        }
    }
}
