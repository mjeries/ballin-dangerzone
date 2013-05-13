package cisc230.game.player.target;

import java.util.Random;
import cisc230.Array2D;
import cisc230.game.Player;
import cisc230.game.Game;
import cisc230.game.GameObject;
import cisc230.ui.GameArea;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Vector;

/**
 * A target bot that moves and shoots occasionaly used for game testing.
 */
public class ShootingTargetBot extends Player {
	private int shotDirection;
	private boolean isShootingEnabled;
	Random generator;
	int moveCounter;

	public ShootingTargetBot(boolean isShootingEnabled) {
		super();
		setColor(Color.red);
		// The same random number generator will be used through out
		// the life of this bot
		generator = new Random();
		moveCounter = 0;
		shotDirection = 1;
		this.isShootingEnabled = isShootingEnabled;
	}
	/**
	 * Overrides default draw method to draw a target bot as a small
	 * target.
	 */
	public void draw(BufferedImage image) {
		Graphics2D g2d = image.createGraphics();
		g2d.setColor(getColor());
		g2d.fillOval((getGridX()-1)*GameArea.TILE_WIDTH, (getGridY()-1)*GameArea.TILE_HEIGHT, GameArea.TILE_WIDTH, GameArea.TILE_HEIGHT);
		g2d.setColor(Color.white);
		g2d.fillOval((getGridX()-1)*GameArea.TILE_WIDTH+GameArea.TILE_WIDTH/6, (getGridY()-1)*GameArea.TILE_HEIGHT+GameArea.TILE_HEIGHT/6, GameArea.TILE_WIDTH * 3/4, GameArea.TILE_HEIGHT * 3/4);
		g2d.setColor(getColor());
		g2d.fillOval((getGridX()-1)*GameArea.TILE_WIDTH+GameArea.TILE_WIDTH/4 + GameArea.TILE_WIDTH/8 , (getGridY()-1)*GameArea.TILE_HEIGHT+GameArea.TILE_HEIGHT/4 + GameArea.TILE_HEIGHT/8, GameArea.TILE_WIDTH/2, GameArea.TILE_HEIGHT/2);
	}
	/**
	 * Moves the same way as TargetBot. Every move it will check only one
	 * direction and if any bot is in its line of sight shoot a bullet in
	 * that direction.
	 */
	public void execute() {
		// Where to move:
		if (moveCounter == 0) { // Set where to move
			setMove(generator.nextInt(5)); // Will generate random move 0 to 4
		} else {
			setMove(getPreviousMove());
		}
		if (moveCounter < 4) {
			moveCounter++;
		} else {
			moveCounter = 0;
		}
		// Where to shoot
		if (shotDirection > 4) {
			shotDirection = 1;
		}
		// Only look in one direction in each step of the game
		// If another both that is not ShootingTargetBot is seen
		// in that direction fire a shot
		Player aTarget = getFirstBotInLineOfSight(shotDirection);
		if (aTarget != null && !(aTarget instanceof ShootingTargetBot)) {
			setShot(shotDirection);
		}
		shotDirection++;
		if (!isShootingEnabled) {
			setShot(GameObject.NONE);
		}
	}
	public void reset() {
		// If you decide to override reset() method you have to call it's parent reset() method
		super.reset();
		shotDirection = 1;
	}
	protected Player getFirstBotInLineOfSight(int direction) {
		getPlayerTiles().start(getGridX()-1, getGridY()-1, direction);
		while (getPlayerTiles().hasNext()) {
			getPlayerTiles().next();
			if (getPlayerTiles().get() != null) {
				return getPlayerTiles().get();
			}
		}
		return null;
	}
}