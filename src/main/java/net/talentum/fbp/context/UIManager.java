package net.talentum.fbp.context;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.talentum.fbp.display.RedrawRequestHandler;
import net.talentum.fbp.hardware.button.ButtonEvent;
import net.talentum.fbp.hardware.button.ButtonEventHandler;
import net.talentum.fbp.hardware.drivers.DisplayDriver;

/**
 * Class that is responsible for rendering content on display. It does not
 * directly draw to {@link DisplayDriver}, but uses {@link Context}s instead.
 * There is always exactly one active context renderer, which renders content on
 * display and receives {@link ButtonEvent}s delegated from this class.
 * 
 * {@link UIManager} is also {@link RedrawRequestHandler} to be able to pass
 * itself to {@link Context} as handler for the redraw requests.
 * 
 * @author JJurM
 */
public class UIManager implements ButtonEventHandler, RedrawRequestHandler, ContextHolder {
	private static final Logger LOG = LogManager.getLogger();

	private DisplayDriver displayDriver;

	private Context activeContext;

	/**
	 * Default constructor.
	 * 
	 * @param displayDriver
	 */
	public UIManager(DisplayDriver displayDriver) {
		this.displayDriver = displayDriver;
	}

	@Override
	public Context getContext() {
		return activeContext;
	}

	/**
	 * This method performs four simple steps:
	 * <ul>
	 * <li>deregisters itself (as handler) from currently active {@link Context}
	 * </li>
	 * <li>makes given context active</li>
	 * <li>registers itself as handler in newly active context</li>
	 * <li>redraws display</li>
	 * </ul>
	 * 
	 * @param context
	 */
	@Override
	public void switchContext(Context context) {
		LOG.debug("display: Switching context to " + context.getClass().getName());

		// deregister handler from old context
		if (activeContext != null)
			activeContext.removeRedrawRequestHandler(this);

		// make given context active
		activeContext = context;

		// register handler in new context
		activeContext.addRedrawRequestHandler(this);

		// redraw context
		activeContext.renderContext(displayDriver);
	}

	@Override
	public void buttonStateChanged(ButtonEvent event) {
		LOG.debug("display: Button event", event);

		// delegate all button events to active context
		activeContext.buttonStateChanged(event);
	}

	@Override
	public void request() {
		LOG.trace("display: Redraw request");

		// redraw active context
		activeContext.renderContext(displayDriver);
	}

}
