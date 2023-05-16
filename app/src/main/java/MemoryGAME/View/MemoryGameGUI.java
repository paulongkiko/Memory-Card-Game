package MemoryGame.view;

import java.awt.*;
import javax.swing.*;

import MemoryGame.ControllerInterface;
import MemoryGame.model.CardBoard;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MemoryGameGUI implements ActionListener {
    private JFrame gameFrame;
    private CardPanel gamePanel;
    private JPanel scorePanel;
    private JLabel playerLabel;
    private JLabel timerLabel;
    private JLabel playerMoves;
    private JLabel player1Label;
    private JLabel player2Label;
    private JLabel player1Score;
    private JLabel player2Score;
    private JLabel currentPlayerLabel;
    private Card previousCardPressed;
    private Card currentCardPressed;
    Color panelColor = new Color(255,230,205);
    Color fontColor = new Color(76,41,8);


    private ControllerInterface controller;
    private CardBoard board;
    private int disabledCards;
    private int numberOfPlayers;
    private Timer timer;


    public MemoryGameGUI(ControllerInterface controller, CardBoard board, int numberOfPlayers)
    {
        this.board = board;
        this.controller = controller;
        this.numberOfPlayers = numberOfPlayers;

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
    
        if (numberOfPlayers == 1) {
            timer = new Timer(1, new ActionListener() {
                private int elapsedMilliseconds = 0;
                private int elapsedSeconds = 0;
                private int elapsedMinutes = 0;
        
                @Override
                public void actionPerformed(ActionEvent e) {
                    elapsedMilliseconds++;
                    if (elapsedMilliseconds == 1000) {
                        elapsedMilliseconds = 0;
                        elapsedSeconds++;
                        if (elapsedSeconds == 60) {
                            elapsedSeconds = 0;
                            elapsedMinutes++;
                        }
                    }
                    timerLabel.setText(String.format("%02d:%02d.%03d", elapsedMinutes, elapsedSeconds, elapsedMilliseconds));
                }
            });
            playerLabel = new JLabel("Timer:");
            timerLabel = new JLabel("00.00.00");
            playerLabel.setHorizontalAlignment(JLabel.CENTER);
            playerLabel.setForeground(fontColor);
            playerLabel.setFont(playerLabel.getFont().deriveFont(30f)); // Increases font size
            timerLabel.setHorizontalAlignment(JLabel.CENTER);
            timerLabel.setForeground(fontColor);
            timerLabel.setFont(timerLabel.getFont().deriveFont(30f)); // Increases font size
            
            playerMoves = new JLabel("Moves: 0");
            playerMoves.setHorizontalAlignment(JLabel.CENTER);
            playerMoves.setForeground(fontColor);
            playerMoves.setFont(playerMoves.getFont().deriveFont(30f)); // Increases font size

            scorePanel.add(playerLabel);
            scorePanel.add(timerLabel);
            scorePanel.add(playerMoves); // add moves label to score panel

            timer.start();
        } else {
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

            currentPlayerLabel = new JLabel("Current Player: Player " + this.board.getPlayerTurn()); // Adds a label to indicate which player's
            currentPlayerLabel.setForeground(Color.DARK_GRAY);
            currentPlayerLabel.setHorizontalAlignment(JLabel.CENTER);
            currentPlayerLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0)); // Adds padding to the label
            
            gameFrame.add(currentPlayerLabel, BorderLayout.NORTH); // Puts the current player label at the top of the frame
        }
        
        gameFrame.add(scorePanel, BorderLayout.SOUTH); // Puts score panel at the bottom of the frame
        gameFrame.add(gamePanel, BorderLayout.CENTER);
        gameFrame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent event)
    {
        Card card = (Card)event.getSource();
        
        if (card.isMatched()) {
            return; // Do nothing if the card is already matched
        }

        this.controller.cardPressed(card, currentPlayerLabel, player1Score, player2Score, playerMoves, timer);
    }
}
