package net.talentum.fbp.hardware.drivers;

import net.talentum.fbp.hardware.Piezzo;
import net.talentum.fbp.hardware.Pins;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.PinState;

public class PiezzoDriver implements Driver{

	private Piezzo piezzo;

	public PiezzoDriver(GpioController gpio) {
		setup(gpio);
	}
	
	public void beep(long ms) {
		piezzo.out.pulse(ms);
		piezzo.out.setState(PinState.LOW);
	}
	
	public void on() {
		piezzo.out.setState(PinState.HIGH);
	} 
	
	public void off() {		
		piezzo.out.setState(PinState.LOW);	
	}
	
	@Override
	public void setup(GpioController gpio) {
		piezzo = new Piezzo(Pins.PIN_PIEZO, gpio);
	}

	@Override
	public void close() {
		piezzo.out.unexport();
	}
	
	
}
