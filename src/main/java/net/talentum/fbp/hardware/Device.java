package net.talentum.fbp.hardware;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.Pin;

/**
 * A device connected to a pin. This class groups 
 * both output and input devices.
 * @author padr31
 *
 */
public abstract class Device {

	public Device(Pin pin, GpioController gpio) {
		
	}
}
