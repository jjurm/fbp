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
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.db.jdbc.ColumnConfig;
import org.apache.logging.log4j.core.appender.db.jdbc.JdbcAppender;
import org.apache.logging.log4j.core.config.Configuration;

import net.talentum.fbp.logging.StackAppender;
import net.talentum.fbp.system.ConfigurationManager;

/**
 * Class responsible for managing database connection pools set-up and shutdown,
 * also for receiving connections.
 * <p>
 * Method {@link #getConnection()} of this class is used in Log4j2 configuration
 * file as ConnectionFactory for {@link JdbcAppender}.
 * </p>
 * 
 * @author JJurM
 */
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
	 * Simple singleton class used as
	 * {@link org.apache.logging.log4j.core.appender.db.jdbc.ConnectionSource}
	 * for Log4j2
	 * 
	 * @author JJurM
	 */
	public static class ConnectionSource implements org.apache.logging.log4j.core.appender.db.jdbc.ConnectionSource {

		// single instance
		private static final ConnectionSource INSTANCE = new ConnectionSource();

		private ConnectionSource() {
		}

		public static ConnectionSource getInstance() {
			return INSTANCE;
		}

		@Override
		public Connection getConnection() throws SQLException {
			return DatabaseManager.getConnection();
		}

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
	 * Creates and configures {@link JdbcAppender}, then adds it to the
	 * {@link StackAppender} named {@code "DatabaseStack"}.
	 */
	public static void addLog4j2JdbcAppender() {
		final LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
		final Configuration config = ctx.getConfiguration();

		ColumnConfig[] columnConfigs = new ColumnConfig[] {
				ColumnConfig.createColumnConfig(config, "date", null, null, "true", "false", "false"),
				ColumnConfig.createColumnConfig(config, "thread", "%thread", null, null, null, null),
				ColumnConfig.createColumnConfig(config, "level", "%level", null, null, null, null),
				ColumnConfig.createColumnConfig(config, "logger", "%logger", null, null, null, null),
				ColumnConfig.createColumnConfig(config, "message", "%message", null, null, null, null),
				ColumnConfig.createColumnConfig(config, "throwable", "%ex{}", null, null, null, null) };
		Appender jdbcAppender = JdbcAppender.createAppender("Database", "true", null, ConnectionSource.INSTANCE, "0",
				"logs", columnConfigs);

		jdbcAppender.start();
		config.addAppender(jdbcAppender);

		StackAppender stackAppender = (StackAppender) config.getAppender("DatabaseStack");
		stackAppender.setAppender(jdbcAppender);
	}

	/**
	 * Removes {@link JdbcAppender} from {@link StackAppender} named {@code "DatabaseStack"}.
	 */
	public static void removeLog4j2JdbcAppender() {
		final LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
		final Configuration config = ctx.getConfiguration();

		StackAppender stackAppender = (StackAppender) config.getAppender("DatabaseStack");
		stackAppender.setAppender(null);
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
