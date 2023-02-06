public class Board {
    private Piece[][] board;
    private int size;

    public Board(int size) {
        this.size = size;
        board = new Piece[size][size];
        this.init();
    }

    private void init() {
        this.clearBoard();
        this.setCell(size / 2 - 1, size / 2 - 1, 1);
        this.setCell(size / 2, size / 2, 1);
        this.setCell(size / 2 - 1, size / 2, 2);
        this.setCell(size / 2, size / 2 - 1, 2);
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
    }
}
