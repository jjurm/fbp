package net.talentum.fbp.hardware.drivers;

import net.talentum.fbp.hardware.InputDevice;
import net.talentum.fbp.hardware.Pins;
import net.talentum.fbp.hardware.hall.HallSensorDataMonitor;
import net.talentum.fbp.hardware.hall.HallSensorEvent;
import net.talentum.fbp.hardware.hall.HallSensorState;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;

/**
 * Class for controlling the {@link HallSensor} and adding the interrupt-based
 * listener to the sensor.
 * @author padr31
 *
 */
public class HallSensorDriver implements Driver{
	
	private InputDevice hallSensor;
		
	private HallSensorDataMonitor monitor;
	
	public HallSensorDriver(GpioController gpio, HallSensorDataMonitor monitor) {
		this.monitor = monitor;
		setup(gpio);
		addListener();
	}

	private void addListener() {
		hallSensor.getInput().addListener(new GpioPinListenerDigital() {
			
			 @Override
	            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
				 	long time = System.nanoTime();
	                HallSensorState state = event.getState() == PinState.LOW ? HallSensorState.NEAR : HallSensorState.FAR;
	                monitor.hallSensorStateChanged(new HallSensorEvent(state, time));
	            }
			 
		});
	}

	@Override
	public void setup(GpioController gpio) {
		hallSensor = new InputDevice(Pins.PIN_HALL, gpio);
	}

	@Override
	public void close() {
		hallSensor.getInput().unexport();
	}
}
