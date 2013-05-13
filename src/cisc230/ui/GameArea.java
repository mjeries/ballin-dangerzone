package cisc230.ui;

import cisc230.game.Game;
import cisc230.game.GameObject;
import cisc230.game.Player;
import cisc230.game.Bullet;
import cisc230.GameThread;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.TextArea;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.concurrent.atomic.AtomicInteger;
import javax.swing.JPanel;

public class GameArea extends JPanel {
	final public static int TILE_WIDTH = 15; // Grid tile width in pixels
	final public static int TILE_HEIGHT = 15; // Grid tile height in pixels
	final public static int SCREEN_WIDTH = Game.WIDTH * TILE_WIDTH; // Game area width in pixels
	final public static int SCREEN_HEIGHT = Game.HEIGHT * TILE_HEIGHT; // Game area width in pixels


	// SCREEN_WIDTH and SCREEN_HEIGHT must be divisible by LINE_WIDTH
	//final static int LINE_WIDTH = 2;

	final public static Color BACKGROUND_COLOR = Color.black;
	final static int BORDER_COLOR = Color.black.getRGB();
	final public static Color BULLET_COLOR = Color.yellow;
	final static int PLAYER1_COLOR = Color.green.getRGB();
	final static int PLAYER2_COLOR = Color.blue.getRGB();
	final static int PLAYER3_COLOR = Color.yellow.getRGB();
	final static int PLAYER4_COLOR = Color.red.getRGB();
	final static int PLAYER5_COLOR = Color.orange.getRGB();
	final static int PLAYER6_COLOR = Color.pink.getRGB();


	Game game;
	BufferedImage image;
	BufferedImage initialImage;
	TextArea gameLog;
	GameThread drawingThread;
	private AtomicInteger circleCounter = new AtomicInteger(1);
	private int maxCircles = 150;

	public GameArea(TextArea gameLog) {
    	this.gameLog = gameLog;
        //setBorder(BorderFactory.createLineBorder(Color.black));

    	// Create game area image
    	image = new BufferedImage(SCREEN_WIDTH, SCREEN_HEIGHT, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = image.createGraphics();
        g2d.setColor(GameArea.BACKGROUND_COLOR);
        g2d.fillRect(0, 0, GameArea.SCREEN_WIDTH, GameArea.SCREEN_HEIGHT);
		// Draw border

		// Draw obstacles ???

		initialImage = image.getSubimage(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);


        //g2d.drawRect(100, 100, 200, 50);


        // KeyListener methods are executed (running) in Event Dispatch
        // Thread
        this.addKeyListener(new KeyListener() {

			public void keyTyped(KeyEvent e) {}

			public void keyReleased(KeyEvent e) {}

			public void keyPressed(KeyEvent e) {
				// Process moves and shots for interactive player
				Player interactivePlayer = ((GameArea)e.getComponent()).getGame().getInteractivePlayer();
				if (interactivePlayer != null) {
					// Process move
					if (e.getKeyChar() == 'w') { // Move up
						getGame().setInteractivePlayerMove(GameObject.UP);
					}
					if (e.getKeyChar() == 's') { // Move down
						getGame().setInteractivePlayerMove(GameObject.DOWN);
					}
					if (e.getKeyChar() == 'd') { // Move right
						getGame().setInteractivePlayerMove(GameObject.RIGHT);
					}
					if (e.getKeyChar() == 'a') { // Move left
						getGame().setInteractivePlayerMove(GameObject.LEFT);
					}
					// Process shot
					if (e.getKeyChar() == 'i') { // Shoot up
						getGame().setInteractivePlayerShot(GameObject.UP);
					}
					if (e.getKeyChar() == 'k') { // Shoot down
						getGame().setInteractivePlayerShot(GameObject.DOWN);
					}
					if (e.getKeyChar() == 'l') { // Shoot right
						getGame().setInteractivePlayerShot(GameObject.RIGHT);
					}
					if (e.getKeyChar() == 'j') { // Shoot left
						getGame().setInteractivePlayerShot(GameObject.LEFT);
					}
				}
				// Process game start, pause and resume
				if (e.getKeyChar() == 'g') { // Toggle game running
					GameArea gameArea = ((GameArea)e.getComponent());
					if (gameArea.getGame().isRunning()) {
						gameArea.getGame().setRunning(false);
						//gameArea.writeToLog("Game is not running.\n");
					} else {
						gameArea.getGame().setRunning(true);
						//gameArea.writeToLog("Game is running.\n");
					}
				}
				// Quit application
				if (e.getKeyChar() == 'q') {
					System.exit(0);
				}
			}
		});
    }

    // You cannot rely that this method will ever be called, unless you
    // indirectly call it yourself by calling repaint(). Note that every
    // call to paintComponent will automatically erase entire gameArea and
    // display the result of its drawing commands. In other words it always
    // starts from clean slate.
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, null);
    }

    public Dimension getPreferredSize() {
    	return new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT);
    }

    public Dimension getMinimumSize() {
    	return new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT);
    }

    public void writeToLog(String text) {
    	gameLog.append(text);
    }

    public void setDrawingThread(GameThread drawingThread) {
    	this.drawingThread = drawingThread;
    }

    public GameThread getDrawingThread() {
    	return drawingThread;
    }

	public BufferedImage getImage() {
		return image;
	}

    public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}
}