package net.talentum.fbp.database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.configuration.HierarchicalConfiguration;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbcp2.BasicDataSourceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.talentum.fbp.system.ConfigurationManager;

public class DatabaseManager {
	private static final Logger LOG = LogManager.getLogger();

	private static BasicDataSource dataSource;

	public static Connection getConnection() throws SQLException {
		if (dataSource != null)
			return dataSource.getConnection();
		else
			return null;
	}

	/**
	 * This will load configuration and set up pooled {@link BasicDataSource}
	 * for creating connections to database.
	 * 
	 * @throws Exception
	 */
	public static void createConnectionPool() throws Exception {

		// Load JDBC drivers
		loadDrivers();

		// Obtain configuration
		LOG.debug("database: Loading database configuration");
		HierarchicalConfiguration config = ConfigurationManager.getConfiguration("database");

		// Prepare properties for ConnectionPool
		Properties props = new Properties();
		props.put("username", config.getString("user"));
		props.put("password", config.getString("password"));
		props.put("url", "jdbc:" + config.getString("driverType") + "://" + config.getString("host") + ":"
				+ config.getString("port") + "/" + config.getString("database"));

		// Construct BasicDataSource
		LOG.info("system: Connecting to database");
		dataSource = BasicDataSourceFactory.createDataSource(props);

		// Check connectivity
		checkConnectivity();

	}

	/**
	 * This will call {@link BasicDataSource#close()} method.
	 * 
	 * @throws SQLException
	 */
	public static void releasePool() throws SQLException {
		if (dataSource != null) {
			dataSource.close();
		}
	}

	/**
	 * This will load supported JDBC drivers.
	 * 
	 * @throws ClassNotFoundException
	 */
	private static void loadDrivers() throws ClassNotFoundException {
		LOG.debug("database: Loading JDBC drivers");
		Class.forName("com.mysql.jdbc.Driver");
		Class.forName("org.postgresql.Driver");
	}

	/**
	 * Checks if the {@link DataSource} is successfully established.
	 * 
	 * @throws SQLException
	 *             thrown in case of failure.
	 */
	private static void checkConnectivity() throws SQLException {
		// Obtain connection
		Connection connection = dataSource.getConnection();

		// Check database name
		DatabaseMetaData databaseMetaData = connection.getMetaData();
		String url = databaseMetaData.getURL();
		String dbName = url.substring(url.lastIndexOf("/") + 1);
		LOG.info("Succesfully connected to database: " + dbName + " (" + databaseMetaData.getDatabaseProductName()
				+ ")");
	}

}
