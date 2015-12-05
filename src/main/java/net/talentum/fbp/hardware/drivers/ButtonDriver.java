package net.talentum.fbp.hardware.drivers;

import net.talentum.fbp.context.UIManager;
import net.talentum.fbp.hardware.Pins;
import net.talentum.fbp.hardware.button.Button;
import net.talentum.fbp.hardware.button.ButtonEvent;
import net.talentum.fbp.hardware.button.ButtonState;
import net.talentum.fbp.hardware.button.ButtonType;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;

/**
 * Class for working with {@link ButtonEvent}s.
 * Adding listeners to the button pins.
 * @author padr31
 *
 */
public class ButtonDriver implements Driver{
	
	private Button btn_ok;
	private Button btn_r;
	private Button btn_l;
	
	private UIManager uiManager;
	
	public ButtonDriver(GpioController gpio, UIManager uiManager) {
		this.uiManager = uiManager;
		
		setup(gpio);
		
		addListener(btn_ok);
		addListener(btn_r);
		addListener(btn_l);
	}

	private void addListener(Button button) {
		button.getInput().addListener(new GpioPinListenerDigital(){

			@Override
			public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
				ButtonState state = event.getState() == PinState.LOW ? ButtonState.PRESSED : ButtonState.RELEASED;
				ButtonType type = button.getType();
				uiManager.buttonStateChanged(new ButtonEvent(type, state));
			}
			
		});
	}
	
	@Override
	public void setup(GpioController gpio) {
		btn_ok = new Button(Pins.PIN_BTN_OK, gpio, ButtonType.OK);
		btn_r = new Button(Pins.PIN_BTN_R, gpio, ButtonType.RIGHT);
		btn_l = new Button(Pins.PIN_BTN_L, gpio, ButtonType.LEFT);
	}

	@Override
	public void close() {
		btn_ok.getInput().unexport();
		btn_r.getInput().unexport();
		btn_l.getInput().unexport();
	}
	
}
