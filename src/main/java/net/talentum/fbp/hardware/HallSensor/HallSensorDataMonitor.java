package net.talentum.fbp.hardware.HallSensor;

import net.talentum.fbp.hardware.drivers.HallSensorDriver;

public class HallSensorDataMonitor implements HallSensorEventHandler{
	
	private HallSensorDriver hallSensorDriver;
	
	public HallSensorDataMonitor(HallSensorDriver hallSensorDriver) {
		this.hallSensorDriver = hallSensorDriver;
		
	}

	@Override
	public void hallSensorStateChanged(HallSensorEvent event) {
		// TODO Auto-generated method stub
		
	}
	
}
