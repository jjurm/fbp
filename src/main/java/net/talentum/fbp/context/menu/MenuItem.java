package net.talentum.fbp.context.menu;

import net.talentum.fbp.context.ElementRenderer;

/**
 * Interface representing item in {@link MenuItem}.
 * 
 * @author JJurM
 */
public interface MenuItem extends ElementRenderer {

	/**
	 * Call to MenuItem means that it was selected and called from a
	 * {@link Menu}.
	 * @param menu TODO
	 */
	public void call(Menu menu);

}
