package net.talentum.fbp.context;

import java.util.ArrayList;
import java.util.List;

import net.talentum.fbp.display.RedrawRequestHandler;
import net.talentum.fbp.hardware.ButtonEvent;
import net.talentum.fbp.hardware.ButtonEventHandler;
import net.talentum.fbp.hardware.drivers.DisplayDriver;

/**
 * This class is fully responsible for actions about specific context and
 * rendering the display (using method {@link #renderContext(DisplayDriver)}).
 * Every DisplayContext can create and spread a redraw request to notify
 * observers ( {@link RedrawRequestHandler}s ) that it can provide updated
 * content by next redraw. Can also receive {@link ButtonEvent}s.
 * 
 * @author JJurM
 */
public abstract class Context implements ButtonEventHandler {

	/**
	 * List of registered {@link RedrawRequestHandler}s.
	 */
	protected List<RedrawRequestHandler> handlers = new ArrayList<RedrawRequestHandler>();

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
	 * This will create redraw request and spread it across {@code holders}.
	 */
	protected void populateRedrawRequest() {
		for (RedrawRequestHandler handler : handlers) {
			handler.request();
		}
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
