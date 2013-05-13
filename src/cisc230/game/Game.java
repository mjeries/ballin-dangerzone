package cisc230.game;

import cisc230.ui.GameArea;
//import cisc230.game.Game;
import cisc230.Array2D;
import cisc230.GameThread;
import cisc230.game.player.*; // To import all classes in a package use *
import java.util.Vector;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicBoolean;
import java.awt.Color;
import java.lang.Integer;
import java.util.Random;
import cisc230.game.player.bot.*;
import cisc230.game.player.target.*;
// TODO: Uncomment line below that contains your import statement
//import cisc230.game.player.andrew.*;
//import cisc230.game.player.brasil.*;
//import cisc230.game.player.charles.*;
//import cisc230.game.player.courtney.*;
//import cisc230.game.player.david.*;
//import cisc230.game.player.devin.*;
//import cisc230.game.player.edward.*;
//import cisc230.game.player.emmaly.*;
//import cisc230.game.player.gary.*;
//import cisc230.game.player.james.*;
//import cisc230.game.player.jason.*;
import cisc230.game.player.joel.*;
//import cisc230.game.player.jordan.*;
//import cisc230.game.player.michaeld.*;
import cisc230.game.player.michaelj.*;
//import cisc230.game.player.paul.*;
//import cisc230.game.player.solomon.*;
//import cisc230.game.player.theodore.*;
//import cisc230.game.player.tou.*;
//import cisc230.game.player.vang.*;
//import cisc230.game.player.vontez.*;
//import cisc230.game.player.zachary.*;

// See http://download.oracle.com/javase/6/docs/api/java/util/Vector.html
// See http://download.oracle.com/javase/tutorial/collections/interfaces/collection.html
// See http://download.oracle.com/javase/6/docs/api/java/util/Iterator.html

public class Game {
	final int[] START_X = {7, 13, 19, 25, 31, 37};
	final int[] START_Y = {7, 14, 21, 28};
	final public static int WIDTH = 44; // Game area width in tiles
	final public static int HEIGHT = 33; // Game area height in tiles
	final public static int MAX_GAME_STEPS = 150; // How many steps a game round can last if there is no winner
	GameArea gameArea;
	AtomicBoolean isRunning;
	int gameCounter;
	// Vector of all players in the game
	Vector<Player> players;
	// Temporary vector of players that are still alive in current game
	Vector<Player> activePlayers;
	//Vector<Player> observers;
	Vector<Bullet> shots;
	Array2D<Player> playerTiles; // 2D array of Player type
	Array2D<Integer> threatMatrix; // 2D array of Integer type
	Player interactivePlayer;
	Random generator;

