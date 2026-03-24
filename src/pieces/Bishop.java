package pieces;

import board.Board;
import utils.Position;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for bishop pieces
 * Can only move diagonally, and not past other pieces
 */
public class Bishop extends Piece {

    /**
     * Makes a bishop piece using the given color and position
     * @param color    "white"/"black"
     * @param position start position
     */
    public Bishop(String color, Position position) {
        super(color, position);
    }

    /**
     * Returns all possible moves for a bishop at this position
     * @param board the current board
     * @return list of possible positions
     */
    @Override
    public List<Position> possibleMoves(Board board) {
        List<Position> moves = new ArrayList<>();
        int row = position.getRow();
        int col = position.getCol();

        // The four diagonal directions it can move in
        int[][] directions = {{1, 1}, {1, -1}, {-1, 1}, {-1, -1}};

        for (int[] dir : directions) {
            int r = row + dir[0];
            int c = col + dir[1];
            while (inBounds(r, c)) {
                Piece target = board.getPiece(new Position(r, c));
                if (target == null) {
                    moves.add(new Position(r, c));
                } else {
                    if (!target.getColor().equals(this.color)) {
                        moves.add(new Position(r, c)); // capture the opposing piece
                    }
                    break; // block the movement
                }
                r += dir[0];
                c += dir[1];
            }
        }

        return moves;
    }

    /** @return Bishop symbol with the correct color */
    @Override
    public String getSymbol() {
        return color.equals("white") ? "wB" : "bB";
    }
}
