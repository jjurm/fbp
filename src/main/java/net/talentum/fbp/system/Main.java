package net.talentum.fbp.system;

import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configurator;
import org.joda.time.DateTimeZone;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;

import net.talentum.fbp.database.DatabaseManager;
import net.talentum.fbp.logging.Levels;
import net.talentum.fbp.ui.UIManager;

/**
 * Main starting class of the program.
 * 
 * @author JJurM
 */
public class Main {
	private static final Logger LOG = LogManager.getLogger();

	private static GpioController gpio;
	private static UIManager uiManager;

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

		LOG.log(Levels.DIAG, String.format("Starting program: %s %s (%s)", Run.getProjectName(),
				Run.getProjectVersion(), Run.projectProperties.get("run.type")));

		try {

			ConfigurationManager.init();
			Config.init();

		} catch (ConfigurationException e) {
			throw new StartupException(e);
		}

		setTimeZone();
		createConnectionPool();
		DatabaseManager.addLog4j2JdbcAppender();

		setupGpio();
		setupDevices();
		setupUI();

		LOG.log(Levels.DIAG, "Succesfully started!");

		Utils.sleep(3000);

	}

	/**
	 * Will attempt to shut down the program, calling {@link #shutdownActions()}
	 * first.
	 */
	public static void shutdown() {

		LOG.log(Levels.DIAG, "Shutdown requested!");

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

			LOG.log(Levels.DIAG, "Performing shutdown");

			// Shutdown GPIO
			if (gpio != null) {
				LOG.info("Shutting down GPIO");
				gpio.shutdown();
			}

			// terminate communication with database
			LOG.info("Releasing connection pool");
			try {
				DatabaseManager.removeLog4j2JdbcAppender();
				DatabaseManager.releasePool();
			} catch (SQLException e) {
				LOG.error("Could not release connection pool", e);
			}

			// shutdown log4j2
			LOG.info("Shutting down log4j2");
			Configurator.shutdown((LoggerContext) LogManager.getContext());

			System.out.println("Terminating program");
		} else {
			LOG.warn("Duplicately requested shutdown actions");
		}
	}

	private static void setupGpio() {

		LOG.info("Setting up GPIO");

		gpio = GpioFactory.getInstance();

	}

	private static void setupDevices() {

		LOG.info("Setting up devices");

	}

	private static void setupUI() {

	}

	private static void createConnectionPool() {
		try {

			DatabaseManager.createConnectionPool();

		} catch (Exception e) {
			LOG.error("Can't create ConnectionPool, exiting program", e);
		}

	}

	public static void setTimeZone() {
		try {
			DateTimeZone timeZone = DateTimeZone.forID(Config.get().getString("fbp/system/timezone"));
			DateTimeZone.setDefault(timeZone);
		} catch (IllegalArgumentException e) {
			LOG.warn("Incorrect time zone ID, using default");
		}
	}

}
