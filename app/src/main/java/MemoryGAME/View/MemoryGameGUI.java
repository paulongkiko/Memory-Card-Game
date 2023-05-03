package MemoryGame.view;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import java.awt.Color;


import MemoryGame.ControllerInterface;
import MemoryGame.GameObserver;
import MemoryGame.model.CardBoard;


public class MemoryGameGUI implements ActionListener {
    private JFrame gameFrame;
    private CardPanel gamePanel;
    private JPanel scorePanel;
    private JLabel playerLabel;
    private JLabel playerScore;
    private JLabel movesLabel;
    Color panelColor = new Color(255,230,205);
    Color fontColor = new Color(76,41,8);

    private ControllerInterface controller;
    private CardBoard board;

    public MemoryGameGUI(ControllerInterface controller, CardBoard board)
    {
        this.board = board;
        this.controller = controller;

        gameFrame = new JFrame("Memory Game");
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setSize(800, 600);
        
        gamePanel = new CardPanel(board, this);
        
        scorePanel = new JPanel();
        scorePanel.setLayout(new GridLayout(2, 2));
        scorePanel.setBackground(panelColor);
        scorePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        
        playerLabel = new JLabel("Timer:");
        playerScore = new JLabel("00.00.00");
        playerLabel.setHorizontalAlignment(JLabel.CENTER);
        playerLabel.setForeground(fontColor);
        playerLabel.setFont(playerLabel.getFont().deriveFont(30f)); // Increases font size
        playerScore.setHorizontalAlignment(JLabel.CENTER);
        playerScore.setForeground(fontColor);
        playerScore.setFont(playerScore.getFont().deriveFont(30f)); // Increases font size
        
        movesLabel = new JLabel("Moves: 0");
        movesLabel.setHorizontalAlignment(JLabel.CENTER);
        movesLabel.setForeground(fontColor);
        movesLabel.setFont(movesLabel.getFont().deriveFont(30f)); // Increases font size

        scorePanel.add(playerLabel);
        scorePanel.add(playerScore);
        scorePanel.add(movesLabel); // add moves label to score panel

        // create and start a timer to update the player 1 score every second
        Timer timer = new Timer(1, new ActionListener() {
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
                playerScore.setText(String.format("%02d:%02d.%03d", elapsedMinutes, elapsedSeconds, elapsedMilliseconds));
            }
        });
        timer.start();

        gameFrame.add(gamePanel, BorderLayout.CENTER);
        gameFrame.add(scorePanel, BorderLayout.SOUTH);
        gameFrame.setVisible(true);
    }

    // ActionListener implementation
    @Override
    public void actionPerformed(ActionEvent event)
    {
        Card button = (Card)event.getSource();
        this.controller.cardPressed(button);

        this.board.incrementMoves();
        movesLabel.setText("Moves: " + this.board.getMoves());
    }
}