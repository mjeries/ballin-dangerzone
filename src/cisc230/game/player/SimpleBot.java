package cisc230.game.player;

import java.util.Random;
import cisc230.game.Player;
import cisc230.game.GameObject;
import cisc230.game.Game;
import cisc230.ui.GameArea;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Vector;

/**
 * Semi intelligent bot that avoids imediate threat but has no
 * notion of choosing a target among bots. If there is no threat
 * it will move in random direction if the spot is safe (i.e.
 * threat is 0). It will only shoot if another bot is in its
 * line of sight, i.e. in the same row or same column.
 */
public class SimpleBot extends Player {
	Random generator; // Random number generator
	int moveCounter;
	int moveDirection;

	public SimpleBot() {
		super();
		setColor(Color.pink);
		// The same random number generator will be used through out
		// the life of this bot
		moveDirection = GameObject.NONE;
		moveCounter = 0;
		generator = new Random();
	}
	/**
	 * Overrides default draw method to draw a simple bot as a pink
	 * circle.
	 */
	protected void draw(BufferedImage image) {
		Graphics2D g2d = image.createGraphics();
		g2d.setColor(getColor());
		g2d.fillOval((getGridX()-1)*GameArea.TILE_WIDTH, (getGridY()-1)*GameArea.TILE_HEIGHT, GameArea.TILE_WIDTH, GameArea.TILE_HEIGHT);
		g2d.setColor(GameArea.BACKGROUND_COLOR);
		g2d.fillOval((getGridX()-1)*GameArea.TILE_WIDTH+GameArea.TILE_WIDTH/6, (getGridY()-1)*GameArea.TILE_HEIGHT+GameArea.TILE_HEIGHT/6, GameArea.TILE_WIDTH * 3/4, GameArea.TILE_HEIGHT * 3/4);
	}
	/**
	 * <p>
	 * Generates a move to a tile with less threat than the current
	 * tile. Checks all four directions and if any bot is "seen"
	 * generate a shot in that direction.<br/>
	 * Continues generating moves and shots for as long as the both is
	 * alive (active in a game).
	 * </p>
	 */
	public void execute() {
		setMove(generateMove());
		if (getFirstBotInLineOfSight(GameObject.UP) != null ) {
			setShot(GameObject.UP);
		} else if (getFirstBotInLineOfSight(GameObject.DOWN) != null ) {
			setShot(GameObject.DOWN);
		} else if (getFirstBotInLineOfSight(GameObject.LEFT) != null ) {
			setShot(GameObject.LEFT);
		} else if (getFirstBotInLineOfSight(GameObject.RIGHT) != null ) {
			setShot(GameObject.RIGHT);
		}
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
	protected int generateMove() {
		// Check tile above this bot
		if (getThreatOnTileInDirection(GameObject.UP) != null) {
			// If threat on the tile above is less than the threat on current tile move up
			if (getThreatOnTileInDirection(GameObject.UP) < getThreatMatrix().get(getGridX()-1, getGridY()-1)) {
				return GameObject.UP;
			}
		}
		// Check tile below this bot
		if (getThreatOnTileInDirection(GameObject.DOWN) != null) {
			// If threat on the tile below is less than the threat on current tile move up
			if (getThreatOnTileInDirection(GameObject.DOWN) < getThreatMatrix().get(getGridX()-1, getGridY()-1)) {
				return GameObject.DOWN;
			}
		}
		// Check tile to the left of this bot
		if (getThreatOnTileInDirection(GameObject.LEFT) != null) {
			// If threat on the tile below is less than the threat on current tile move up
			if (getThreatOnTileInDirection(GameObject.LEFT) < getThreatMatrix().get(getGridX()-1, getGridY()-1)) {
				return GameObject.LEFT;
			}
		}
		// Check tile to the right of this bot
		if (getThreatOnTileInDirection(GameObject.RIGHT) != null) {
			// If threat on the tile below is less than the threat on current tile move up
			if (getThreatOnTileInDirection(GameObject.RIGHT) < getThreatMatrix().get(getGridX()-1, getGridY()-1)) {
				return GameObject.RIGHT;
			}
		}
		if (getGridY()==1 && moveDirection==GameObject.UP) {
			moveDirection=GameObject.DOWN;
		}
		if (getGridY()==Game.HEIGHT && moveDirection==GameObject.DOWN) {
			moveDirection=GameObject.UP;
		}
		if (getGridX()==Game.WIDTH && moveDirection==GameObject.RIGHT) {
			moveDirection=GameObject.LEFT;
		}
		if (getGridX()==1 && moveDirection==GameObject.LEFT) {
			moveDirection=GameObject.RIGHT;
		}
		if (moveCounter < 10) {
			moveCounter++;
		} else {
			moveDirection = generator.nextInt(4) + 1; // Always move
			moveCounter = 0;
		}
		if (moveDirection != GameObject.NONE && getThreatOnTileInDirection(moveDirection) != null) {
			if (getThreatOnTileInDirection(moveDirection).intValue() == 0) {
				return moveDirection;
			}
		}
		// If no move was decided based on threat and no random move to safe position
		return GameObject.NONE;
	}
	/**
	 * Returns a threat on a tile in a given direction from this bot. Returns a null
	 * if tile is not valid. This behavior comes from how Array2D is implemented.
	 */
	protected Integer getThreatOnTileInDirection(int direction) {
		Integer threat = null;
		switch (direction) {
			case GameObject.UP: threat = getThreatMatrix().get(getGridX()-1, getGridY()-1-1);
			break;
			case GameObject.DOWN: threat = getThreatMatrix().get(getGridX()-1, getGridY()-1+1);
			break;
			case GameObject.LEFT: threat = getThreatMatrix().get(getGridX()-1-1, getGridY()-1);
			break;
			case GameObject.RIGHT: threat = getThreatMatrix().get(getGridX()-1+1, getGridY()-1);
			break;
		}
		return threat;
	}
}