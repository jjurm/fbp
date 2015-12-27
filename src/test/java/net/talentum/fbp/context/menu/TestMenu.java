package net.talentum.fbp.context.menu;

import java.util.List;

import net.talentum.fbp.context.ContextHolder;
import net.talentum.fbp.hardware.drivers.DisplayDriver;

/**
 * Menu designed only for test purposes.
 * 
 * @author JJurM
 */
public class TestMenu extends BasicMenu {

	public boolean rendered = false;

	public TestMenu(String name, ContextHolder contextHolder, List<MenuItem> menuItems) {
		super("TestMenu", contextHolder, menuItems);
	}

	@Override
	public void renderContext(DisplayDriver displayDriver) {
		rendered = true;
		for (int i = 0; i < 3; i++) {
			menuItems.get(scrollPosition + i).render(null, displayDriver);
		}
	}
	
	public ContextHolder getContextHolder() {
		return contextHolder;
	}

}
