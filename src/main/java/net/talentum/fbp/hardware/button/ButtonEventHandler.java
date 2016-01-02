package net.talentum.fbp.hardware.button;

import java.util.concurrent.Future;

/**
 * Interface providing opportunity to receive {@link ButtonEvent}s.
 * 
 * @author JJurM
 */
public interface ButtonEventHandler {

	/**
	 * Method that is called whenever state of any observed button has changed.
	 * This is synchronized - returns after the event execution has been
	 * completed.
	 * 
	 * @param event
	 *            {@link ButtonEvent} with info about the event
	 */
	public void buttonStateChanged(ButtonEvent event);

	/**
	 * Should do the same as {@link #buttonStateChanged(ButtonEvent)}, but
	 * returns {@link Future} immediately, if the implementation supports
	 * asynchronous call. Returns {@code null} by default, but can be overridden
	 * to provide asynchronous functionality.
	 * 
	 * @param event
	 * @return
	 */
	public default Future<?> buttonStateChangedAsync(ButtonEvent event) {

		buttonStateChanged(event);
		return null;

	}

}
