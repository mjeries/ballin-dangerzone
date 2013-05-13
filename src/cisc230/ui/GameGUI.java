package cisc230.ui;

import cisc230.ui.GameGUI;
import cisc230.game.Game;
import cisc230.GameThread;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics; 
import java.awt.Graphics2D;
import java.awt.TextArea;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener; 
import java.util.concurrent.atomic.AtomicInteger;

public class GameGUI {	
	public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI(); 
            }
        });
    }
	
    private static void createAndShowGUI() {
    	// Event Dispatch Thread is main Java GUI (Swing) system thread
    	// that listens to events and executes appropriate methods
    	// associated with those events (keyTyped, keyPressed, etc.)
        System.out.println("Created GUI on Event Dispatch Thread? "+
        SwingUtilities.isEventDispatchThread());
        
        // Game window
        TextArea gameLog = new TextArea();
        // We are constructing gameArea with a reference to gameLog TextArea
        // to be able to write (.append("...")) to gameLog 
        GameArea gameArea = new GameArea(gameLog);
        GameWindow gameWindow = new GameWindow("CISC 230 Game Project", gameArea);
        gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container pane = gameWindow.getContentPane();
        
        // Game status message
        //JLabel gameStatus = new JLabel("Initialized");
        //pane.add(gameStatus, BorderLayout.PAGE_START);
        
        // Game area
       
        // Make sure a component is focusable to be able to receive 
        // keyboard input
        gameArea.setFocusable(true);
        pane.add(gameArea, BorderLayout.CENTER);
        
        // Game log/messages
        gameLog.append("Press g to start each game round.\n\n");
        gameLog.setPreferredSize(new Dimension(350, 400));
        gameLog.setEditable(false);
        // TextArea (gameLog) cannot be focusable or else we'll never be able to get focus
        // back to the gameArea
        gameLog.setFocusable(false);
        pane.add(gameLog, BorderLayout.LINE_END);
            
        // Create a game
        Game game = new Game(gameArea);
        gameArea.setGame(game);
        gameWindow.pack();
        gameWindow.setVisible(true);
        // Start a game thread to repaint the screen (gameArea) 
        // at regular intervals
        new GameThread("Repaint", gameArea) {
        	public void execute () {
        		gameArea.repaint();
        	}
        }.setDelay(100).start();
        
        // Start a game thread to request focus for gameArea  at 
        // regular intervals to able to continue to detect key presses
        new GameThread("Focus", gameArea) {
        	public void execute () {
        		gameArea.requestFocus();
        	}
        }.setDelay(100).start();
        
        /* Above code can also be written as:
        class FocusThread extends GameThread {
        	public FocusThread(String threadName, GameArea gameArea) {
        		super(threadName, gameArea);
        	}
        	public void execute () {
        		gameArea.requestFocus();
        	}
        }
        FocusThread focusThread = new FocusThread("Focus", gameArea); 
        focusThread.setDelay(100);
        focusThread.start();
        */

		// At the end start drawing thread created in Game's constructor
		gameArea.getDrawingThread().start();
		System.out.println("At the end createAndShowGUI()");

    } 
    
}