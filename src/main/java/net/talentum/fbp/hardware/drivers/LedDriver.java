package net.talentum.fbp.hardware.drivers;

import net.talentum.fbp.hardware.Led;
import net.talentum.fbp.hardware.Pins;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;

public class LedDriver {

	private static Led led_on;
	private static Led led_act;
	private static Led led_err;
	
	public LedDriver(GpioController gpio) {
		led_on = new Led(Pins.PIN_LED_ON, gpio);
		led_act = new Led(Pins.PIN_LED_ACT, gpio);
		led_err = new Led(Pins.PIN_LED_ERR, gpio);
	}
	
	public void led_on(boolean on) {
		if(on){
			led_on.on();
		}else {
			led_on.off();
		}
	}
	public void blink_err(int ms){
		
	}
	
	public void blink_on(int ms){
		
	}
	
	public void blink_act(int ms){
		
	}
	
}
