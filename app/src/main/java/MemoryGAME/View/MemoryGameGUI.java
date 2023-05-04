package MemoryGame.view;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.Timer;
import java.awt.Color;


import MemoryGame.ControllerInterface;
// import MemoryGame.GameObserver;
import MemoryGame.model.CardBoard;


public class MemoryGameGUI implements ActionListener {
    private JFrame gameFrame;
    private CardPanel gamePanel;
    private JPanel scorePanel;
    private JLabel playerLabel;
    private JLabel playerScore;
    private JLabel movesLabel;
    private Card previousCardPressed;
    private Card currentCardPressed;
    Color panelColor = new Color(255,230,205);
    Color fontColor = new Color(76,41,8);
    Color frontCardColor = new Color(35,79,30);

    private ControllerInterface controller;
    private CardBoard board;
    private int disabledCards;

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

    public MemoryGameGUI(ControllerInterface controller, CardBoard board)
    {
        this.board = board;
        this.controller = controller;
        this.disabledCards = 0;

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
        
        timer.start();

        gameFrame.add(gamePanel, BorderLayout.CENTER);
        gameFrame.add(scorePanel, BorderLayout.SOUTH);
        gameFrame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent event)
    {
        Card button = (Card)event.getSource();
        if (button.isMatched()) {
            return; // Do nothing if the card is already matched
        }

        if (previousCardPressed == null) {
            // First card clicked
            button.setBackground(Color.WHITE);
            previousCardPressed = button;

            previousCardPressed.revealCardContent();
        } else {
            // Second card clicked
            if(button.revealCardContent()){
                button.setBackground(Color.WHITE);
                currentCardPressed = button;
                boolean result = this.board.checkMatch(previousCardPressed, currentCardPressed);
                if (result == false) {
                    Timer timer = new Timer(200, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            previousCardPressed.setBackground(frontCardColor);
                            currentCardPressed.setBackground(frontCardColor);
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
                    currentCardPressed.setBackground(Color.WHITE);
                    previousCardPressed.setEnabled(false);
                    currentCardPressed.setEnabled(false);
                    disabledCards += 2;

                    if (this.disabledCards == 16) {
                        timer.stop();
                        addHighScore(this.board.getMoves());
                        //JOptionPane.showMessageDialog(null, "Game Over!");
                        JPanel gameOverPanel = new JPanel();
                        gameOverPanel.setLayout(new GridLayout(2, 1)); // create a grid layout with 2 rows and 1 column

                        JButton startOverButton = new JButton("Start over");
                        startOverButton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                
                                restartGame();
                                Window win = SwingUtilities.getWindowAncestor(gameOverPanel);
                                win.dispose();
                                
                             }
                        });

                        JButton gameOverButton = new JButton("Game over");
                        gameOverButton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                System.exit(0);
                            }
                        });

                        gameOverPanel.add(startOverButton); // add the "Start over" button to the panel
                        gameOverPanel.add(gameOverButton); // add the "Game over" button to the panel

                        JOptionPane.showMessageDialog(null, gameOverPanel, "Game Over", JOptionPane.PLAIN_MESSAGE);

                    }

                    previousCardPressed = null;
                    currentCardPressed = null;
                }
            } else {
                previousCardPressed.hideCardContent();  //add green
                previousCardPressed.setBackground(frontCardColor);
                previousCardPressed=null; 
            }
        }

        this.controller.cardPressed(button);
        this.board.incrementMoves();
        movesLabel.setText("Moves: " + this.board.getMoves());
    }

    public void addHighScore(int i){
        String fileName = "HighScores.txt";
    
        try {
            File file = new File(fileName);
            if (!file.exists()) {
                file.createNewFile();
                System.out.println("New file created here: "+file.getAbsolutePath());
            }
    
            // Read the contents of the file into a list of integers
            ArrayList<Integer> highScores = new ArrayList<>();
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                highScores.add(Integer.parseInt(line));
            }
            bufferedReader.close();
    
            // Add the new integer to the list
            highScores.add(i);
    
            // Sort the list in ascending order
            Collections.sort(highScores);
    
            // Write the sorted list back to the file
            FileWriter fileWriter = new FileWriter(file, false);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for (int score : highScores) {
                bufferedWriter.write(Integer.toString(score));
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
    
            System.out.println("The integer has been appended to the file in ascending order.");
        } catch (IOException e) {
            System.out.println("An error occurred while appending the integer to the file.");
            e.printStackTrace();
        }


    }

    public void restartGame() {
        // Stop the timer
        timer.stop();
    
        // Reset the board
        gameFrame.dispose();
        // create a new card board object
        CardBoard newBoard = new CardBoard();

        // create a new memory game GUI with the new board object
        MemoryGameGUI newGame = new MemoryGameGUI(controller, newBoard);
        
        // Reset the score panel
        playerScore.setText("00.00.00");
        movesLabel.setText("Moves: 0");
    
        // Reset the cards
        previousCardPressed = null;
        currentCardPressed = null;
        disabledCards = 0;
    
        // Start the timer again
        timer.restart();
    }
    
        

}