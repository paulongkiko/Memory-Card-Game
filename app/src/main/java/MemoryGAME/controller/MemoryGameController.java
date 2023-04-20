package MemoryGame.controller;

import MemoryGame.MemoryGameControllerInterface;
import MemoryGame.view.MemoryGameGUI;

public class MemoryGameController implements MemoryGameControllerInterface {
    
    public MemoryGameController() {
        new MemoryGameGUI();
    }

}