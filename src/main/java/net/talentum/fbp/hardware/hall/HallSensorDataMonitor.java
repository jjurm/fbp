package net.talentum.fbp.hardware.hall;

import net.talentum.fbp.hardware.drivers.HallSensorDriver;
/**
 * This class is responsible for gathering the data from the {@link HallSensor}. It will
 * perform checks and database uploads in every specified-length time interval. 
 * @author padr31
 *
 */
public class HallSensorDataMonitor implements HallSensorEventHandler, Runnable{
	
	private HallSensorDriver hallSensorDriver;
	private Thread hallSensorDataMonitorThread;
	
	public HallSensorDataMonitor(HallSensorDriver hallSensorDriver) {
		this.hallSensorDriver = hallSensorDriver;
		
	}

	@Override
	public void hallSensorStateChanged(HallSensorEvent event) {		
		
	}

	@Override
	public void run() {
		
	}
	
}
