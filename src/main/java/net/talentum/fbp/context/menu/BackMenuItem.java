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
	public void call(Menu menu) {
		// check if the menu has a parent
		if (menu.getCallerMenu() != null)
			// switch context to the parent menu
			menu.contextHolder.switchContext(menu.getCallerMenu());
	}
	
	@Override
	public void render(DisplaySection displaySection, DisplayDriver displayDriver) {
		// TODO render method of BackMenuItem
	}

}
