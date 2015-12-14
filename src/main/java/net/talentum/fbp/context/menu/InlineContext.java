package net.talentum.fbp.context.menu;

import net.talentum.fbp.context.Context;
import net.talentum.fbp.display.RedrawRequestDispatcher;
import net.talentum.fbp.hardware.button.ButtonEvent;
import net.talentum.fbp.hardware.button.ButtonEventHandler;

/**
 * {@link InlineContext} is special type of context - has similar behavior to
 * {@link Context}. However, it's not responsible for rendering content of the
 * entire display, only a section. The {@link InlineContext} is designed to be
 * registered as a {@link MenuItem} of a menu.
 * 
 * @author JJurM
 * @see Context
 */
public abstract class InlineContext extends RedrawRequestDispatcher implements ButtonEventHandler, MenuItem {

	private AbstractMenu callerMenu;
	private boolean active = false;

	@Override
	public final void call(AbstractMenu menu) {
		// store reference to the caller
		callerMenu = menu;
		// set active state
		active = true;
		// notify subclasses
		called();
		// switch menu's inlineContext to this
		menu.setInlineContext(this);
	}

	/**
	 * This will let the {@code callerMenu} regain control over
	 * {@link ButtonEvent}s.
	 */
	protected final void returnToMenu() {
		callerMenu.setInlineContext(null);
		active = false;
	}

	/**
	 * Child classes can be notified that the {@link InlineContext} was called
	 * by a menu, just before the {@link InlineContext} is set in the menu.
	 */
	protected abstract void called();

	/**
	 * Determines active state of the {@link InlineContext}.
	 * 
	 * @return {@code true}, if the inline context is currently active.
	 */
	protected boolean isActive() {
		return active;
	}

}
