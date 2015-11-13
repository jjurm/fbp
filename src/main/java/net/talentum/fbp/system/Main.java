package net.talentum.fbp.system;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;

/**
 * Main starting class of the program.
 * 
 * @author JJurM
 */
public class Main {
	private static final Logger LOG = LogManager.getLogger();
	
	private static GpioController gpio;

	public static void start(String args[]) {
		
		LOG.info("Program started!");
		System.out.println("ok");
		
	}

	public static void shutdownActions() {
		
		// these actions will be performed once during shutdown
		
	}
	
	public void setupGpio() {
		
		gpio = GpioFactory.getInstance();
		
	}
	
	public void setupDevices() {
		
	}
	
}
