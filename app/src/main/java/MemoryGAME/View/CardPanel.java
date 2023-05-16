package MemoryGame.view;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.*;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import MemoryGame.model.CardBoard;

public class CardPanel extends JPanel{
    private Card[][] cards;
    Color frontCardColor = new Color(35,79,30);
    
    public CardPanel(CardBoard board, ActionListener cardPressedListener) {
        this.setLayout(new GridLayout(4, 4, 10, 10)); // Adds some padding between the cards
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        
        cards = new Card[4][4];
        List<String> cardsContent = board.getCardsContent();
        for(int i=0; i<4; i++) {
            for(int j=0; j<4; j++) {
                cards[i][j] = new Card(cardsContent.get(4 * i + j));
                cards[i][j].setBackground(frontCardColor);
                cards[i][j].setOpaque(true);
                cards[i][j].setBorderPainted(false);
                cards[i][j].setCardFont();
                cards[i][j].hideCardContent();
                cards[i][j].addActionListener(cardPressedListener);
                this.add(cards[i][j]);
            }
        }
    }
}