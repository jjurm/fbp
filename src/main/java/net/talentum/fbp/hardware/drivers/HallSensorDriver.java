package net.talentum.fbp.hardware.drivers;

import net.talentum.fbp.hardware.Pins;
import net.talentum.fbp.hardware.HallSensor.HallSensor;
import net.talentum.fbp.hardware.HallSensor.HallSensorDataMonitor;
import net.talentum.fbp.hardware.HallSensor.HallSensorEvent;
import net.talentum.fbp.hardware.HallSensor.HallSensorState;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;

public class HallSensorDriver implements Driver{
	
	private HallSensor hallSensor;
	private GpioPinDigitalInput in;
	private HallSensorDataMonitor monitor;
	
	public HallSensorDriver(GpioController gpio, HallSensorDataMonitor monitor) {
		this.monitor = monitor;
		setup(gpio);
		addListener();
	}

	private void addListener() {
		in.addListener(new GpioPinListenerDigital() {
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
		hallSensor = new HallSensor(Pins.PIN_HALL, gpio);
		in = hallSensor.in;
	}

	@Override
	public void close() {
		in.unexport();
	}
}
