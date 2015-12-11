package net.talentum.fbp.context;

import net.talentum.fbp.context.menu.Menu;
import net.talentum.fbp.hardware.button.ButtonEvent;
import net.talentum.fbp.hardware.drivers.DisplayDriver;
import net.talentum.fbp.ui.UIManager;

/**
 * {@code HomeScreen} context will be placed as parent of all menus in the menu
 * hierarchy.
 * 
 * @author JJurM
 * @see UIManager#constructMenuHierarchy()
 */
public class HomeScreen extends Context {

	protected Menu menu;

	public HomeScreen(ContextHolder contextHolder) {
		super(contextHolder);
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	@Override
	public void buttonStateChanged(ButtonEvent event) {
		// TODO Auto-generated method stub
	}

	@Override
	public void renderContext(DisplayDriver displayDriver) {
		// TODO Auto-generated method stub
	}

}
