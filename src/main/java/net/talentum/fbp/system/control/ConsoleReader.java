package net.talentum.fbp.system.control;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Class for reading input from an {@code InputStream}. Passes the input to the
 * given {@code Commander}.
 * 
 * @author JJurM
 */
public class ConsoleReader implements Runnable {

	private AtomicBoolean run = new AtomicBoolean(false);
	private AtomicBoolean stopped = new AtomicBoolean(false);

	/**
	 * {@link Commander} to pass input to.
	 */
	private Commander commander;

	BufferedReader br;
	PrintWriter pw;

	Thread readerThread;

	/**
	 * Basic constructor.
	 * 
	 * @param commander
	 *            commander to pass input to
	 */
	public ConsoleReader(Commander commander) {
		this.commander = commander;

		br = new BufferedReader(new InputStreamReader(System.in));
		pw = new PrintWriter(System.out, true);

		readerThread = new Thread(this);
	}

	/**
	 * Starts the thread.
	 */
	public void start() {
		if (run.compareAndSet(false, true)) {
			readerThread.start();
		}
	}

	/**
	 * Stops the reader and closes resources.
	 */
	public void stop() {
		if (run.get()) {
			stopped.set(true);
		}
	}

	@Override
	public void run() {
		Thread.currentThread().setName("ConsoleReader");

		while (!stopped.get()) {

			try {
				commander.process(br, pw);
			} catch (StreamCloseRequest e) {
				// Never close the console streams
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

}
