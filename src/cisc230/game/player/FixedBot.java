package cisc230.game.player;

import cisc230.game.Player;
import cisc230.ui.GameArea;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Fixed, immovable bot used for game testing.
 */
public class FixedBot extends Player {
	
	public FixedBot(int startGridX, int startGridY) {
		super(startGridX, startGridY); // DO NOT MODIFY THIS LINE! IT HAS TO BE FIRST STATEMENT IN THE CONSTRUCTOR!
		setColor(Color.blue);
	}
	public void execute() {
		// Do nothing, but we have to "implement" this method to be
		// able to create an instance of FixedBod in Game.java. If
		// we didn't, than this class would be abstract as well and
		// we wouldn't be able to instantiate it, i.e. create an
		// object of type FixedBot.
	}
}