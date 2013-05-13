package cisc230.game.player.michaelj;

import cisc230.Array2D;
import cisc230.game.Game;
import cisc230.game.GameObject;
import cisc230.game.Player;
import cisc230.ui.GameArea;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.util.Vector;

public class BotMichaelJ extends Player {
	int moveCounter = 0;
	//Random colorChanger = new Random();
	Strategy s = new Strategy(this);
	TargetCollection c = new TargetCollection(this);
	DirectionCalculator d = new DirectionCalculator(this);
	Random random = new Random();

	public BotMichaelJ() {
		super();
		setColor(Color.yellow);
		setName("BotMichaelJ");
	}

	/**
	 * Overrides default draw method to draw a simple bot as a yellow circle.
	 */
	protected void draw(BufferedImage image) {
		Graphics2D g2d = image.createGraphics();
		g2d.setColor(Color.yellow);
		g2d.fillOval((getGridX()-1)*GameArea.TILE_WIDTH, (getGridY()-1)*GameArea.TILE_HEIGHT, GameArea.TILE_WIDTH, GameArea.TILE_HEIGHT);
		g2d.setColor(getColor());
		g2d.fillOval((getGridX()-1)*GameArea.TILE_WIDTH+GameArea.TILE_WIDTH/6, (getGridY()-1)*GameArea.TILE_HEIGHT+GameArea.TILE_HEIGHT/6, GameArea.TILE_WIDTH * 3/4, GameArea.TILE_HEIGHT * 3/4);
	}

	private void changeColor(int x){
		switch (x%2)
		{
		case 0: {setColor(Color.orange); break;}
		case 1: {setColor(Color.red); break;}
		}
	}

	/**
	 * Stuff to do at the beginning of the move.
	 */
	private void preprocess(){
		changeColor(moveCounter); //for random, (colorChanger.nextInt(2))

		if(moveCounter < 1 || c.v.isEmpty()){
			c.populateList();
		} else{
			c.updateActive();
		}

		moveCounter++;
	}

	private void determineTarget() {
		try {
			if (c.nearestBotPlayer(this) != s.target && c.size() > 1){  //If the nearest bot isn't the target and there's two opposing bots,
				s.f.setTarget(c.nearestBotPlayer(this));				//set the nearest bot as the target.
			} else if (c.size() == 1) {									
				s.f.setTarget(c.v.firstElement());						//If there's only one opposing bot, set that bot as the target.
			}
		} catch (ArrayIndexOutOfBoundsException e) {					//Vector list occaisonally goes empty when I run the game without any delays.
			System.out.println("No elements in vector list.");			
			c.populateList();
			//System.out.println(c.size());
			s.f.setTarget(c.nearestBotPlayer(this));					//So then I just repopulate the list and set the target as nearest bot.
		}
	}

	public void execute() {
		int move = 0;

		preprocess();
		determineTarget();

		if (s.evade() == NONE) {										//the highest priority is to get out of the way of bullets
			//If there's no imminent threat, I can go to my normal strategies.

			if (c.size() <= 2){
				//System.out.print(" rake ");
				move = s.rake()[0];  //TODO need better conditions for rake()
				setShot(s.rake()[1]);
			} else {
				//System.out.print(" normal ");
				move =s.genMove();
				setShot(s.f.generateShot());
			}

		} else {
			move = s.evade();
			//move = d.best();
			setShot(s.f.generateShot());
		}

		if (getGridY()==1 && move==GameObject.UP) {						//from SimpleBot. Seems to work well.
			move=GameObject.DOWN;
		}
		if (getGridY()==Game.HEIGHT && move==GameObject.DOWN) {
			move=GameObject.UP;
		}
		if (getGridX()==Game.WIDTH && move==GameObject.RIGHT) {
			move=GameObject.LEFT;
		}
		if (getGridX()==1 && move==GameObject.LEFT) {
			move=GameObject.RIGHT;
		}
		setMove(move);
	}
}

