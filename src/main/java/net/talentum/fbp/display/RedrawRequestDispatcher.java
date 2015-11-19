package net.talentum.fbp.display;

import java.util.ArrayList;
import java.util.List;

/**
 * Every {@link RedrawRequestDispatcher} can create and spread a redraw request
 * to notify observers ( {@link RedrawRequestHandler}s ) that updated content
 * can be provided by next redraw.
 * 
 * @author JJurM
 */
public abstract class RedrawRequestDispatcher {

	/**
	 * List of registered {@link RedrawRequestHandler}s.
	 */
	protected List<RedrawRequestHandler> handlers = new ArrayList<RedrawRequestHandler>();

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
	 * This will create redraw request and dispatch it across {@code holders}.
	 */
	protected void dispatchRedrawRequest() {
		for (RedrawRequestHandler handler : handlers) {
			handler.request();
		}
	}

}
