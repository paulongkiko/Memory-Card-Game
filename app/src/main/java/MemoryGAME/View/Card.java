package MemoryGame.view;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;


public class Card extends JButton 
{
    private boolean matched = false;
    private String cardContent;
    private Color frontCardColor = new Color(35, 79, 30);
    Color numColor = new Color(75,0,30);

    public Card(String cardContent) {
        this.cardContent = cardContent;
        this.setText(cardContent);
    }
    public void hideCardContent() {
        this.setText("");
    }
    public void setCardFont() {
        this.setForeground(numColor); // Set the text color to white
        this.setFont(new Font("Arial", Font.BOLD, 30)); // Set the font to Arial, bold, 20px size
    }
    public boolean revealCardContent() {
        if (this.getText().equals("")){
            this.setText(this.cardContent);
            return true;
        }
        this.setForeground(frontCardColor);
        return false;
    }

    public String getCardContent() {
        return this.cardContent;
    }
    public boolean isMatched() {
        return matched;
    }
    
    public void setMatched(boolean matched) {
        this.matched = matched;
    }
    public Color getFrontCardColor() {
        return this.frontCardColor;
    }

}