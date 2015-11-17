package net.talentum.fbp.display;

import java.util.ArrayList;
import java.util.List;

import net.talentum.fbp.hardware.ButtonEvent;
import net.talentum.fbp.hardware.ButtonEventHandler;
import net.talentum.fbp.hardware.drivers.DisplayDriver;

/**
 * This is class that is responsible for rendering the display (using method
 * {@link #render(DisplayDriver)}). Every DisplayContext can create and spread a
 * redraw request to notify observers ( {@link RedrawRequestHandler}s ) that it
 * can provide updated content by next redraw. Can also receive
 * {@link ButtonEvent}s.
 * 
 * @author JJurM
 */
public abstract class DisplayContextRenderer implements ButtonEventHandler, Renderer {

	/**
	 * List of registered {@link RedrawRequestHandler}s.
	 */
	protected List<RedrawRequestHandler> handlers = new ArrayList<RedrawRequestHandler>();

	/**
	 * Reference to holder of {@link DisplayContextRenderer}, which should be
	 * called in order to switch active {@link DisplayContextRenderer}.
	 */
	public DisplayContextRendererHolder rendererHolder;

	/**
	 * Default constructor.
	 * 
	 * @param rendererHolder
	 */
	public DisplayContextRenderer(DisplayContextRendererHolder rendererHolder) {
		this.rendererHolder = rendererHolder;
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

}
