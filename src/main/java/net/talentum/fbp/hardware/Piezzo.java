package net.talentum.fbp.hardware;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinState;

public class Piezzo {

	public GpioPinDigitalOutput out;
	
	public Piezzo(Pin pin, GpioController gpio ) {
		out = gpio.provisionDigitalOutputPin(pin, PinState.LOW);
	}
}
