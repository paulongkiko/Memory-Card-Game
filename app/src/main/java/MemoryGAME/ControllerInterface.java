package MemoryGame;

import javax.swing.Timer;
import javax.swing.JLabel;

import MemoryGame.view.Card;

public interface ControllerInterface {
    public void cardPressed(Card card, JLabel currentPlayerLabel, JLabel player1Score, JLabel player2Score, JLabel playerMoves, Timer timer);
    public void restartGame();
    public String gameResult();
}
