package net.talentum.fbp.hardware;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinState;

/**
 * A class representing the basic output of a piezo.
 * @author padr31
 * @deprecated Use {@link OutputDevice} instead
 */
public class Piezo {

	public GpioPinDigitalOutput out;
	
	public Piezo(Pin pin, GpioController gpio ) {
		out = gpio.provisionDigitalOutputPin(pin, PinState.LOW);
	}
}
