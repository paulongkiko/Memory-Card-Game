package MemoryGame.view;

import javax.swing.JButton;

public class Card extends JButton 
{
    private String cardContent;

    public Card(String cardContent) {
        this.cardContent = cardContent;
        this.setText(cardContent);
    }

    public void hideCardContent() {
        this.setText("");
    }

    public void revealCardContent() {
        this.setText(this.cardContent);
    }

    public String getCardContent() {
        return this.cardContent;
    }
}