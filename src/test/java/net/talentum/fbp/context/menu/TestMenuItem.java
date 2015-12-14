package net.talentum.fbp.context.menu;

import net.talentum.fbp.display.DisplaySection;
import net.talentum.fbp.hardware.drivers.DisplayDriver;

/**
 * {@link MenuItem} used only for test purposes.
 * 
 * @author JJurM
 */
public class TestMenuItem implements MenuItem {

	protected boolean called = false;

	@Override
	public void call(AbstractMenu menu) {
		called = true;
	}

	@Override
	public void render(DisplaySection displaySection, DisplayDriver displayDriver) {
		// do not render
	}

}
