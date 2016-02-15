package net.talentum.fbp.hardware;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.talentum.fbp.hardware.button.ButtonEventHandler;
import net.talentum.fbp.hardware.drivers.ButtonDriver;
import net.talentum.fbp.hardware.drivers.DisplayDriver;
import net.talentum.fbp.hardware.drivers.Driver;
import net.talentum.fbp.hardware.drivers.HallSensorDriver;
import net.talentum.fbp.hardware.drivers.LedDriver;
import net.talentum.fbp.hardware.drivers.PiezoDriver;
import net.talentum.fbp.hardware.hall.HallSensorEventHandler;

import com.pi4j.io.gpio.GpioController;

/**
 * This class constructs all the drivers for hardware.
 * 
 * @author padr31
 *
 */
public class HardwareManager {

	private static final Logger LOG = LogManager.getLogger();
	
	private ButtonDriver buttonDriver;
	private DisplayDriver displayDriver;
	private HallSensorDriver hallSensorDriver;
	private LedDriver ledDriver;
	private PiezoDriver piezoDriver;

	private ButtonEventHandler buttonEventHandler;
	private HallSensorEventHandler hallSensorEventHandler;
	private GpioController gpio;

	public HardwareManager(GpioController gpio,
			ButtonEventHandler buttonEventHandler,
			HallSensorEventHandler hallSensorEventHandler) {
		this.gpio = gpio;
		this.buttonEventHandler = buttonEventHandler;
		this.hallSensorEventHandler = hallSensorEventHandler;
		constructDrivers();
	}

	/**
	 * The mathod constructs all the drivers usef in this project and lets use
	 * of the methods in the Drivers.
	 */
	private void constructDrivers() {
		LOG.debug("hardware: Setting up buttons...");
		buttonDriver = new ButtonDriver(gpio, buttonEventHandler);
		buttonDriver.addListeners();
		LOG.debug("hardware: ButtonDriver and listeners are set up.");

		LOG.debug("hardware: Setting up DisplayDriver...");
		displayDriver = new DisplayDriver(gpio, true);
		LOG.debug("hardware: DisplayDriver set up.");

		LOG.debug("hardware: Setting up hall sensor...");
		hallSensorDriver = new HallSensorDriver(gpio, hallSensorEventHandler);
		hallSensorDriver.addListener();
		LOG.debug("hardware: HallSensorDriver and listeners are set up.");

		LOG.debug("hardware: Setting up leds and piezo...");
		ledDriver = new LedDriver(gpio);
		piezoDriver = new PiezoDriver(gpio);
		LOG.debug("hardware: LedDriver and PiezoDriver are set up.");

	}

	public Driver getButtonDriver() {
		return this.buttonDriver;
	}

	public Driver getDisplayDriver() {
		return this.displayDriver;
	}

	public Driver getHallSensorDriver() {
		return this.hallSensorDriver;
	}

	public Driver getLedDriver() {
		return this.ledDriver;
	}

	public Driver getPiezoDriver() {
		return this.piezoDriver;
	}

	/**
	 * Closes the communication with all the drivers and prepares the program
	 * for exit.
	 */
	public void close() {
		buttonDriver.close();
		displayDriver.close();
		hallSensorDriver.close();
		ledDriver.close();
		piezoDriver.close();
	}

	/**
	 * Sets the {@link ButtonEventHandler}s in all the Drivers which need it.
	 *
	 * @param buttonEventHandler
	 */
	public void setButtonEventHandler(ButtonEventHandler buttonEventHandler) {
		buttonDriver.setButtonEventHandler(buttonEventHandler);
	}

	/**
	 * Sets the {@link HallSensorEventHandler}s in all the Drivers which need
	 * it.
	 * 
	 * @param hallSensorEventHandler
	 */
	public void setHallSensorEventHandler(
			HallSensorEventHandler hallSensorEventHandler) {
		hallSensorDriver.setHallSensorEventHandler(hallSensorEventHandler);
	}
}
