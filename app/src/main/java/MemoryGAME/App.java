package MemoryGame;

import javax.swing.JOptionPane;

import MemoryGame.controller.GameController;
import MemoryGame.controller.GameControllerTwoPlayer;
import MemoryGame.model.CardBoard;
import MemoryGame.model.HighScores;
import MemoryGame.view.HighScoresGUI;

public class App {
    public static void main(String[] args) {

        // Starting the game with an option dialog to let the user choose which mode
        String[] options = {"2 Players", "1 Player", "High Scores"};
        int selection = JOptionPane.showOptionDialog(null, "Select a game mode:", "Memory Game Menu", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

        CardBoard board = new CardBoard();
        HighScores scores = new HighScores();
        if (selection == 0) {
            GameControllerTwoPlayer controller = new GameControllerTwoPlayer(board);
        } else if (selection == 1) {
            GameController controller = new GameController(board);
        } else {
            HighScoresGUI scoresGUI = new HighScoresGUI(scores);
        }
    }
}
