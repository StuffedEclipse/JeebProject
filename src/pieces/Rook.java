package pieces;

import board.Board;
import utils.Position;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for rook pieces
 * Can only move horizontally or vertically, and cannot go over other pieces
 */
public class Rook extends Piece {

    /**
     * Makes a rook piece using the given color and position
     * @param color    "white"/"black"
     * @param position start position
     */
    public Rook(String color, Position position) {
        super(color, position);
    }

    /**
     * Returns all possible moves for a rook at this position
     * @param board the current board
     * @return list of possible positions
     */
    @Override
    public List<Position> possibleMoves(Board board) {
        List<Position> moves = new ArrayList<>();
        int row = position.getRow();
        int col = position.getCol();

        // Four directions: up, down, left, right
        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

        for (int[] dir : directions) {
            int r = row + dir[0];
            int c = col + dir[1];
            while (inBounds(r, c)) {
                Piece target = board.getPiece(new Position(r, c));
                if (target == null) {
                    moves.add(new Position(r, c));
                } else {
                    if (!target.getColor().equals(this.color)) {
                        moves.add(new Position(r, c)); // capture
                    }
                    break; // blocked either way
                }
                r += dir[0];
                c += dir[1];
            }
        }

        return moves;
    }

    /** @return Rook symbol with the correct color */
    @Override
    public String getSymbol() {
        return color.equals("white") ? "wR" : "bR";
    }
}
