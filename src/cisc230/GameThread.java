package cisc230;

import cisc230.Thread;
import cisc230.ui.GameArea;

public abstract class GameThread extends Thread {
	public GameArea gameArea;
	
	public GameThread(String threadName, GameArea gameArea) {
		super(threadName);
		this.gameArea = gameArea;
		// By default all game threads are running "forever"
		this.setToExecuteForever();
	}

	public abstract void execute();
}
