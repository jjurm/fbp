package net.talentum.fbp.display;

import java.util.ArrayList;
import java.util.List;

import net.talentum.fbp.hardware.ButtonEvent;
import net.talentum.fbp.hardware.ButtonEventHandler;
import net.talentum.fbp.hardware.drivers.DisplayDriver;

/**
 * DisplayContext is class that can be rendered on a display. Every
 * DisplayContext can create and spread a redraw request to notify observers (
 * {@link RedrawRequestHandler}s ) that it can provide updated content by next
 * redraw. Can also receive {@link ButtonEvent}s.
 * 
 * @author JJurM
 */
public abstract class DisplayContext implements ButtonEventHandler {

	/**
	 * List of registered {@link RedrawRequestHandler}s.
	 */
	protected List<RedrawRequestHandler> handlers = new ArrayList<RedrawRequestHandler>();

	/**
	 * Reference to holder of {@link DisplayContext}, which should be called in
	 * order to switch active {@link DisplayContext}.
	 */
	public DisplayContextHolder displayContextHolder;

	/**
	 * Default constructor.
	 * 
	 * @param displayContextHolder
	 */
	public DisplayContext(DisplayContextHolder displayContextHolder) {
		this.displayContextHolder = displayContextHolder;
	}

	/**
	 * Registers new handler that will be notified upon creation of redraw
	 * request.
	 * 
	 * @param handler
	 *            handler to register
	 */
	public void addRedrawRequestHandler(RedrawRequestHandler handler) {
		handlers.add(handler);
	}

	/**
	 * Removes event handler previously registered with
	 * {@link #addRedrawRequestHandler(RedrawRequestHandler)}.
	 * 
	 * @param handler
	 *            handler to deregister
	 */
	public void removeRedrawRequestHandler(RedrawRequestHandler handler) {
		handlers.remove(handler);
	}

	/**
	 * This method must be overridden by every non-abstract class extending
	 * DisplayContext to redraw the display content.
	 * 
	 * @param displayDriver
	 *            {@link DisplayDriver} used to draw on display
	 */
	public abstract void redraw(DisplayDriver displayDriver);

}
