package net.talentum.fbp.hardware;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinState;

/**
 * Class representing one LED light.
 * 
 * @author padr
 */
public class Led {

	private GpioController gpio;
	private Pin pin;
	private GpioPinDigitalOutput out;
	
	public Led(Pin pin, GpioController gpio) {
		this.pin = pin;
		this.gpio = gpio;
		out = gpio.provisionDigitalOutputPin(pin, "LED_ACT", PinState.HIGH);
	}

	public void on() {
		out.toggle();
	}
	
	public void off() {
		out.low();
	}
	
}
