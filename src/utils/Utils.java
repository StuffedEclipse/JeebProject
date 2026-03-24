package utils;

import board.Board;
import pieces.Piece;
import java.util.List;

/**
 * Class for miscellaneous utility methods needed in other classes
 */
public class Utils {

    /**
     * Returns the opposite color
     * @param color "white"/"black"
     * @return "black"/"white"
     */
    public static String opponentColor(String color) {
        return color.equals("white") ? "black" : "white";
    }

    /**
     * Prints all possible moves for every piece of a given team
     * @param board the current board
     * @param color the team to check
     */
    public static void printAllMoves(Board board, String color) {
        System.out.println("All possible moves for " + color + ":");
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = board.getPiece(new Position(row, col));
                if (piece != null && piece.getColor().equals(color)) {
                    List<Position> moves = piece.possibleMoves(board);
                    if (!moves.isEmpty()) {
                        System.out.println("  " + piece.getSymbol() + " at " +
                            piece.getPosition() + " -> " + moves);
                    }
                }
            }
        }
    }

    /**
     * Checks if a string is a valid chess notation
     * @param notation the string to check
     * @return true if it's valid
     */
    public static boolean isValidNotation(String notation) {
        if (notation == null || notation.length() != 2) return false;
        char file = Character.toUpperCase(notation.charAt(0));
        char rank = notation.charAt(1);
        return file >= 'A' && file <= 'H' && rank >= '1' && rank <= '8';
    }
}
