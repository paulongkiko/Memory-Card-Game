package MemoryGame.model;

import java.util.ArrayList;

public class Card
{
	private int value;
	private boolean flippedOver;

	public Card(int value)
	{
		this.value = value;
		this.flippedOver = false;
	}

	public int getValue()
	{
		return value;
	}

	public boolean isFlipped()
	{
		return flippedOver;
	}

	public void flip()
	{
		flippedOver = true;
	}

	public void unflip()
	{
		flippedOver = false;
	}
}