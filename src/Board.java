public class Board {
    private Piece[][] board;
    private int size;

    public Board(int size) {
        this.size = size;
        board = new Piece[size][size];
        this.init();
    }

    public Board(Board board) {
        this.size = board.getSize();
        this.board = new Piece[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board.getCell(i, j) != 0) {
                    this.setCell(i, j, board.getCell(i, j));
                }
            }
        }
    }

    private void init() {
        this.clearBoard();
        this.setCell(size / 2 - 1, size / 2 - 1, 1);
        this.setCell(size / 2, size / 2, 1);
        this.setCell(size / 2 - 1, size / 2, 2);
        this.setCell(size / 2, size / 2 - 1, 2);

//        this.setCell(0, 0, 1);
//        this.setCell(0, 1, 1);
//        this.setCell(0, 2, 1);
//        this.setCell(0, 3, 2);
//        this.setCell(1, 0, 1);
//        this.setCell(2, 0, 1);
//        this.setCell(1, 3, 1);
//        this.setCell(2, 3, 1);
//        this.setCell(3, 1, 2);
//        this.setCell(3, 2, 2);
//        this.setCell(3, 3, 1);
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

    public void print() {
        System.out.print("  ");
        for (int i = 0; i < this.size; i++) {
            System.out.print((char) (i + 65) + " ");
        }
        System.out.println();

        for (int i = 0; i < this.size; i++) {
            System.out.print(i + 1 + " ");
            for (int j = 0; j < this.size; j++) {
                if (this.getCell(i, j) == 0) {
                    System.out.print("- ");
                } else if (this.getCell(i, j) == 1) {
                    System.out.print("O ");
                } else if (this.getCell(i, j) == 2) {
                    System.out.print("X ");
                }
            }
            System.out.print(" " + (i + 1));
            System.out.println();
        }

        System.out.print("  ");
        for (int i = 0; i < this.size; i++) {
            System.out.print((char) (i + 65) + " ");
        }
        System.out.println();
    }

    public int getWinner() {
        int whiteCount = 0;
        int blackCount = 0;
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                if (this.getCell(i, j) == 1) {
                    whiteCount++;
                } else if (this.getCell(i, j) == 2) {
                    blackCount++;
                }
            }
        }

        if (whiteCount > blackCount) {
            return 1;
        } else if (blackCount > whiteCount) {
            return 2;
        } else {
            return 0;
        }
    }

    public boolean legalMove(int row, int col, int color) {
        //check if the cell is empty and within bounds
        if (this.getCell(row, col) != 0) {
            return false;
        }
        if (row < 0 || row >= this.size || col < 0 || col >= this.size) {
            return false;
        }
        //check if there is a piece of the opposite color in any of the 8 directions
        for (int rowCheck = -1; rowCheck <= 1; rowCheck++) {
            for (int colCheck = -1; colCheck <= 1; colCheck++) {
                if (this.getCell(row + rowCheck, col + colCheck) == 3 - color) {
                    //if there is, check if there is a piece of the same color in the same direction
                    int rowCheck2 = rowCheck;
                    int colCheck2 = colCheck;
                    while (this.getCell(row + rowCheck2, col + colCheck2) == 3 - color) {
                        rowCheck2 += rowCheck;
                        colCheck2 += colCheck;
                    }
                    if (this.getCell(row + rowCheck2, col + colCheck2) == color) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean hasLegalMoves(int color) {
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j <  this.size; j++) {
                if (this.legalMove(i, j, color)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void makeMove(int row, int col, int color) {
        //check if the move is legal
        if (this.legalMove(row, col, color)) {
            //if it is, set the cell to the color of the piece
            this.setCell(row, col, color);
            //check in all 8 directions for pieces of the opposite color
            for (int rowCheck = -1; rowCheck <= 1; rowCheck++) {
                for (int colCheck = -1; colCheck <= 1; colCheck++) {
                    if (this.getCell(row + rowCheck, col + colCheck) == 3 - color) {
                        //if there is, check if there is a piece of the same color in the same direction
                        int rowCheck2 = rowCheck;
                        int colCheck2 = colCheck;
                        while (this.getCell(row + rowCheck2, col + colCheck2) == 3 - color) {
                            rowCheck2 += rowCheck;
                            colCheck2 += colCheck;
                        }
                        if (this.getCell(row + rowCheck2, col + colCheck2) == color) {
                            //if there is, change all the pieces of the opposite color to the same color
                            while (this.getCell(row + rowCheck, col + colCheck) == 3 - color) {
                                this.switchColor(row + rowCheck, col + colCheck);
                                rowCheck += rowCheck;
                                colCheck += colCheck;
                            }
                        }
                    }
                }
            }
        }
    }


    // call the appropriate AI algorithm and get the next move
    public int[] getNextMove(int color, boolean useHeuristic) {
        if (useHeuristic) {
            return HMinimax.minimax(this, color, 8);
        } else {
            return Minimax.minimax(this, color);
        }
    }
}
