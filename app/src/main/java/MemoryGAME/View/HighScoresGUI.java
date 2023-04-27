package MemoryGame.view;

import java.awt.BorderLayout;

import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import MemoryGame.model.HighScores;

public class HighScoresGUI {
    public HighScoresGUI(HighScores scores) {
        JPanel scorePanel = new JPanel(new BorderLayout());
        JList<String> scoreList = new JList<>(scores.getCurrentScores().toArray(new String[0]));
        scorePanel.add(scoreList, BorderLayout.CENTER);
        
        JScrollPane scrollPane = new JScrollPane(scorePanel);
        JOptionPane.showMessageDialog(null, scrollPane, "High Scores", JOptionPane.PLAIN_MESSAGE);
    }
}
