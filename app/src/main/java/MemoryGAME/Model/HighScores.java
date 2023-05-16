package MemoryGame.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class HighScores {
    private String fileName;

    public HighScores() {
        this.fileName =  "HighScores.txt";
    }

    public ArrayList<String> getCurrentScores() {
        ArrayList<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return lines;
    }

    public void addScore(int score) {
        try {
            File file = new File(this.fileName);
            if (!file.exists()) {
                file.createNewFile();
                System.out.println("New file created here: "+file.getAbsolutePath());
            }
    
            ArrayList<Integer> highScores = new ArrayList<>();
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                highScores.add(Integer.parseInt(line));
            }
            bufferedReader.close();
    
            // Add the new integer to the list
            highScores.add(score);
    
            // Sort the list in ascending order
            Collections.sort(highScores);
    
            // Write the sorted list back to the file
            FileWriter fileWriter = new FileWriter(file, false);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for (int i : highScores) {
                bufferedWriter.write(Integer.toString(i));
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred while appending the integer to the file.");
            e.printStackTrace();
        }
    }
    
}
