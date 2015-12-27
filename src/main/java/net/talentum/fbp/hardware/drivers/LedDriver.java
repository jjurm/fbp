package net.talentum.fbp.hardware.drivers;

import net.talentum.fbp.hardware.OutputDevice;
import net.talentum.fbp.hardware.Pins;

import com.pi4j.io.gpio.GpioController;

/**
 * Controlling the behavior of LEDs. Blinking,
 * turning on, off 
 * @author padr31
 *
 */
public class LedDriver implements Driver{

	private static OutputDevice led_on;
	private static OutputDevice led_act;
	private static OutputDevice led_err;
	
	public LedDriver(GpioController gpio) {
		setup(gpio);
	}
	
	/**
	 * Change the state of led_on to the value of the boolean on. 
	 * 
	 * @param on 1 stands for on, 0 for off
	 */
	public void led_on(boolean on) {
		if(on){
			led_on.getOutput().high();
		}else {
			led_on.getOutput().low();
		}
	}
	
	/**
	 * Change the state of led_err to the value of the boolean on. 
	 * 
	 * @param on 1 stands for on, 0 for off
	 */
	public void led_err(boolean on) {
		if(on){
			led_err.getOutput().high();
		}else {
			led_err.getOutput().low();
		}
	}
	
	/**
	 * Change the state of led_act to the value of the boolean on. 
	 * 
	 * @param on 1 stands for on, 0 for off
	 */
	public void led_act(boolean on) {
		if(on){
			led_act.getOutput().high();
		}else {
			led_act.getOutput().low();
		}
	}
	
	public void blink_err(int ms){
		led_err.getOutput().pulse(ms);
	}
	
	public void blink_on(int ms){
		led_on.getOutput().pulse(ms);
	}
	
	public void blink_act(int ms){
		led_act.getOutput().pulse(ms);
	}

	@Override
	public void setup(GpioController gpio) {
		led_on = new OutputDevice(Pins.PIN_LED_ON, gpio);
		led_act = new OutputDevice(Pins.PIN_LED_ACT, gpio);
		led_err = new OutputDevice(Pins.PIN_LED_ERR, gpio);
	}

	@Override
	public void close() {
		led_on.getOutput().unexport();
		led_act.getOutput().unexport();
		led_err.getOutput().unexport();
	}
	
}
