package net.talentum.fbp.hardware.button;

import net.talentum.fbp.hardware.InputDevice;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.Pin;

/**
 * An input device for button inputs. The type represents
 * the type of the Button. Left, Right or OK.
 * @author padr31
 *
 */
public class Button extends InputDevice{

	private ButtonType type;
	
	public Button(Pin pin, GpioController gpio, ButtonType type) {
		super(pin, gpio);
		this.type = type;
	}
	
	public ButtonType getType(){
		return this.type;
	}
}
