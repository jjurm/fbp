package net.talentum.fbp.system;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.HierarchicalConfiguration;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;

import snaq.db.ConnectionPool;

/**
 * Main starting class of the program.
 * 
 * @author JJurM
 */
public class Main {
	private static final Logger LOG = LogManager.getLogger();

	private static GpioController gpio;
	private static ConnectionPool connectionPool;

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
			if (connectionPool != null) {
				LOG.info("Releasing ConnectionPool");
				connectionPool.release();
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
			// Create configuration object
			LOG.debug("system: Loading database configuration");
			HierarchicalConfiguration config = new XMLConfiguration("config/database.xml");

			// Prepare properties for ConnectionPool
			Properties props = new Properties();
			props.put("user", config.getString("user"));
			props.put("password", config.getString("password"));

			// Load JDBC driver
			Class.forName("org.postgresql.Driver");

			// Construct ConnectionPool
			LOG.info("system: Connecting to database");
			String url = "jdbc:mysql://" + config.getString("host") + "/" + config.getString("database");
			connectionPool = new ConnectionPool("local", 0, 3, 0, url, props);

			// Obtain connection
			Connection connection = connectionPool.getConnection();
			// Check database name
			DatabaseMetaData databaseMetaData = connection.getMetaData();
			url = databaseMetaData.getURL();
			String dbName = url.substring(url.lastIndexOf("/") + 1);
			LOG.info("Succesfully connected to database: " + dbName + " (" + databaseMetaData.getDatabaseProductName()
					+ ")");

		} catch (ConfigurationException | ClassNotFoundException | SQLException e) {
			LOG.error("Can't create ConnectionPool, exiting program", e);
		}
	}

}
