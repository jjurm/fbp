package net.talentum.fbp.system;

import java.sql.Connection;
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
		createConnectionPool();

		LOG.info("Succesfully started!");

	}

	/**
	 * Performs necessary shutdown actions and assures they will be run at most
	 * once.
	 */
	public static void shutdownActions() {
		if (shutdownActionsPerformed.compareAndSet(false, true)) {
			// these actions will be performed once during shutdown

			// terminate communication with database
			if (connectionPool != null)
				connectionPool.releaseAsync();

			// Shutdown GPIO
			gpio.shutdown();

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
			ConnectionPool pool = new ConnectionPool("local", 0, 3, 0, url, props);

			Connection connection = pool.getConnection();
			LOG.info("Succesfully connected to database: " + connection.getMetaData().getDatabaseProductName());

		} catch (ConfigurationException | ClassNotFoundException | SQLException e) {
			LOG.error("Can't create ConnectionPool, exiting program", e);
		}
	}

}
