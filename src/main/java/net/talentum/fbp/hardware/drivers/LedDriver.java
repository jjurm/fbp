package net.talentum.fbp.hardware.drivers;

import net.talentum.fbp.hardware.Led;
import net.talentum.fbp.hardware.Pins;
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
			led_on.out.high();
		}else {
			led_on.out.low();
		}
	}
	
	public void led_err(boolean on) {
		if(on){
			led_err.out.high();
		}else {
			led_err.out.low();
		}
	}
	
	public void led_act(boolean on) {
		if(on){
			led_act.out.high();
		}else {
			led_act.out.low();
		}
	}
	
	public void blink_err(int ms){
		led_err.out.pulse(ms);
	}
	
	public void blink_on(int ms){
		led_on.out.pulse(ms);
	}
	
	public void blink_act(int ms){
		led_act.out.pulse(ms);
	}

	@Override
	public void setup(GpioController gpio) {
		led_on = new Led(Pins.PIN_LED_ON, gpio);
		led_act = new Led(Pins.PIN_LED_ACT, gpio);
		led_err = new Led(Pins.PIN_LED_ERR, gpio);
	}

	@Override
	public void close() {
		led_on.out.unexport();
		led_act.out.unexport();
		led_err.out.unexport();
	}
	
}
