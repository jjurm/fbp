package net.talentum.fbp.context.menu;

import net.talentum.fbp.context.ElementRenderer;

/**
 * Interface representing item in {@link MenuItem}.
 * 
 * @author JJurM
 */
public interface MenuItem extends ElementRenderer {

	/**
	 * Call to {@link MenuItem#call(AbstractMenu)} method means that it was
	 * selected and called from a parent menu.
	 * 
	 * @param menu
	 */
	public void call(AbstractMenu menu);

}
