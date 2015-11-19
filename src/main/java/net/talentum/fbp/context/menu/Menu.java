package net.talentum.fbp.context.menu;

import java.util.ArrayList;
import java.util.List;

import net.talentum.fbp.context.Context;
import net.talentum.fbp.context.ContextHolder;
import net.talentum.fbp.context.ContextMenuItem;
import net.talentum.fbp.display.DisplaySection;
import net.talentum.fbp.hardware.ButtonEvent;
import net.talentum.fbp.hardware.ButtonState;
import net.talentum.fbp.hardware.drivers.DisplayDriver;

/**
 * Graphical menu usually displayed on a display; extends {@link Context}.
 * 
 * @author JJurM
 */
public class Menu extends ContextMenuItem {

	protected List<MenuItem> menuItems = new ArrayList<MenuItem>();

	protected int selected = 0;

	/**
	 * Defines the lists index of first displayed {@link MenuItem}
	 */
	protected int scrollPosition = 0;

	/**
	 * Default constructor.
	 * 
	 * @param contextHolder
	 *            holder that holds this context
	 */
	public Menu(ContextHolder contextHolder) {
		super(contextHolder);
	}

	@Override
	public void buttonStateChanged(ButtonEvent event) {
		if (event.getButtonState() == ButtonState.PRESSED) {
			switch (event.getButtonType()) {
			case LEFT:
				// move selection to the previous item
				selected--;
				if (selected < 0)
					selected = menuItems.size() - 1;
				adjustScrollPosition();
				break;
			case RIGHT:
				// move selection to the next item
				selected++;
				if (selected >= menuItems.size())
					selected = 0;
				adjustScrollPosition();
				break;
			case OK:
				// call selected item
				menuItems.get(selected).call(this);
				break;
			}
		}
	}

	/**
	 * This method will move scroll position if necessary to assert that the
	 * selected {@link MenuItem} will be shown.
	 */
	protected void adjustScrollPosition() {
		if (scrollPosition > selected) {
			scrollPosition = selected;
		} else if (selected - 2 > scrollPosition) {
			scrollPosition = selected - 2;
		}
	}

	@Override
	public void call(Menu menu) {
		// reset selected item index
		selected = 0;
		adjustScrollPosition();
		super.call(menu);
	}

	@Override
	public void renderContext(DisplayDriver displayDriver) {
		// TODO renderContext method of Menu
	}

	@Override
	public void render(DisplaySection displaySection, DisplayDriver displayDriver) {
		// TODO render method of Menu
	}

}
