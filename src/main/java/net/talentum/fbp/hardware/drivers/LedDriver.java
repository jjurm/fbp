package net.talentum.fbp.hardware.drivers;

import net.talentum.fbp.hardware.Led;
import net.talentum.fbp.hardware.Pins;
import net.talentum.fbp.system.Utils;
import com.pi4j.io.gpio.GpioController;

public class LedDriver implements Driver{

	private static Led led_on;
	private static Led led_act;
	private static Led led_err;
	
	public LedDriver(GpioController gpio) {
		setup(gpio);
	}
	
	public void led_on(boolean on) {
		if(on){
			led_on.on();
		}else {
			led_on.off();
		}
	}
	
	public void led_err(boolean on) {
		if(on){
			led_err.on();
		}else {
			led_err.off();
		}
	}
	
	public void led_act(boolean on) {
		if(on){
			led_act.on();
		}else {
			led_act.off();
		}
	}
	
	public void blink_err(int ms){
		led_err(true);
		Utils.sleep(ms);
		led_err(false);
	}
	
	public void blink_on(int ms){
		led_on(true);
		Utils.sleep(ms);
		led_on(false);
	}
	
	public void blink_act(int ms){
		led_act(true);
		Utils.sleep(ms);
		led_act(false);
	}

	@Override
	public void setup(GpioController gpio) {
		led_on = new Led(Pins.PIN_LED_ON, gpio);
		led_act = new Led(Pins.PIN_LED_ACT, gpio);
		led_err = new Led(Pins.PIN_LED_ERR, gpio);
	}

	@Override
	public void close() {
		
	}
	
}
