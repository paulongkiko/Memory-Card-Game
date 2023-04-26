package MemoryGame.view;

import javax.swing.JButton;

public class Card extends JButton {

    public Card(String cardContent) {
        this.setText(cardContent);
    }

    public void setCardContent(String cardContent) {
        this.setText(cardContent);
    }

    public String getCardContent() {
        return this.getText();
    }
}