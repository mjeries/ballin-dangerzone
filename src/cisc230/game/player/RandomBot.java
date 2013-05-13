package cisc230.game.player;

import java.util.Random;
import cisc230.game.Player;
import cisc230.ui.GameArea;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Vector;

/**
 * Random, crazy behavior used for game testing
 * See: http://www.cs.geneseo.edu/~baldwin/reference/random.html
 */
public class RandomBot extends Player {
	Random generator; // Random number generator

	public RandomBot(int startGridX, int startGridY) {
		super(startGridX, startGridY);
		setColor(Color.red);
		// The same random number generator will be used through out
		// the life of this bot
		generator = new Random();
	}
	/**
	 * Generator a ranodm move and a random shot for each step of
	 * the game for as long as the bot is alive.
	 */
	public void execute() {
		setMove(generator.nextInt(5)); // Will generate random move 0 to 4
		setShot(generator.nextInt(5)); // Will generate random shot 0 to 4
	}
}