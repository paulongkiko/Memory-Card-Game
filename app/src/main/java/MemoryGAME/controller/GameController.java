package MemoryGame.controller;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import MemoryGame.ControllerInterface;
import MemoryGame.model.CardBoard;
import MemoryGame.model.HighScores;
import MemoryGame.view.Card;
import MemoryGame.view.HighScoresGUI;
import MemoryGame.view.MemoryGameGUI;

public class GameController implements ControllerInterface
{
    private CardBoard board;
    private Card previousCardPressed;
    private Card currentCardPressed;
    private int disabledCards;
    private HighScores highScores;
    private int numberOfPlayers;
    private Color frontCardColor = new Color(35,79,30);
    
    public GameController(CardBoard board, int numberOfPlayers) {
        this.board = board;
        this.numberOfPlayers = numberOfPlayers;
        this.disabledCards = 0;
        this.highScores = new HighScores();
        if (numberOfPlayers == 0) {
            new HighScoresGUI(this.highScores);
        } else {
            new MemoryGameGUI(this, board, numberOfPlayers);
        }
    }

    public void cardPressed(Card card, JLabel currentPlayerLabel, JLabel player1Score, JLabel player2Score, JLabel playerMoves, Timer timer) {
        if (previousCardPressed == null) {
            // First card clicked
            card.setBackground(Color.WHITE);
            previousCardPressed = card;
            previousCardPressed.revealCardContent();
        } else {
            // Second card clicked
            if(card.revealCardContent()) {
                card.setBackground(Color.WHITE);
                currentCardPressed = card;
                boolean result = this.board.checkMatch(previousCardPressed, currentCardPressed);
                if (result == false) {
                    // If the two cards are not a match, wait for 1 second and then change the background color back to green
                    Timer localTimer = new Timer(200, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            previousCardPressed.setBackground(frontCardColor);
                            currentCardPressed.setBackground(frontCardColor);
                            previousCardPressed.hideCardContent();
                            currentCardPressed.hideCardContent();
                            previousCardPressed = null;
                            currentCardPressed = null;
                            if (numberOfPlayers == 2) {
                                if (board.getPlayerTurn() == 1) {
                                    board.setPlayerTurn(2);
                                } else {
                                    board.setPlayerTurn(1);
                                }
                                currentPlayerLabel.setText("Current Player: Player " + board.getPlayerTurn());
                            }
                        }
                    });
                    localTimer.setRepeats(false);
                    localTimer.start();
                } else {
                    // If the two cards are a match, set their isMatched variable to true
                    previousCardPressed.setMatched(true);
                    currentCardPressed.setMatched(true);
                    if (numberOfPlayers == 2) {
                        if (this.board.getPlayerTurn() == 1){
                            currentCardPressed.setBackground(Color.RED);
                            previousCardPressed.setBackground(Color.RED);
                            this.board.setPlayer1Score(this.board.getPlayer1Score() + 1);
                            player1Score.setText(String.valueOf(this.board.getPlayer1Score()));
                        } else {
                            currentCardPressed.setBackground(Color.BLUE);
                            previousCardPressed.setBackground(Color.BLUE);
                            this.board.setPlayer2Score(this.board.getPlayer2Score() + 1);
                            player2Score.setText(String.valueOf(this.board.getPlayer2Score()));
                        }
                    } else {
                        currentCardPressed.setBackground(Color.WHITE);
                    }
                    previousCardPressed.setEnabled(false);
                    currentCardPressed.setEnabled(false);
                    disabledCards += 2;

                    if (this.disabledCards == 16) {
                        if (numberOfPlayers == 1) {
                            this.highScores.addScore(this.board.getMoves());
                            timer.stop();
                        }
                        gameOver();
                    }

                    previousCardPressed = null;
                    currentCardPressed = null;

                    if (numberOfPlayers == 2) {
                        player1Score.setText(String.valueOf(this.board.getPlayer1Score()));
                        player2Score.setText(String.valueOf(this.board.getPlayer2Score()));
                    }
                }
            } else {
                previousCardPressed.hideCardContent();  //add green
                previousCardPressed.setBackground(frontCardColor);
                previousCardPressed=null; 
            }
        }

        if (numberOfPlayers == 1) {
            this.board.incrementMoves();
            playerMoves.setText("Moves: " + this.board.getMoves());
        }
    }

    public void gameOver() {
        String finalResult = "";
        if (numberOfPlayers == 2) {
            finalResult = this.gameResult();
        }
        
        JPanel gameOverPanel = new JPanel();
        gameOverPanel.setLayout(new GridLayout(2, 1)); // create a grid layout with 2 rows and 1 column

        JButton startOverButton = new JButton("Start over! " + finalResult);
        startOverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                restartGame();
                Window win = SwingUtilities.getWindowAncestor(gameOverPanel);
                win.dispose();
            }
        });

        JButton gameOverButton = new JButton("Game over! " + finalResult);
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

    public void restartGame() {
        // Reset the board
        // gameFrame.dispose();
        
        // create a new card board object
        CardBoard newBoard = new CardBoard();

        // create a new memory game GUI with the new board object
        new MemoryGameGUI(this, newBoard, this.numberOfPlayers);
    
        // Reset the cards
        this.previousCardPressed = null;
        this.currentCardPressed = null;
        this.disabledCards = 0;
    }

    public String gameResult() {
        String finalResult = "";
        int player1Score = this.board.getPlayer1Score();
        int player2Score = this.board.getPlayer2Score();
        if (player1Score > player2Score) {
            finalResult = "Player 1 Won!";
        } else if (player1Score < player2Score) {
            finalResult = "Player 2 Won!";
        } else {
            finalResult = "It is a tie!";
        }
        return finalResult;
    }
}
