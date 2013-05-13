package cisc230.game;

import cisc230.Thread;
import cisc230.ui.GameArea;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicInteger;

// See: http://download.oracle.com/javase/6/docs/api/java/util/concurrent/atomic/AtomicInteger.html

/**
 * <p>
 * Represents a generic object in the game. Defines attributes
 * and actions every game object should have. The only reason
 * it is declared abstract is because there should be no objects
 * created (instantiated) since it describes an abstract concept.
 * </p><p>
 * All grid variables have valid values from 1 to the horizontal
 * or vertical size of the grid. That's why 1 has to be subtrated
 * from them when used as index values in any array.
 * </p>
 */
public abstract class GameObject {
	// Possible moves for each game object
	public static final int NONE  = 0;
	public static final int UP    = 1;
	public static final int RIGHT = 2;
	public static final int DOWN  = 3;
	public static final int LEFT  = 4;
	private int startGridX; // Starting horizontal position of bot in the grid
	private int startGridY; // Starting vertical position of bot in grid
	private int gridX; // Horizontal position of bot in the grid
	private int gridY; // Vertical position of bot in grid
	private Color color;
	private int move;
	private int previousMove;

	/**
	 * Creates a game object at the starting position.
	 */
	protected GameObject(int startGridX, int startGridY) {
		this.startGridX = startGridX;
		this.startGridY = startGridY;
		gridX = startGridX;
		gridY = startGridY;
		setColor(Color.blue); // Default color for each game object
		move = GameObject.NONE;
		previousMove = GameObject.NONE;
	}
	/**
	 * Sets starting position for a game object.
	 */
	void setStartingPosition(int startGridX, int startGridY) {
		this.startGridX = startGridX;
		this.startGridY = startGridY;
		gridX = startGridX;
		gridY = startGridY;
	}
	/**
	 * Allows other objects (e.g. instance of Game class) to
	 * find out where this game object should/wants to move.
	 */
	public final int getMove() {
		return move;
	}
	/**
	 * Sets the move for this game object.
	 */
	public final void setMove(int move) {
		setPreviousMove(this.move);
		this.move = move;
	}
	/**
	 * Allows other objects (e.g. instance of Game class) to
	 * find out where this game object requested to move in a
	 * previous game step.
	 */
	public final int getPreviousMove() {
		return previousMove;
	}
	/**
	 * Sets the previous move for this game object.
	 */
	final void setPreviousMove(int move) {
		previousMove = move;
	}
	/**
	 * Changes position of game object by deltaX and deltaY
	 */
	final void changePosition(int deltaX, int deltaY) {
		gridX = gridX + deltaX;
		gridY = gridY + deltaY;
	}
	/**
	 * Resets game object to its starting position and resets
	 * the move to NONE.
	 */
	protected void reset() {
		gridX = startGridX;
		gridY = startGridY;
		setMove(GameObject.NONE);
		setPreviousMove(GameObject.NONE);
	}
	/**
	 * Draws game object on the screen.
	 */
	protected void draw(BufferedImage image) {
		Graphics2D g2d = image.createGraphics();
		g2d.setColor(color);
		// Subtract 1 from gridX and gridY so we don't deal with 0's in grid variables
		g2d.fillRect((gridX-1)*GameArea.TILE_WIDTH, (gridY-1)*GameArea.TILE_HEIGHT, GameArea.TILE_WIDTH, GameArea.TILE_HEIGHT);
	}
	/**
	 * Erases game object on the screen.
	 */
	final void erase(BufferedImage image) {
		Graphics2D g2d = image.createGraphics();
		g2d.setColor(GameArea.BACKGROUND_COLOR);
		// Subtract 1 from gridX and gridY so we don't deal with 0's in grid variables
		g2d.fillRect((gridX-1)*GameArea.TILE_WIDTH, (gridY-1)*GameArea.TILE_HEIGHT, GameArea.TILE_WIDTH, GameArea.TILE_HEIGHT);
	}
	/**
	 * Gets horizontal position of the game object.
	 */
	public final int getGridX() {
		return gridX;
	}
	/**
	 * Gets vertical position of the game object.
	 */
	public final int getGridY() {
		return gridY;
	}
	/**
	 * Gets color of the game object.
	 */
	protected final Color getColor() {
		return color;
	}
	/**
	 * Sets color of the game object.
	 */
	protected final void setColor(Color color) {
		this.color = color;
	}
}


