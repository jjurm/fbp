package net.talentum.fbp.hardware.hall;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.Pin;

public class HallSensor {

	public GpioPinDigitalInput in;
	
	public HallSensor(Pin pin, GpioController gpio) {
		in = gpio.provisionDigitalInputPin(pin);
	}

}
