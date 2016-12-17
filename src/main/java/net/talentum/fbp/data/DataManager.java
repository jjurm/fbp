package net.talentum.fbp.data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import net.talentum.fbp.database.DatabaseManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This class has an active connection to the database and is responsible for communication with the
 * database and has methods for providing accurate information about the running fbp.
 * 
 * DatabaseManager is also responsible for storing info, working with data in the database,
 * analyzing it and giving it away in form of {@link Data}.
 * 
 * @author padr31 TODO Make methods for easy submission of Data of any type to database
 */
public class DataManager {

  private static final Logger LOG = LogManager.getLogger();

  private Connection dbConnection;
  private Statement dbStatement;

  public DataManager() {
    getDatabaseConnection();
    createDatabaseStatement();
    try {
      createFBPTable();
    } catch (SQLException e) {
      LOG.error("Cannot create fbp table.");;
    }
  }

  /**
   * Gets a connection from {@link DatabaseManager}.
   */
  private void getDatabaseConnection() {
    try {
      LOG.debug("Getting a database connection...");
      dbConnection = DatabaseManager.getConnection();
      LOG.debug("Database connection ready.");
    } catch (SQLException e) {
      LOG.error("Couldn't get a database connection.", e);
      e.printStackTrace();
    }
  }

  public void closeConnection() {
    try {
      dbConnection.close();
    } catch (SQLException e) {
      LOG.error("Couldn't close the database connection.", e);
      e.printStackTrace();
    }
  }

  /**
   * Creates a statement from the connection which is used for querying the database.
   */
  private void createDatabaseStatement() {
    try {
      if (dbConnection.isValid(0)) {
        dbStatement = dbConnection.createStatement();
      }
    } catch (SQLException e) {
      LOG.error("Couldn't create database statement.");
      e.printStackTrace();
    }

  }

  /**
   * A method for writing the {@link Data} into the database. Decides how to write depending on the
   * {@link DataType}.
   * 
   * @param data
   */
  // TODO Do sth with the switch to make it more consistent
  public void databaseWrite(Data data) {
    LOG.info("Performing database write");
    switch (data.getType()) {
      case HALL:
        String type = data.getType().toString();
        Timestamp time = data.getTime();
        String value = (String) data.getValue();
        try {
          dbStatement.executeUpdate(String.format(
              "INSERT INTO fbp(type, time, value)VALUES('%s', '%s', '%s')", type, time, value));
        } catch (SQLException e) {
          e.printStackTrace();
        }
        break;
      case DEBUG:
        break;
      case ERROR:
        break;
      case INFO:
        break;
      default:
        break;
    }
  }

  // Checks if table exists, if yes return, if no create it
  public void createFBPTable() throws SQLException {

    java.sql.DatabaseMetaData dbm = dbConnection.getMetaData();

    // check if "fbp" table is there
    
    ResultSet tables = dbm.getTables(null, null, "fbp", null);
    if (tables.next()) {
      LOG.debug("There is already an fbp table.");
      // Table exists
      return;
    } else {
      LOG.debug("Creating fbp table.");
      // Table does not exist, create
      String query = "CREATE TABLE fbp" +
          " (id INTEGER AUTO_INCREMENT PRIMARY KEY," +
          " type VARCHAR(255), " + 
          " time TIMESTAMP, " + 
          " value VARCHAR(255))";
      
      databaseQuery(query);
    }
  }

  public void databaseQuery(String query) {
    try {
      dbStatement.executeUpdate(query);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public Data databaseRead(Data data) {
    return data;
  }
}
