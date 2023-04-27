package MemoryGame.controller;

import javax.swing.JOptionPane;

import MemoryGame.ControllerInterface;
import MemoryGame.model.CardBoard;
import MemoryGame.view.Card;
import MemoryGame.view.MemoryGameTwoPlayerGUI;

public class GameControllerTwoPlayer implements ControllerInterface
{
    private MemoryGameTwoPlayerGUI view;
    private CardBoard board;
    private Card previousCardPressed;
    private Card currentCardPressed;
    private int disabledCards;
    
    public GameControllerTwoPlayer(CardBoard board) {
        this.board = board;
        this.view = new MemoryGameTwoPlayerGUI(this, board);
        this.disabledCards = 0;
    }

    public void cardPressed(Card card) {
        card.revealCardContent();

        if (this.previousCardPressed == null) {
            this.previousCardPressed = card;
            return;
        } 

        if (this.currentCardPressed == null) {
            this.currentCardPressed = card;
        }

        boolean result = this.board.checkMatch(previousCardPressed, currentCardPressed);

        if (result == false) {
            this.previousCardPressed.hideCardContent();
            this.currentCardPressed.hideCardContent();

            if (this.board.getPlayerTurn() == 1) {
                this.board.setPlayerTurn(2);
            } else {
                this.board.setPlayerTurn(1);
            }
        } else {
            if (this.board.getPlayerTurn() == 1) {
                int currentScore = this.board.getPlayer1Score();
                this.board.setPlayer1Score(currentScore+1);
            } else {
                int currentScore = this.board.getPlayer2Score();
                this.board.setPlayer2Score(currentScore+1);
            }

            this.previousCardPressed.setEnabled(false);
            this.currentCardPressed.setEnabled(false);

            this.disabledCards += 2;
        }

        if (this.disabledCards == 16) {
            if (this.board.getPlayer1Score() > this.board.getPlayer2Score()) {
                JOptionPane.showMessageDialog(null, "Game Over! " + "Player 1 Won");
            } else if (this.board.getPlayer1Score() < this.board.getPlayer2Score()) {
                JOptionPane.showMessageDialog(null, "Game Over! " + "Player 2 Won");
            } else {
                JOptionPane.showMessageDialog(null, "Game Over! " + "It's a tie");
            }
        }

        this.previousCardPressed = null;
        this.currentCardPressed = null;
    }
}
