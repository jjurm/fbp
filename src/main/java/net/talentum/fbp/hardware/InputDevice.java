package net.talentum.fbp.hardware;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.Pin;

/**
 * This class represents an input device connected to
 * a pin.
 * @author padr31
 *
 */
public class InputDevice extends Device{
	
	protected GpioPinDigitalInput in;
	
	public InputDevice(Pin pin, GpioController gpio) {
		super(pin, gpio);
		in = gpio.provisionDigitalInputPin(pin);
	}
	
	public GpioPinDigitalInput getInput() {
		return this.in;
	}
}
