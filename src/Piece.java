// Each black and white piece is a Piece object

public class Piece {
    private int row;
    private int col;
    //white = 1, black = 2
    private int color;

    public Piece(int row, int col, int color) {
        this.row = row;
        this.col = col;
        this.color = color;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
