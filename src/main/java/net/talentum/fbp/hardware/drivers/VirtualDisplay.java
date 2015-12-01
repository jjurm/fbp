package net.talentum.fbp.hardware.drivers;

/**
 * This class is used besides and never without a {@link DisplayDriver}. It simulates a real display with the
 * parameters specified in the {@link DisplayDriver}. 
 * @author padr31
 */
public class VirtualDisplay {

	private DisplayDriver displayDriver;

	public VirtualDisplay(DisplayDriver displayDriver) {
		this.displayDriver = displayDriver;
		
	}
}
