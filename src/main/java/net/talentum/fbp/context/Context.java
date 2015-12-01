package net.talentum.fbp.context;

import net.talentum.fbp.display.RedrawRequestDispatcher;
import net.talentum.fbp.hardware.button.ButtonEvent;
import net.talentum.fbp.hardware.button.ButtonEventHandler;
import net.talentum.fbp.hardware.drivers.DisplayDriver;

/**
 * This class is fully responsible for actions about specific context and
 * rendering the display (using method {@link #renderContext(DisplayDriver)}).
 * Can receive {@link ButtonEvent}s.
 * 
 * @author JJurM
 */
public abstract class Context extends RedrawRequestDispatcher implements ButtonEventHandler {

	/**
	 * Reference to holder of {@link Context}, which should be called in order
	 * to switch active {@link Context}.
	 */
	public ContextHolder contextHolder;

	/**
	 * Default constructor.
	 * 
	 * @param contextHolder
	 */
	public Context(ContextHolder contextHolder) {
		this.contextHolder = contextHolder;
	}

	/**
	 * This method is responsible for rendering content on display.
	 * 
	 * @param displayDriver
	 *            {@link DisplayDriver} used to draw on display
	 * 
	 */
	public abstract void renderContext(DisplayDriver displayDriver);

}
