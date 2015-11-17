package net.talentum.fbp.display;

import net.talentum.fbp.hardware.ButtonEvent;
import net.talentum.fbp.hardware.ButtonEventHandler;
import net.talentum.fbp.hardware.drivers.DisplayDriver;

/**
 * Class that is responsible for rendering content on display. It does not
 * directly draw to {@link DisplayDriver}, but uses
 * {@link DisplayContextRenderer}s instead. There is always exactly one active
 * context renderer, which renders content on display and receives
 * {@link ButtonEvent}s delegated from this class.
 * 
 * {@link DisplayUIManager} is also {@link RedrawRequestHandler} to be able to
 * pass itself to {@link DisplayContextRenderer} as handler for the redraw
 * requests.
 * 
 * @author JJurM
 */
public class DisplayUIManager implements ButtonEventHandler, RedrawRequestHandler, DisplayContextRendererHolder {

	private DisplayDriver displayDriver;

	private DisplayContextRenderer activeRenderer;

	/**
	 * Default constructor.
	 * 
	 * @param displayDriver
	 */
	public DisplayUIManager(DisplayDriver displayDriver) {
		this.displayDriver = displayDriver;
	}

	@Override
	public DisplayContextRenderer getDisplayContextRenderer() {
		return activeRenderer;
	}

	/**
	 * This method performs four simple steps:
	 * <ul>
	 * <li>deregisters itself (as handler) from currently active
	 * {@link DisplayContextRenderer}</li>
	 * <li>makes given context active</li>
	 * <li>registers itself as handler in newly active context</li>
	 * <li>redraws display</li>
	 * </ul>
	 * 
	 * @param displayContextRenderer
	 */
	@Override
	public void switchDisplayContextRenderer(DisplayContextRenderer displayContextRenderer) {

		// deregister handler from old context
		activeRenderer.removeRedrawRequestHandler(this);

		// make given context active
		activeRenderer = displayContextRenderer;

		// register handler in new context
		activeRenderer.addRedrawRequestHandler(this);

		// redraw context
		activeRenderer.render(displayDriver);
	}

	@Override
	public void buttonStateChanged(ButtonEvent event) {
		// delegate all button events to active context
		activeRenderer.buttonStateChanged(event);
	}

	@Override
	public void request() {
		activeRenderer.render(displayDriver);
	}

}
