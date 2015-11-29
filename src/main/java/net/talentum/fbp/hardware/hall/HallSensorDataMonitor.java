package net.talentum.fbp.hardware.hall;

import net.talentum.fbp.hardware.drivers.HallSensorDriver;

public class HallSensorDataMonitor implements HallSensorEventHandler{
	
	private HallSensorDriver hallSensorDriver;
	
	public HallSensorDataMonitor(HallSensorDriver hallSensorDriver) {
		this.hallSensorDriver = hallSensorDriver;
		
	}

	@Override
	public void hallSensorStateChanged(HallSensorEvent event) {		
	}
	
}
