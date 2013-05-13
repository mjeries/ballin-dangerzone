package cisc230;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Thread class is used to create threads that:
 * 1. Execute n number of times (setCounter(n))
 * 2. Execute m milliseconds (setTimer(m))
 * 3. Execute "forever" (setToExecuteForever())
 * 4. Execute once and end (this is the default) 
 * 
 * Every thread can be: 
 * - paused (pause()) and resumed (resume())
 * - killed (exit())
 * - have delay after each execution (setDelay(m)) 
 * 
 * Thread is declared as abstract meaning we can have no instance 
 * of this class, but have to extend it. This makes sense because
 * if you do not override execute() method then there is no point 
 * in having this class. Thread's execute() method serves the same 
 * purpose as java.lang.Thread's run() method. Thread's run()
 * method is declared final, i.e. it cannot be overridden and is 
 * used to call execute(). 
 * 
 * Is Vector really safe to use in multi-threaded program? 
 * http://www.ibm.com/developerworks/java/library/j-jtp09263.html
 * 
 */
public abstract class Thread implements Runnable {
	String threadName;
	// Reference to a class that implements Executable interface
	Executable executable;
	// keepRunning is used to keep thread alive even if we 
	// are not executing anything (see outer while in run())
	AtomicBoolean keepRunning;
	// keepExecuting is used to repeatedly call execute()
	// until pause() is called or until condition of either 
	// execution time or number of executions is satisfied.
	// See inner while in run()
	AtomicBoolean keepExecuting;
	// delayTime is used to specify a delay in milliseconds
	// between each thread execution (i.e. call to execute())
	private int delayTime;
	// executionTime is used specify how long in milliseconds
	// a thread will keep executing (i.e. calling execute())
	private int executionTime;
	// numberOfExecutions is used to specify how many times a
	// thread will be executed (i.e. called execute())
	private int numberOfExecutions;
	// infoEnabled is used to indicate if information about thread
	// is printed out every time execute() is called
	AtomicBoolean infoEnabled;
	
	public Thread() {
		this.threadName = "Nameless thread";
		this.keepRunning = new AtomicBoolean(true);
		this.keepExecuting = new AtomicBoolean(true);
		this.numberOfExecutions = 1;
		this.executionTime = 0;
		this.delayTime = 0;
		this.infoEnabled = new AtomicBoolean(false);
	}
	
	public Thread(String threadName) {
		this();
		this.threadName = threadName;
	}
	
	public Thread(Executable executable) {
		this();
		this.executable = executable;
		
	}
	
	public Thread setToExecuteForever() {
		setCounter(0);
		setTimer(0);
		return this;
	}

	/**
	 * Sets the number of executions. Allows specification of threads that 
	 * need to execute a certain number of times.
	 * @param numberOfExecutions how many times a thread should execute
	 * @return the thread itself so multiple thread methods can be called on 
	 *         the same thread object, i.e. chained
	 * 
	 */
	public Thread setCounter(int numberOfExecutions) {
		this.numberOfExecutions = numberOfExecutions;
		this.executionTime = 0;
		return this;
	}
	
	/**
	 * Resets the internal counter. Should only be called after setCounter 
	 * method has been called. Allows repeating thread execution(s) specified
	 * by setCounter.
	 * @return the thread itself so multiple thread methods can be called on
	 *         the same thread object, i.e. chained
	 */
	public Thread resetCounter() {
		this.numberOfExecutions = numberOfExecutions;
		this.executionTime = 0;
		return this;
	}
	
	public Thread setTimer(int executionTime) {
		this.executionTime = executionTime;
		this.numberOfExecutions = 0;
		return this;
	}
	
	public Thread setDelay(int delayTime) {
		this.delayTime = delayTime;
		return this;
	}
	
	public Thread setInfoMessages(boolean info) {
		infoEnabled.set(info);
		return this;
	}
	
	public final void run() {
		int executionCounter = 0;
		long startTime = System.currentTimeMillis();
		System.out.println("Starting thread: " + threadName);
		while (keepRunning.get()) {
			while (keepExecuting.get()) {
				long currentTime = System.currentTimeMillis();
				if ((executionTime > 0) && (executionTime < (currentTime - startTime))) {
					return;
				}
				if ((numberOfExecutions > 0) && (executionCounter == numberOfExecutions)) {
					return;
				}
				if (infoEnabled.get()) {
					System.out.println("\nThread: \"" + threadName + "\"" + 
							" executions=" + numberOfExecutions +
							" counter=" + executionCounter +
							" execution time=" + executionTime +
							" running time=" + (currentTime - startTime));
				}
				execute();
				executionCounter++;
    			sleep(delayTime);
			}
			// Per Tim Tursich's suggestion we are adding small delay 
			// to decrease CPU usage when thread is paused
			sleep(1);
		}
	}
	
	public Thread start() {
		new java.lang.Thread(this).start();
		return this;
	}
	
	/**
	 * Creates a thread for an object (executable) whose class implements 
	 * Executable interface.
	 */
	public static Thread createThread(Executable executable) {
		return new Thread(executable) {
			//Executable executable
			public void execute() {
				executable.execute();
			}
		};
	}
	
	public void sleep(long time) {
		try {java.lang.Thread.sleep(time);} catch (InterruptedException e) {}
	}

	// Subclasses have to implement execute() method. This method is the 
	// same as run() method in regular Thread class. 
	public abstract void execute();

	// Resume execution of a thread
	public Thread resume() {
		keepExecuting.set(true);
		return this;
	}
	
	// Pause execution of a thread
	public Thread pause() {
		keepExecuting.set(false);
		return this;
	}
	
	// Exit thread completely
	public void exit() {
		pause();
		keepRunning.set(false);
	}
}
