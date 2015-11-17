package net.talentum.fbp.display;

import net.talentum.fbp.hardware.drivers.DisplayDriver;

/**
 * This class renders parts of the display and is usually called by
 * {@link DisplayContextRenderer}.
 * 
 * @author JJurM
 */
public interface Renderer {

	/**
	 * This method is responsible for rendering content on display.
	 * 
	 * @param displayDriver
	 *            {@link DisplayDriver} used to draw on display
	 */
	public void render(DisplayDriver displayDriver);

}
