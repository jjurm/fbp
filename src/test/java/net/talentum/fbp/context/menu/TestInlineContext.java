package net.talentum.fbp.context.menu;

import net.talentum.fbp.display.DisplaySection;
import net.talentum.fbp.hardware.button.ButtonEvent;
import net.talentum.fbp.hardware.button.ButtonState;
import net.talentum.fbp.hardware.drivers.DisplayDriver;

/**
 * The most simple implementation of {@link InlineContext}, used only for test
 * purposes.
 * 
 * @author JJurM
 */
public class TestInlineContext extends InlineContext {

	public int value = 0;
	public boolean rendered = false;

	@Override
	public void buttonStateChanged(ButtonEvent event) {
		if (event.getButtonState() == ButtonState.PRESSED) {
			switch (event.getButtonType()) {
			case LEFT:
				value--;
				dispatchRedrawRequest();
				break;
			case RIGHT:
				value++;
				dispatchRedrawRequest();
				break;
			case OK:
				returnToMenu();
			}
		}
	}

	@Override
	protected void called() {
		dispatchRedrawRequest();
	}

	@Override
	public void render(DisplaySection displaySection, DisplayDriver displayDriver) {
		rendered = true;
	}

	@Override
	public void dispatchRedrawRequest() {
		// just to make the method public
		super.dispatchRedrawRequest();
	}

}
