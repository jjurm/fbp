package net.talentum.fbp.hardware.hall;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
/**
 * This class is responsible for gathering the data from the {@link HallSensor}. It will
 * perform checks and database uploads in every specified-length time interval. 
 * @author padr31
 *
 */
public class HallSensorDataMonitor implements HallSensorEventHandler, Runnable{
	
	private static final Logger LOG = LogManager.getLogger();
	
	private Thread hallSensorDataMonitorThread;
	
	private boolean running = false;
	
	private List<HallSensorEvent> events = new ArrayList<HallSensorEvent>();
	
	public HallSensorDataMonitor() {
		
	}

	@Override
	public void hallSensorStateChanged(HallSensorEvent event) {		
		events.add(event);
		if(event.getState().equals(HallSensorState.FAR)) System.out.println("Magnet is far.");
		else System.out.println("Magnet is near.");
	}
	
	private void writeToDatabase() {
		
	}
	
	private synchronized void start() {
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
		
	}
	
}
