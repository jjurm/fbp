package net.talentum.fbp.context.menu;

import net.talentum.fbp.context.ContextHolder;
import net.talentum.fbp.hardware.drivers.DisplayDriver;

/**
 * {@link Menu} designed only for test purposes.
 * 
 * @author JJurM
 */
public class TestMenu extends Menu {

	public boolean rendered = false;

	public TestMenu(ContextHolder contextHolder) {
		super("TestMenu", contextHolder);
	}

	@Override
	public void renderContext(DisplayDriver displayDriver) {
		rendered = true;
		for (int i = 0; i < 3; i++) {
			menuItems.get(scrollPosition + i).render(null, displayDriver);
		}
	}

}
