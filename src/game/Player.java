package game;

import board.Board;
import utils.Position;
import java.util.Scanner;

/**
 * Class for the players
 * Reads the inputs for playing the game
 */
public class Player {

    /** The player's team color */
    private String color;

    /** Scanner to read the player's inputs */
    private Scanner scanner;

    /**
     * Creates a player for a certain team
     * @param color "white"/"black"
     */
    public Player(String color) {
        this.color = color;
        this.scanner = new Scanner(System.in);
    }

    /**
     * Asks the player for an input and turns it into a Position array
     * Will loop through until their sent input is a correct move
     * @param board the current board
     * @return a Position array {starting position, ending position}
     */
    public Position[] makeMove(Board board) {
        while (true) {
            System.out.print(color.toUpperCase() + "'s move (e.g. E2 E4): ");
            String input = scanner.nextLine().trim().toUpperCase();

            // {XX, YY} format
            String[] parts = input.split("\\s+");
            if (parts.length != 2 || parts[0].length() != 2 || parts[1].length() != 2) {
                System.out.println("Invalid format. Use chess notation like 'E2 E4'.");
                continue;
            }

            Position from = Position.fromNotation(parts[0]);
            Position to = Position.fromNotation(parts[1]);

            if (from == null || to == null) {
                System.out.println("Invalid squares. Columns must be A-H, rows must be 1-8.");
                continue;
            }

            if (from.equals(to)) {
                System.out.println("Source and destination cannot be the same square.");
                continue;
            }

            return new Position[]{from, to};
        }
    }

    /** @return The player's team color */
    public String getColor() { return color; }
}
