package board;

import pieces.*;
import utils.Position;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles the chessboard and the movement of pieces on said board
 * Checks for possible checks/checkmates whenever pieces are moved in certain ways
 * Prints the board as the game goes on
 */
public class Board {

    /** 8x8 grid of squares on the board, null means no piece is there, otherwise, the string value represents the symbol of what piece is there */
    private Piece[][] squares;

    /** List of previously captured pieces */
    private List<Piece> capturedPieces;

    /**
     * Creates a new & empty board
     */
    public Board() {
        squares = new Piece[8][8];
        capturedPieces = new ArrayList<>();
    }

    /**
     * Puts all the pieces in their starting positions
     */
    public void initialize() {
        // White back row
        squares[0][0] = new Rook("white", new Position(0, 0));
        squares[0][1] = new Knight("white", new Position(0, 1));
        squares[0][2] = new Bishop("white", new Position(0, 2));
        squares[0][3] = new Queen("white", new Position(0, 3));
        squares[0][4] = new King("white", new Position(0, 4));
        squares[0][5] = new Bishop("white", new Position(0, 5));
        squares[0][6] = new Knight("white", new Position(0, 6));
        squares[0][7] = new Rook("white", new Position(0, 7));

        // White pawns
        for (int col = 0; col < 8; col++) {
            squares[1][col] = new Pawn("white", new Position(1, col));
        }

        // Black back row
        squares[7][0] = new Rook("black", new Position(7, 0));
        squares[7][1] = new Knight("black", new Position(7, 1));
        squares[7][2] = new Bishop("black", new Position(7, 2));
        squares[7][3] = new Queen("black", new Position(7, 3));
        squares[7][4] = new King("black", new Position(7, 4));
        squares[7][5] = new Bishop("black", new Position(7, 5));
        squares[7][6] = new Knight("black", new Position(7, 6));
        squares[7][7] = new Rook("black", new Position(7, 7));

        // Black pawns
        for (int col = 0; col < 8; col++) {
            squares[6][col] = new Pawn("black", new Position(6, col));
        }
    }

    /**
     * Returns whatever piece is at the given position
     * If no piece is there, returns null
     * @param position the board position
     * @return the Piece/null
     */
    public Piece getPiece(Position position) {
        return squares[position.getRow()][position.getCol()];
    }

    /**
     * Places the piece at the given position on the board
     * @param position the given position
     * @param piece    the given piece, or null if it's to clear said position
     */
    public void setPiece(Position position, Piece piece) {
        squares[position.getRow()][position.getCol()] = piece;
    }

    /**
     * Moves a piece from one position to another, capturing an opposing piece at said position if it's there
     * @param from the starting position
     * @param to   the ending position
     */
    public void movePiece(Position from, Position to) {
        Piece moving = squares[from.getRow()][from.getCol()];
        Piece target = squares[to.getRow()][to.getCol()];

        if (target != null) {
            capturedPieces.add(target);
        }

        squares[to.getRow()][to.getCol()] = moving;
        squares[from.getRow()][from.getCol()] = null;
        moving.setPosition(to);
    }

    /**
     * Checks the legality of a given move with a given piece
     * The move is counted as legal if the piece is at the correct starting position, the movement doesn't put their king piece in check, and the ending position is in one of the pieces' possible moves
     * @param from  the starting position
     * @param to    the ending position
     * @param color the team of the piece
     * @return true if it's a legal move
     */
    public boolean isLegalMove(Position from, Position to, String color) {
        Piece piece = getPiece(from);
        if (piece == null || !piece.getColor().equals(color)) return false;

        List<Position> possible = piece.possibleMoves(this);
        boolean found = false;
        for (Position p : possible) {
            if (p.equals(to)) { found = true; break; }
        }
        if (!found) return false;

        // See if the king were to go in check if the move were to happen
        return !moveLeavesKingInCheck(from, to, color);
    }

