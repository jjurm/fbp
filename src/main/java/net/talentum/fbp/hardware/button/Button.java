package net.talentum.fbp.hardware.button;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.Pin;

public class Button {

	private GpioPinDigitalInput in;
	
	public Button(Pin pin, GpioController gpio) {
		in = gpio.provisionDigitalInputPin(pin);
	}
}
