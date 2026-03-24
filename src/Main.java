import game.Game;

/**
 * Creates a game of chess, runs it, and loops it until the game ends
 */
public class Main {
    /**
     * Starts up the game
     */
    public static void main(String[] args) {
        Game game = new Game();
        game.start();
        game.play();
    }
}
