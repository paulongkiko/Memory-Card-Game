package MemoryGame.view;

import java.awt.*;
import javax.swing.*;

import MemoryGame.ControllerInterface;
import MemoryGame.model.CardBoard;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MemoryGameTwoPlayerGUI implements ActionListener {
    private JFrame gameFrame;
    private CardPanel gamePanel;
    private JPanel scorePanel;
    private JLabel player1Label;
    private JLabel player2Label;
    private JLabel player1Score;
    private JLabel player2Score;
    private JLabel movesLabel;
    private JLabel currentPlayerLabel;
    private Card previousCardPressed;
    private Card currentCardPressed;
    Color panelColor = new Color(255,230,205);
    Color fontColor = new Color(76,41,8);


    private ControllerInterface controller;
    private CardBoard board;

    public MemoryGameTwoPlayerGUI(ControllerInterface controller, CardBoard board)
    {
        this.board = board;
        this.controller = controller;

        gameFrame = new JFrame("Memory Game");
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setSize(800, 600);

        gameFrame = new JFrame("Memory Game");
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setSize(800, 600);
        
        gamePanel = new CardPanel(board, this);
        
        scorePanel = new JPanel();
        scorePanel.setLayout(new GridLayout(2, 2));
        scorePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        
        player1Label = new JLabel("Player 1");
        player1Label.setHorizontalAlignment(JLabel.CENTER);
        player1Label.setForeground(Color.RED);
        player1Label.setFont(player1Label.getFont().deriveFont(24f)); // Increases font size
        player1Score = new JLabel(String.valueOf(this.board.getPlayer1Score()));
        player1Score.setHorizontalAlignment(JLabel.CENTER);
        player1Score.setForeground(Color.RED);
        player1Score.setFont(player1Score.getFont().deriveFont(24f)); // Increases font size
        

        player2Label = new JLabel("Player 2");
        player2Score = new JLabel(String.valueOf(this.board.getPlayer2Score()));
        player2Label.setHorizontalAlignment(JLabel.CENTER);
        player2Label.setForeground(Color.BLUE);
        player2Label.setFont(player2Label.getFont().deriveFont(24f)); // Increases font size
        player2Score.setHorizontalAlignment(JLabel.CENTER);
        player2Score.setForeground(Color.BLUE);
        player2Score.setFont(player2Score.getFont().deriveFont(24f)); // Increases font size
        
        scorePanel.add(player1Label);
        scorePanel.add(player1Score);
        scorePanel.add(player2Label);
        scorePanel.add(player2Score);
        
        gameFrame.add(gamePanel, BorderLayout.CENTER);
        gameFrame.add(scorePanel, BorderLayout.SOUTH); // Puts score panel at the bottom of the frame
        
        currentPlayerLabel = new JLabel("Current Player: Player " + this.board.getPlayerTurn()); // Adds a label to indicate which player's
        currentPlayerLabel.setForeground(Color.DARK_GRAY);
        currentPlayerLabel.setHorizontalAlignment(JLabel.CENTER);
        currentPlayerLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0)); // Adds padding to the label
        gameFrame.add(currentPlayerLabel, BorderLayout.NORTH); // Puts the current player label at the top of the frame
    
        gameFrame.setVisible(true);
    }

     // ActionListener implementation
    /*@Override
    public void actionPerformed(ActionEvent event)
    {
        Card button = (Card)event.getSource();
        this.controller.cardPressed(button);

        this.board.incrementMoves();
        currentPlayerLabel.setText("Current Player: Player " + this.board.getPlayerTurn());

        player1Score.setText(String.valueOf(this.board.getPlayer1Score()));
        player2Score.setText(String.valueOf(this.board.getPlayer2Score()));
    }*/
    @Override
    public void actionPerformed(ActionEvent event)
    {
        Card button = (Card)event.getSource();
        this.controller.cardPressed(button);
        this.board.incrementMoves();
        currentPlayerLabel.setText("Current Player: Player " + this.board.getPlayerTurn());
        if (button.isMatched()) {
            return; // Do nothing if the card is already matched
        }

        if (previousCardPressed == null) {
            // First card clicked
            button.setBackground(Color.WHITE);
            previousCardPressed = button;
        } else {
            // Second card clicked
            currentCardPressed = button;
            boolean result = this.board.checkMatch(previousCardPressed, currentCardPressed);
            if (result == false) {
                // If the two cards are not a match, wait for 1 second and then change the background color back to green
                Timer timer = new Timer(1000, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        previousCardPressed.setBackground(previousCardPressed.getFrontCardColor());
                        currentCardPressed.setBackground(currentCardPressed.getFrontCardColor());
                        previousCardPressed.hideCardContent();
                        currentCardPressed.hideCardContent();
                        previousCardPressed = null;
                        currentCardPressed = null;
                    }
                });
                timer.setRepeats(false);
                timer.start();
            } else {
                // If the two cards are a match, set their isMatched variable to true
                previousCardPressed.setMatched(true);
                currentCardPressed.setMatched(true);
                if (this.board.getPlayerTurn() == 1){
                    currentCardPressed.setBackground(Color.RED);
                    previousCardPressed.setBackground(Color.RED);

                }else{
                    currentCardPressed.setBackground(Color.BLUE);
                    previousCardPressed.setBackground(Color.BLUE);
                }


                previousCardPressed = null;
                currentCardPressed = null;


                player1Score.setText(String.valueOf(this.board.getPlayer1Score()));
                player2Score.setText(String.valueOf(this.board.getPlayer2Score()));

            }
        }

 
    }
}
