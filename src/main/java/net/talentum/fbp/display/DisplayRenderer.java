package net.talentum.fbp.display;

import net.talentum.fbp.hardware.drivers.DisplayDriver;

/**
 * Renderer of display is capable of rendering content of the entire display.
 * Can, of course, involve other renderers, for example {@link ElementRenderer}.
 * 
 * @author JJurM
 */
@FunctionalInterface
public interface DisplayRenderer {

	/**
	 * This method is responsible for rendering content on display.
	 * 
	 * @param display
	 *            {@link DisplayDriver} used to draw on display
	 * 
	 */
	public void renderContext(DisplayDriver display);

}
