package MemoryGame;

import javax.swing.JOptionPane;

import MemoryGame.controller.GameController;
import MemoryGame.model.CardBoard;

public class App {
    public static void main(String[] args) {

        // Starting the game with an option dialog to let the user choose which mode
        String[] options = {"2 Players", "1 Player", "High Scores"};
        int selection = JOptionPane.showOptionDialog(null, "Select a game mode:", "Memory Game Menu", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

        CardBoard board = new CardBoard();
        if (selection == 0) {
           new GameController(board, 2);
        } else if (selection == 1) {
            new GameController(board, 1);
        } else {
            // If there is no player, then the high scores will be displayed
            new GameController(board, 0);
        }
    }
}
