package MemoryGame.model;

import java.util.ArrayList;
//import MemoryGame.GameObserver;

public class CardBoard
{
	private Card[][] cards;
	//private ArrayList<GameObserver> observers = new ArrayList<GameObserver>();

	public CardBoard()
	{
		this.cards = new Card[4][4];
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