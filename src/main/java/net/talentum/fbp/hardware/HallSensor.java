package net.talentum.fbp.hardware;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.Pin;

public class HallSensor {

	private GpioPinDigitalInput in;
	
	public HallSensor(Pin pin, GpioController gpio) {
		in = gpio.provisionDigitalInputPin(pin);
	}
}
