// interface for determining the next move based on the current state of the game
public interface NextMove {
    // returns the next move as a string
    public static String getNextMove(Board board, int color) {
        return null;
    }

    public static int utility(Board board, int color) {
        return 0;
    }

    public static int[] minimax(Board board, int color) {
        return new int[0];
    }

}
