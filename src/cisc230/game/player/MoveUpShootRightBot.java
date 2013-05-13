package cisc230.game.player;

import java.util.Random;
import cisc230.game.Player;
import cisc230.game.GameObject;
import cisc230.ui.GameArea;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Vector;

/**
 * An example class to show you what we would have to create if
 * we didn't use inner class to create this class in lines 60 to
 * 65 in Game.java.
 */
public class MoveUpShootRightBot extends Player {
	public MoveUpShootRightBot(int startGridX, int startGridY) {
		super(startGridX, startGridY);
	}
	/**
	 * Generates a random move and repeats it 4 times, after that another
	 * random move is generated and repeated 4 times and so on for as long
	 * as the both is alive (active in a game).
	 */
	public void execute() {
		setMove(GameObject.UP);
		setShot(GameObject.RIGHT);
	}
}