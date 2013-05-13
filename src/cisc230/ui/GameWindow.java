package cisc230.ui;

import javax.swing.JFrame;

public class GameWindow extends JFrame {
	GameArea gameArea;
	
	public GameWindow(String title, GameArea gameArea) {
		super(title);
		this.gameArea = gameArea;
	}
}
