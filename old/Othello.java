import java.util.Scanner;

//TODO: add comments to the code


// The main class to play the game
// The legend for the color of the pieces: empty = 0, white = 1, black = 2
public class Othello {
    // game over logic - board is either completely full or nobody has any legal moves
    public boolean gameOver(Board gameBoard) {
        for (int i = 0; i < gameBoard.getSize(); i++) {
            for (int j = 0; j < gameBoard.getSize(); j++) {
                if (gameBoard.getCell(i, j) == 0) {
                    return false;
                }
            }
        }
        //check if there are any legal moves for either player
        for (int i = 0; i < gameBoard.getSize(); i++) {
            for (int j = 0; j < gameBoard.getSize(); j++) {
                if (legalMove(gameBoard, i, j, 1) || legalMove(gameBoard, i, j, 2)) {
                    return false;
                }
            }
        }
        return true;
    }



    public static void main(String[] args) {
        //driver code
        Othello game = new Othello();
        Board gameBoard = game.createGameBoard(4);
        game.printBoard(gameBoard);
        System.out.println();
        //who is the human player?
        System.out.println("Do you want to play DARK (X) or LIGHT (O)?");
        Scanner input = new Scanner(System.in);
        String humanPlayer = input.nextLine();
        //track current player
        int currentPlayer = 2;
        //track consecutive passes, if there are two passes in a row, the game is over
        int passes = 0;
        //game loop
        while (!game.gameOver(gameBoard)) {
            //check if there are any legal moves for the current player
            boolean legalMoves = false;
            for (int i = 0; i < gameBoard.getSize(); i++) {
                for (int j = 0; j < gameBoard.getSize(); j++) {
                    if (game.legalMove(gameBoard, i, j, currentPlayer)) {
                        legalMoves = true;
                    }
                }
            }
            //if there are no legal moves, switch players and pass move
            if (!legalMoves) {
                System.out.println("No legal moves for player " + convertToPlayer(currentPlayer) + ", passing move");
                if (currentPlayer == 1) {
                    currentPlayer = 2;
                } else {
                    currentPlayer = 1;
                }
                passes++;
                //if there are two consecutive passes, put the board in a pass state
                if (passes == 2) {
                    System.out.println("No legal moves for either player");
                    //break out of the game loop
                    break;
                }
            }
            //if there are legal moves, prompt the user for a move
            else {
                passes = 0;
                System.out.println("Next to play: " + convertToPlayer(currentPlayer));
                System.out.println();
                String move;
                //if the current player is the human player, prompt for a move
                if (convertToPlayer(currentPlayer).charAt(convertToPlayer(currentPlayer).length()-1) == humanPlayer.charAt(0)) {
                    System.out.print("Your Move (? for help): ");
                    input = new Scanner(System.in);
                    move = input.nextLine();
                }
                //if the current player is the computer, request a move from the getNextMove method
                else {
                    move = game.getNextMove(gameBoard, currentPlayer, 1);
                }

                //check if the user wants help
                if (move.length() == 1 && move.charAt(0) == '?') {
                    System.out.println("Enter moves as COLUMN then LETTER.");
                    System.out.println("For example, \"a1\" is the top left corner.");
                    System.out.println("Enter \"pass\" (without the quotes) if you have no legal moves.");
                    // Skip the rest of the loop, go back to the beginning
                    continue;
                }
                //check if the move is valid
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
                    if (game.legalMove(gameBoard, row, col, currentPlayer)) {
                        game.makeMove(gameBoard, row, col, currentPlayer);
                        game.printBoard(gameBoard);
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
