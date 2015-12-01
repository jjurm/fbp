package net.talentum.fbp.hardware.drivers;

import com.pi4j.io.gpio.GpioController;

/**
 * A {@link DisplayDriver} that can do nothing, used only for test purposes.
 * 
 * @author JJurM
 */
public class NullDisplayDriver extends DisplayDriver {

	public NullDisplayDriver() {
		super(null);
	}

	@Override
	public void setup(GpioController gpio, boolean backlightOn) {
		// do nothing
	}

}
