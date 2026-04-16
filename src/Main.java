import gui.ChessGUI;
import javax.swing.SwingUtilities;

/**
 * Second version of the chess game
 * Launches the Swing GUI on the Event Dispatch Thread to start the game and its visuals
 */


public class Main {
    /**
     * Starts the new version of the chess game
     * @param args Command line arguments
     */

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ChessGUI());
    }
}
