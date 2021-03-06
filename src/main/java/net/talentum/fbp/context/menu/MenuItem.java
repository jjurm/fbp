package net.talentum.fbp.context.menu;

import net.talentum.fbp.display.ElementRenderer;
import net.talentum.fbp.ui.Nameable;

/**
 * Interface representing item in {@link MenuItem}.
 * 
 * @author JJurM
 */
public interface MenuItem extends ElementRenderer, Nameable {

	/**
	 * Call to {@link MenuItem#call(AbstractMenu)} method means that it was
	 * selected and called from a parent menu.
	 * 
	 * @param menu
	 */
	public void call(AbstractMenu menu);

}
