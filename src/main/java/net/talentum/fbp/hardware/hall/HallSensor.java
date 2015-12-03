package net.talentum.fbp.hardware.hall;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.Pin;

/**
 * The basic implementation of a Hall Sensor hardware component.
 * @author padr31
 *
 */
public class HallSensor {

	public GpioPinDigitalInput in;
	
	public HallSensor(Pin pin, GpioController gpio) {
		in = gpio.provisionDigitalInputPin(pin);
	}

}
