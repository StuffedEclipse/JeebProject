package pieces;

import board.Board;
import utils.Position;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for pawn pieces
 * Can only move up/down, depending on team, and capture diagonally
 * Can move 2 up/down if it's their first move
 */
public class Pawn extends Piece {

    /**
     * Makes a pawn piece using the given color and position
     * @param color    "white"/"black"
     * @param position start position
     */
    public Pawn(String color, Position position) {
        super(color, position);
    }

    /**
     * Returns all possible moves for a pawn at this position
     * Does not include en pessant
     * @param board the current board
     * @return list of possible positions
     */
    @Override
    public List<Position> possibleMoves(Board board) {
        List<Position> moves = new ArrayList<>();
        int row = position.getRow();
        int col = position.getCol();

        // White moves upwards, black moves downwards
        int direction = color.equals("white") ? 1 : -1;
        int startRow = color.equals("white") ? 1 : 6;

        // 1 step up/down if the square is empty
        int nextRow = row + direction;
        if (inBounds(nextRow, col) && board.getPiece(new Position(nextRow, col)) == null) {
            moves.add(new Position(nextRow, col));

            // 2 steps up/down if the square is empty and the pawn is at it's starting position
            if (row == startRow && board.getPiece(new Position(nextRow + direction, col)) == null) {
                moves.add(new Position(nextRow + direction, col));
            }
        }

        // Can capture pieces at the pawn's diagonals
        int[] captureCols = {col - 1, col + 1};
        for (int captureCol : captureCols) {
            if (inBounds(nextRow, captureCol)) {
                Piece target = board.getPiece(new Position(nextRow, captureCol));
                if (target != null && !target.getColor().equals(this.color)) {
                    moves.add(new Position(nextRow, captureCol));
                }
            }
        }

        return moves;
    }

    /** @return Pawn symbol with the correct color */
    @Override
    public String getSymbol() {
        return color.equals("white") ? "wP" : "bP";
    }
}
