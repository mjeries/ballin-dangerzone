package cisc230.game;

import cisc230.ui.GameArea;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Vector;

public final class Bullet extends GameObject {
	Player playerWhoFired; // Player who fired this bullet

	/**
	 * Creates a bullet at starting position with movement
	 * direction and reference to a player who fired it.
	 */
	protected Bullet(int startGridX, int startGridY, int move, Player playerWhoFired) {
		super(startGridX, startGridY);
		setColor(Color.yellow); // Default bullet color
		setMove(move);
		this.playerWhoFired = playerWhoFired;
	}
	/**
	 * Overrides default draw method to draw a bullet as a small
	 * circle.
	 */
	protected void draw(BufferedImage image) {
		Graphics2D g2d = image.createGraphics();
		g2d.setColor(getColor());
		g2d.fillOval((getGridX()-1)*GameArea.TILE_WIDTH+GameArea.TILE_WIDTH/4, (getGridY()-1)*GameArea.TILE_HEIGHT+GameArea.TILE_HEIGHT/4, GameArea.TILE_WIDTH/2, GameArea.TILE_HEIGHT/2);
	}
	/**
	 * Gets player who fired this bullet.
	 */
	public Player getPlayerWhoFired() {
		return playerWhoFired;
	}
}

