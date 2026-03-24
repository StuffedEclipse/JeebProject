package pieces;

import board.Board;
import utils.Position;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for knight pieces
 * Can only move in L-shaped movements, 2 forwards and 1 to the side, though it can go over other pieces on the way
 */
public class Knight extends Piece {

    /**
     * Makes a knight piece using the given color and position
     * @param color    "white"/"black"
     * @param position start position
     */
    public Knight(String color, Position position) {
        super(color, position);
    }

    /**
     * Returns all possible moves for a knight at this position
     * @param board the current board
     * @return list of possible positions
     */
    @Override
    public List<Position> possibleMoves(Board board) {
        List<Position> moves = new ArrayList<>();
        int row = position.getRow();
        int col = position.getCol();

        int[][] offsets = {
            {-2, -1}, {-2, 1},
            {-1, -2}, {-1, 2},
            { 1, -2}, { 1, 2},
            { 2, -1}, { 2, 1}
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

    /** @return Knight symbol with the correct color */
    @Override
    public String getSymbol() {
        return color.equals("white") ? "wN" : "bN";
    }
}
