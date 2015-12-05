package net.talentum.fbp.hardware.drivers;

import net.talentum.fbp.hardware.OutputDevice;
import net.talentum.fbp.hardware.Pins;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.PinState;

/**
 * Working with the {@link Piezo}. Setting up, turning on/off and beeping
 * for a period of time.
 * @author padr31
 *
 */
public class PiezzoDriver implements Driver{

	private OutputDevice piezo;

	public PiezzoDriver(GpioController gpio) {
		setup(gpio);
	}
	
	public void beep(long ms) {
		piezo.getOutput().pulse(ms);
		piezo.getOutput().setState(PinState.LOW);
	}
	
	public void on() {
		piezo.getOutput().setState(PinState.HIGH);
	} 
	
	public void off() {		
		piezo.getOutput().setState(PinState.LOW);	
	}
	
	@Override
	public void setup(GpioController gpio) {
		piezo = new OutputDevice(Pins.PIN_PIEZO, gpio);
	}

	@Override
	public void close() {
		piezo.getOutput().unexport();
	}
	
	
}
