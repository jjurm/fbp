package net.talentum.fbp.hardware.hall;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import net.talentum.fbp.data.Data;
import net.talentum.fbp.data.DataManager;
import net.talentum.fbp.data.DataType;
import net.talentum.fbp.display.RedrawRequestDispatcher;
import net.talentum.fbp.system.Config;
import net.talentum.fbp.system.Main;
import net.talentum.fbp.system.Utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
/**
 * This class is responsible for gathering the data from the {@link HallSensor}. It will
 * perform checks and database uploads in every specified-length time interval. 
 * @author padr31
 *
 */
/**
 * This class is responsible for gathering the data from the {@link HallSensor}. It will
 * perform checks and database uploads in every specified-length time interval. 
 * @author padr31
 *
 */
public class HallSensorDataMonitor extends RedrawRequestDispatcher implements HallSensorEventHandler, Runnable {
	
	private static final Logger LOG = LogManager.getLogger();
		
	private Thread hallSensorDataMonitorThread;
	
	private DataManager dataManager;
	
	private boolean running = false;
	
	private List<HallSensorEvent> events = new ArrayList<HallSensorEvent>();
	
	private long dataWriteInterval;
	
	private static int RPM = 0;
	private static int lastRPM = 0;
	
	public HallSensorDataMonitor(DataManager dataManager) {
		dataWriteInterval = Config.getDataWriteInterval();
		this.dataManager = dataManager;
	}

	@Override
	public void hallSensorStateChanged(HallSensorEvent event) {		
		events.add(event);
		if(event.getState().equals(HallSensorState.FAR)) System.out.println("Magnet is far.");
		else {
		  System.out.println("Magnet is near.");
		  RPM++;
		}
	}
	
	public synchronized void start() {
		if(running) return;
		running = true;
		LOG.info("Starting hallSensorDataMonitorThread...");
		hallSensorDataMonitorThread = new Thread(this);
		hallSensorDataMonitorThread.start(); 
	}

	public synchronized void stop() {
		if(!running) return;
		running = false;
		try {
			LOG.info("Stopping hallSensorDataMonitorThread...");
			hallSensorDataMonitorThread.join(0);
			LOG.info("hallSensorDataMonitorThread successfully stopped");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * This method performs data collection in specified time intervals({@code dataWriteInterval}).
	 * Writes the current time and the number of time the magnet was near the hall sensor into the database.
	 */
	@Override
	public void run() {
	  LOG.info("Starting data monitoring.");
	  this.addRedrawRequestHandler(Main.uiManager);
	  
		while(running){
			events.clear();
			Utils.sleep(dataWriteInterval);
			lastRPM = RPM;
			RPM = 0;
			this.dispatchRedrawRequest();
			LOG.info("Gathering data...");
			dataManager.databaseWrite(new Data.Builder(DataType.HALL, new Timestamp(new DateTime().getMillis())).setValue(getRPM()).build());
		}
	}

  public static String getRPM() {
    return "" + lastRPM;
  }
	
}
