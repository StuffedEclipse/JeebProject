package pieces;

import board.Board;
import utils.Position;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for king pieces
 * Can only move one square in any direction
 * Making sure the king doesn't put itself in check is in a separate script
 */
public class King extends Piece {

    /**
     * Makes a king piece using the given color and position
     * @param color    "white"/"black"
     * @param position start position
     */
    public King(String color, Position position) {
        super(color, position);
    }

    /**
     * Returns all possible moves for a king at this position
     * @param board the current board
     * @return list of possible positions
     */
    @Override
    public List<Position> possibleMoves(Board board) {
        List<Position> moves = new ArrayList<>();
        int row = position.getRow();
        int col = position.getCol();

        // All 8 surrounding squares
        int[][] offsets = {
            {-1, -1}, {-1, 0}, {-1, 1},
            { 0, -1},           { 0, 1},
            { 1, -1}, { 1, 0}, { 1, 1}
        };

        for (int[] offset : offsets) {
            int r = row + offset[0];
            int c = col + offset[1];
            if (canMoveTo(board, r, c)) {
                moves.add(new Position(r, c));
            }
        }

        return moves;
    }

    /** @return King symbol with the correct color */
    @Override
    public String getSymbol() {
        return color.equals("white") ? "wK" : "bK";
    }
}