class Strategy {
	Functions f;
	Player target;
	BotMichaelJ me;

	int step;
	int moveDirection;

	public Strategy (BotMichaelJ me) {
		f = new Functions(me);
		this.me = me;

	}

	protected int[] rake(){
		int decision[] = {0,0};

		decision[1] = me.c.targetDir();

		if (target.getGridY() < me.getGridY()){
			if (step < 6){
				decision[0] = GameObject.UP;
				step++;
			}
			if (step == 6) {
				decision[0] = GameObject.DOWN;
				step++;
			} else if (step == 7){
				decision[0] = GameObject.DOWN;
				step = 0;
			}
		} else if (target.getGridY() > me.getGridY()){
			if (step < 6){
				decision[0] = GameObject.DOWN;
				step++;
			}
			if (step == 6) {
				decision[0] = GameObject.UP;
				step++;
			} else if (step == 7){
				decision[0] = GameObject.UP;
				step = 0;
			}
		} else if (target.getGridY() < me.getGridY()){
			while (step < 6){
				decision[0] = GameObject.LEFT;
				step++;
			}
			if (step == 6) {
				decision[0] = GameObject.RIGHT;
				step++;

			} else if (step == 7){
				decision[0] = GameObject.RIGHT;
				step = 0;
			}
		} else if (target.getGridY() > me.getGridY()){
			while (step < 6){
				decision[0] = GameObject.RIGHT;
				step++;
			}
			if (step == 6) {
				decision[0] = GameObject.LEFT;
				step++;

			} else if (step == 7){
				decision[0] = GameObject.LEFT;
				step = 0;
			}
		}
		return decision;
	}

	protected int evade(){

		if (me.d.getThreat(GameObject.UP) != null){
			if(me.d.getThreat(GameObject.UP) < me.d.getThreat(GameObject.NONE)) { //and threat less than 5?
				return GameObject.UP;
			}
		}
		if (me.d.getThreat(GameObject.DOWN) != null){
			if(me.d.getThreat(GameObject.DOWN) < me.d.getThreat(GameObject.NONE)) {
				return GameObject.DOWN;
			}
		}
		if (me.d.getThreat(GameObject.LEFT) != null){
			if(me.d.getThreat(GameObject.LEFT) < me.d.getThreat(GameObject.NONE)) {
				return GameObject.LEFT;
			}
		}
		if (me.d.getThreat(GameObject.RIGHT) != null){
			if(me.d.getThreat(GameObject.RIGHT) < me.d.getThreat(GameObject.NONE)) {
				return GameObject.RIGHT;
			}
		}

		return GameObject.NONE;
	}

	protected int genMove(){

		// Check tile above this bot
		if (me.d.getThreat(GameObject.UP) != null) {
			// If threat on the tile above is less than the threat on current tile move up
			if (me.d.getThreat(GameObject.UP) < me.d.getThreat(GameObject.NONE)) {
				return GameObject.UP;
			}
		}
		// Check tile below this bot
		if (me.d.getThreat(GameObject.DOWN) != null) {
			// If threat on the tile below is less than the threat on current tile move up
			if (me.d.getThreat(GameObject.DOWN) < me.d.getThreat(GameObject.NONE)) {
				return GameObject.DOWN;
			}
		}
		// Check tile to the left of this bot
		if (me.d.getThreat(GameObject.LEFT) != null) {
			// If threat on the tile below is less than the threat on current tile move up
			if (me.d.getThreat(GameObject.LEFT) < me.d.getThreat(GameObject.NONE)) {
				return GameObject.LEFT;
			}
		}
		// Check tile to the right of this bot
		if (me.d.getThreat(GameObject.RIGHT) != null) {
			// If threat on the tile below is less than the threat on current tile move up
			if (me.d.getThreat(GameObject.RIGHT) < me.d.getThreat(GameObject.NONE)) {
				return GameObject.RIGHT;
			}
		}
		if (me.getGridY()==1 && moveDirection==GameObject.UP) {
			moveDirection=GameObject.DOWN;
		}
		if (me.getGridY()==Game.HEIGHT && moveDirection==GameObject.DOWN) {
			moveDirection=GameObject.UP;
		}
		if (me.getGridX()==Game.WIDTH && moveDirection==GameObject.RIGHT) {
			moveDirection=GameObject.LEFT;
		}
		if (me.getGridX()==1 && moveDirection==GameObject.LEFT) {
			moveDirection=GameObject.RIGHT;
		}
		if (me.moveCounter < 10) {
			me.moveCounter++;
		}
		// If no move was decided based on threat and no random move to safe position
		return GameObject.NONE;
	}

