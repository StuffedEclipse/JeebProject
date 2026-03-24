package pieces;

import board.Board;
import utils.Position;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for queen pieces
 * Can move any number of spaces horizontally, vertically, or diagonally, though can not go over other pieces
 */
public class Queen extends Piece {

    /**
     * Makes a queen piece using the given color and position
     * @param color    "white"/"black"
     * @param position start position
     */
    public Queen(String color, Position position) {
        super(color, position);
    }

    /**
     * Returns all possible moves for a queen at this position
     * @param board the current board
     * @return list of possible positions
     */
    @Override
    public List<Position> possibleMoves(Board board) {
        List<Position> moves = new ArrayList<>();
        int row = position.getRow();
        int col = position.getCol();

        // All 8 directions: rook (4) + bishop (4)
        int[][] directions = {
            {1, 0}, {-1, 0}, {0, 1}, {0, -1},  // rook directions
            {1, 1}, {1, -1}, {-1, 1}, {-1, -1}  // bishop directions
        };

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
                    break;
                }
                r += dir[0];
                c += dir[1];
            }
        }

        return moves;
    }

    /** @return Queen symbol with the correct color */
    @Override
    public String getSymbol() {
        return color.equals("white") ? "wQ" : "bQ";
    }
}