	public Game(GameArea gameArea) {
		this.gameArea = gameArea;
		isRunning = new AtomicBoolean(false);
		gameCounter = 0;
		players = new Vector<Player>();
		activePlayers = new Vector<Player>();
		playerTiles = new Array2D(WIDTH, HEIGHT); // 2D array of Player type
		threatMatrix = new Array2D(WIDTH, HEIGHT); // 2D array of Integer type
		shots = new Vector<Bullet>();
		generator = new Random();

		// Add players/bots
		// TODO: Uncomment line bellow that contains your constructor:
		//addPlayer(new Bot()).setPlayerTiles(playerTiles).setThreatMatrix(threatMatrix);
		//addPlayer(new BotAndrewN()).setPlayerTiles(playerTiles).setThreatMatrix(threatMatrix);
		//addPlayer(new BotBrasilP()).setPlayerTiles(playerTiles).setThreatMatrix(threatMatrix);
		//addPlayer(new BotCharlesK()).setPlayerTiles(playerTiles).setThreatMatrix(threatMatrix);
		//addPlayer(new BotCourtneyB()).setPlayerTiles(playerTiles).setThreatMatrix(threatMatrix);
		//addPlayer(new BotDavidA()).setPlayerTiles(playerTiles).setThreatMatrix(threatMatrix);
		//addPlayer(new BotDevinD()).setPlayerTiles(playerTiles).setThreatMatrix(threatMatrix);
		//addPlayer(new BotEdwardL()).setPlayerTiles(playerTiles).setThreatMatrix(threatMatrix);
		//addPlayer(new EmmaBotJr()).setPlayerTiles(playerTiles).setThreatMatrix(threatMatrix);
		//addPlayer(new BotGaryV()).setPlayerTiles(playerTiles).setThreatMatrix(threatMatrix);
		//addPlayer(new BotJamesR()).setPlayerTiles(playerTiles).setThreatMatrix(threatMatrix);
		//addPlayer(new BotJasonB()).setPlayerTiles(playerTiles).setThreatMatrix(threatMatrix);
		addPlayer(new BotJoelH()).setPlayerTiles(playerTiles).setThreatMatrix(threatMatrix);
		//addPlayer(new BotJordanW()).setPlayerTiles(playerTiles).setThreatMatrix(threatMatrix);
		//addPlayer(new BotMichaelD()).setPlayerTiles(playerTiles).setThreatMatrix(threatMatrix);
		addPlayer(new BotMichaelJ()).setPlayerTiles(playerTiles).setThreatMatrix(threatMatrix);
		//addPlayer(new BotPaulH()).setPlayerTiles(playerTiles).setThreatMatrix(threatMatrix);
		//addPlayer(new BotSolomonA()).setPlayerTiles(playerTiles).setThreatMatrix(threatMatrix);
		//addPlayer(new BotTheodoreK()).setPlayerTiles(playerTiles).setThreatMatrix(threatMatrix);
		//addPlayer(new BotTouL()).setPlayerTiles(playerTiles).setThreatMatrix(threatMatrix);
		//addPlayer(new BotVangL()).setPlayerTiles(playerTiles).setThreatMatrix(threatMatrix);
		//addPlayer(new BotVontezD()).setPlayerTiles(playerTiles).setThreatMatrix(threatMatrix);
		//addPlayer(new BotZacharyB()).setPlayerTiles(playerTiles).setThreatMatrix(threatMatrix);


		boolean isMinimumAcceptableProject = true;
		// TODO: Change variable below to false if you are not shooting for the full grade for the project
		boolean isFullGradeProject = true;
		if (isMinimumAcceptableProject || isFullGradeProject) {
			boolean isShootingEnabled = false;
			if (isFullGradeProject) {
				isShootingEnabled = true;
				addPlayer(new SimpleBot()).setPlayerTiles(playerTiles).setThreatMatrix(threatMatrix).setName("SimpleBot");
			}
			addPlayer(new ShootingTargetBot(isShootingEnabled)).setPlayerTiles(playerTiles).setThreatMatrix(threatMatrix).setName("ShootingTargetBot 1");
			addPlayer(new ShootingTargetBot(isShootingEnabled)).setPlayerTiles(playerTiles).setThreatMatrix(threatMatrix).setName("ShootingTargetBot 2");
			addPlayer(new ShootingTargetBot(isShootingEnabled)).setPlayerTiles(playerTiles).setThreatMatrix(threatMatrix).setName("ShootingTargetBot 3");
			addPlayer(new ShootingTargetBot(isShootingEnabled)).setPlayerTiles(playerTiles).setThreatMatrix(threatMatrix).setName("ShootingTargetBot 4");
			addPlayer(new ShootingTargetBot(isShootingEnabled)).setPlayerTiles(playerTiles).setThreatMatrix(threatMatrix).setName("ShootingTargetBot 5");
			addPlayer(new ShootingTargetBot(isShootingEnabled)).setPlayerTiles(playerTiles).setThreatMatrix(threatMatrix).setName("ShootingTargetBot 6");
		}
		//addPlayer(new RandomBot(22, 29)).setName("RandomBot 1"); // No need to set playerTiles and threatMatrix because this bot is not using them
		// Instead of using inner class we could create a separate class but it's too much redundancy
		// since we only need one instance of the class and we are only implementing execute method()
		// with two line of code.
		//addPlayer(new FixedBot(22, 17) {
		//	public void execute() {
		//		setMove(GameObject.UP);
		//		setShot(GameObject.RIGHT);
		//	}
		//}).setName("MoveUpShootRightBot"); // No need to set playerTiles and threatMatrix because this bot is not using them
		//setInteractivePlayer(new FixedBot(1, 1));
		//getInteractivePlayer().setName("Interactive Player").setColor(Color.green);
		//addPlayer(getInteractivePlayer()).setPlayerTiles(playerTiles).setThreatMatrix(threatMatrix);

		initialize(); // Do not comment out this line

		// Create a game/drawing thread that drives entire game
		GameThread drawingThread = new GameThread("Drawing", gameArea) {
	    	public void execute () {
				int gameStepCounter = 0;
	    		Game game = this.gameArea.getGame();
				while (game.isRunning()) { // Game loop
					createThreatMatrix();
					// Call each player's execute() method
					for (int i=0; i<activePlayers.size(); i++) {
						activePlayers.get(i).execute();
					}
					//printThreatMatrix();
					sleep(250);
					game.fireShots();
					//sleep(50);
					game.processShots();
					//sleep(100);
					game.processMoves();
					//sleep(50);
					game.processShots();
					gameStepCounter++;
					// Used to debug player's status
					//System.out.println("In this step of the game:");
					//for (int i=0; i<players.size(); i++) {
					//	System.out.println(players.get(i));
					//}
					if (activePlayers.size()<=1 || gameStepCounter==Game.MAX_GAME_STEPS) { // Detect the end of the game
						game.setRunning(true); // Stop game loop
						gameStepCounter = 0;
						game.showResults();
						game.eraseAll(); // Erase any remaining shots and last bot if any
						game.resetPlayers();
						game.initialize();
					}
				}
	    	}
	    };
	    drawingThread.setDelay(1500);
	    // We need to have a reference to drawing thread so we can pause
	    // and resume drawing in key listener in gameArea
	    gameArea.setDrawingThread(drawingThread);
	}

