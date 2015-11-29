package net.talentum.fbp.system;

import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;

import net.talentum.fbp.database.DatabaseManager;

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
	 * underlying methods, log it and terminate program.
	 * 
	 * @param args
	 */
	public static void run(String args[]) {
		try {

			start(args);

		} catch (StartupException | RuntimeException e) {

			// always log the throwable
			LOG.fatal("Could not start program, immediately shutting down", e);

			shutdown();

		}
	}

	/**
	 * Main starting method that is responsible for starting individual program
	 * sections.
	 * 
	 * @param args
	 * @throws StartupException
	 */
	public static void start(String args[]) throws StartupException {

		LOG.info("Starting program...");

		try {

			ConfigurationManager.performChecks();

		} catch (ConfigurationException e) {
			throw new StartupException(e);
		}

		createConnectionPool();
		setupGpio();
		setupDevices();

		LOG.info("Succesfully started!");

	}

	/**
	 * Will attempt to shut down the program, calling {@link #shutdownActions()}
	 * first.
	 */
	public static void shutdown() {

		LOG.fatal("Shutdown requested!");

		shutdownActions();
		System.exit(0);

	}

	/**
	 * Performs necessary shutdown actions and assures they will be run at most
	 * once.
	 */
	static void shutdownActions() {
		if (shutdownActionsPerformed.compareAndSet(false, true)) {
			// these actions will be performed once during shutdown

			LOG.info("Performing shutdown actions...");

			// Shutdown GPIO
			if (gpio != null) {
				LOG.info("Shutting down GPIO");
				gpio.shutdown();
			}

			// terminate communication with database
			LOG.info("Releasing connection pool");
			try {
				DatabaseManager.releasePool();
			} catch (SQLException e) {
				LOG.error("Could not release connection pool", e);
			}

			LOG.fatal("Terminating program");
		} else {
			LOG.debug("Duplicately requested shutdown actions");
		}
	}

	static void setupGpio() {

		LOG.info("Setting up GPIO");

		gpio = GpioFactory.getInstance();

	}

	static void setupDevices() {

		LOG.info("Setting up devices");

	}

	static void createConnectionPool() {
		try {

			DatabaseManager.createConnectionPool();

		} catch (Exception e) {
			LOG.error("Can't create ConnectionPool, exiting program", e);
		}

	}

}
