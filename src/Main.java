import java.util.Scanner;

public class Main {
    // static function to convert the color code to the player name
    public static String convertToPlayer(int color) {
        return color == 1 ? "LIGHT/O" : "DARK/X";
    }

    public static int switchPlayer(int currentPlayer) {
        return 3 - currentPlayer;
    }

    public static int[] stringToMove(String input) {
        int[] result = new int[2];

        int row = Character.getNumericValue(input.charAt(1)) - 1;
        //if lowercase column input, convert to uppercase
        if (input.charAt(0) >= 97) {
            input = input.substring(0, 1).toUpperCase() + input.substring(1);
        }
        int col = Character.getNumericValue(input.charAt(0)) - 10;

        result[0] = row;
        result[1] = col;
        return result;
    }

    public static String moveToString(int[] move) {
        return (char) (move[1] + 65) + Integer.toString(move[0] + 1);
    }

    public static void printWinner(Board board) {
        System.out.println();
        System.out.println("Game Over");
        //print the winner
        int winner = board.getWinner();
        if (winner == 0) {
            System.out.println("Tie game");
        } else {
            System.out.println("Winner:" + convertToPlayer(winner));
        }
    }

    public static void main(String[] args) {
        //track current player
        int currentPlayer = 2;
        //track consecutive passes, if there are two passes in a row, the game is over
        int passes = 0;
        int size;
        boolean useHeuristics = false;

        Scanner scanner = new Scanner(System.in);
        String choice;

        System.out.println("Choose your game:");
        System.out.println("1. Small 4x4 Reversi without heuristics and alpha-beta pruning");
        System.out.println("2. Large 8x8 Reversi with heuristics and alpha-beta pruning");
        System.out.print("Your choice: ");
        choice = scanner.nextLine();
        switch (choice) {
            case "1" -> {
                size = 4;
            }
            case "2" -> {
                size = 8;
                useHeuristics = true;
            }
            default -> {
                System.out.println("Invalid choice");
                return;
            }
        }

        //who is the human player?
        System.out.println("Do you want to play DARK (X) or LIGHT (O)?");
        choice = scanner.nextLine();
        int humanPlayer = choice.toUpperCase().charAt(0) == 'X' ? 2 : 1;

        Board board = new Board(size);
        board.print();

        while (true) {
            //check if there are any legal moves for the current player
            boolean legalMoves = board.hasLegalMoves(currentPlayer);

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
                String input;
                int[] move;

                System.out.println();
                System.out.println("Next to play: " + convertToPlayer(currentPlayer));

                //if the current player is the human player, prompt for a move
                if (currentPlayer == humanPlayer) {
                    System.out.print("Your Move (? for help): ");
                    input = scanner.nextLine();

                    //check if the user wants help
                    if (input.length() == 1 && input.charAt(0) == '?') {
                        System.out.println("Enter moves as COLUMN then LETTER.");
                        System.out.println("For example, \"a1\" is the top left corner.");
                        System.out.println("Enter \"pass\" (without the quotes) if you have no legal moves.");
                        // Skip the rest of the loop, go back to the beginning
                        continue;
                    }

                    if (input.length() != 2) {
                        System.out.println("Invalid move");
                        continue;
                    }

                    move = stringToMove(input);
                } else {
                    move = board.getNextMove(currentPlayer, useHeuristics);
                    input = moveToString(move);
                }

                //print the move in format
                System.out.println(convertToPlayer(currentPlayer) + " @ " + input);

                //check if the move is legal
                if (board.legalMove(move[0], move[1], currentPlayer)) {
                    board.makeMove(move[0], move[1], currentPlayer);
                    board.print();
                    currentPlayer = switchPlayer(currentPlayer);
                } else {
                    System.out.println("Invalid move - Not a legal move or cell is occupied or out of bounds");
                }
            }
        }
    }
}