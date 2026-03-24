package utils;

/**
 * Class for a position on the chessboard, using rows and columns
 */
public class Position {

    /** Row index */
    private int row;

    /** Column index */
    private int col;

    /**
     * Makes a position given a row and column
     * @param row given row
     * @param col given column
     */
    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

    /**
     * Converts a chess notation into a position on the board
     * @param notation the given chess notation
     * @return the converted position, null if it is not able to be converted
     */
    public static Position fromNotation(String notation) {
        if (notation == null || notation.length() != 2) return null;
        char file = Character.toUpperCase(notation.charAt(0));
        char rank = notation.charAt(1);
        if (file < 'A' || file > 'H') return null;
        if (rank < '1' || rank > '8') return null;
        int col = file - 'A';
        int row = rank - '1';
        return new Position(row, col);
    }

    /**
     * Converts a position on the board into a chess notation
     * @return converted chess notation
     */
    public String toNotation() {
        char file = (char) ('A' + col);
        char rank = (char) ('1' + row);
        return "" + file + rank;
    }

    /** @return row index */
    public int getRow() { return row; }

    /** @return column index */
    public int getCol() { return col; }

    /**
     * Checks if the 2 positions are equal
     * @param o the other object
     * @return true if same row and column
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Position)) return false;
        Position p = (Position) o;
        return row == p.row && col == p.col;
    }

    @Override
    public int hashCode() {
        return 31 * row + col;
    }

    @Override
    public String toString() {
        return toNotation();
    }
}