	class Functions{

		Random generator; 
		BotMichaelJ me;
		int distance = 0;

		public Functions(BotMichaelJ me){
			this.me = me;
			generator = new Random();
		}

		protected void setTarget(Player target){			//class Functions should use Strategy's variable for target.
			Strategy.this.target = target;
		}

		public Player getTarget(){							//again, calling Strategy's version of target.
			return Strategy.this.target;
		}

		/**
		 * Shoot at a target.
		 */
		protected int generateShot() { 

			int decision = 0;
			/*								**Extra code is supposed to enable this method to shoot at the closest bot of all the directions.**
			int direction = 0;
			int distances[] = {0,0,0,0,0};
			int bestdistance = 99;

			//get the distance to first bot in each direction
			getFirstBot(GameObject.UP);
			distances[GameObject.UP] = distance;
			getFirstBot(GameObject.DOWN);
			distances[GameObject.DOWN] = distance;
			getFirstBot(GameObject.LEFT);
			distances[GameObject.LEFT] = distance;
			getFirstBot(GameObject.RIGHT);
			distances[GameObject.RIGHT] = distance;

			//compare the distances
			if (distances[GameObject.UP] < bestdistance){
				bestdistance = distances[GameObject.UP];
				direction = GameObject.UP;
			}
			if (distances[GameObject.DOWN] < bestdistance){
				bestdistance = distances[GameObject.DOWN];
				direction = GameObject.DOWN;
			}
			if (distances[GameObject.LEFT] < bestdistance){
				bestdistance = distances[GameObject.LEFT];
				direction = GameObject.LEFT;
			}
			if (distances[GameObject.RIGHT] < bestdistance){
				bestdistance = distances[GameObject.RIGHT];
				direction = GameObject.RIGHT;
			}

			//fire in the shortest direction
			if (bestdistance == GameObject.UP && getFirstBotInLineOfSight(GameObject.UP) != null){
				decision = GameObject.UP;
			}
			if (bestdistance == GameObject.DOWN && getFirstBotInLineOfSight(GameObject.DOWN) != null){
				decision = GameObject.DOWN;
			}
			if (bestdistance == GameObject.LEFT && getFirstBotInLineOfSight(GameObject.LEFT) != null){
				decision = GameObject.LEFT;
			}
			if (bestdistance == GameObject.RIGHT && getFirstBotInLineOfSight(GameObject.RIGHT) != null){
				decision = GameObject.RIGHT;
			}
			 */

			if(getFirstBot(GameObject.UP) != null){
				decision = GameObject.UP;
			} else if (getFirstBot(GameObject.DOWN) != null){
				decision = GameObject.DOWN;
			} else if (getFirstBot(GameObject.LEFT) != null){
				decision = GameObject.LEFT;
			} else if (getFirstBot(GameObject.RIGHT) != null){
				decision = GameObject.RIGHT;
			} else {
				decision = GameObject.NONE;
			}
			return decision;
		}

		/**
		 * Returns first bot in specified direction.
		 */
		protected Player getFirstBot(int direction) {
			distance = 0;
			me.getPlayerTiles().start(me.getGridX()-1, me.getGridY()-1, direction);
			while (me.getPlayerTiles().hasNext()) {
				me.getPlayerTiles().next();
				distance++;
				if (me.getPlayerTiles().get() != null) {
					return me.getPlayerTiles().get();
				}
			}
			return null;
		}

