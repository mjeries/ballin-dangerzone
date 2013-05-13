package cisc230.game.player.bot;

import cisc230.game.GameObject;
import cisc230.game.Player;
import cisc230.game.Game;
import cisc230.ui.GameArea;
import cisc230.Array2D;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * This is a template class you need to implement for the project.
 * <p>
 * In your implementation you are allowed to use only following methods:<br/>
 * - Player class (methods inherited from Player)<br/>
 *   - getMove()<br/>
 *   - setMove(int move)<br/>
 *   - getPreviousMove()
 *   - getName()<br/>
 *   - getColor()<br/>
 *   - setColor(Color color)<br/>
 *   - setName(String name)<br/>
 *   - setShot(int shot)<br/>
 * - bots created by others (accessible to you via playerTiles):<br/>
 *   - getPreviousMove()
 *   - getName()
 * - playerTiles and threatMatrix (your local references inherited from Player)<br/>
 *   - get(int x, int y)<br/>
 *   - start(int x, int y, int direction)<br/>
 *   - get()<br/>
 *   - hasNext()<br/>
 *   - next()<br/>
 * </p>
 */

// TODO: Rename this class, append first name and last initial, e.g. BotMichaelD
public class Bot extends Player {

	public Bot() {
		super(); // DO NOT MODIFY THIS LINE! IT HAS TO BE FIRST STATEMENT IN THE CONSTRUCTOR!
		setColor(Color.blue);
		setName("BotMichaelD"); // TODO: Set your bot's name here to be the same as your class name

		// TODO: Anything specific to your bot's initialization should be done here
	}

	protected void draw(BufferedImage image) {
		super.draw(image); // TODO: When you code your own implementation of draw method delete this line only
		// Note: You don't need to call draw of superclass, when you override it with your implementation
		Graphics2D g2d = image.createGraphics();
		g2d.setColor(getColor());

		// TODO: Change how your avatar looks like by implementing this method
	}

	public void execute() {

		// TODO: Create you logic here, i.e. how your bot should move and shoot

		// Following line are just dummy calls to methods you can call in your
		// Implementation of execute method().
		// TODO: Delete these example method calls before you start your own
		//       impementation.
		setColor(Color.blue);
		setMove(GameObject.NONE);
		int aMove = getMove();
		aMove = getPreviousMove();
		String aName = getName();
		setShot(GameObject.NONE);

		Player aPlayer = getPlayerTiles().get(10, 15);
		getPlayerTiles().start(0, 0, GameObject.RIGHT);
		boolean hasNextFlag = getPlayerTiles().hasNext();
		getPlayerTiles().next();
		aPlayer = getPlayerTiles().get();

		if (aPlayer != null) {
			aPlayer.getPreviousMove();
			aPlayer.getName();
		}

		Integer threat = getThreatMatrix().get(10, 15);
		getThreatMatrix().start(0, 0, GameObject.RIGHT);
		hasNextFlag = getThreatMatrix().hasNext();
		getThreatMatrix().next();
		threat = getThreatMatrix().get();
	}

	// TODO: Any additonal methods of your own bot class you create should be here.

}

// TODO: Any additonal classes you create should be here. Note: don't use public!
