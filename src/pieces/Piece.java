package pieces;

import board.Board;
import utils.Position;
import java.util.List;

/**
 * Base piece class that the other piece classes will use and override
 */
public abstract class Piece {

    /** "white" or "black", depending on the team */
    protected String color;

    /** The current position of this piece on the board */
    protected Position position;

    /**
     * Makes a piece using a given color and position
     * @param color    "white"/"black"
     * @param position starting position
     */
    public Piece(String color, Position position) {
        this.color = color;
        this.position = position;
    }

    /**
     * Returns all spots the piece can possibly move to, regardless
     * of if it puts them in check
     * @param board      the current board
     * @return      list of all possible move spots
     */
    public abstract List<Position> possibleMoves(Board board);

    /**
     * Returns the symbol of the piece
     * @return symbol string
     */
    public abstract String getSymbol();

    /** @return the piece's color */
    public String getColor() { return color; }

    /** @return the piece's position */
    public Position getPosition() { return position; }

    /**
     * Moves the piece to a new position
     * @param position where to put the piece
     */
    public void setPosition(Position position) { this.position = position; }

    /**
     * Checks if a certain position is in the board's bounds
     * @param row the position's row
     * @param col the position's column
     * @return true if it's on the board
     */
    protected boolean inBounds(int row, int col) {
        return row >= 0 && row < 8 && col >= 0 && col < 8;
    }

    /**
     * Checks if the square is available to be moved into, whether it's empty or occupied by the opposite team
     * @param board  the current board
     * @param row    the target's row
     * @param col    the target's column
     * @return true if the square can be moved into
     */
    protected boolean canMoveTo(Board board, int row, int col) {
        if (!inBounds(row, col)) return false;
        Piece target = board.getPiece(new Position(row, col));
        return target == null || !target.getColor().equals(this.color);
    }

    @Override
    public String toString() {
        return getSymbol() + "@" + position.toNotation();
    }
}
