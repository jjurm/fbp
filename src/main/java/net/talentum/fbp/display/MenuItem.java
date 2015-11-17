package net.talentum.fbp.display;

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
	 */
	public void call();

}