    /**
     * Checks to see if the king were to get put in check were a certain move were to happen
     * Reverts the board after the check
     *
     * @param from  starting position
     * @param to    ending position
     * @param color the team checking the move
     * @return true if the move would put their king in check
     */
    private boolean moveLeavesKingInCheck(Position from, Position to, String color) {
        // Save the current state
        Piece moving = squares[from.getRow()][from.getCol()];
        Piece captured = squares[to.getRow()][to.getCol()];
        Position originalPos = moving.getPosition();

        // Temporarily apply the move
        squares[to.getRow()][to.getCol()] = moving;
        squares[from.getRow()][from.getCol()] = null;
        moving.setPosition(to);

        boolean inCheck = isCheck(color);

        // Undo the given move
        squares[from.getRow()][from.getCol()] = moving;
        squares[to.getRow()][to.getCol()] = captured;
        moving.setPosition(originalPos);

        return inCheck;
    }

    /**
     * Checks if a team's king is currently being checked
     * Looks through all the pieces to see if the king is in check
     *
     * @param color the king's team
     * @return true if that team's king is getting put in check
     */
    public boolean isCheck(String color) {
        Position kingPos = findKing(color);
        if (kingPos == null) return false;

        String opponentColor = color.equals("white") ? "black" : "white";

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = squares[row][col];
                if (piece != null && piece.getColor().equals(opponentColor)) {
                    List<Position> moves = piece.possibleMoves(this);
                    for (Position move : moves) {
                        if (move.equals(kingPos)) return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Checks if the given team is in checkmate
     *
     * @param color the team to check
     * @return true if the team is in checkmate
     */
    public boolean isCheckmate(String color) {
        if (!isCheck(color)) return false;
        return getAllLegalMoves(color).isEmpty();
    }

    /**
     * Checks if the given team is in stalemate
     *
     * @param color the team to check
     * @return true if the team is in stalemate
     */
    public boolean isStalemate(String color) {
        if (isCheck(color)) return false;
        return getAllLegalMoves(color).isEmpty();
    }

    /**
     * Gets all legal moves of a certain team
     * @param color the team to check moves for
     * @return list of [from starting, to ending] position arrays
     */
    public List<Position[]> getAllLegalMoves(String color) {
        List<Position[]> legalMoves = new ArrayList<>();
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = squares[row][col];
                if (piece != null && piece.getColor().equals(color)) {
                    Position from = new Position(row, col);
                    for (Position to : piece.possibleMoves(this)) {
                        if (!moveLeavesKingInCheck(from, to, color)) {
                            legalMoves.add(new Position[]{from, to});
                        }
                    }
                }
            }
        }
        return legalMoves;
    }

    /**
     * Returns the given team's king's position
     *
     * @param color the given team
     * @return the given team's king's position
     */
    private Position findKing(String color) {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = squares[row][col];
                if (piece instanceof King && piece.getColor().equals(color)) {
                    return new Position(row, col);
                }
            }
        }
        return null;
    }

    /**
     * Prints the current board, using A-H and 1-8 for the columns and rows respectively
     * Empty squares are "##", occupied squares show the piece's symbol
     */
    public void display() {
        System.out.println();
        System.out.println("    A   B   C   D   E   F   G   H");
        System.out.println("  +---+---+---+---+---+---+---+---+");

        for (int row = 7; row >= 0; row--) {
            System.out.print((row + 1) + " |");
            for (int col = 0; col < 8; col++) {
                Piece piece = squares[row][col];
                if (piece == null) {
                    System.out.print(" ##|");
                } else {
                    System.out.print(" " + piece.getSymbol() + "|");
                }
            }
            System.out.println(" " + (row + 1));
            System.out.println("  +---+---+---+---+---+---+---+---+");
        }

        System.out.println("    A   B   C   D   E   F   G   H");
        System.out.println();
    }

    /**
     * Returns the list of captured pieces
     * @return list of captured pieces
     */
    public List<Piece> getCapturedPieces() {
        return capturedPieces;
    }
}
