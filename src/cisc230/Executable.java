package cisc230;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Equivalent of Java's Runnable interface. Classes that cannot 
 * extend cisc230.Thread because they are already extending
 * another class should implement this interface instead.
 */
public interface Executable {
	/**
	 * Equivalent of run() method in Java's Runnable interface
	 */
	public void execute();
}