		/**
		 * Align with target.
		 */
		protected int align() {

			if(target.getGridY() < me.getGridY()) { return GameObject.UP;
			}
			if(target.getGridY() > me.getGridY()) { return GameObject.DOWN;
			} 
			if(target.getGridX() < me.getGridX()) { return GameObject.LEFT;
			}
			if(target.getGridX() > me.getGridX()) { return GameObject.RIGHT;
			}
			return GameObject.NONE;
		}
	}
}

class DirectionCalculator extends Object{
	private BotMichaelJ me;

	public DirectionCalculator(BotMichaelJ me){
		this.me = me;
	}

	protected boolean getSafe(int direction) {

		switch (direction){

		case GameObject.UP:
			if (getThreat(GameObject.UP) != null && getThreat(GameObject.UP) < 8) {
				return true; }

		case GameObject.DOWN:
			if (getThreat(GameObject.DOWN) != null && getThreat(GameObject.DOWN) < 8) {
				return true; }

		case GameObject.LEFT:
			if (getThreat(GameObject.LEFT) != null && getThreat(GameObject.LEFT) < 8) {
				return true; }

		case GameObject.RIGHT:
			if (getThreat(GameObject.RIGHT) != null && getThreat(GameObject.RIGHT) < 8) {
				return true; }
		}
		
		if (direction > 4 || direction < 0){
			System.out.println("getSafe: not in range");	
		}
		
		return false;
	}

	protected Integer getThreat(int direction) {
		switch (direction) {
		case GameObject.NONE: return me.getThreatMatrix().get(me.getGridX()-1, me.getGridY()-1);
		case GameObject.UP: return me.getThreatMatrix().get(me.getGridX()-1, me.getGridY()-1-1);
		case GameObject.DOWN: return me.getThreatMatrix().get(me.getGridX()-1, me.getGridY()-1+1);
		case GameObject.LEFT: return me.getThreatMatrix().get(me.getGridX()-1-1, me.getGridY()-1);
		case GameObject.RIGHT: return me.getThreatMatrix().get(me.getGridX()-1+1, me.getGridY()-1);
		}
		if (direction > 4 || direction < 0){
			System.out.println("getThreat: not in range");	
		}
		return null;
	}

	/**
	 * Compares the threat on each tile around the bot. The lowest tile is deemed the "best" direction.
	 * Returns direction in form of GameObject.
	 */
	protected int best() {
		int bestdirection = 0;  //actual direction that's returned
		int bestThreat = 11;	//this is what's used to compare

		if (getThreat(GameObject.UP) != null) {
			bestdirection = GameObject.UP;			
			bestThreat = getThreat(GameObject.UP);
		}

		if (getThreat(GameObject.RIGHT) != null) {
			if(getThreat(GameObject.RIGHT) < bestThreat){
				bestdirection = GameObject.RIGHT;
				bestThreat = getThreat(GameObject.RIGHT);
			}	
		}

		if (getThreat(GameObject.DOWN) != null) {
			if(getThreat(GameObject.DOWN) < bestThreat) {
				bestdirection = GameObject.DOWN;
				bestThreat = getThreat(GameObject.DOWN);
			}
		}

		if (getThreat(GameObject.LEFT) != null) {
			if(getThreat(GameObject.LEFT) < bestThreat) {
				bestdirection = GameObject.LEFT;
				bestThreat = getThreat(GameObject.LEFT);
			}
		}
		return bestdirection;
	}

