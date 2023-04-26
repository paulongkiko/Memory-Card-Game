package MemoryGame.controller;

import MemoryGame.MemoryGameControllerInterface;
import MemoryGame.view.MemoryGameGUI;
import MemoryGame.model.CardBoard;

public class MemoryGameController implements MemoryGameControllerInterface {
    
    public MemoryGameController() {
        new MemoryGameGUI();
        CardBoard board = new CardBoard();
    }

}