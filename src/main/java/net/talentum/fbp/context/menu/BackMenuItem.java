package net.talentum.fbp.context.menu;

import net.talentum.fbp.display.DisplaySection;
import net.talentum.fbp.hardware.drivers.DisplayDriver;

/**
 * This {@link MenuItem} will cause the menu to return to the parent menu.
 * 
 * @author JJurM
 */
public class BackMenuItem implements MenuItem {

	@Override
	public void call(AbstractMenu menu) {
		menu.back();
	}

	@Override
	public void render(DisplaySection section, DisplayDriver display) {
		display.write("Back", section);
	}

}
