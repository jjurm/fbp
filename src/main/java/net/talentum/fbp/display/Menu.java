package net.talentum.fbp.display;

import java.util.ArrayList;
import java.util.List;

import net.talentum.fbp.hardware.ButtonEvent;
import net.talentum.fbp.hardware.drivers.DisplayDriver;

/**
 * Graphical menu usually displayed on a display; extends {@link DisplayContext}.
 * 
 * @author JJurM
 */
public class Menu extends DisplayContext implements MenuItem {

	protected List<MenuItem> menuItems = new ArrayList<MenuItem>();
	
	protected int selected = 0;
	
	public Menu(DisplayContextHolder displayContext) {
		super(displayContext);
	}

	@Override
	public void redraw(DisplayDriver displayDriver) {
		// TODO redraw method of menu
	}

	@Override
	public void buttonStateChanged(ButtonEvent event) {
		// TODO respond to button events
	}

	@Override
	public void call() {
		
	}

}
