package net.talentum.fbp.display;

import net.talentum.fbp.context.Context;
import net.talentum.fbp.hardware.drivers.DisplayDriver;

/**
 * This class renders parts of the display (usually one element) and is usually
 * called by {@link Context}. The portion of display destined for this renderer
 * is specified by {@link DisplaySection} object.
 * 
 * @author JJurM
 */
@FunctionalInterface
public interface ElementRenderer {

	/**
	 * This method is responsible for rendering content on display.
	 * 
	 * @param displayDriver
	 *            {@link DisplayDriver} used to draw on display
	 */
	public void render(DisplaySection displaySection, DisplayDriver displayDriver);

}
