package net.talentum.fbp.data;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import net.talentum.fbp.database.DatabaseManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This class has an active connection to the database and is responsible for communication 
 * with the database and has methods for providing accurate information about the running fbp. 
 * @author padr31
 *
 */
public class DataManager {

	private static final Logger LOG = LogManager.getLogger();
	
	private Connection dbConnection;
	private Statement dbStatement;

	public DataManager(){
		getDatabaseConnection();
		createDatabaseStatement();
	}

	private void getDatabaseConnection() {
		try {
			LOG.debug("DataManager: Getting a database connection...");
			dbConnection = DatabaseManager.getConnection();
			LOG.debug("DataManager: Database connection ready.");
		} catch (SQLException e) {
			LOG.error("DataManager: Couldn't get a database connection.", e);
			e.printStackTrace();
		}
	}
	
	public void closeConnection() {
		try {
			dbConnection.close();
		} catch (SQLException e) {
			LOG.error("DataManager: Couldn't close the database connection.", e);
			e.printStackTrace();
		}
	}
	
	private void createDatabaseStatement() {
		try {
			if(dbConnection.isValid(0)){
				dbStatement = dbConnection.createStatement();
			}
		} catch (SQLException e) {
			LOG.error("DataManager: Couldn't create database statement.");
			e.printStackTrace();
		}
		
	}
	
	public void databaseWrite(Data data) {
		switch(data.getType()){
		case HALL:
			String type = data.getType().toString();
			String time = Long.toString(data.getTime());
			String value = Integer.toString((Integer)data.getValue());
			try {
				dbStatement.executeUpdate(String.format("INSERT INTO fbp(type, time, value)VALUES(%s, %s, %s)", type, time, value));
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
	
	public void databaseQuery() {
		
	}
	
	public Data databaseRead(Data data) {
		return data;
	}
}
