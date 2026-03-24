package game;

import board.Board;
import pieces.Piece;
import utils.Position;

/**
 * Handle's the game's flow & functionality
 * Makes the board, alternates turns, checks if moves are correct and if the move is game-winning
 */
public class Game {

    /** The game's chessboard */
    private Board board;

    /** The white team's player */
    private Player whitePlayer;

    /** The black team's player */
    private Player blackPlayer;

    /** Keeps track of who's turn it currently is */
    private String currentTurn;

    /** Keeps track of if the game is ongoing */
    private boolean gameOver;

    /**
     * Creates a new game of chess
     * White goes first
     */
    public Game() {
        board = new Board();
        whitePlayer = new Player("white");
        blackPlayer = new Player("black");
        currentTurn = "white";
        gameOver = false;
    }

    /**
     * Starts the board up and prints a few starting lines
     */
    public void start() {
        board.initialize();
        System.out.println("Chess Project!!!! :D");
        System.out.println("Format: [START] [END]  e.g. E2 E4");
        System.out.println("Type 'resign' to forfeit.");
        System.out.println();
    }

    /**
     * Main game loop, will loop until the game ends, either by checkmate, stalemate, or resignment
     */
    public void play() {
        while (!gameOver) {
            board.display();

            // Check if current player is in check
            if (board.isCheck(currentTurn)) {
                System.out.println("*** " + currentTurn.toUpperCase() + " IS IN CHECK! ***");
            }

            Player currentPlayer = currentTurn.equals("white") ? whitePlayer : blackPlayer;

            // Get move from current player
            Position[] move = getMoveFromPlayer(currentPlayer);
            if (move == null) {
                // Player resigned
                String winner = currentTurn.equals("white") ? "Black" : "White";
                System.out.println(currentTurn.toUpperCase() + " resigns. " + winner + " wins!");
                gameOver = true;
                break;
            }

            Position from = move[0];
            Position to = move[1];

            // Validate the move
            if (!board.isLegalMove(from, to, currentTurn)) {
                System.out.println("Illegal move. That piece can't go there, or it leaves your king in check.");
                continue;
            }

            // Execute the move
            board.movePiece(from, to);
            System.out.println(currentTurn.toUpperCase() + " moved " + from + " -> " + to);

            // Alternate the turn
            String opponent = currentTurn.equals("white") ? "black" : "white";

            // Check to see if the game should end
            if (board.isCheckmate(opponent)) {
                board.display();
                System.out.println("CHECKMATE! " + currentTurn.toUpperCase() + " WINS!");
                gameOver = true;
            } else if (board.isStalemate(opponent)) {
                board.display();
                System.out.println("STALEMATE! The game is a draw.");
                gameOver = true;
            } else {
                currentTurn = opponent;
            }
        }

        end();
    }

    /**
     * Loop as the game goes to check if a player resigns
     * Returns null if the player types "resign"
     * @param player The current player
     * @return Position array {start, end}, or null if they resigned
     */
    private Position[] getMoveFromPlayer(Player player) {
        // Get past the loop if a player resigns
        System.out.print(player.getColor().toUpperCase() + "'s move (e.g. E2 E4, or 'resign'): ");
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        while (true) {
            String input = scanner.nextLine().trim().toUpperCase();

            if (input.equals("RESIGN")) return null;

            String[] parts = input.split("\\s+");
            if (parts.length != 2 || parts[0].length() != 2 || parts[1].length() != 2) {
                System.out.print("Invalid format. Try again (e.g. E2 E4): ");
                continue;
            }

            Position from = Position.fromNotation(parts[0]);
            Position to = Position.fromNotation(parts[1]);

            if (from == null || to == null) {
                System.out.print("Invalid squares. Columns A-H, rows 1-8. Try again: ");
                continue;
            }

            if (from.equals(to)) {
                System.out.print("Source and destination can't be the same. Try again: ");
                continue;
            }

            return new Position[]{from, to};
        }
    }

    /**
     * Ending game message
     */
    public void end() {
        System.out.println("Thanks for playing!");
    }
}
