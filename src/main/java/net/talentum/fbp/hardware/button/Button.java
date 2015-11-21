package net.talentum.fbp.hardware.button;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.Pin;

public class Button {

	public GpioPinDigitalInput in;
	private ButtonType type;
	
	public Button(Pin pin, GpioController gpio, ButtonType type) {
		in = gpio.provisionDigitalInputPin(pin);
		this.type = type;
	}
	
	public ButtonType getType(){
		return this.type;
	}
}
