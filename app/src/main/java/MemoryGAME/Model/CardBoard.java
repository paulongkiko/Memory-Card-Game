package MemoryGame.model;

import java.util.ArrayList;
import java.util.Random;
//import MemoryGame.GameObserver;

public class CardBoard
{
	private Card[][] cards;
    private int[][] cardValues = {{1, 1, 2, 2}, {3, 3, 4, 4}, {5, 5, 6, 6}, {7, 7, 8, 8}};
	//private ArrayList<GameObserver> observers = new ArrayList<GameObserver>();

	public CardBoard()
	{
        this.cards = new Card[4][4];
        this.randomizeCards();

        for (int i = 0; i < 4; i++)
        {
            for (int j = 0; j < 4; j++)
            {
                cards[i][j] = new Card(cardValues[i][j]);
            }
        }
	}
	
    public void randomizeCards()
    {
        // Create a new Random object
        Random rand = new Random();

        // Iterate through each element in the array
        for (int i = 0; i < cardValues.length; i++) {
            for (int j = 0; j < cardValues[i].length; j++) {
                // Generate random indices for swapping
                int iRand = rand.nextInt(cardValues.length);
                int jRand = rand.nextInt(cardValues[i].length);

                // Swap the current element with the randomly selected element
                int temp = cardValues[i][j];
                cardValues[i][j] = cardValues[iRand][jRand];
                cardValues[iRand][jRand] = temp;
            }
        }
    }

	public boolean checkMatch(Card card1, Card card2)
	{
		if(card1.getValue() == card2.getValue())
		{
			return true;
		} else {
			card1.unflip();
			card2.unflip();
			return false;
		}
	}
}