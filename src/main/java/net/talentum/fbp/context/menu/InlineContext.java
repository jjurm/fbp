package net.talentum.fbp.context.menu;

import net.talentum.fbp.context.Context;
import net.talentum.fbp.display.RedrawRequestDispatcher;
import net.talentum.fbp.hardware.ButtonEvent;
import net.talentum.fbp.hardware.ButtonEventHandler;

/**
 * {@link InlineContext} is special type of context - has similar behavior to
 * {@link Context}. However, it's not responsible for rendering content of the
 * entire display, only a section. The {@link InlineContext} is designed to be
 * registered as a {@link MenuItem} of a {@link Menu}.
 * 
 * @author JJurM
 * @see Context
 */
public abstract class InlineContext extends RedrawRequestDispatcher implements ButtonEventHandler, MenuItem {

	private Menu callerMenu;

	@Override
	public final void call(Menu menu) {
		// store reference to the caller
		callerMenu = menu;
		// switch menu's inlineContext to this
		menu.setInlineContext(this);
		// finally notify subclasses
		called();
	}

	/**
	 * This will let the {@code callerMenu} regain control over
	 * {@link ButtonEvent}s.
	 */
	protected final void returnToMenu() {
		callerMenu.setInlineContext(null);
	}

	/**
	 * Child classes can be notified that the {@link InlineContext} was called
	 * by a {@link Menu}.
	 */
	protected abstract void called();

}
