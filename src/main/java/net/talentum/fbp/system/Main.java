package net.talentum.fbp.system;

import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;

/**
 * Main starting class of the program.
 * 
 * @author JJurM
 */
public class Main {
	private static final Logger LOG = LogManager.getLogger();

	private static GpioController gpio;
	private static AtomicBoolean shutdownActionsPerformed = new AtomicBoolean(false);

	/**
	 * This method will catch any exception (or {@code Throwable}) thrown from
	 * underlying methods, log it and rethrow it.
	 * 
	 * @param args
	 */
	public static void run(String args[]) {
		try {

			start(args);

		} catch (Throwable e) {

			// always log the throwable
			LOG.fatal(e);

			// rethrow the exception
			throw e;
		}
	}

	/**
	 * Main starting method that is responsible for starting individual program
	 * sections.
	 * 
	 * @param args
	 */
	public static void start(String args[]) {

		LOG.info("Starting program...");

		setupGpio();
		setupDevices();

		LOG.info("Succesfully started!");

	}

	/**
	 * Performs necessary shutdown actions and assures they will be run at most
	 * once.
	 */
	public static void shutdownActions() {
		if (shutdownActionsPerformed.compareAndSet(false, true)) {
			
			// these actions will be performed once during shutdown
			
		}
	}

	public static void setupGpio() {

		LOG.info("Setting up GPIO");

		gpio = GpioFactory.getInstance();

	}

	public static void setupDevices() {

		LOG.info("Setting up devices");

	}

}
