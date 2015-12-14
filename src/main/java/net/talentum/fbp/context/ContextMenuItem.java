package net.talentum.fbp.context;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.talentum.fbp.context.menu.AbstractMenu;
import net.talentum.fbp.context.menu.BackMenuItem;
import net.talentum.fbp.context.menu.BasicMenu;
import net.talentum.fbp.context.menu.MenuItem;

/**
 * Simple abstract class uniting {@link Context} and {@link MenuItem}, with
 * implemented some common logic.
 * 
 * @author JJurM
 */
public abstract class ContextMenuItem extends Context implements MenuItem {
	private static final Logger LOG = LogManager.getLogger();

	private final String name;

	/**
	 * @see #getCallerMenu()
	 */
	private AbstractMenu callerMenu;

	/**
	 * Default constructor.
	 * 
	 * @param contextHolder
	 */
	public ContextMenuItem(String name, ContextHolder contextHolder) {
		super(contextHolder);
		this.name = name;
	}

	@Override
	public void call(AbstractMenu menu) {
		enter();
		// store reference of the parent Menu
		callerMenu = menu;
		// switch to this context when called
		contextHolder.switchContext(this);
	}

	/**
	 * This method is called just before switching actual context to this object
	 * - when user is <em>entering</em> to the context from parent menu. Note,
	 * that purpose of this method appeared by joining superclass
	 * {@link Context} with interface {@link MenuItem}.
	 * <p>
	 * Child classes shall override it and define custom body with actions which
	 * should be performed during the enter.
	 * </p>
	 */
	protected abstract void enter();

	/**
	 * Switch context to the parent menu, if any. Intended to be called for
	 * example from {@link BackMenuItem}.
	 */
	protected void back() {
		// check if the menu has a parent
		if (getCallerMenu() != null) {
			// switch context to the parent menu
			contextHolder.switchContext(getCallerMenu());
		} else {
			LOG.debug("Back method called, but no caller menu is stored");
		}
	}

	/**
	 * Returns {@link AbstractMenu} that last called this {@link MenuItem}.
	 * Returned object can be considered as temporary parent since the last
	 * call. However, note that {@link ContextMenuItem} may be called by
	 * different {@link BasicMenu}s each time.
	 * 
	 * @return
	 */
	public AbstractMenu getCallerMenu() {
		return callerMenu;
	}

	/**
	 * Returns name of this {@link ContextMenuItem}.
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}

}
