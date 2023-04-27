package MemoryGame.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import MemoryGame.view.Card;

public class CardBoard
{
    private int moves;
    private int player;

    private int player1Score;
    private int player2Score;

    public CardBoard () {
        moves = 0;
        player = 1;

        player1Score = 0;
        player2Score = 0;
    }

    public void incrementMoves() {
        moves++;
    }

    public int getMoves() {
        return this.moves;
    }

    public void setPlayerTurn(int player) {
        this.player = player;
    }

    public int getPlayerTurn() {
        return this.player;
    }

    public void setPlayer1Score(int score) {
        this.player1Score = score;
    }

    public int getPlayer1Score() {
        return this.player1Score;
    }

    public void setPlayer2Score(int score) {
        this.player2Score = score;
    }

    public int getPlayer2Score() {
        return this.player2Score;
    }

    public List<String> getCardsContent() {
        List<String> list = Arrays.asList("1", "1", "2", "2", "3", "3", "4", "4", "5", "5", "6", "6", "7", "7", "8", "8");
        
        // Shuffle the list to randomize the order
        Collections.shuffle(list);

        return list;
    }
	
	public boolean checkMatch(Card card1, Card card2)
	{
		if(card1.getCardContent().equals(card2.getCardContent()))
		{
			return true;
		} else {
            return false;
        }
	}
}