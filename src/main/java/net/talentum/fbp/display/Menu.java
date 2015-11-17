package net.talentum.fbp.display;

import java.util.ArrayList;
import java.util.List;

import net.talentum.fbp.context.Context;
import net.talentum.fbp.context.ContextHolder;
import net.talentum.fbp.hardware.ButtonEvent;
import net.talentum.fbp.hardware.drivers.DisplayDriver;

/**
 * Graphical menu usually displayed on a display; extends {@link Context}.
 * 
 * @author JJurM
 */
public class Menu extends Context implements MenuItem {

	protected List<MenuItem> menuItems = new ArrayList<MenuItem>();
	
	protected int selected = 0;
	
	public Menu(ContextHolder displayContext) {
		super(displayContext);
	}

	@Override
	public void renderContext(DisplayDriver displayDriver) {
		// TODO renderContext method of Menu
	}

	@Override
	public void render(DisplaySection displaySection, DisplayDriver displayDriver) {
		// TODO render method of Menu
	}
	
	@Override
	public void buttonStateChanged(ButtonEvent event) {
		// TODO respond to button events
	}

	@Override
	public void call() {
		// TODO call method of Menu
	}

}
