package net.talentum.fbp.hardware.hall;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.talentum.fbp.data.Data;
import net.talentum.fbp.data.DataManager;
import net.talentum.fbp.data.DataType;
import net.talentum.fbp.database.DatabaseManager;
import net.talentum.fbp.system.Config;
import net.talentum.fbp.system.Utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.Instant;
/**
 * This class is responsible for gathering the data from the {@link HallSensor}. It will
 * perform checks and database uploads in every specified-length time interval. 
 * @author padr31
 *
 */
public class HallSensorDataMonitor implements HallSensorEventHandler, Runnable{
	
	private static final Logger LOG = LogManager.getLogger();
		
	private Thread hallSensorDataMonitorThread;
	
	private DataManager dataManager;
	
	private boolean running = false;
	
	private List<HallSensorEvent> events = new ArrayList<HallSensorEvent>();
	
	private long dataWriteInterval;
	
	public HallSensorDataMonitor(DataManager dataManager) {
		dataWriteInterval = Config.getDataWriteInterval();
		this.dataManager = dataManager;
	}

	@Override
	public void hallSensorStateChanged(HallSensorEvent event) {		
		events.add(event);
		if(event.getState().equals(HallSensorState.FAR)) System.out.println("Magnet is far.");
		else System.out.println("Magnet is near.");
	}
	
	public synchronized void start() {
		if(running) return;
		hallSensorDataMonitorThread = new Thread(this);
		hallSensorDataMonitorThread.start(); 
	}

	public synchronized void stop() {
		if(!running) return;
		try {
			LOG.info("Stopping hallSensorDataMonitorThread...");
			hallSensorDataMonitorThread.join(0);
			LOG.info("hallSensorDataMonitorThread successfully stopped");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void run() {
		while(running){
			events.clear();
			Utils.sleep(dataWriteInterval);
			dataManager.databaseWrite(new Data.Builder(DataType.HALL, new Instant().getMillis()).setValue(events.size()/2).build());
		}
	}
	
}