	protected int nearWallDir(){

		int direction = 0;
		int distances[] = {0,0,0,0,0};
		int bestdistance = 99;

		distances[GameObject.UP] = me.getGridY() - 1;				//get the distance to each wall
		distances[GameObject.DOWN] = Game.HEIGHT - me.getGridY();
		distances[GameObject.LEFT] = me.getGridX() - 1;
		distances[GameObject.RIGHT] = Game.WIDTH - me.getGridX();

		if (distances[GameObject.UP] < bestdistance) {				 //compare the distances
			bestdistance = distances[GameObject.UP];
			direction = GameObject.UP;
		}
		if (distances[GameObject.DOWN] < bestdistance) {
			bestdistance = distances[GameObject.DOWN];
			direction = GameObject.DOWN;
		}
		if (distances[GameObject.LEFT] < bestdistance) {
			bestdistance = distances[GameObject.LEFT];
			direction = GameObject.LEFT;
		}
		if (distances[GameObject.RIGHT] < bestdistance) {
			bestdistance = distances[GameObject.RIGHT];
			direction = GameObject.RIGHT;
		}
		return direction;
	}

	protected void printDirs() {
		System.out.print(getThreat(GameObject.UP) + " " + getThreat(GameObject.RIGHT)
				+ " " + getThreat(GameObject.DOWN) + " " + getThreat(GameObject.LEFT));
	}
}

class TargetCollection {

	BotMichaelJ me;
	Vector<Player> v = new Vector<Player>(0);

	public TargetCollection(BotMichaelJ me) {
		this.me = me;
	}

	public void add(Player bot) {
		v.add(bot);
	}

	public int size(){
		return v.size();
	}

	public void populateList() {
		for(int i=0; i < Game.HEIGHT-1; i++){
			for(int j=0; j < Game.WIDTH-1; j++){
				Player temp = me.getPlayerTiles().get(j,i);
				if(temp != null && temp.getName() != me.getName()){
					add(temp);
				}
			}
		}
		System.out.println("updated list: " + size());
	}

	public void updateActive() {
		for(int i = 0 ; i < size() ; i++) {
			if(v.get(i) != null && !v.get(i).isActive()) {
				v.remove(i);
			}
		}
	}

	/**
	 * Returns the player that is the nearest to me.
	 */
	protected Player nearestBotPlayer(BotMichaelJ me) {

		Integer smallLocation=0;
		double smallestdistance=getDistanceFromMe(v.get(0),me);

		for(int i=0; i < size(); i++){
			if(getDistanceFromMe(v.get(i),me) < smallestdistance){
				smallestdistance = getDistanceFromMe(v.get(i),me);
				smallLocation = i;
			}
		}
		return v.get(smallLocation.intValue());
	}

	protected int[] nearestBotLoc(BotMichaelJ me) {
		int location[] = {0,0};
		Player temp = nearestBotPlayer(me);

		location[0] = temp.getGridX();
		location[1] = temp.getGridY();

		return location;
	}

	/**
	 * Direction of nearest bot (target).
	 */
	protected int targetDir() {

		Player b = nearestBotPlayer(me);
		double s = slopeToTarget(nearestBotPlayer(me),me);

		boolean targetX = (b.getGridX() < me.getGridX()) ? true : false; //true = LEFT , false = RIGHT
		boolean targetY = (b.getGridY() < me.getGridY()) ? true : false; //true = UP   , false = DOWN

		if (targetX == false && (s <= 1 && s >= -1)) { //conditions to determine if bot is right of me
			return GameObject.RIGHT;
		}
		if (targetX == true && (s <= 1 && s >= -1)) { 
			return GameObject.LEFT;
		}
		if (targetY == true && (s >= 1 && s <= -1)) {
			return GameObject.UP;
		}
		if (targetY == false && (s >= 1 && s <= -1)) {
			return GameObject.DOWN;
		}
		return 0;
	}

	/**
	 * Returns distance to specified bot.
	 */
	protected double getDistanceFromMe(Player player,BotMichaelJ me) {
		int dx = me.getGridX()-player.getGridX();
		int dy = me.getGridY()-player.getGridY();

		return Math.sqrt(dx*dx+dy*dy);
	}

	protected double slopeToTarget(Player player,BotMichaelJ me) {
		int dx = me.getGridX()-player.getGridX();
		int dy = me.getGridY()-player.getGridY();

		return (double) dy/dx;
	}

}