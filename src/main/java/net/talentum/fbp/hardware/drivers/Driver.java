package net.talentum.fbp.hardware.drivers;

import com.pi4j.io.gpio.GpioController;

/**
 * This interface is the template for making a driver. 
 * Grouping the the methods setup() and close() which are specific for all drivers.
 * @author padr31
 *
 */
public interface Driver {
	
	/**
	 * The method for initializing the driver when starting the communication.
	 */
	void setup(GpioController gpio); 
	
	/**
	 * The method for cleaning up after the communication.  
	 */
	void close();

	
	
}
