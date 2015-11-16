package net.talentum.fbp.display;

import net.talentum.fbp.hardware.ButtonEvent;
import net.talentum.fbp.hardware.ButtonEventHandler;
import net.talentum.fbp.hardware.drivers.DisplayDriver;

/**
 * Class that is responsible for rendering content on display. It does not
 * directly draw to {@link DisplayDriver}, but uses {@link DisplayContext}s
 * instead. There is always exactly one active context, which renders content on
 * display and receives {@link ButtonEvent}s delegated from DisplayUIManager.
 * 
 * DisplayUIManager is also {@link RedrawRequestHandler} to be able to pass
 * itself to {@link DisplayContext} as handler for the redraw requests.
 * 
 * @author JJurM
 */
public class DisplayUIManager implements ButtonEventHandler, RedrawRequestHandler, DisplayContextHolder {

	private DisplayDriver displayDriver;

	private DisplayContext activeDisplayContext;

	/**
	 * Default constructor.
	 * 
	 * @param displayDriver
	 */
	public DisplayUIManager(DisplayDriver displayDriver) {
		this.displayDriver = displayDriver;
	}
	
	@Override
	public DisplayContext getDisplayContext() {
		return activeDisplayContext;
	}

	/**
	 * This method performs four simple steps:
	 * <ul>
	 * <li>deregisters itself (as handler) from currently active
	 * {@link DisplayContext}</li>
	 * <li>makes given context active</li>
	 * <li>registers itself as handler in newly active context</li>
	 * <li>redraws display</li>
	 * </ul>
	 * 
	 * @param displayContext
	 */
	public void switchDisplayContext(DisplayContext displayContext) {
		// deregister handler from old context
		activeDisplayContext.removeRedrawRequestHandler(this);

		// make given context active
		activeDisplayContext = displayContext;

		// register handler in new context
		activeDisplayContext.addRedrawRequestHandler(this);

		// redraw context
		activeDisplayContext.redraw(displayDriver);
	}

	@Override
	public void buttonStateChanged(ButtonEvent event) {
		// delegate all button events to active context
		activeDisplayContext.buttonStateChanged(event);
	}

	@Override
	public void request() {
		activeDisplayContext.redraw(displayDriver);
	}

}
