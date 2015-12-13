package net.talentum.fbp.hardware;

import net.talentum.fbp.hardware.button.ButtonEventHandler;
import net.talentum.fbp.hardware.drivers.ButtonDriver;
import net.talentum.fbp.hardware.drivers.DisplayDriver;
import net.talentum.fbp.hardware.drivers.HallSensorDriver;
import net.talentum.fbp.hardware.drivers.LedDriver;
import net.talentum.fbp.hardware.drivers.PiezoDriver;
import net.talentum.fbp.hardware.hall.HallSensorEventHandler;
import com.pi4j.io.gpio.GpioController;

/**
 * This class constructs all the drivers for hardware.
 * @author padr31
 *
 */
public class HardwareManager {
	
	private ButtonDriver buttonDriver;
	private DisplayDriver displayDriver;
	private HallSensorDriver hallSensorDriver;
	private LedDriver ledDriver;
	private PiezoDriver piezoDriver;
	
	private ButtonEventHandler buttonEventHandler;
	private HallSensorEventHandler hallSensorEventHandler;
	private GpioController gpio;
	
	public HardwareManager(GpioController gpio, ButtonEventHandler buttonEventHandler, HallSensorEventHandler hallSensorEventHandler) {
		this.gpio = gpio;
		this.buttonEventHandler = buttonEventHandler;
		this.hallSensorEventHandler = hallSensorEventHandler;
		constructDrivers();
	}
	
	private void constructDrivers() {
		buttonDriver = new ButtonDriver(gpio, buttonEventHandler);
		buttonDriver.addListeners();
		
		displayDriver = new DisplayDriver(gpio, true);
		
		
		hallSensorDriver = new HallSensorDriver(gpio, hallSensorEventHandler);
		hallSensorDriver.addListener();
		
		ledDriver = new LedDriver(gpio);
		
		piezoDriver = new PiezoDriver(gpio);
		
		
	}
	
	public void close() {
		buttonDriver.close();
		displayDriver.close();
		hallSensorDriver.close();
		ledDriver.close();
		piezoDriver.close();
	}
	
	public void setButtonEventHandler(ButtonEventHandler buttonEventHandler) {
		buttonDriver.setButtonEventHandler(buttonEventHandler);

	}
	
	public void setHallSensorEventHandler(HallSensorEventHandler hallSensorEventHandler) {
		hallSensorDriver.setHallSensorEventHandler(hallSensorEventHandler);
	}
}
