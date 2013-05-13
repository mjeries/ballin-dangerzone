package cisc230;

import cisc230.ui.GameArea;
import cisc230.game.Game;
import cisc230.GameThread;
import cisc230.game.player.*; // To import all classes in a package use *
import java.util.Vector;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicBoolean;
import java.awt.Color;

// See http://download.oracle.com/javase/6/docs/api/java/util/Vector.html
// See http://download.oracle.com/javase/tutorial/collections/interfaces/collection.html
// See http://download.oracle.com/javase/6/docs/api/java/util/Iterator.html

/**
 * Implements two dimensional arrray with generics and safe get and
 * set operations in other words it prevents array index out of bound
 * exception. Note that this class will cause compiler warnings but
 * that is acceptable. T is generic type specified when you create a
 * variable of type Array2D. For example to create two dimensional
 * of Double numbers called doubleArray2D you would declare it as:<b/>
 * Array2D<Double> doubleArray2D = new Array2D(20, 10);
 */
public class Array2D<T> {
	public static final int NONE  = 0;
	public static final int UP    = 1;
	public static final int RIGHT = 2;
	public static final int DOWN  = 3;
	public static final int LEFT  = 4;
	int sizeX;
	int sizeY;
	T[][] elements;
	int x;
	int y;
	int direction;

	public Array2D(int sizeX, int sizeY) {
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		// We can't create an array of generics thus typecasting:
		elements = (T[][])new Object[sizeX][sizeY];
		this.x = 0;
		this.y = 0;
		this.direction = NONE;
	}
	/**
	 * Gets element at row x and column y. If x and y are invalid row and
	 * column then return null. The actual return type T will be the type
	 * specified in between < and > when Array2D is declared. For example:
	 * Array2D<Player> playerTiles;
	 */
	public T get(int x, int y) {
		if (x>=0 && x<sizeX && y>=0 && y<sizeY) {
			return elements[x][y];
		} else {
			return null;
		}

	}
	/**
	 * Sets element at row x and column y to a value of t. If x and y are
	 * invalid row and column then nothing will be set. The actual type T
	 * of t will be the type specified in between < and > when Array2D is
	 * declared. For example:
	 * Array2D<Player> playerTiles;
	 */
	public void set(int x, int y, T t) {
		if (x>=0 && x<sizeX && y>=0 && y<sizeY) {
			elements[x][y] = t;
		}
	}
	/**
	 * Sets row, and column of the first element to be read or written as
	 * well as direction in which next element is read or written. Has to
	 * be called before get(), set(T t), hasNext() and next() methods.
	 */
	public void start(int x, int y, int direction) {
		this.x = x;
		this.y = y;
		this.direction = direction;
	}
	/**
	 * Gets element at the position specified by start method. Should only
	 * be called after start method is called to specify row, column and
	 * direction.
	 */
	public T get() {
		return get(x, y);
	}
	/**
	 * Sets element at the position specified by start method. Should only
	 * be called after start method is called to specify row, column and
	 * direction.
	 */
	public void set(T t) {
		set(x, y, t);
	}
	/**
	 * Returns true is there is another element in direction specified by
	 * by start method, otherwise returns false.
	 */
	public boolean hasNext() {
		int tempX = x;
		int tempY = y;
		switch (direction) {
			case UP:
				tempY-=1;
			break;
			case DOWN:
				tempY+=1;
			break;
			case LEFT:
				tempX-=1;
			break;
			case RIGHT:
				tempX+=1;
			break;
			default: // do nothing
			break;
		}
		if (tempX>=0 && tempX<sizeX && tempY>=0 && tempY<sizeY) {
			return true;
		} else {
			return false;
		}
	}
	/**
	 * Moves to next element in direction specified by start method so all
	 * subsequent calls to get() and set(T t) will work on this next element.
	 */
	public void next() {
		// Change x or y depending on the direction the array is read
		switch (direction) {
			case UP:
				y-=1; // same as: y=y-1;
			break;
			case DOWN:
				y+=1; // same as: y=y+1;
			break;
			case LEFT:
				x-=1;
			break;
			case RIGHT:
				x+=1;
			break;
			default: // do nothing
			break;
		}
	}
	/**
	 * Initializes all elements of this 2D array to a specified value
	 * of t.
	 */
	public void initialize(T t) {
		for (int x=0; x<sizeX; x++) {
			for (int y=0; y<sizeY; y++) {
				elements[x][y] = t;
			}
		}
	}
}