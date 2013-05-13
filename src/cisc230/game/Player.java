package cisc230.game;

import cisc230.Executable;
import cisc230.ui.GameArea;
import cisc230.Array2D;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Vector;

/**
 * Defines a player in the game. Player class is declared
 * abstract because it doesn't implement execute() method
 * in Executable interface it implements.
 */
public abstract class Player extends GameObject implements Executable {
	private int shot;
	private int previousShot;
	private String name;
	private boolean isActive;
	public static final int MAX_BULLET_COUNT = 5;
	public static final int NUMBER_OF_STEPS_TO_WAIT = 3;
	private int bulletCounter;
	private int waitCounter;
	// Player's reference to playerTiles from Game object
	private Array2D<Player> playerTiles; // Array2D of Player type
	// Player's reference to threatMatrix from Game object
	private Array2D<Integer> threatMatrix; // Array2D of Integer type

	private int killedCounter; // How many times this player was killed
	private int gameScore; // Current game score
	private int totalScore; // Total score for all games

	/**
	 * Creates a player at the starting position.
	 */
	protected Player(int startGridX, int startGridY) {
		super(startGridX, startGridY);
		shot = GameObject.NONE;
		previousShot = GameObject.NONE;
		name = "NoName";
		setActive(true);
		bulletCounter = 0;
		waitCounter = 0;
		killedCounter = 0;
		gameScore = 0;
		totalScore = 0;
	}
	/**
	 * Creates a player when starting position is not important.
	 */
	protected Player() {
		this(1, 1);
	}
	/**
	 * Returns the name of the player.
	 */
	public String getName() {
		return name;
	}
	/**
	 * Sets the name of the player.
	 */
	protected Player setName(String name) {
		this.name = name;
		return this;
	}
	/**
	 * Gets direction in which player wants to shoot. Every time
	 * a bulet is fired a bulletCounter is increased. If the counter
	 * reaches MAX_BULLET_COUNT the player won't be able to shoot
	 * for NUMBER_OF_STEPS_TO_WAIT. If a bulletCounter hasn't
	 * reached MAX_BULLET_COUNT and the player doesn't shoot the
	 * bulletCounter will be decreased.
	 */
	final int getShot() {
		if (waitCounter > 0) {
			waitCounter--;
			shot = GameObject.NONE;
		}
		if (bulletCounter >= Player.MAX_BULLET_COUNT) {
			waitCounter = NUMBER_OF_STEPS_TO_WAIT;
			bulletCounter = 0;
			shot = GameObject.NONE;
		}
		if (bulletCounter > 0 && shot == GameObject.NONE) {
			bulletCounter--;
		}
		if (shot != GameObject.NONE) {
			bulletCounter++;
		}
		setPreviousShot(shot);
		return shot;
	}
	/**
	 * Sets the direction in which this player wants to shoot.
	 */
	public final void setShot(int shot) {
		this.shot = shot;
	}
	/**
	 * Gets previous shot.
	 */
	 public int getPreviousShot() {
		 return previousShot;
	 }
	/**
	 * Sets previous shot.
	 */
	void setPreviousShot(int previousShot) {
		 this.previousShot = previousShot;
	 }
	/**
	 * Returns how many steps of the game you have to wait until you
	 * can fire again.
	 */
	public int getWaitCounter () {
		return waitCounter;
	}
	/**
	 * Sets isActive flag. True indicates that this bot is active
	 * in the current game round. False indicates it's not.
	 */
	void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	/**
	 * Returns active status of the bot. True indicates that this
	 * bot is active in the current game round. False indicates
	 * it's not.
	 */
	public boolean isActive() {
		return isActive;
	}
	/**
	 * Adds a player who shot this player.
	 */
	public void addPlayerWhoShotMe(Player player) {
		// This is not an abstract method because its implementation is optional
		// It's up to you to implement this method if you want to.
	}
	/**
	 * Resets the position, move and shot of the player.
	 */
	protected void reset() {
		super.reset();
		setShot(GameObject.NONE);
		setActive(true);
		bulletCounter = 0;
		waitCounter = 0;
		gameScore = 0;
		//System.out.println("Resetting a player");
	}
	/**
	 * Gets player's reference to playerTiles
	 */
	public Array2D<Player> getPlayerTiles() {
		return playerTiles;
	}
	/**
	 * Sets player's reference to playerTiles
	 */
	Player setPlayerTiles(Array2D<Player> playerTiles) {
		this.playerTiles = playerTiles;
		return this;
	}
	/**
	 * Gets player's reference to threatMatrix
	 */
	public Array2D<Integer> getThreatMatrix() {
		return threatMatrix;
	}
	/**
	 * Sets player's reference to threatMatrix
	 */
	Player setThreatMatrix(Array2D<Integer> threatMatrix) {
		this.threatMatrix = threatMatrix;
		return this;
	}
	int getKilledCounter() {	return killedCounter; }
	void incrementKilledCounter() { killedCounter++; }
	int getGameScore() { return gameScore; }
	void incrementGameScore(int score) { gameScore += score; }
    int getTotalScore() { return totalScore; }
    void incrementTotalScore(int score) { totalScore += score; }
	public String toString() {
		return getName() + "\tactive: " + isActive();
	}
}