	public boolean isRunning() { return isRunning.get(); }
	public void setRunning(boolean isRunning) { this.isRunning.set(isRunning); }
	public Player getInteractivePlayer() {
		return interactivePlayer;
	}
	public void setInteractivePlayer(Player interactivePlayer) {
		this.interactivePlayer = interactivePlayer;
	}
	public void setInteractivePlayerMove(int move) {
		getInteractivePlayer().setMove(move);
	}
	public void setInteractivePlayerShot(int shot) {
		getInteractivePlayer().setShot(shot);
	}
	public void showResults() {
		gameCounter++;
		gameArea.writeToLog("Round " + gameCounter + " ended.\n");
		gameArea.writeToLog("Total\tGame\tKilled\tName\n");
		for (int i=0; i<players.size(); i++) {
			gameArea.writeToLog(players.get(i).getTotalScore() + "\t");
			gameArea.writeToLog(players.get(i).getGameScore() + "\t");
			gameArea.writeToLog(players.get(i).getKilledCounter() + "\t");
			gameArea.writeToLog(players.get(i).getName() + "\n");
		}
		gameArea.writeToLog("-----------------------------------------------------------------------------\n");
	}

	public void eraseAll() {
		// Erase all active players
		for (int i=0; i<activePlayers.size(); i++) {
			activePlayers.get(i).erase(gameArea.getImage());
			playerTiles.set(activePlayers.get(i).getGridX()-1, activePlayers.get(i).getGridY()-1, null);
		}
		// Erase all shots
		for (int i=0; i<shots.size(); i++) {
			shots.get(i).erase(gameArea.getImage());
		}
	}
	public void resetPlayers() {
		for (int i=0; i<players.size(); i++) {
			players.get(i).reset();
		}
	}
	public void fireShots() {
		// Get new shots that are just fired
		for (int i=0; i<activePlayers.size(); i++) {
			Player player = activePlayers.get(i);
			int shot = player.getShot();
			if (shot != GameObject.NONE) {
				shots.add(new Bullet(player.getGridX(), player.getGridY(), shot, player));
				player.setShot(GameObject.NONE);
			}
		}
	}
	public void processShots() {
		// Move all shots one tile
		Iterator<Bullet> i = shots.iterator();
		while (i.hasNext()) {
			Bullet shot = (Bullet)i.next();
			Player player = null;
			int move = shot.getMove();
			// If shot has not just been fired erase its previous position
			// Every just fired shot is initially on the same tile as the player who shot it
			if (playerTiles.get(shot.getGridX()-1, shot.getGridY()-1) == null) {
				shot.erase(gameArea.getImage());
			}
			// Remove shot if it's hitting the edge of game area
			if ((move==GameObject.UP && shot.getGridY()==1) ||
				(move==GameObject.DOWN && shot.getGridY()==Game.HEIGHT) ||
				(move==GameObject.LEFT && shot.getGridX()==1) ||
				(move==GameObject.RIGHT && shot.getGridX()==Game.WIDTH)) {
				i.remove(); // You cannot use shots.remove(shot); here!!!
			// Remove player and shot if shot is moving into player's tile
			} else if ((move==GameObject.UP && (player=playerTiles.get(shot.getGridX()-1, shot.getGridY()-1-1))!=null) ||
				(move==GameObject.DOWN && (player=playerTiles.get(shot.getGridX()-1, shot.getGridY()-1+1))!=null) ||
				(move==GameObject.LEFT && (player=playerTiles.get(shot.getGridX()-1-1, shot.getGridY()-1))!=null) ||
				(move==GameObject.RIGHT && (player=playerTiles.get(shot.getGridX()-1+1, shot.getGridY()-1))!=null)) {
				playerTiles.set(player.getGridX()-1, player.getGridY()-1, null);
				player.erase(gameArea.getImage());
				player.setActive(false);
				player.addPlayerWhoShotMe(shot.getPlayerWhoFired());
				player.incrementKilledCounter();
				shot.getPlayerWhoFired().incrementGameScore(1);
				shot.getPlayerWhoFired().incrementTotalScore(1);
				// Remove shot player
				activePlayers.remove(player);
				i.remove(); // You cannot use shots.remove(shot); here!!!
			} else {
				// Move shot
				switch (move) {
					case GameObject.UP:
					shot.changePosition(0, -1);
					break;
					case GameObject.DOWN:
					shot.changePosition(0, 1);
					break;
					case GameObject.LEFT:
					shot.changePosition(-1, 0);
					break;
					case GameObject.RIGHT:
					shot.changePosition(1, 0);
					break;
				}
				shot.draw(gameArea.getImage());
			}
		}
	}
	public boolean isMovePossible(Player player, int move) {
		// Is player hitting the edge of the game area?
		if ((move==GameObject.UP && player.getGridY()==1) ||
			(move==GameObject.DOWN && player.getGridY()==Game.HEIGHT) ||
			(move==GameObject.LEFT && player.getGridX()==1) ||
			(move==GameObject.RIGHT && player.getGridX()==Game.WIDTH)) {
			return false;
		// Is player hitting another player?
		} else if ((move==GameObject.UP && playerTiles.get(player.getGridX()-1, player.getGridY()-1-1)!=null) ||
			(move==GameObject.DOWN && playerTiles.get(player.getGridX()-1, player.getGridY()-1+1)!=null) ||
			(move==GameObject.LEFT && playerTiles.get(player.getGridX()-1-1, player.getGridY()-1)!=null) ||
			(move==GameObject.RIGHT && playerTiles.get(player.getGridX()-1+1, player.getGridY()-1)!=null)) {
			return false;
		} else {
			return true;
		}
	}
	public void processMoves() {
		for (int i=0; i<activePlayers.size(); i++) {
			Player player = activePlayers.get(i);
			int move = player.getMove();
			if (isMovePossible(player, move)) {
				player.erase(gameArea.getImage());
				playerTiles.set(player.getGridX()-1, player.getGridY()-1, null);
				switch (move) {
					case GameObject.UP:
					player.changePosition(0, -1);
					break;
					case GameObject.DOWN:
					player.changePosition(0, 1);
					break;
					case GameObject.LEFT:
					player.changePosition(-1, 0);
					break;
					case GameObject.RIGHT:
					player.changePosition(1, 0);
					break;
				}
				player.draw(gameArea.getImage());
				playerTiles.set(player.getGridX()-1, player.getGridY()-1, player);
			}


			// Reset move
			player.setMove(GameObject.NONE);
		}
	}
	public void initialize() {
		// Set all players' tiles to null
		playerTiles.initialize(null);
		// Set starting position for all players
		for (int i=0; i<players.size(); i++) {
			boolean foundPosition = false;
			while (!foundPosition) {
				int startX = START_X[generator.nextInt(5)];
				int startY = START_Y[generator.nextInt(3)];
				if (playerTiles.get(startX-1, startY-1) == null) {
					foundPosition = true;
					Player player = players.get(i);
					player.setStartingPosition(startX, startY);
					playerTiles.set(player.getGridX()-1, player.getGridY()-1, player);
				}

			}
		}

		// Initialize active players
		activePlayers.clear();
		activePlayers.addAll(players);
		// Initialize shots
		shots.clear();
		// Set playerTiles array to reflect active players' positions
		//Iterator<Player> i = activePlayers.iterator();
		//while (i.hasNext()) {
		//	Player player = i.next();
		//	playerTiles.set(player.getGridX()-1, player.getGridY()-1, player);
		//}
	}
	public Player addPlayer(Player player) {
		players.add(player);
		activePlayers.add(player);
		return player;
	}
	public void createThreatMatrix() {
		threatMatrix.initialize(new Integer(0)); // by default set threat level to 0
		for (int i=0; i<shots.size(); i++) {
			Bullet shot = shots.get(i);
			threatMatrix.start(shot.getGridX()-1, shot.getGridY()-1, shot.getMove());
			int newThreatLevel = 10; // 10 means highest, 9 second highest, ..., 1 lowest, 0 no threat
			while (threatMatrix.hasNext() && newThreatLevel>0) {
				threatMatrix.next();
				int currentThreatLevel;
				if (threatMatrix.get() != null) {
					currentThreatLevel = threatMatrix.get().intValue();
				} else {
					currentThreatLevel = 0;
				}
				if (newThreatLevel > currentThreatLevel) {
					threatMatrix.set(new Integer(newThreatLevel));
				}
				threatMatrix.next();
				if (threatMatrix.get() != null) {
					currentThreatLevel = threatMatrix.get().intValue();
				} else {
					currentThreatLevel = 0;
				}
				if (newThreatLevel > currentThreatLevel) {
					threatMatrix.set(new Integer(newThreatLevel));
				}
				newThreatLevel-=1;
			}
		}
	}
	public void printThreatMatrix() { // Useful for debugging only
		for (int i=0; i<HEIGHT; i++) {
			for (int j=0; j<WIDTH; j++) {
				System.out.print(threatMatrix.get(j,i).intValue());
			}
			System.out.println();
		}
	}
}
