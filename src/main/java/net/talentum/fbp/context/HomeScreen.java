package net.talentum.fbp.context;

import net.talentum.fbp.context.menu.MainMenu;
import net.talentum.fbp.hardware.button.ButtonEvent;
import net.talentum.fbp.hardware.button.ButtonState;
import net.talentum.fbp.hardware.button.ButtonType;
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

	protected MainMenu mainMenu;

	/**
	 * Default constructor.
	 * 
	 * @param mainMenu
	 * @param contextHolder
	 */
	public HomeScreen(ContextHolder contextHolder) {
		super(contextHolder);
	}
	
	@Override
	public String getName() {
		return "HomeScreen";
	}
	
	public void setMainMenu(MainMenu mainMenu) {
		this.mainMenu = mainMenu;
	}

	@Override
	public void buttonStateChanged(ButtonEvent event) {
		if (event.getButtonState() == ButtonState.PRESSED && event.getButtonType() == ButtonType.OK) {
			mainMenu.enter();
			contextHolder.switchContext(mainMenu);
		}
	}

	@Override
	public void renderContext(DisplayDriver display) {
		// title bar
		display.clear();
		display.wline(0, "HomeScreen");
	}

}
