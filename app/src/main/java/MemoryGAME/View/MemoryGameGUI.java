package MemoryGame.view;

import java.util.List;
import java.util.ArrayList;
import java.awt.*;
import javax.swing.*;

public class MemoryGameGUI {
    private JFrame gameFrame;
    private JPanel gamePanel;
    private JPanel scorePanel;
    private JLabel player1Label;
    private JLabel player2Label;
    private JLabel player1Score;
    private JLabel player2Score;
    private JLabel currentPlayerLabel;
    private JButton[][] cardButtons;
    private int numberPlayers;

    public MemoryGameGUI() {
        gameFrame = new JFrame("Memory Game");
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setSize(800, 600);

        String[] options = {"2 Player", "1 Player", "High Scores"}; // Options for the menu dialog
        int selection = JOptionPane.showOptionDialog(null, "Select a game mode:", "Memory Game Menu", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

        if (selection == 0 || selection == 1) {
            this.numberPlayers = selection + 1;
            gameGUI();
        } else {
            // High scores
            displayHighScores();
        }
    }

    private void gameGUI() {
        gameFrame = new JFrame("Memory Game");
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setSize(800, 600);
        
        gamePanel = new JPanel();
        gamePanel.setLayout(new GridLayout(4, 4, 10, 10)); // Adds some padding between the cards
        gamePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        
        cardButtons = new JButton[4][4];
        for(int i=0; i<4; i++) {
            for(int j=0; j<4; j++) {
                cardButtons[i][j] = new JButton();
                gamePanel.add(cardButtons[i][j]);
            }
        }
        
        scorePanel = new JPanel();
        scorePanel.setLayout(new GridLayout(2, 2));
        scorePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        
        player1Label = new JLabel("Player 1");
        player1Label.setHorizontalAlignment(JLabel.CENTER);
        player1Label.setForeground(Color.RED);
        player1Label.setFont(player1Label.getFont().deriveFont(24f)); // Increases font size
        player1Score = new JLabel("0");
        player1Score.setHorizontalAlignment(JLabel.CENTER);
        player1Score.setForeground(Color.RED);
        player1Score.setFont(player1Score.getFont().deriveFont(24f)); // Increases font size
        
        if (numberPlayers == 1) {
            player2Label = new JLabel("Player 2");
            player2Score = new JLabel("0");

        } else {
            player2Label = new JLabel("Timer:");
            player2Score = new JLabel("00.00.00");

        }
        
        player2Label.setHorizontalAlignment(JLabel.CENTER);
        player2Label.setForeground(Color.BLUE);
        player2Label.setFont(player2Label.getFont().deriveFont(24f)); // Increases font size
        //player2Score = new JLabel("0");
        player2Score.setHorizontalAlignment(JLabel.CENTER);
        player2Score.setForeground(Color.BLUE);
        player2Score.setFont(player2Score.getFont().deriveFont(24f)); // Increases font size
        
        scorePanel.add(player1Label);
        scorePanel.add(player1Score);
        scorePanel.add(player2Label);
        scorePanel.add(player2Score);
        
        gameFrame.add(gamePanel, BorderLayout.CENTER);
        gameFrame.add(scorePanel, BorderLayout.SOUTH); // Puts score panel at the bottom of the frame
        
        currentPlayerLabel = new JLabel("Current Player: Player 1"); // Adds a label to indicate which player's
        currentPlayerLabel.setForeground(Color.DARK_GRAY);
        currentPlayerLabel.setHorizontalAlignment(JLabel.CENTER);
        currentPlayerLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0)); // Adds padding to the label
        gameFrame.add(currentPlayerLabel, BorderLayout.NORTH); // Puts the current player label at the top of the frame
    
        gameFrame.setVisible(true);
    }

    public void displayHighScores() {
        List<String> highScores = new ArrayList<>(); 
        JPanel scorePanel = new JPanel(new BorderLayout());
        JList<String> scoreList = new JList<>(highScores.toArray(new String[0]));
        scorePanel.add(scoreList, BorderLayout.CENTER);
        JScrollPane scrollPane = new JScrollPane(scorePanel);

        JOptionPane.showMessageDialog(null, scrollPane, "High Scores", JOptionPane.PLAIN_MESSAGE);
    }
}
