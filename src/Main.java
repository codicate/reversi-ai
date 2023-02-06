import java.util.Scanner;

public class Main {
    // static function to convert the color code to the player name
    public static String convertToPlayer(int color) {
        return color == 1 ? "LIGHT/O" : "DARK/X";
    }

    public static int switchPlayer(int currentPlayer) {
        return 3 - currentPlayer;
    }

    public static void printWinner(Board board) {
        System.out.println();
        System.out.println("Game Over");
        //print the winner
        int winner = board.getWinner();
        if (winner == 0) {
            System.out.println("Tie game");
        } else {
            if (winner == 1) {
                System.out.println("Winner:" + convertToPlayer(winner));
            }
        }
    }

    public static void main(String[] args) {
        //track current player
        int currentPlayer = 2;
        //track consecutive passes, if there are two passes in a row, the game is over
        int passes = 0;

        Scanner input = new Scanner(System.in);
        Board board = new Board(4);
        board.print();

       //who is the human player?
       System.out.println("Do you want to play DARK (X) or LIGHT (O)?");
       String choice = input.nextLine();
       int humanPlayer = choice.toUpperCase().charAt(0) == 'X' ? 2 : 1;

        while (true) {
            //check if there are any legal moves for the current player
            boolean legalMoves = false;
            for (int i = 0; i < board.getSize(); i++) {
                for (int j = 0; j < board.getSize(); j++) {
                    if (board.legalMove(i, j, currentPlayer)) {
                        legalMoves = true;
                    }
                }
            }

            //if there are no legal moves, switch players and pass move
            if (!legalMoves) {
                System.out.println("No legal moves for player " + convertToPlayer(currentPlayer) + ", passing move");
                currentPlayer = switchPlayer(currentPlayer);
                passes++;

                //if there are two consecutive passes, put the board in a pass state
                if (passes == 2) {
                    System.out.println("No legal moves for either player");
                    printWinner(board);
                    return;
                }
            } else {
                passes = 0;
                System.out.println();
                System.out.println("Next to play: " + convertToPlayer(currentPlayer));
                String move;
                //if the current player is the human player, prompt for a move
                if (currentPlayer == humanPlayer) {
                    System.out.print("Your Move (? for help): ");
                    input = new Scanner(System.in);
                    move = input.nextLine();
                } else {
                    System.out.print("Your Move (? for help): ");
                    input = new Scanner(System.in);
                    move = input.nextLine();
//                    move = board.getNextMove(currentPlayer, 1);
                }

                //check if the user wants help
                if (move.length() == 1 && move.charAt(0) == '?') {
                    System.out.println("Enter moves as COLUMN then LETTER.");
                    System.out.println("For example, \"a1\" is the top left corner.");
                    System.out.println("Enter \"pass\" (without the quotes) if you have no legal moves.");
                    // Skip the rest of the loop, go back to the beginning
                    continue;
                }
                if (move.length() != 2) {
                    System.out.println("Invalid move");
                } else {
                    //print the move in format
                    System.out.println(convertToPlayer(currentPlayer)+ " @ " + move);
                    int row = Character.getNumericValue(move.charAt(1)) - 1;
                    //if lowercase column input, convert to uppercase
                    if (move.charAt(0) >= 97) {
                        move = move.substring(0, 1).toUpperCase() + move.substring(1);
                    }
                    int col = Character.getNumericValue(move.charAt(0)) - 10;
                    //check if the move is legal
                    if (board.legalMove(row, col, currentPlayer)) {
                        board.makeMove(row, col, currentPlayer);
                        board.print();
                        System.out.println();
                        //switch players
                        if (currentPlayer == 1) {
                            currentPlayer = 2;
                        } else {
                            currentPlayer = 1;
                        }
                    } else {
                        System.out.println("Invalid move - Not a legal move or cell is occupied or out of bounds");
                    }
                }
            }
        }
    }
}