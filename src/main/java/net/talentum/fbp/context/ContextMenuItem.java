package net.talentum.fbp.context;

import net.talentum.fbp.context.menu.Menu;
import net.talentum.fbp.context.menu.MenuItem;

/**
 * Simple abstract class uniting {@link Context} and {@link MenuItem}, with
 * implemented some common logic.
 * 
 * @author JJurM
 */
public abstract class ContextMenuItem extends Context implements MenuItem {

	/**
	 * @see #getCallerMenu()
	 */
	protected Menu callerMenu;

	/**
	 * Default constructor.
	 * 
	 * @param contextHolder
	 */
	public ContextMenuItem(ContextHolder contextHolder) {
		super(contextHolder);
	}

	@Override
	public void call(Menu menu) {
		// store reference to the parent Menu
		callerMenu = menu;
		// switch to this context when called
		contextHolder.switchContext(this);
	}

	/**
	 * Returns {@link Menu} that last called this {@link MenuItem}. Returned
	 * object can be considered as temporary parent since the last call.
	 * However, note, that {@link ContextMenuItem} may be called by different
	 * {@link Menu}s each time.
	 * 
	 * @return
	 */
	public Menu getCallerMenu() {
		return callerMenu;
	}

}