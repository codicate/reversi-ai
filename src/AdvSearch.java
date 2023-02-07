public interface AdvSearch {
    public int utility(Board board, int color);
    public int[] minimax(Board board, int color);
    public int recurse(Board board, int color);
}
